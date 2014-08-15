package org.bip.executor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPGlue;
import org.bip.engine.DataCoordinatorImpl;
import org.bip.engine.api.DataCoordinator;
import org.bip.exceptions.BIPException;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.junit.Test;

public class ManyDataRoutesTests {

	@Test
	public void bipTenSwTest() throws BIPException {

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

		DataCoordinator engine = new DataCoordinatorImpl(null);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor,
				true);
		Thread tM = new Thread(executorM, "M");
		executorM.setEngine(engine);
		executorM.register(engine);
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(
				"1", camelContext);
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);

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
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers(
				"11", camelContext);
		final ExecutorImpl executor11 = new ExecutorImpl("", route11, true);

		final RoutePolicy routePolicy11 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11.inform("end");
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
		SwitchableRouteDataTransfers route111 = new SwitchableRouteDataTransfers(
				"111", camelContext);
		final ExecutorImpl executor111 = new ExecutorImpl("", route111, true);

		final RoutePolicy routePolicy111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111.inform("end");
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
		SwitchableRouteDataTransfers route1111 = new SwitchableRouteDataTransfers(
				"1111", camelContext);
		final ExecutorImpl executor1111 = new ExecutorImpl("", route1111, true);

		final RoutePolicy routePolicy1111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111.inform("end");
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
		SwitchableRouteDataTransfers route11111 = new SwitchableRouteDataTransfers(
				"11111", camelContext);
		final ExecutorImpl executor11111 = new ExecutorImpl("", route11111,
				true);

		final RoutePolicy routePolicy11111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11111.inform("end");
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
		SwitchableRouteDataTransfers route111111 = new SwitchableRouteDataTransfers(
				"111111", camelContext);
		final ExecutorImpl executor111111 = new ExecutorImpl("", route111111,
				true);

		final RoutePolicy routePolicy111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111111.inform("end");
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
		SwitchableRouteDataTransfers route1111111 = new SwitchableRouteDataTransfers(
				"1111111", camelContext);
		final ExecutorImpl executor1111111 = new ExecutorImpl("", route1111111,
				true);

		final RoutePolicy routePolicy1111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111111.inform("end");
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
		SwitchableRouteDataTransfers route11111111 = new SwitchableRouteDataTransfers(
				"11111111", camelContext);
		final ExecutorImpl executor11111111 = new ExecutorImpl("",
				route11111111, true);

		final RoutePolicy routePolicy11111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11111111.inform("end");
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
		SwitchableRouteDataTransfers route111111111 = new SwitchableRouteDataTransfers(
				"111111111", camelContext);
		final ExecutorImpl executor111111111 = new ExecutorImpl("",
				route111111111, true);

		final RoutePolicy routePolicy111111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111111111.inform("end");
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
		SwitchableRouteDataTransfers route1111111111 = new SwitchableRouteDataTransfers(
				"1111111111", camelContext);
		final ExecutorImpl executor1111111111 = new ExecutorImpl("",
				route1111111111, true);

		final RoutePolicy routePolicy1111111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111111111.inform("end");
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
		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						.routePolicy(routePolicy1).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder1");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder111?delete=true").routeId("111")
						.routePolicy(routePolicy111).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder111");

				from("file:inputfolder1111?delete=true").routeId("1111")
						.routePolicy(routePolicy1111).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder1111");

				from("file:inputfolder11111?delete=true").routeId("11111")
						.routePolicy(routePolicy11111).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11111");

				from("file:inputfolder111111?delete=true").routeId("111111")
						.routePolicy(routePolicy111111)
						.process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder111111");

				from("file:inputfolder1111111?delete=true").routeId("1111111")
						.routePolicy(routePolicy1111111)
						.process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder1111111");

				from("file:inputfolder11111111?delete=true")
						.routeId("11111111").routePolicy(routePolicy11111111)
						.process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11111111");

				from("file:inputfolder111111111?delete=true")
						.routeId("111111111").routePolicy(routePolicy111111111)
						.process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder111111111");

				from("file:inputfolder1111111111?delete=true")
						.routeId("1111111111")
						.routePolicy(routePolicy1111111111)
						.process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder1111111111");

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
		Thread t1 = new Thread(executor1, "SW1");
		executor1.setEngine(engine);
		executor1.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route111.setCamelContext(camelContext);
		Thread t111 = new Thread(executor111, "SW111");
		executor111.setEngine(engine);
		executor111.register(engine);
		route1111.setCamelContext(camelContext);
		Thread t1111 = new Thread(executor1111, "SW1111");
		executor1111.setEngine(engine);
		executor1111.register(engine);
		route11111.setCamelContext(camelContext);
		Thread t11111 = new Thread(executor11111, "SW11111");
		executor11111.setEngine(engine);
		executor11111.register(engine);
		route111111.setCamelContext(camelContext);
		Thread t111111 = new Thread(executor111111, "SW111111");
		executor111111.setEngine(engine);
		executor111111.register(engine);
		route1111111.setCamelContext(camelContext);
		Thread t1111111 = new Thread(executor1111111, "SW1111111");
		executor1111111.setEngine(engine);
		executor1111111.register(engine);
		route11111111.setCamelContext(camelContext);
		Thread t11111111 = new Thread(executor11111111, "SW11111111");
		executor11111111.setEngine(engine);
		executor11111111.register(engine);
		route111111111.setCamelContext(camelContext);
		Thread t111111111 = new Thread(executor111111111, "SW111111111");
		executor111111111.setEngine(engine);
		executor111111111.register(engine);
		route1111111111.setCamelContext(camelContext);
		Thread t1111111111 = new Thread(executor1111111111, "SW1111111111");
		executor1111111111.setEngine(engine);
		executor1111111111.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t11.start();
			t111.start();
			t1111.start();
			t11111.start();
			t111111.start();
			t1111111.start();
			t11111111.start();
			t111111111.start();
			t1111111111.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
	}

	@Test
	public void bipfourSwTest() throws BIPException {

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

		DataCoordinator engine = new DataCoordinatorImpl(null);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor, true);
		Thread tM = new Thread(executorM, "M");
		executorM.setEngine(engine);
		executorM.register(engine);
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);

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
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers("11", camelContext);
		final ExecutorImpl executor11 = new ExecutorImpl("", route11, true);

		final RoutePolicy routePolicy11 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11.inform("end");
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
		SwitchableRouteDataTransfers route111 = new SwitchableRouteDataTransfers("111", camelContext);
		final ExecutorImpl executor111 = new ExecutorImpl("", route111, true);

		final RoutePolicy routePolicy111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111.inform("end");
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
		SwitchableRouteDataTransfers route1111 = new SwitchableRouteDataTransfers("1111", camelContext);
		final ExecutorImpl executor1111 = new ExecutorImpl("", route1111, true);

		final RoutePolicy routePolicy1111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111.inform("end");
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

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {
					public void process(Exchange exchange) throws Exception {
					}
				}).to("file:outputfolder1");

				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder111?delete=true").routeId("111").routePolicy(routePolicy111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder111");

				from("file:inputfolder1111?delete=true").routeId("1111").routePolicy(routePolicy1111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder1111");
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
		Thread t1 = new Thread(executor1, "SW1");
		executor1.setEngine(engine);
		executor1.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route111.setCamelContext(camelContext);
		Thread t111 = new Thread(executor111, "SW111");
		executor111.setEngine(engine);
		executor111.register(engine);
		route1111.setCamelContext(camelContext);
		Thread t1111 = new Thread(executor1111, "SW1111");
		executor1111.setEngine(engine);
		executor1111.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t11.start();
			t111.start();
			t1111.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
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
	public void bipfiveSwTest() throws BIPException {

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

		DataCoordinator engine = new DataCoordinatorImpl(null);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor, true);
		Thread tM = new Thread(executorM, "M");
		executorM.setEngine(engine);
		executorM.register(engine);
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);

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
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers("11", camelContext);
		final ExecutorImpl executor11 = new ExecutorImpl("", route11, true);

		final RoutePolicy routePolicy11 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11.inform("end");
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
		SwitchableRouteDataTransfers route111 = new SwitchableRouteDataTransfers("111", camelContext);
		final ExecutorImpl executor111 = new ExecutorImpl("", route111, true);

		final RoutePolicy routePolicy111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111.inform("end");
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
		SwitchableRouteDataTransfers route1111 = new SwitchableRouteDataTransfers("1111", camelContext);
		final ExecutorImpl executor1111 = new ExecutorImpl("", route1111, true);

		final RoutePolicy routePolicy1111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111.inform("end");
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
		SwitchableRouteDataTransfers route11111 = new SwitchableRouteDataTransfers("11111", camelContext);
		final ExecutorImpl executor11111 = new ExecutorImpl("", route11111, true);

		final RoutePolicy routePolicy11111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11111.inform("end");
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

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {
					public void process(Exchange exchange) throws Exception {
					}
				}).to("file:outputfolder1");

				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder111?delete=true").routeId("111").routePolicy(routePolicy111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder111");

				from("file:inputfolder1111?delete=true").routeId("1111").routePolicy(routePolicy1111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder1111");

				from("file:inputfolder11111?delete=true").routeId("11111").routePolicy(routePolicy11111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder11111");
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
		Thread t1 = new Thread(executor1, "SW1");
		executor1.setEngine(engine);
		executor1.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route111.setCamelContext(camelContext);
		Thread t111 = new Thread(executor111, "SW111");
		executor111.setEngine(engine);
		executor111.register(engine);
		route1111.setCamelContext(camelContext);
		Thread t1111 = new Thread(executor1111, "SW1111");
		executor1111.setEngine(engine);
		executor1111.register(engine);
		route11111.setCamelContext(camelContext);
		Thread t11111 = new Thread(executor11111, "SW11111");
		executor11111.setEngine(engine);
		executor11111.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t11.start();
			t111.start();
			t1111.start();
			t11111.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void bipSixSwTest() throws BIPException {

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

		DataCoordinator engine = new DataCoordinatorImpl(null);
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(600);
		final ExecutorImpl executorM = new ExecutorImpl("", routeOnOffMonitor, true);
		Thread tM = new Thread(executorM, "M");
		executorM.setEngine(engine);
		executorM.register(engine);
		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		final ExecutorImpl executor1 = new ExecutorImpl("", route1, true);

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
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers("11", camelContext);
		final ExecutorImpl executor11 = new ExecutorImpl("", route11, true);

		final RoutePolicy routePolicy11 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11.inform("end");
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
		SwitchableRouteDataTransfers route111 = new SwitchableRouteDataTransfers("111", camelContext);
		final ExecutorImpl executor111 = new ExecutorImpl("", route111, true);

		final RoutePolicy routePolicy111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111.inform("end");
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
		SwitchableRouteDataTransfers route1111 = new SwitchableRouteDataTransfers("1111", camelContext);
		final ExecutorImpl executor1111 = new ExecutorImpl("", route1111, true);

		final RoutePolicy routePolicy1111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor1111.inform("end");
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
		SwitchableRouteDataTransfers route11111 = new SwitchableRouteDataTransfers("11111", camelContext);
		final ExecutorImpl executor11111 = new ExecutorImpl("", route11111, true);

		final RoutePolicy routePolicy11111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor11111.inform("end");
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
		SwitchableRouteDataTransfers route111111 = new SwitchableRouteDataTransfers("111111", camelContext);
		final ExecutorImpl executor111111 = new ExecutorImpl("", route111111, true);

		final RoutePolicy routePolicy111111 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor111111.inform("end");
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

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {
					public void process(Exchange exchange) throws Exception {
					}
				}).to("file:outputfolder1");

				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder111?delete=true").routeId("111").routePolicy(routePolicy111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder111");

				from("file:inputfolder1111?delete=true").routeId("1111").routePolicy(routePolicy1111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder1111");

				from("file:inputfolder11111?delete=true").routeId("11111").routePolicy(routePolicy11111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder11111");

				from("file:inputfolder111111?delete=true").routeId("111111").routePolicy(routePolicy111111)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
							}
						}).to("file:outputfolder111111");

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
		Thread t1 = new Thread(executor1, "SW1");
		executor1.setEngine(engine);
		executor1.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route111.setCamelContext(camelContext);
		Thread t111 = new Thread(executor111, "SW111");
		executor111.setEngine(engine);
		executor111.register(engine);
		route1111.setCamelContext(camelContext);
		Thread t1111 = new Thread(executor1111, "SW1111");
		executor1111.setEngine(engine);
		executor1111.register(engine);
		route11111.setCamelContext(camelContext);
		Thread t11111 = new Thread(executor11111, "SW11111");
		executor11111.setEngine(engine);
		executor11111.register(engine);
		route111111.setCamelContext(camelContext);
		Thread t111111 = new Thread(executor111111, "SW111111");
		executor111111.setEngine(engine);
		executor111111.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t11.start();
			t111.start();
			t1111.start();
			t11111.start();
			t111111.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}