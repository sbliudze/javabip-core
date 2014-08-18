package org.bip.executor;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.api.DataCoordinator;
import org.bip.engine.DataCoordinatorImpl;
import org.bip.exceptions.BIPException;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.ComponentA;
import org.bip.spec.ComponentB;
import org.bip.spec.ComponentC;
import org.bip.spec.Consumer;
import org.bip.spec.Feeder;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.TwoDataProvider1;
import org.bip.spec.TwoDataProvider2;
import org.bip.spec.TwoDataTaker;
import org.bip.spec.hanoi.HanoiOptimalMonitor;
import org.junit.Ignore;
import org.junit.Test;

public class DataTests {

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

	@Test
	public void bipHannoiWithDataTest() throws JAXBException, BIPException {

		int size = 8;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder()
				.build();

		bipGlue4Hanoi.toXML(System.out);

		HanoiOptimalMonitor hanoiMonitor = new HanoiOptimalMonitor(size);
		final ExecutorImpl hanoiExecutor = new ExecutorImpl("", hanoiMonitor, false);
		Thread t1 = new Thread(hanoiExecutor, "hanoiMonitor");

		org.bip.spec.hanoi.LeftHanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.LeftHanoiPeg(
				size);
		final ExecutorImpl lExecutor = new ExecutorImpl("", leftHanoiPeg, false);
		Thread t2 = new Thread(lExecutor, "LeftHanoiPeg");

		org.bip.spec.hanoi.MiddleHanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.MiddleHanoiPeg(
				size);
		final ExecutorImpl mExecutor = new ExecutorImpl("", middleHanoiPeg, false);
		Thread t3 = new Thread(mExecutor, "MiddleHanoiPeg");

		org.bip.spec.hanoi.RightHanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.RightHanoiPeg(
				size);
		final ExecutorImpl rExecutor = new ExecutorImpl("", rightHanoiPeg, false);
		Thread t4 = new Thread(rExecutor, "RightHanoiPeg");

		// BIP engine.

		DataCoordinator engine = new DataCoordinatorImpl(null);

		hanoiExecutor.setEngine(engine);
		lExecutor.setEngine(engine);
		mExecutor.setEngine(engine);
		rExecutor.setEngine(engine);

		mExecutor.register(engine);
		hanoiExecutor.register(engine);
		lExecutor.register(engine);
		rExecutor.register(engine);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		try {
			t1.start();
			t2.start();
			t3.start();
			t4.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
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

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());
	}

