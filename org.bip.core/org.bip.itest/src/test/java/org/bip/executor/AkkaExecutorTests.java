package org.bip.executor;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
import org.bip.spec.ComponentB;
import org.bip.spec.Consumer;
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
import org.bip.spec.hanoi.HanoiOptimalMonitor;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaExecutorTests {

	@Test
	public void akkaExecutorSimpleTest() {

		HanoiMonitor hanoiMonitor = new HanoiMonitor(3);

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);

		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl();

		Executor kernel = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		assertEquals("Actor Typed actor is not working properly", "hanoiMonitor", kernel.getId());

	}

	@Test
	public void bipDataTransferTest() throws BIPException {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(
				system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(
						MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(
						MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off")
						.acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off")
						.requiresNothing();
				data(SwitchableRouteDataTransfers.class,
						"deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(
				"1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);



		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final RoutePolicy routePolicy1 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor1.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

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

		final RoutePolicy routePolicy2 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor2.inform("end");
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

		final RoutePolicy routePolicy3 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor3.inform("end");
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

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						.routePolicy(routePolicy1).process(new Processor() {

							public void process(Exchange exchange)
									throws Exception {

							}
						}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {

							public void process(Exchange exchange)
									throws Exception {

							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).to("file:outputfolder3");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}





		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final Executor executorM = factory.create(engine, routeOnOffMonitor,
				"monitor", true);


		//
		// executorM.register(engine);
		// executor1.register(engine);
		//
		// executor2.register(engine);
		// executor3.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		// assertEquals("The state is not appropriate", "off",
		// executor1.getCurrentState());
		// assertEquals("The state is not appropriate", "off",
		// executor2.getCurrentState());
		// assertEquals("The state is not appropriate", "off",
		// executor3.getCurrentState());
		// assertEquals("The state is not appropriate", "one",
		// executorM.getCurrentState());

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}
	
	@Test
	public void bipHannoiWithDataTest() throws JAXBException, BIPException {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder()
				.build();

		// bipGlue4Hanoi.toXML(System.out);

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
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);
		engine.stop();
	}
	
	@Test
	public void akkaExecutorHannoiwithActorEngineTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new BIPCoordinatorImpl());


		int size = 3;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		// bipGlue4Hanoi.toXML(System.out);

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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);
		engine.stop();

	}	



	@Test
	public void akkaExecutorHannoiTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);


		int size = 8;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		// bipGlue4Hanoi.toXML(System.out);

		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl();

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

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);
		engine.stop();

	}	

	
	// TODO, IMPROVEMENT When BIP protocol is optimized and wait no longer is used then enable this test.
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

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory executorFactory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		PSSComponent pssComponent = new PSSComponent(true);

		Executor pssExecutor = executorFactory.create(engine, pssComponent, "pssCompE", true);
		
		ComponentB bComponent = new ComponentB();
		
		Executor bExecutor = executorFactory.create(engine, bComponent, "bCompE", true);
		
		Consumer cComponent = new Consumer(100);
		Executor cExecutor = executorFactory.create(engine, cComponent, "cCompE", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Spontaneous wait on one component has blocked all the components", bComponent.counterA > 0, true);

		boolean destroyed = executorFactory.destroy(pssExecutor);
		destroyed &= executorFactory.destroy(bExecutor);
		destroyed &= executorFactory.destroy(cExecutor);

		engine.stop();

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);

	}

	@Test
	public void ServersTest() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		InitialServer server1 = new InitialServer(1);

		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor1 = factory.create(engine, server1, "1", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeerTest()
	{		
				

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(
				system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));
		
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
		engine.execute();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

}