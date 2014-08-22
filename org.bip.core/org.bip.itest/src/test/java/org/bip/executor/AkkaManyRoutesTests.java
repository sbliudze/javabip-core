package org.bip.executor;

import static org.junit.Assert.assertEquals;

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
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRoute;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaManyRoutesTests {

	@Test
	public void bipDataTransferTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 3+1");

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);

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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipFourSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 4+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipFiveSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 5+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipSixSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 6+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipSevenSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 7+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();
			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipEightSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 8+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);
		final Executor executor2 = factory.create(engine, route2, "2", true);
		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
		final Executor executor8 = factory.create(engine, route8, "8", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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

		final RoutePolicy routePolicy8 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor8.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipNineSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 9+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);

		final Executor executor2 = factory.create(engine, route2, "2", true);

		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
		final Executor executor8 = factory.create(engine, route8, "8", true);
		final Executor executor9 = factory.create(engine, route9, "9", true);
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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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

		final RoutePolicy routePolicy8 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor8.inform("end");
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

		final RoutePolicy routePolicy9 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor9.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipTenSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 10+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);
		final Executor executor2 = factory.create(engine, route2, "2", true);
		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
		final Executor executor8 = factory.create(engine, route8, "8", true);
		final Executor executor9 = factory.create(engine, route9, "9", true);
		final Executor executor10 = factory.create(engine, route10, "10", true);

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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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

		final RoutePolicy routePolicy8 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor8.inform("end");
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

		final RoutePolicy routePolicy9 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor9.inform("end");
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

		final RoutePolicy routePolicy10 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor10.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipFourteenSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 14+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);
		SwitchableRoute route11 = new SwitchableRoute("11", camelContext);
		SwitchableRoute route12 = new SwitchableRoute("12", camelContext);
		SwitchableRoute route13 = new SwitchableRoute("13", camelContext);
		SwitchableRoute route14 = new SwitchableRoute("14", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);
		final Executor executor2 = factory.create(engine, route2, "2", true);
		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
		final Executor executor8 = factory.create(engine, route8, "8", true);
		final Executor executor9 = factory.create(engine, route9, "9", true);
		final Executor executor10 = factory.create(engine, route10, "10", true);
		final Executor executor11 = factory.create(engine, route11, "11", true);
		final Executor executor12 = factory.create(engine, route12, "12", true);
		final Executor executor13 = factory.create(engine, route13, "13", true);
		final Executor executor14 = factory.create(engine, route14, "14", true);

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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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

		final RoutePolicy routePolicy8 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor8.inform("end");
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

		final RoutePolicy routePolicy9 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor9.inform("end");
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

		final RoutePolicy routePolicy10 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor10.inform("end");
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

		final RoutePolicy routePolicy11 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor11.inform("end");
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

		final RoutePolicy routePolicy12 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor12.inform("end");
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

		final RoutePolicy routePolicy13 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor13.inform("end");
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

		final RoutePolicy routePolicy14 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor14.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.to("file:outputfolder11");
				from("file:inputfolder12?delete=true").routeId("12").routePolicy(routePolicy12)
						.to("file:outputfolder12");
				from("file:inputfolder13?delete=true").routeId("13").routePolicy(routePolicy13)
						.to("file:outputfolder13");
				from("file:inputfolder14?delete=true").routeId("14").routePolicy(routePolicy14)
						.to("file:outputfolder14");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

	@Test
	public void bipNineTeenSRTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 19+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
				synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);
		SwitchableRoute route11 = new SwitchableRoute("11", camelContext);
		SwitchableRoute route12 = new SwitchableRoute("12", camelContext);
		SwitchableRoute route13 = new SwitchableRoute("13", camelContext);
		SwitchableRoute route14 = new SwitchableRoute("14", camelContext);
		SwitchableRoute route15 = new SwitchableRoute("15", camelContext);
		SwitchableRoute route16 = new SwitchableRoute("16", camelContext);
		SwitchableRoute route17 = new SwitchableRoute("17", camelContext);
		SwitchableRoute route18 = new SwitchableRoute("18", camelContext);
		SwitchableRoute route19 = new SwitchableRoute("19", camelContext);

		final Executor executor1 = factory.create(engine, route1, "1", true);
		final Executor executor2 = factory.create(engine, route2, "2", true);
		final Executor executor3 = factory.create(engine, route3, "3", true);
		final Executor executor4 = factory.create(engine, route4, "4", true);
		final Executor executor5 = factory.create(engine, route5, "5", true);
		final Executor executor6 = factory.create(engine, route6, "6", true);
		final Executor executor7 = factory.create(engine, route7, "7", true);
		final Executor executor8 = factory.create(engine, route8, "8", true);
		final Executor executor9 = factory.create(engine, route9, "9", true);
		final Executor executor10 = factory.create(engine, route10, "10", true);
		final Executor executor11 = factory.create(engine, route11, "11", true);
		final Executor executor12 = factory.create(engine, route12, "12", true);
		final Executor executor13 = factory.create(engine, route13, "13", true);
		final Executor executor14 = factory.create(engine, route14, "14", true);
		final Executor executor15 = factory.create(engine, route15, "15", true);
		final Executor executor16 = factory.create(engine, route16, "16", true);
		final Executor executor17 = factory.create(engine, route17, "17", true);
		final Executor executor18 = factory.create(engine, route18, "18", true);
		final Executor executor19 = factory.create(engine, route19, "19", true);

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

		final RoutePolicy routePolicy4 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor4.inform("end");
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

		final RoutePolicy routePolicy5 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor5.inform("end");
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

		final RoutePolicy routePolicy6 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor6.inform("end");
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

		final RoutePolicy routePolicy7 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor7.inform("end");
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

		final RoutePolicy routePolicy8 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor8.inform("end");
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

		final RoutePolicy routePolicy9 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor9.inform("end");
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

		final RoutePolicy routePolicy10 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor10.inform("end");
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

		final RoutePolicy routePolicy11 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor11.inform("end");
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

		final RoutePolicy routePolicy12 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor12.inform("end");
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

		final RoutePolicy routePolicy13 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor13.inform("end");
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

		final RoutePolicy routePolicy14 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor14.inform("end");
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

		final RoutePolicy routePolicy15 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor15.inform("end");
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

		final RoutePolicy routePolicy16 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor16.inform("end");
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

		final RoutePolicy routePolicy17 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor17.inform("end");
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

		final RoutePolicy routePolicy18 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor18.inform("end");
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

		final RoutePolicy routePolicy19 = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

				executor19.inform("end");
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
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.to("file:outputfolder11");
				from("file:inputfolder12?delete=true").routeId("12").routePolicy(routePolicy12)
						.to("file:outputfolder12");
				from("file:inputfolder13?delete=true").routeId("13").routePolicy(routePolicy13)
						.to("file:outputfolder13");
				from("file:inputfolder14?delete=true").routeId("14").routePolicy(routePolicy14)
						.to("file:outputfolder14");
				from("file:inputfolder15?delete=true").routeId("15").routePolicy(routePolicy15)
						.to("file:outputfolder15");
				from("file:inputfolder16?delete=true").routeId("16").routePolicy(routePolicy16)
						.to("file:outputfolder16");
				from("file:inputfolder17?delete=true").routeId("17").routePolicy(routePolicy17)
						.to("file:outputfolder17");
				from("file:inputfolder18?delete=true").routeId("18").routePolicy(routePolicy18)
						.to("file:outputfolder18");
				from("file:inputfolder19?delete=true").routeId("19").routePolicy(routePolicy19)
						.to("file:outputfolder19");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RouteOnOffMonitor routeOnOffMonitor = new RouteOnOffMonitor(2);
		final Executor executorM = factory.create(engine, routeOnOffMonitor, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Monitor does not have a proper id ", executorM.getId(), "monitor");
	}

}