	// No asserts yet, just to see if the whole thing does not blow at
	// initialization time and due to first few cycles.
	@Test
	public void bipRandomLargerHannoiWithDataTest() throws JAXBException,
			BIPException {

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiRandomGlueBuilder()
				.build();

		bipGlue4Hanoi.toXML(System.out);

		org.bip.spec.hanoi.HanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, false);
		final ExecutorImpl lExecutor = new ExecutorImpl("", leftHanoiPeg, false);
		Thread t2 = new Thread(lExecutor, "LeftHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, true);
		final ExecutorImpl mExecutor = new ExecutorImpl("", middleHanoiPeg, false);
		Thread t3 = new Thread(mExecutor, "MiddleHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, true);
		final ExecutorImpl rExecutor = new ExecutorImpl("", rightHanoiPeg, false);
		Thread t4 = new Thread(rExecutor, "RightHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg rightMiddleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, true);
		final ExecutorImpl rMExecutor = new ExecutorImpl("", rightMiddleHanoiPeg,
				false);
		Thread t5 = new Thread(rMExecutor, "RightMiddleHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg leftMiddleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, false);
		final ExecutorImpl lMExecutor = new ExecutorImpl("", leftMiddleHanoiPeg,
				false);
		Thread t6 = new Thread(lMExecutor, "LeftMiddleHanoiPeg");

		// BIP engine.

		DataCoordinator engine = new DataCoordinatorImpl(null);

		lExecutor.setEngine(engine);
		mExecutor.setEngine(engine);
		rExecutor.setEngine(engine);
		lMExecutor.setEngine(engine);
		rMExecutor.setEngine(engine);

		mExecutor.register(engine);
		lExecutor.register(engine);
		rExecutor.register(engine);
		lMExecutor.register(engine);
		rMExecutor.register(engine);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		try {
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
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
	}

	// No asserts yet, just to see if the whole thing does not blow at
	// initialization time and due to first few cycles.
	@Test
	public void bipRandomHannoiWithDataTest() throws JAXBException,
			BIPException {

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiRandomGlueBuilder()
				.build();

		bipGlue4Hanoi.toXML(System.out);

		org.bip.spec.hanoi.HanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, false);
		final ExecutorImpl lExecutor = new ExecutorImpl("", leftHanoiPeg, false);
		Thread t2 = new Thread(lExecutor, "LeftHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, true);
		final ExecutorImpl mExecutor = new ExecutorImpl("", middleHanoiPeg, false);
		Thread t3 = new Thread(mExecutor, "MiddleHanoiPeg");

		org.bip.spec.hanoi.HanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.HanoiPeg(
				size, true);
		final ExecutorImpl rExecutor = new ExecutorImpl("", rightHanoiPeg, false);
		Thread t4 = new Thread(rExecutor, "RightHanoiPeg");

		// BIP engine.

		DataCoordinator engine = new DataCoordinatorImpl(null);

		lExecutor.setEngine(engine);
		mExecutor.setEngine(engine);
		rExecutor.setEngine(engine);

		mExecutor.register(engine);
		lExecutor.register(engine);
		rExecutor.register(engine);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		try {
			t2.start();
			t3.start();
			t4.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.execute();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// assertEquals( (int) Math.pow(2, size) - 1,
		// hanoiMonitor.getNumberOfMoves() );
	}

	@Test
	public void bipDataTransferTest() throws BIPException {

		// BIPGlue bipGlue =
		// createGlue("src/test/resources/bipGlueExecutableBehaviourDataTransfers.xml");

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
		
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(
				"1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor, true);

		DataCoordinator engine = new DataCoordinatorImpl(null);


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

		RouteBuilder builder = new RouteBuilder() {

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
		camelContext.setAutoStartup(false);
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		route1.setCamelContext(camelContext);
		route2.setCamelContext(camelContext);
		route3.setCamelContext(camelContext);

		Thread t1 = new Thread(executor1, "SW1");
		Thread t2 = new Thread(executor2, "SW2");
		Thread t3 = new Thread(executor3, "SW3");
		Thread tM = new Thread(executorM, "M");

		executor1.setEngine(engine);
		executor2.setEngine(engine);
		executor3.setEngine(engine);
		executorM.setEngine(engine);
		executorM.register(engine);
		executor1.register(engine);

		executor2.register(engine);
		executor3.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		assertEquals("The state is not appropriate", "off",
				executor1.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor2.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor3.getCurrentState());
		assertEquals("The state is not appropriate", "one",
				executorM.getCurrentState());
		try {
			t2.start();
			t3.start();
			t1.start();
			tM.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
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
	}

	@Test
	public void bipDataTransferFromFileTest() throws BIPException {

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueExecutableBehaviourDataTransfers.xml");
		bipGlue.toXML(System.out);

		// BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
		// @Override
		// public void configure() {
		//
		// synchron(SwitchableRouteDataTransfers.class, "on").to(
		// MemoryMonitor.class, "add");
		// synchron(SwitchableRouteDataTransfers.class, "finished").to(
		// MemoryMonitor.class, "rm");
		// port(SwitchableRouteDataTransfers.class, "off")
		// .acceptsNothing();
		// port(SwitchableRouteDataTransfers.class, "off")
		// .requiresNothing();
		// data(SwitchableRouteDataTransfers.class,
		// "deltaMemoryOnTransition").to(MemoryMonitor.class,
		// "memoryUsage");
		//
		// }
		//
		// }.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);
		
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(
				"1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor, true);

		DataCoordinator engine = new DataCoordinatorImpl(null);



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

		route1.setCamelContext(camelContext);
		route2.setCamelContext(camelContext);
		route3.setCamelContext(camelContext);

		Thread t1 = new Thread(executor1, "SW1");
		Thread t2 = new Thread(executor2, "SW2");
		Thread t3 = new Thread(executor3, "SW3");
		Thread tM = new Thread(executorM, "M");

		executor1.setEngine(engine);
		executor2.setEngine(engine);
		executor3.setEngine(engine);
		executorM.setEngine(engine);
		executorM.register(engine);
		executor1.register(engine);

		executor2.register(engine);
		executor3.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		assertEquals("The state is not appropriate", "off",
				executor1.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor2.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor3.getCurrentState());
		assertEquals("The state is not appropriate", "one",
				executorM.getCurrentState());
		try {
			t2.start();
			t3.start();
			t1.start();
			tM.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
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
	}

	@Test
	public void bipDataFeederConsumerTest() throws BIPException {

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueFeederConsumer.xml");

		Feeder feeder = new Feeder();
		final ExecutorImpl executorF = new ExecutorImpl("", feeder, true);
		Consumer consumer = new Consumer(350);
		final ExecutorImpl executorC = new ExecutorImpl("", consumer, true);

		DataCoordinator engine = new DataCoordinatorImpl(null);

		Thread tF = new Thread(executorF, "Feeder");
		Thread tC = new Thread(executorC, "Consumer");

		executorF.setEngine(engine);
		executorC.setEngine(engine);
		executorF.register(engine);
		executorC.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			tF.start();
			tC.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// No asserts yet, just to see if the whole thing does not blow at
	// initialization time and due to first few cycles.
	@Test
	public void bipDataAvailabilityTest() throws BIPException {
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueDataAvailability.xml");

		ComponentA componentA = new ComponentA(250);
		final ExecutorImpl executorA = new ExecutorImpl("", componentA, true);
		ComponentB componentB = new ComponentB();
		final ExecutorImpl executorB = new ExecutorImpl("", componentB, true);
		ComponentC componentC = new ComponentC();
		final ExecutorImpl executorC = new ExecutorImpl("", componentC, true);

		DataCoordinator engine = new DataCoordinatorImpl(null);

		Thread tA = new Thread(executorA, "ComponentA");
		Thread tB = new Thread(executorB, "ComponentB");
		Thread tC = new Thread(executorC, "ComponentC");

		executorA.setEngine(engine);
		executorB.setEngine(engine);
		executorC.setEngine(engine);
		executorA.register(engine);
		executorB.register(engine);
		executorC.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			tA.start();
			tB.start();
			tC.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void bipTwoDataTest() throws BIPException {
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueTwoData.xml");

		TwoDataTaker componentA = new TwoDataTaker(100);
		final ExecutorImpl executorA = new ExecutorImpl("", componentA, true);
		TwoDataProvider1 componentB = new TwoDataProvider1();
		final ExecutorImpl executorB = new ExecutorImpl("", componentB, true);
		TwoDataProvider2 componentC = new TwoDataProvider2();
		final ExecutorImpl executorC = new ExecutorImpl("", componentC, true);

		DataCoordinator engine = new DataCoordinatorImpl(null);

		Thread tA = new Thread(executorA, "ComponentA");
		Thread tB = new Thread(executorB, "ComponentB");
		Thread tC = new Thread(executorC, "ComponentC");

		executorA.setEngine(engine);
		executorB.setEngine(engine);
		executorC.setEngine(engine);
		executorA.register(engine);
		executorB.register(engine);
		executorC.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		try {
			tA.start();
			tB.start();
			tC.start();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void bipMultipleSwRTransferTest() throws BIPException, IOException {
		int size = 64;
		int memoryMonitorLimit = 3250;
		int sleepTime = 12000;
		
//		FileInputStream inStream = new FileInputStream(
//				"src/test/resources/classheader.txt");

		FileOutputStream outStream = new FileOutputStream(
				"src/test/java/org/bip/executor/ManyDataRoutesTests"+size+".java");
//		copyLarge(inStream, outStream);
//		inStream.close();
//		
		String testName = "@Test\n	public void bipSwMultiTest"+size+"()";
		outStream.write(testName.getBytes());
		
		FileInputStream inStream = new FileInputStream(
				"src/test/resources/header.txt");
		copyLarge(inStream, outStream);
		inStream.close();
		
	     String monitorString = " MemoryMonitor routeOnOffMonitor = new MemoryMonitor("+memoryMonitorLimit+");\n"+
			"final Executor executorM = factory.create(engine, routeOnOffMonitor, \"monitor\", true);\n";
			//"Thread tM = new Thread(executorM, \"M\");\n"+
			//"executorM.setEngine(engine);\n"+
			//"executorM.register(engine);\n";
	 	outStream.write(monitorString.getBytes());
		
		String replaceString = "";
		for (int i = 1; i <= size; i++) {

			replaceString=String.valueOf(i);
			
			String componentStr = "	SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(\"1\", camelContext);\n"+
			"final Executor executor1 = factory.create(engine, route1, \"1\", true);\n\n";
			outStream.write(componentStr.replace("1", replaceString).getBytes());

			String routePolicyStr = "		final RoutePolicy routePolicy1 = new RoutePolicy() {public void onInit(Route route) {}\n	public void onExchangeDone(Route route, Exchange exchange) {executor1.inform(\"end\");}\n\n	public void onExchangeBegin(Route route, Exchange exchange) {}\n	public void onRemove(Route arg0) {}\n	@Override	public void onResume(Route arg0) {}\n @Override public void onStart(Route arg0) {}\n			@Override	public void onStop(Route arg0) {}\n	@Override	public void onSuspend(Route arg0) {}\n		};";
			outStream.write(routePolicyStr.replace("1", replaceString).getBytes());

		}
		String routeBuilderStr = "\nRouteBuilder builder = new RouteBuilder() \n{@Override	public void configure() throws Exception {";
		outStream.write(routeBuilderStr.getBytes());
		
		replaceString = "";
		for (int i = 1; i <= size; i++) {
			replaceString=String.valueOf(i);
			String routeBuilder = "from(\"file:inputfolder1?delete=true\").routeId(\"1\").routePolicy(routePolicy1).process(new Processor() {\n	public void process(Exchange exchange) throws Exception {}}).to(\"file:outputfolder1\");\n\n";

			outStream.write(routeBuilder.replace("1", replaceString)
					.getBytes());
		}
		
		String camelContext = "		}};\n try { camelContext.addRoutes(builder);	camelContext.start(); } catch (Exception e){e.printStackTrace();}\n";
		outStream.write(camelContext.getBytes());

		replaceString = "";
		for (int i = 1; i <= size; i++) {
			replaceString=String.valueOf(i);
			String setup = "		route1.setCamelContext(camelContext);\n";
				//	+ "Thread t1 = new Thread(executor1, \"SW1\");\n"
				//	+ "executor1.setEngine(engine);\n"
				//	+ "executor1.register(engine);\n";
			outStream.write(setup.replace("1", replaceString).getBytes());
		}
		
		String engineSetup = "	engine.specifyGlue(bipGlue);\n"+"		engine.start();\n";//+"try { tM.start();";
		outStream.write(engineSetup.getBytes());
		
//		replaceString = "";
//		for (int i = 1; i <= size; i++) {
//			replaceString=String.valueOf(i);
//			String threadStart = "t1.start();\n";
//			outStream.write(threadStart.replace("1", replaceString).getBytes());
//		}
		
		String threadrun  = //"}\n catch (IllegalArgumentException e) {e.printStackTrace();} catch (SecurityException e) {	e.printStackTrace();}\n"+
		"engine.execute();";
		outStream.write(threadrun.getBytes());
		
		String threadSleep  ="Thread.sleep("+ sleepTime+");";
		outStream.write(threadSleep.getBytes());
		String endStr = "}";
		outStream.write(endStr.getBytes());
		
		outStream.close();
	}

	public static long copyLarge(InputStream input, OutputStream output)
		       throws IOException {
		   byte[] buffer = new byte[100000000];
		   long count = 0;
		   int n = 0;
		   while (-1 != (n = input.read(buffer))) {
		       output.write(buffer, 0, n);
		       count += n;
		   }
		   return count;
		}

}