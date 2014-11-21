package org.bip.executor;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.InitialServer;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.MonitorNoDataManyRoutes;
import org.bip.spec.Peer;
import org.bip.spec.Server;
import org.bip.spec.SwitchableRoute;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.Tracker;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaSmallTests {

	@Test
	public void Servers3Test() {
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
	public void TrackerPeer5Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Peer peer1a = new Peer(11);
		Peer peer1b = new Peer(12);
		Peer peer2a = new Peer(21);
		Peer peer2b = new Peer(22);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor1a = factory.create(engine, peer1a, "11", true);
		final Executor executor1b = factory.create(engine, peer1b, "12", true);
		final Executor executor2a = factory.create(engine, peer2a, "21", true);
		final Executor executor2b = factory.create(engine, peer2b, "22", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
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
		
	@Test
	public void bipDataTransferTest() throws BIPException {
		System.out.println("Switchable Routes with Data: 3+1");

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
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
	public void bipFourSRDataTest() throws BIPException {
		System.out.println("Switchable Routes with Data: 4+1");
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(400);
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
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl());

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
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

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(2);
		final Executor executorM = factory.create(engine, monitorNoDataManyRoutes, "monitor", true);

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
	
}
