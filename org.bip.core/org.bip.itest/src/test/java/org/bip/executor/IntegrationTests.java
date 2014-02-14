package org.bip.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPGlue;
import org.bip.api.Port;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.api.BIPCoordinator;
import org.bip.exceptions.BIPException;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.impl.PortImpl;
import org.bip.spec.HanoiGlueBuilder;
import org.bip.spec.HanoiMonitor;
import org.bip.spec.LeftHanoiPeg;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.MiddleHanoiPeg;
import org.bip.spec.PComponent;
import org.bip.spec.PResizableBehaviorComponent;
import org.bip.spec.PSSComponent;
import org.bip.spec.QComponent;
import org.bip.spec.RComponent;
import org.bip.spec.RightHanoiPeg;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRoute;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.SwitchableRouteExecutableBehavior;
import org.bip.spec.TestSpecEnforceableSpontaneous;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTests {

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
	public void bipGlueTest() {
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		assertEquals("The number of accept constraints is not appropriate", 5,
				bipGlue.getAcceptConstraints().size());
		assertEquals("The number of require constraints is not appropriate", 5,
				bipGlue.getRequiresConstraints().size());
	}

	@Test
	public void test() throws BIPException {

		// get Glue object from xml file
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlue.xml");

		SwitchableRoute route1 = new SwitchableRoute("1");
		SwitchableRoute route2 = new SwitchableRoute("2");
		SwitchableRoute route3 = new SwitchableRoute("3");
		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);

		final ExecutorImpl executor1 = new ExecutorImpl(route1, true);
		final ExecutorImpl executor2 = new ExecutorImpl(route2, true);
		final ExecutorImpl executor3 = new ExecutorImpl(route3, true);

		final ExecutorImpl executorM = new ExecutorImpl(routeOnOffMonitor, true);

		BIPCoordinator engine = new BIPCoordinatorImpl();

		CamelContext camelContext = new DefaultCamelContext();
		final RoutePolicy routePolicy1 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor1.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
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
		};
		final RoutePolicy routePolicy3 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor3.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
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
		camelContext.setAutoStartup(false);
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
		executor1.register(engine);
		t1.start();
		executor2.register(engine);
		executor3.register(engine);
		executorM.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		assertEquals("The state is not appropriate", "off",
				executor1.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor2.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor3.getCurrentState());
		assertEquals("The state is not appropriate", "0",
				executorM.getCurrentState());
		try {
			t2.start();
			t3.start();
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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testHashCodePort() {
		Port portA = new PortImpl("p", "enforceable", SwitchableRoute.class);
		Port portB = new PortImpl("p", "enforceable", SwitchableRoute.class);
		Port portC = new PortImpl("p", "enforceable", SwitchableRoute.class);
		Port portD = new PortImpl("p", "enforceable", SwitchableRoute.class);

		assertEquals(portA.hashCode(), portB.hashCode());
		assertEquals(portC.hashCode(), portD.hashCode());
	}

	@Test
	public void testBehaviourBuilding() throws BIPException {

		// get Glue object from xml file
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");

		SwitchableRouteExecutableBehavior route1 = new SwitchableRouteExecutableBehavior(
				"1");
		SwitchableRouteExecutableBehavior route2 = new SwitchableRouteExecutableBehavior(
				"2");
		SwitchableRouteExecutableBehavior route3 = new SwitchableRouteExecutableBehavior(
				"3");
		final ExecutorImpl executor1 = new ExecutorImpl(route1, false);
		final ExecutorImpl executor2 = new ExecutorImpl(route2, false);
		final ExecutorImpl executor3 = new ExecutorImpl(route3, false);
		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final ExecutorImpl executorM = new ExecutorImpl(routeOnOffMonitor, true);

		BIPCoordinator engine = new BIPCoordinatorImpl();

		CamelContext camelContext = new DefaultCamelContext();
		final RoutePolicy routePolicy1 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor1.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
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
		};
		final RoutePolicy routePolicy3 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor3.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
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
		camelContext.setAutoStartup(false);
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
		executor1.register(engine);
		t1.start();
		executor2.register(engine);
		executor3.register(engine);
		executorM.register(engine);

		engine.specifyGlue(bipGlue);
		engine.start();

		assertEquals("The state is not appropriate", "off",
				executor1.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor2.getCurrentState());
		assertEquals("The state is not appropriate", "off",
				executor3.getCurrentState());
		assertEquals("The state is not appropriate", "0",
				executorM.getCurrentState());
		try {
			t2.start();
			t3.start();
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
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testEnforceableSpontaneous() throws BIPException {

		final int noSpontaneousToBeSend = 5;
		final int noOfMilisecondsBetweenS = 1000;
		final int executorLoopDelay = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(TestSpecEnforceableSpontaneous.class, "p")
						.requiresNothing();

				port(TestSpecEnforceableSpontaneous.class, "p")
						.acceptsNothing();

			}

		}.build();

		bipGlue.toXML(System.out);
		TestSpecEnforceableSpontaneous component1 = new TestSpecEnforceableSpontaneous();

		final ExecutorImpl executor1 = new ExecutorImpl(component1, true);

		BIPCoordinator engine = new BIPCoordinatorImpl();

		Thread t1 = new Thread(executor1, "SW1");

		Thread t2 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noSpontaneousToBeSend; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					executor1.inform("s");
				}

			}
		}, "SW2");

		executor1.setEngine(engine);
		executor1.register(engine);
		t1.start();

		engine.specifyGlue(bipGlue);
		engine.start();

		t2.start();

		int sleepCounter = 0;

		engine.execute();

		while (component1.getsCounter() < noSpontaneousToBeSend) {
			final int internalSleep = 2000;
			try {
				Thread.sleep(internalSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleepCounter++;
			if (sleepCounter > 2 * noOfMilisecondsBetweenS
					* noSpontaneousToBeSend / internalSleep + executorLoopDelay
					* noSpontaneousToBeSend * 2)
				fail("Not enough spontaneous events have been executed within a given time frame.");
		}

		assertEquals(component1.getsCounter(), noSpontaneousToBeSend);

	}

	@Test
	public void testEnforceableSpontaneous2() throws BIPException {

		final int noSpontaneousToBeSend = 1;
		final int noOfMilisecondsBetweenS = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(TestSpecEnforceableSpontaneous.class, "p")
						.requiresNothing();

				port(TestSpecEnforceableSpontaneous.class, "p")
						.acceptsNothing();

			}

		}.build();

		bipGlue.toXML(System.out);
		TestSpecEnforceableSpontaneous component1 = new TestSpecEnforceableSpontaneous();

		final ExecutorImpl executor1 = new ExecutorImpl(component1, true);

		BIPCoordinator engine = new BIPCoordinatorImpl();

		Thread t1 = new Thread(executor1, "SW1");

		Thread t2 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noSpontaneousToBeSend; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					executor1.inform("s");
				}

			}
		}, "SW2");

		executor1.setEngine(engine);
		executor1.register(engine);
		t1.start();

		engine.specifyGlue(bipGlue);
		engine.start();

		t2.start();

		int sleepCounter = 0;

		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (component1.getsCounter() < noSpontaneousToBeSend) {
			final int internalSleep = 2000;
			try {
				Thread.sleep(internalSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleepCounter++;
			if (sleepCounter > 2 * noOfMilisecondsBetweenS
					* noSpontaneousToBeSend / internalSleep)
				fail("Not enough spontaneous events have been executed within a given time frame: "
						+ component1.getsCounter());
		}

		assertEquals(component1.getsCounter(), noSpontaneousToBeSend);

	}

	@Test
	public void testTernaryInteractionWithTrigger() throws BIPException {

		/*
		 * Test story.
		 * 
		 * Rcomponent with its port triggers an interaction where pComponent (p
		 * port) is synchronized with qComponent (q) port
		 * 
		 * pComponent because it is initialized with false will not be able to
		 * execute transitions with spontaneous events. What happens then with
		 * BIP Executor? Lets assume for a moment that queuing behavior is
		 * correct. P is always enabled. Therefore, the maximum interaction will
		 * always choose pqr (or pr), and not just qr.
		 * 
		 * Components q and r have to have spontaneous events received and
		 * treated to be able to have enforceable transition enabled.
		 */

		final int noIterations = 5;
		final int noOfMilisecondsBetweenS = 1000;
		// final int executorLoopDelay = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PComponent.class, "p").requires(RComponent.class, "r");

				port(PComponent.class, "p").accepts(QComponent.class, "q",
						RComponent.class, "r");

				port(QComponent.class, "q").requires(PComponent.class, "p");

				port(QComponent.class, "q").accepts(PComponent.class, "p",
						RComponent.class, "r");

				port(RComponent.class, "r").accepts(PComponent.class, "p",
						QComponent.class, "q");

				// TODO, do we need the requiresNothing ? Should we need it?
				port(RComponent.class, "r").requiresNothing();

			}

		}.build();

		// TODO: The testXml file should be dumped in a more
		// adequate folder than at the root of the project.

		ByteArrayOutputStream bipGlueOutputStream = new ByteArrayOutputStream();

		bipGlue.toXML(bipGlueOutputStream);

		bipGlue.toXML(System.out);

		ByteArrayInputStream bipGlueInputStream = new ByteArrayInputStream(
				bipGlueOutputStream.toByteArray());

		bipGlue = GlueBuilder.fromXML(bipGlueInputStream);

		// Component P that does not need enable signals.

		PComponent pComponent = new PComponent(false);

		final ExecutorImpl pExecutor = new ExecutorImpl(pComponent);

		Thread t1 = new Thread(pExecutor, "PComponent");

		// Component Q

		QComponent qComponent = new QComponent();

		final ExecutorImpl qExecutor = new ExecutorImpl(qComponent);

		Thread t2 = new Thread(qExecutor, "QComponent");

		// Component R

		RComponent rComponent = new RComponent();

		final ExecutorImpl rExecutor = new ExecutorImpl(rComponent);

		Thread t3 = new Thread(rExecutor, "RComponent");

		// BIP engine.

		BIPCoordinator engine = new BIPCoordinatorImpl();

		Thread testDriver = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					pExecutor.inform("s");

					rExecutor.inform("s");

					qExecutor.inform("s");
				}

			}
		}, "TestDriver");

		pExecutor.setEngine(engine);
		pExecutor.register(engine);
		t1.start();

		qExecutor.setEngine(engine);
		qExecutor.register(engine);
		t2.start();

		rExecutor.setEngine(engine);
		rExecutor.register(engine);
		t3.start();

		engine.specifyGlue(bipGlue);
		engine.start();

		testDriver.start();

		// int sleepCounter = 0;

		engine.execute();
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// while (pComponent.pCounter < noIterations) {
		// final int internalSleep = 2000;
		// try {
		// Thread.sleep(internalSleep);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// sleepCounter++;
		// if (sleepCounter > 2 * noOfMilisecondsBetweenS * noIterations /
		// internalSleep + executorLoopDelay * noIterations * 2)
		// fail("Not enough spontaneous events have been executed within a given time frame.");
		// }

		assertEquals(noIterations, pComponent.pCounter);
		assertEquals(noIterations, rComponent.rCounter);

		assertTrue(qComponent.qCounter <= noIterations);

	}

	@Test
	// (expected = BIPEngineException.class)
	public void testWithMistakeInWiring() throws BIPException {

		/*
		 * 
		 * In place annotated with comment MISTAKE on purpose, we use the second
		 * time pComponent instead of q component to get
		 * ArrayIndexOutOfBoundException.
		 */

		final int noIterations = 5;
		final int noOfMilisecondsBetweenS = 1000;
		// final int executorLoopDelay = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PComponent.class, "p").requires(RComponent.class, "r");

				port(PComponent.class, "p").accepts(QComponent.class, "q",
						RComponent.class, "r");

				port(QComponent.class, "q").requires(RComponent.class, "r");

				port(QComponent.class, "q").accepts(PComponent.class, "p",
						RComponent.class, "r");

				port(RComponent.class, "r").accepts(PComponent.class, "p",
						QComponent.class, "q");

				// TODO, do we need the requiresNothing ? Should we need it?
				port(RComponent.class, "r").requiresNothing();

			}

		}.build();

		bipGlue.toXML(System.out);

		// Component P that does not need enable signals.

		PComponent pComponent = new PComponent(false);

		final ExecutorImpl pExecutor = new ExecutorImpl(pComponent);

		Thread t1 = new Thread(pExecutor, "PComponent");

		// Component Q

		QComponent qComponent = new QComponent();

		// MISTAKE on purpose.
		final ExecutorImpl qExecutor = new ExecutorImpl(pComponent);

		Thread t2 = new Thread(qExecutor, "QComponent");

		// Component R

		RComponent rComponent = new RComponent();

		final ExecutorImpl rExecutor = new ExecutorImpl(rComponent);

		Thread t3 = new Thread(rExecutor, "RComponent");

		// BIP engine.

		BIPCoordinator engine = new BIPCoordinatorImpl();

		Thread testDriver = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					pExecutor.inform("s");

					qExecutor.inform("s");

					rExecutor.inform("s");
				}

			}
		}, "TestDriver");

		pExecutor.setEngine(engine);
		pExecutor.register(engine);
		t1.start();

		qExecutor.setEngine(engine);
		qExecutor.register(engine);
		t2.start();

		rExecutor.setEngine(engine);
		rExecutor.register(engine);
		t3.start();

		engine.specifyGlue(bipGlue);
		engine.start();

		testDriver.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// TODO can we write the test-condition properly?
	}

	@Test
	public void testMultipleSpontaneous() throws BIPException {

		/*
		 * Test story.
		 * 
		 * A synchronous interaction PSS.p with R.r
		 * 
		 * PSS component receives two spontaneous events that are changing the
		 * status of variable used as p guard. There are twice more enabling
		 * events than disabling events so still noIterations of pr interactions
		 * should take place.
		 * 
		 * R.s is also being sent so that R.r can be enabled.
		 * 
		 * [DONE] This test assumes a new treatment of spontaneous events,
		 * namely that if one spontaneous event has arrived then if it guard
		 * evaluates to true (or guard does not exist) then there is no
		 * exception if enforceable transition also evaluates to true.
		 * Spontaneous transition will have a precedence over enforceable one.
		 */

		final int noIterations = 5;
		final int noOfMilisecondsBetweenS = 10;
		final int executorLoopDelay = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PSSComponent.class, "p").requires(RComponent.class, "r");
				port(PSSComponent.class, "p").accepts(RComponent.class, "r");

				port(RComponent.class, "r").accepts(PSSComponent.class, "p");

				port(RComponent.class, "r").requires(PSSComponent.class, "p");

			}

		}.build();

		bipGlue.toXML(System.out);

		// Component P that does not need enable signals.

		PSSComponent pComponent = new PSSComponent(true);

		final ExecutorImpl pExecutor = new ExecutorImpl(pComponent);

		Thread t1 = new Thread(pExecutor, "PSSComponent");

		// Component R

		RComponent rComponent = new RComponent();

		final ExecutorImpl rExecutor = new ExecutorImpl(rComponent);

		Thread t2 = new Thread(rExecutor, "RComponent");

		// BIP engine.

		BIPCoordinator engine = new BIPCoordinatorImpl();

		Thread testDriver1 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					pExecutor.inform("s1");

				}

			}
		}, "TestDriver1");

		Thread testDriver2 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					pExecutor.inform("s1");

				}

			}
		}, "TestDriver2");

		Thread testDriver3 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					pExecutor.inform("s2");

				}

			}
		}, "TestDriver3");

		Thread testDriver4 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal can be a bit problematic as the
					// guard is never true,

					rExecutor.inform("s");

				}

			}
		}, "TestDriver4");

		pExecutor.setEngine(engine);
		pExecutor.register(engine);
		t1.start();

		rExecutor.setEngine(engine);
		rExecutor.register(engine);
		t2.start();

		engine.specifyGlue(bipGlue);
		engine.start();

		testDriver1.start();
		testDriver2.start();

		testDriver3.start();
		testDriver4.start();

		int sleepCounter = 0;

		engine.execute();

		while (pComponent.pCounter < noIterations) {
			final int internalSleep = 20000;
			try {
				Thread.sleep(internalSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleepCounter++;
			if (sleepCounter > 2 * noOfMilisecondsBetweenS * noIterations
					/ internalSleep + executorLoopDelay * noIterations * 2
					/ internalSleep + 1)
				break;
		}

		assertEquals(noIterations * 2, pComponent.spontaneousEnableCounter);
		assertEquals(noIterations, pComponent.spontaneousDisableCounter);
		assertEquals(noIterations, pComponent.pCounter);

	}

	@Test
	public void bipHannoiTest() throws JAXBException, BIPException {

		int size = 3;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		bipGlue4Hanoi.toXML(System.out);

		HanoiMonitor hanoiMonitor = new HanoiMonitor(size);
		final ExecutorImpl hanoiExecutor = new ExecutorImpl(hanoiMonitor, false);
		Thread t1 = new Thread(hanoiExecutor, "hanoiMonitor");

		LeftHanoiPeg leftHanoiPeg = new LeftHanoiPeg(size);
		final ExecutorImpl lExecutor = new ExecutorImpl(leftHanoiPeg, false);
		Thread t2 = new Thread(lExecutor, "LeftHanoiPeg");

		MiddleHanoiPeg middleHanoiPeg = new MiddleHanoiPeg(size);
		final ExecutorImpl mExecutor = new ExecutorImpl(middleHanoiPeg, false);
		Thread t3 = new Thread(mExecutor, "MiddleHanoiPeg");

		RightHanoiPeg rightHanoiPeg = new RightHanoiPeg(size);
		final ExecutorImpl rExecutor = new ExecutorImpl(rightHanoiPeg, false);
		Thread t4 = new Thread(rExecutor, "RightHanoiPeg");

		// BIP engine.

		BIPCoordinator engine = new BIPCoordinatorImpl();

		hanoiExecutor.setEngine(engine);
		hanoiExecutor.register(engine);
		t1.start();

		lExecutor.setEngine(engine);
		lExecutor.register(engine);
		t2.start();

		mExecutor.setEngine(engine);
		mExecutor.register(engine);
		t3.start();

		rExecutor.setEngine(engine);
		rExecutor.register(engine);
		t4.start();

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
	}

	@Test
	@Ignore
	public void testBinaryInteractionLargeBehavior()
			throws NoSuchMethodException, BIPException {

		/*
		 * Test story.
		 * 
		 * PResizableComponent is a component with one enforceable transition
		 * and two spontaneous. SR does the rollback of enforceable transition.
		 * SE enables next p transition.
		 * 
		 * QComponent has q transition that participates in the interaction with
		 * p.
		 * 
		 * PResizable component has a large state space (e.g. 1000 states
		 * decided upon the construction).
		 * 
		 * 
		 * 
		 * Components q and r have to have spontaneous events recceived and
		 * treated to be able to have enforceable transition enabled.
		 */

		final int noIterations = 100; // no of states in PResizableComponent
										// too.
		final int noOfMilisecondsBetweenS = 1000;
		final int executorLoopDelay = 1000;

		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PResizableBehaviorComponent.class, "p").requires(
						QComponent.class, "q");

				port(PResizableBehaviorComponent.class, "p").accepts(
						QComponent.class, "q");

				port(QComponent.class, "q").requires(
						PResizableBehaviorComponent.class, "p");

				port(QComponent.class, "q").accepts(
						PResizableBehaviorComponent.class, "p");

			}

		}.build();

		bipGlue.toXML(System.out);

		// Component P that does not need enable signals.

		PResizableBehaviorComponent pComponent = new PResizableBehaviorComponent(
				true, noIterations);

		final ExecutorImpl pExecutor = new ExecutorImpl(pComponent, false);

		Thread t1 = new Thread(pExecutor, "PComponent");

		// Component Q

		QComponent qComponent = new QComponent();

		final ExecutorImpl qExecutor = new ExecutorImpl(qComponent);

		Thread t2 = new Thread(qExecutor, "QComponent");

		// BIP engine.

		BIPCoordinator engine = new BIPCoordinatorImpl();

		// We enable noIterations of pq interactions.
		Thread testDriver = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					pExecutor.inform("se");

					qExecutor.inform("s");
				}

			}
		}, "TestDriver");

		// We force noIterations rollback on p component.
		Thread testDriver2 = new Thread(new Runnable() {

			public void run() {

				Random random = new Random(100);
				for (int i = 0; i < noIterations; i++) {
					try {
						Thread.sleep(random.nextInt(noOfMilisecondsBetweenS));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This spontaneous signal has no guard so it will be always
					// executed.

					pExecutor.inform("sr");

				}

			}
		}, "TestDriver2");

		pExecutor.setEngine(engine);
		pExecutor.register(engine);

		t1.start();

		qExecutor.setEngine(engine);
		qExecutor.register(engine);
		t2.start();

		engine.specifyGlue(bipGlue);

		engine.start();

		// TODO check in all tests that created threads are started.
		testDriver.start();
		testDriver2.start();

		int sleepCounter = 0;

		engine.execute();

		while (qComponent.qCounter < noIterations) {
			final int internalSleep = 4000;
			try {
				Thread.sleep(internalSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleepCounter++;
			if (sleepCounter > 2 * noOfMilisecondsBetweenS * noIterations
					/ internalSleep + executorLoopDelay * noIterations * 2)
				fail("Not enough spontaneous events have been executed within a given time frame.");
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// }
		assertEquals(0, pComponent.pCounter);

		assertEquals(noIterations, qComponent.qCounter);

	}

	@Test
	public void testSynchronGlueBuilderSwitchableRoute() {

		BIPGlue glue = new TwoSynchronGlueBuilder() {
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

		glue.toXML(System.out);
		assertEquals("Incorrect number of accepts ", 5, glue
				.getAcceptConstraints().size());
		assertEquals("Incorrect number of requires ", 5, glue
				.getRequiresConstraints().size());
		assertEquals("Incorrect number of data wires ", 1, glue.getDataWires()
				.size());

	}

}
