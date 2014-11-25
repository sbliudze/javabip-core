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
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.api.PortBase;
import org.bip.api.PortType;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.BIPCoordinator;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class IntegrationTests {

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
	public void testHashCodePort() {
		PortBase portA = new PortImpl("p", PortType.enforceable, SwitchableRoute.class);
		PortBase portB = new PortImpl("p", PortType.enforceable, SwitchableRoute.class);
		PortBase portC = new PortImpl("p", PortType.enforceable, SwitchableRoute.class);
		PortBase portD = new PortImpl("p", PortType.enforceable, SwitchableRoute.class);

		assertEquals(portA.hashCode(), portB.hashCode());
		assertEquals(portC.hashCode(), portD.hashCode());
	}

	@Test
	public void testBehaviourBuilding() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
		// get Glue object from xml file
		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");

		SwitchableRouteExecutableBehavior route1 = new SwitchableRouteExecutableBehavior("1");
		SwitchableRouteExecutableBehavior route2 = new SwitchableRouteExecutableBehavior("2");
		SwitchableRouteExecutableBehavior route3 = new SwitchableRouteExecutableBehavior("3");
		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);

		CamelContext camelContext = new DefaultCamelContext();
		route1.setCamelContext(camelContext);
		route2.setCamelContext(camelContext);
		route3.setCamelContext(camelContext);
		
		final Executor executor1 = factory.create(engine, route1, "1", false);
		final Executor executor2 = factory.create(engine, route2, "2", false);
		final Executor executor3 = factory.create(engine, route3, "3", false);

		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);
		
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
		camelContext.setAutoStartup(false);
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	@Test
	public void testEnforceableSpontaneous() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
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


		TestSpecEnforceableSpontaneous component1 = new TestSpecEnforceableSpontaneous();

		final Executor executor1 = factory.create(engine, component1, "comp1", true);

		Thread threadSendingSpontaneousEvents = new Thread(new Runnable() {

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

		engine.specifyGlue(bipGlue);
		engine.start();

		threadSendingSpontaneousEvents.start();

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

		boolean destroyed = factory.destroy(executor1);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);
		
		assertTrue("Component 1 has not made any transitions", component1.pCounter > 0);
		
		assertEquals(component1.getsCounter(), noSpontaneousToBeSend);

	}

	@Test
	public void testEnforceableSpontaneous2() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
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

		// bipGlue.toXML(System.out);
		
		TestSpecEnforceableSpontaneous component1 = new TestSpecEnforceableSpontaneous();

		final Executor executor1 = factory.create(engine, component1, "comp1", true);
		

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

		boolean destroyed = factory.destroy(executor1);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);
		
		assertTrue("Component 1 has not made any transitions", component1.pCounter > 0);
		
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

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
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

				port(RComponent.class, "r").requiresNothing();

			}

		}.build();

		// TODO, make it into a separate test that does test round trip to-from xml.
		ByteArrayOutputStream bipGlueOutputStream = new ByteArrayOutputStream();

		bipGlue.toXML(bipGlueOutputStream);

		ByteArrayInputStream bipGlueInputStream = new ByteArrayInputStream(
				bipGlueOutputStream.toByteArray());

		bipGlue = GlueBuilder.fromXML(bipGlueInputStream);

		// Component P that does not need enable signals.

		PComponent pComponent = new PComponent(false);
		final Executor pExecutor = factory.create(engine, pComponent, "pComponent", true);

		// Component Q

		QComponent qComponent = new QComponent();
		final Executor qExecutor = factory.create(engine, qComponent, "qComponent", true);

		// Component R

		RComponent rComponent = new RComponent();
		final Executor rExecutor = factory.create(engine, rComponent, "rComponent", true);
		
		// BIP engine.


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

		engine.specifyGlue(bipGlue);
		engine.start();

		testDriver.start();

		engine.execute();
		
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(pExecutor);
		destroyed &= factory.destroy(qExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

		
		assertEquals(noIterations, pComponent.pCounter);
		assertEquals(noIterations, rComponent.rCounter);

		assertTrue(qComponent.qCounter <= noIterations);

	}

	@Test
	public void testWithMistakeInWiring() throws BIPException {

		/*
		 * 
		 * In place annotated with comment MISTAKE on purpose, we use the second
		 * time pComponent instead of q component to get
		 * ArrayIndexOutOfBoundException.
		 */

		final int noIterations = 5;
		final int noOfMilisecondsBetweenS = 1000;

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
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

				port(RComponent.class, "r").requiresNothing();

			}

		}.build();

		// Component P that does not need enable signals.

		PComponent pComponent = new PComponent(false);
		final Executor pExecutor = factory.create(engine, pComponent, "pComponent", true);
		
		// Component Q

		// QComponent qComponent = new QComponent();
		// MISTAKE on purpose, so the above line can be commented out.
		final Executor qExecutor = factory.create(engine, pComponent, "qComponent", true);

		// Component R

		RComponent rComponent = new RComponent();
		final Executor rExecutor = factory.create(engine, rComponent, "rComponent", true);
		
		// BIP engine.

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

		engine.specifyGlue(bipGlue);
		engine.start();

		testDriver.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		boolean destroyed = factory.destroy(pExecutor);
		destroyed &= factory.destroy(qExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

		
		assertEquals("No progress for pComponent due to lack of QComponent", 0, pComponent.pCounter);
		assertEquals("No progress for rComponent due to lack of QComponent", 0, rComponent.rCounter);

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

		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());
		
		BIPGlue bipGlue = new GlueBuilder() {
			@Override
			public void configure() {

				port(PSSComponent.class, "p").requires(RComponent.class, "r");
				port(PSSComponent.class, "p").accepts(RComponent.class, "r");

				port(RComponent.class, "r").accepts(PSSComponent.class, "p");

				port(RComponent.class, "r").requires(PSSComponent.class, "p");

			}

		}.build();

		// Component P that does not need enable signals.

		PSSComponent pComponent = new PSSComponent(true);
		
		final Executor pExecutor = factory.create(engine, pComponent, "pComponent", true);

		// Component R

		RComponent rComponent = new RComponent();
		
		final Executor rExecutor = factory.create(engine, rComponent, "rComponent", true);

		// BIP engine.

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

		boolean destroyed = factory.destroy(pExecutor);
		destroyed &= factory.destroy(rExecutor);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

		assertEquals(noIterations * 2, pComponent.spontaneousEnableCounter);
		assertEquals(noIterations, pComponent.spontaneousDisableCounter);
		assertEquals(noIterations, pComponent.pCounter);

	}

	@Test
	@Ignore // old ignore.
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
		
		BIPEngine engine = engineFactory.create("myEngine",	new BIPCoordinatorImpl());

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

		final Executor pExecutor = factory.create(engine, pComponent, "pComponent", false);
		
		// Component Q

		QComponent qComponent = new QComponent();
		
		final Executor qExecutor = factory.create(engine, qComponent, "qComponent", true);

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

		engine.specifyGlue(bipGlue);

		engine.start();

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
		
		boolean destroyed = factory.destroy(pExecutor);
		destroyed &= factory.destroy(qExecutor);

		engine.stop();
		
		assertEquals("Not all BIP actors were terminated.", destroyed, true);

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
