package org.bip.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.BIPCoordinator;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.ComponentA;
import org.bip.spec.ComponentB;
import org.bip.spec.ComponentC;
import org.bip.spec.Consumer;
import org.bip.spec.Feeder;
import org.bip.spec.HanoiGlueBuilder;
import org.bip.spec.HanoiMonitor;
import org.bip.spec.InitialServer;
import org.bip.spec.LeftHanoiPeg;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.MiddleHanoiPeg;
import org.bip.spec.PSSComponent;
import org.bip.spec.Peer;
import org.bip.spec.RightHanoiPeg;
import org.bip.spec.Server;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.Tracker;
import org.bip.spec.TwoDataProvider1;
import org.bip.spec.TwoDataProvider2;
import org.bip.spec.TwoDataTaker;
import org.bip.spec.diningphilosophers.DiningPhilosophersGlueBuilder;
import org.bip.spec.diningphilosophers.Fork;
import org.bip.spec.diningphilosophers.Philosophers;
import org.bip.spec.hanoi.HanoiOptimalMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaExecutorTests {

	ActorSystem system;
	OrchestratedExecutorFactory factory;
	EngineFactory engineFactory;
	
	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem");
		factory = new OrchestratedExecutorFactory(system);
		engineFactory = new EngineFactory(system);

	}
	
	@After
	public void cleanup() {
		
		system.shutdown();
		
	}
	
	@Test
	public void akkaExecutorSimpleTest() {

		// Create BIP engine.
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		// Create BIP Spec. 
		HanoiMonitor hanoiMonitor = new HanoiMonitor(3);
		
		// Create executor for hanoiMonitor spec. 
		Executor executor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);
		
		assertEquals("Actor Typed actor is not working properly", "hanoiMonitor", executor.getId());
		
		boolean destroyed = factory.destroy(executor);
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	@Test
	public void bipDataTransferTest() throws BIPException {
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off")	.requiresNothing();
				
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class, "memoryUsage");

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);
		final Executor executor2 = factory.create(engine, route2, "2", true);
		final Executor executor3 = factory.create(engine, route3, "3", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);

		RouteBuilder builder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						.routePolicy(routePolicy1).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).to("file:outputfolder3");
			}
			
		};
		
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final Executor executorM = factory.create(engine, routeOnOffMonitor,
				"monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.execute();
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		boolean destroyed = factory.destroy(executor1);
		destroyed &= factory.destroy(executor2);
		destroyed &= factory.destroy(executor3);
		destroyed &= factory.destroy(executorM);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);
		
		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);

	}
	
	// No asserts yet, just to see if the whole thing does not blow at
	// initialization time and due to first few cycles.
	@Test
	public void bipRandomLargerHannoiWithDataTest() throws JAXBException,
			BIPException {

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiRandomGlueBuilder()
				.build();

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		org.bip.spec.hanoi.HanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, false);
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "LeftHanoiPeg", false);
		
		org.bip.spec.hanoi.HanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, true);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "MiddleHanoiPeg", false);
		
		org.bip.spec.hanoi.HanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, true);
		Executor rExecutor = factory.create(engine, middleHanoiPeg, "RightHanoiPeg", false);
				
		org.bip.spec.hanoi.HanoiPeg rightMiddleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, true);
		Executor rMExecutor = factory.create(engine, rightMiddleHanoiPeg, "RightMiddleHanoiPeg", false);
		
		org.bip.spec.hanoi.HanoiPeg leftMiddleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, false);
		Executor lMExecutor = factory.create(engine, leftMiddleHanoiPeg, "LeftMiddleHanoiPeg", false);
		
		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean destroyed = factory.destroy(rMExecutor);
		destroyed &= factory.destroy(lMExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
		
		int noOfAllTransitions = leftHanoiPeg.noOfTransitions + rightHanoiPeg.noOfTransitions + leftMiddleHanoiPeg.noOfTransitions + 
								 rightMiddleHanoiPeg.noOfTransitions + middleHanoiPeg.noOfTransitions;

		assertTrue("Hanoi tower have seen progress of executing transitions", noOfAllTransitions > 0);
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}


	// No asserts yet, just to see if the whole thing does not blow at
	// initialization time and due to first few cycles.
	@Test
	public void bipRandomHannoiWithDataTest() throws JAXBException,
			BIPException {

		int size = 3;

		// BIP engine.
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));
		
		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiRandomGlueBuilder()
				.build();

		org.bip.spec.hanoi.HanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, false);
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "LeftHanoiPeg", false);

		org.bip.spec.hanoi.HanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, true);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "MiddleHanoiPeg", false);
		
		org.bip.spec.hanoi.HanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(size, true);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "RightHanoiPeg", false);
		
		engine.specifyGlue(bipGlue4Hanoi);
		
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean destroyed = factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		
		engine.stop();

		int noOfAllTransitions = leftHanoiPeg.noOfTransitions + rightHanoiPeg.noOfTransitions + middleHanoiPeg.noOfTransitions;

		assertTrue("Hanoi tower have seen progress of executing transitions", noOfAllTransitions > 0);
		assertTrue("Number of component transition should sum up to an even number", noOfAllTransitions % 2 == 0);
				
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	
	@Test
	public void bipHannoiWithDataTestSize3() throws JAXBException, BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder()
				.build();

		HanoiOptimalMonitor hanoiMonitor = new HanoiOptimalMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		org.bip.spec.hanoi.LeftHanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.LeftHanoiPeg(
				size);
		
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "LeftHanoiPeg", false);

		org.bip.spec.hanoi.MiddleHanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.MiddleHanoiPeg(
				size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "MiddleHanoiPeg", false);


		org.bip.spec.hanoi.RightHanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.RightHanoiPeg(
				size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "RightHanoiPeg", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Finished test, number of transitions " + hanoiMonitor.getNumberOfMoves());
		System.out.flush();
		
		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();

		assertEquals("Hanoi tower has not reached its final state ", (int) Math.pow(2, size) - 1, hanoiMonitor.getNumberOfMoves());
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	@Test
	public void bipHannoiWithDataTestSize8() throws JAXBException, BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		int size = 8;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder()
				.build();

		HanoiOptimalMonitor hanoiMonitor = new HanoiOptimalMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		org.bip.spec.hanoi.LeftHanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.LeftHanoiPeg(
				size);
		
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "LeftHanoiPeg", false);

		org.bip.spec.hanoi.MiddleHanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.MiddleHanoiPeg(
				size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "MiddleHanoiPeg", false);


		org.bip.spec.hanoi.RightHanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.RightHanoiPeg(
				size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "RightHanoiPeg", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Finished test, number of transitions " + hanoiMonitor.getNumberOfMoves());
		System.out.flush();
		
		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();

		assertEquals("Hanoi tower has not reached its final state ", (int) Math.pow(2, size) - 1, hanoiMonitor.getNumberOfMoves());
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	// It does not use data transfers but plenty of interactions and more ports.
	@Test
	public void akkaExecutorHannoiNoDataTransferswithActorEngineTest() {

		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		int size = 3;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		HanoiMonitor hanoiMonitor = new HanoiMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		LeftHanoiPeg leftHanoiPeg = new LeftHanoiPeg(size);
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "leftHanoi", false); 

		MiddleHanoiPeg middleHanoiPeg = new MiddleHanoiPeg(size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "middleHanoi", false);

		RightHanoiPeg rightHanoiPeg = new RightHanoiPeg(size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "rightHanoi", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Finished test, number of transitions " + hanoiMonitor.getNumberOfMoves());
		System.out.flush();
		
		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
			
		assertEquals("Hanoi tower has not reached its final state ", (int) Math.pow(2, size) - 1, hanoiMonitor.getNumberOfMoves());

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);

	}	


	@Test
	public void akkaExecutorHannoiTest() {

		int size = 8;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		// TODO Discussion, here we do not use TypedActor to create an engine. Thus the BIP engine is not protected 
		// from multiple-thread calls against its functions. BIP engine is not guaranteed to be multiple-thread safe right?, so we should
		// remove this test as there is a similar one that does hanoi testing without data transfers.
		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl(system);

		HanoiMonitor hanoiMonitor = new HanoiMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		LeftHanoiPeg leftHanoiPeg = new LeftHanoiPeg(size);
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "leftHanoi", false); 

		MiddleHanoiPeg middleHanoiPeg = new MiddleHanoiPeg(size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "middleHanoi", false);

		RightHanoiPeg rightHanoiPeg = new RightHanoiPeg(size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "rightHanoi", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
		
		assertEquals("Hanoi tower has not reached its final state ", (int) Math.pow(2, size) - 1, hanoiMonitor.getNumberOfMoves());
		
		assertEquals("Some actors where not destroyed succesfully", true, destroyed);


	}	

	
	// TODO, IMPROVEMENT When BIP protocol is optimized and wait no longer is used then enable this test.
	// Currently, this test will fail as spontaneous wait on one component blocks BIP engine cycle.
	@Test
	@Ignore
	public void testNoSpontaneousWaitBlockEverything() throws BIPException {

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PSSComponent.class, "p").requires(ComponentB.class, "b");

				port(PSSComponent.class, "p").accepts(ComponentB.class, "b");

				port(ComponentB.class, "a").requiresNothing();
				port(ComponentB.class, "b").requires(PSSComponent.class, "p");
				
				// Just to keep the DataCoordinator happy to have something even if this BIP component is not instantiated.
				data(ComponentB.class, "memoryY").to(Consumer.class, "memoryUsage");

			}

		}.build();

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		PSSComponent pssComponent = new PSSComponent(true);

		Executor pssExecutor = factory.create(engine, pssComponent, "pssCompE", true);
		
		ComponentB bComponent = new ComponentB();
		
		Executor bExecutor = factory.create(engine, bComponent, "bCompE", true);
		
		Consumer cComponent = new Consumer(100);
		Executor cExecutor = factory.create(engine, cComponent, "cCompE", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.execute();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(pssExecutor);
		destroyed &= factory.destroy(bExecutor);
		destroyed &= factory.destroy(cExecutor);

		engine.stop();

		assertEquals("Spontaneous wait on one component has blocked all the components", bComponent.counterA > 0, true);
		
		assertEquals("Some actors where not destroyed succesfully", true, destroyed);

	}

	@Test
	public void ServersTest() {
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		// TODO, PLEASE use BIP glue builders and not XML file.
		// TODO, BUG? BTW, BIP glue is not using class InitialServer only Server.
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");

		// TODO, Why both Classes use the same specType org.bip.spec.Server? Would not this cause huge problems on engine side?
		// or at least the second attempt to define type of the spec will be ignored? and thus all the components will actually have 
		// the same behavior associated?
		InitialServer server1 = new InitialServer(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);

		final Executor executor1 = factory.create(engine, server1, "1", true);		
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);


		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean destroyed = factory.destroy(executor1);
		destroyed &= factory.destroy(executor2);
		destroyed &= factory.destroy(executor3);

		engine.stop();

		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	@Test
	public void TrackerPeerTest()
	{		
				
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));
		
		// TODO, PLEASE use BIP glue builders and not XML file.
		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		
		Tracker tracker1 = new Tracker(1);
		Peer peer1a = new Peer(11);
		Peer peer1b = new Peer(12);
		Tracker tracker2 = new Tracker(2);
		Peer peer2a = new Peer(21);
		Peer peer2b = new Peer(22);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor1a = factory.create(engine, peer1a, "11", true);
		final Executor executor1b = factory.create(engine, peer1b, "12", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor2a = factory.create(engine, peer2a, "21", true);
		final Executor executor2b = factory.create(engine, peer2b, "22", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.execute();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean destroyed = factory.destroy(executor1);
		destroyed &= factory.destroy(executor1a);
		destroyed &= factory.destroy(executor1b);
		destroyed &= factory.destroy(executor2);
		destroyed &= factory.destroy(executor2a);
		destroyed &= factory.destroy(executor2b);
		
		engine.stop();
			
		assertTrue("Tracker 1 has not made any transitions", tracker1.noOfTransitions > 0);
		assertTrue("Tracker 2 has not made any transitions", tracker2.noOfTransitions > 0);
		
		assertTrue("Peer 1a has not made any transitions", peer1a.noOfTransitions > 0);
		assertTrue("Peer 2a has not made any transitions", peer2a.noOfTransitions > 0);
		assertTrue("Peer 1b has not made any transitions", peer1b.noOfTransitions > 0);
		assertTrue("Peer 2b has not made any transitions", peer2b.noOfTransitions > 0);
		assertEquals("Not all BIP actors were terminated.", destroyed, true);
		
}
	
	@Test
	public void bipDataFeederConsumerTest() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueFeederConsumer.xml");

		Feeder feeder = new Feeder();
		final Executor executorF = factory.create(engine, feeder, "feeder", true);
		
		Consumer consumer = new Consumer(350);
		final Executor executorC = factory.create(engine, consumer, "consumer", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(executorF);
		destroyed &= factory.destroy(executorC);
		
		engine.stop();
			
		assertTrue("Feeder has not made any transitions", feeder.noOfTransitions > 0);
		assertTrue("Consumer has not made any transitions", consumer.noOfTransitions > 0);
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

	}

	@Test
	public void bipDataAvailabilityTest() throws BIPException {
		
		BIPEngine engine = engineFactory.create("myEngine",
 new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueDataAvailability.xml");

		ComponentA componentA = new ComponentA(250);
		ComponentB componentB = new ComponentB();
		ComponentC componentC = new ComponentC();
		
		final Executor executorA = factory.create(engine, componentA, "compA", true);

		final Executor executorB = factory.create(engine, componentB, "compB", true);

		final Executor executorC = factory.create(engine, componentC, "compC", true);


		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(executorA);
		destroyed &= factory.destroy(executorB);
		destroyed &= factory.destroy(executorC);
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

		assertTrue("CompA has not made any transitions", componentA.noOfTransitions > 0);
		assertTrue("CompB has not made any transitions", componentB.noOfTransitions > 0);
		assertTrue("CompC has not made any transitions", componentC.noOfTransitions > 0);
		
	}
	
	
	@Test
	public void akkaExecutorPhilosopherwithDataTest() {

		BIPEngine engine = engineFactory.create("myEngine",
 new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue4Philosophers = new DiningPhilosophersGlueBuilder().build();

		Fork f1 = new Fork(1);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(3);
		
		Philosophers p1 = new Philosophers(1, 2, true);
		Philosophers p2 = new Philosophers(2, 3, true);
		Philosophers p3 = new Philosophers(3, 1, true);
		
		
		Executor f1E = factory.create(engine, f1, "f1E", true);
		Executor f2E = factory.create(engine, f2, "f2E", true);
		Executor f3E = factory.create(engine, f3, "f3E", true);
		
		Executor p1E = factory.create(engine, p1, "p1E", true);
		Executor p2E = factory.create(engine, p2, "p2E", true);
		Executor p3E = factory.create(engine, p3, "p3E", true);

		engine.specifyGlue(bipGlue4Philosophers);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int noOfTimesUsed = f1.noOfTimesUsed() + f2.noOfTimesUsed() + f3.noOfTimesUsed();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int noOfTimesUsed2 = f1.noOfTimesUsed() + f2.noOfTimesUsed() + f3.noOfTimesUsed();

		boolean destroyed = factory.destroy(f1E);
		destroyed &= factory.destroy(f2E);
		destroyed &= factory.destroy(f3E);
		destroyed &= factory.destroy(p1E);
		destroyed &= factory.destroy(p2E);
		destroyed &= factory.destroy(p3E);

		engine.stop();
		
		assertEquals("BIP engine could not progress the system.", true,	noOfTimesUsed2 > noOfTimesUsed);
		
		assertEquals("Some actors where not destroyed succesfully", true, destroyed);

	}	
	
	// TODO, This test is hitting the limitation of BIP engine concerning multiple transfers.
	// TODO, Explain exactly what is the limitation.
	@Test
	@Ignore
	public void bipTwoDataTest() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine",
 new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueTwoData.xml");

		TwoDataTaker componentA = new TwoDataTaker(100);
		TwoDataProvider1 componentB = new TwoDataProvider1();
		TwoDataProvider2 componentC = new TwoDataProvider2();

		final Executor executorA = factory.create(engine, componentA, "compA", true);
		final Executor executorB = factory.create(engine, componentB, "compB", true);
		final Executor executorC = factory.create(engine, componentC, "compC", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(executorA);
		destroyed &= factory.destroy(executorB);
		destroyed &= factory.destroy(executorC);
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

		assertTrue("CompA has not made any transitions", componentA.noOfTransitions > 0);
		assertTrue("CompB has not made any transitions", componentB.noOfTransitions > 0);
		assertTrue("CompC has not made any transitions", componentC.noOfTransitions > 0);

	}
	
	private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;
	}
	
	private RoutePolicy createRoutePolicy(final Executor executor) {
		
		return  new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			@Override
			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};

	}

}