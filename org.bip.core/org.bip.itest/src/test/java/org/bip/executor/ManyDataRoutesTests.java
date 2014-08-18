package org.bip.executor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPGlue;
import org.bip.engine.api.DataCoordinator;
import org.bip.engine.DataCoordinatorImpl;
import org.bip.exceptions.BIPException;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.MemoryMonitor;
import org.junit.Test;

public class ManyDataRoutesTests {

	@Test
	public void bipSwMultiTest24() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1300);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest29() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1450);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final ExecutorImpl executor25 = new ExecutorImpl("", route25, true);

		final RoutePolicy routePolicy25 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor25.inform("end");
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
		SwitchableRouteDataTransfers route26 = new SwitchableRouteDataTransfers(
				"26", camelContext);
		final ExecutorImpl executor26 = new ExecutorImpl("", route26, true);

		final RoutePolicy routePolicy26 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor26.inform("end");
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
		SwitchableRouteDataTransfers route27 = new SwitchableRouteDataTransfers(
				"27", camelContext);
		final ExecutorImpl executor27 = new ExecutorImpl("", route27, true);

		final RoutePolicy routePolicy27 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor27.inform("end");
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
		SwitchableRouteDataTransfers route28 = new SwitchableRouteDataTransfers(
				"28", camelContext);
		final ExecutorImpl executor28 = new ExecutorImpl("", route28, true);

		final RoutePolicy routePolicy28 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor28.inform("end");
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
		SwitchableRouteDataTransfers route29 = new SwitchableRouteDataTransfers(
				"29", camelContext);
		final ExecutorImpl executor29 = new ExecutorImpl("", route29, true);

		final RoutePolicy routePolicy29 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor29.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

				from("file:inputfolder25?delete=true").routeId("25")
						.routePolicy(routePolicy25).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder25");

				from("file:inputfolder26?delete=true").routeId("26")
						.routePolicy(routePolicy26).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder26");

				from("file:inputfolder27?delete=true").routeId("27")
						.routePolicy(routePolicy27).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder27");

				from("file:inputfolder28?delete=true").routeId("28")
						.routePolicy(routePolicy28).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder28");

				from("file:inputfolder29?delete=true").routeId("29")
						.routePolicy(routePolicy29).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder29");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		route25.setCamelContext(camelContext);
		Thread t25 = new Thread(executor25, "SW25");
		executor25.setEngine(engine);
		executor25.register(engine);
		route26.setCamelContext(camelContext);
		Thread t26 = new Thread(executor26, "SW26");
		executor26.setEngine(engine);
		executor26.register(engine);
		route27.setCamelContext(camelContext);
		Thread t27 = new Thread(executor27, "SW27");
		executor27.setEngine(engine);
		executor27.register(engine);
		route28.setCamelContext(camelContext);
		Thread t28 = new Thread(executor28, "SW28");
		executor28.setEngine(engine);
		executor28.register(engine);
		route29.setCamelContext(camelContext);
		Thread t29 = new Thread(executor29, "SW29");
		executor29.setEngine(engine);
		executor29.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
			t25.start();
			t26.start();
			t27.start();
			t28.start();
			t29.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest34() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1750);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final ExecutorImpl executor25 = new ExecutorImpl("", route25, true);

		final RoutePolicy routePolicy25 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor25.inform("end");
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
		SwitchableRouteDataTransfers route26 = new SwitchableRouteDataTransfers(
				"26", camelContext);
		final ExecutorImpl executor26 = new ExecutorImpl("", route26, true);

		final RoutePolicy routePolicy26 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor26.inform("end");
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
		SwitchableRouteDataTransfers route27 = new SwitchableRouteDataTransfers(
				"27", camelContext);
		final ExecutorImpl executor27 = new ExecutorImpl("", route27, true);

		final RoutePolicy routePolicy27 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor27.inform("end");
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
		SwitchableRouteDataTransfers route28 = new SwitchableRouteDataTransfers(
				"28", camelContext);
		final ExecutorImpl executor28 = new ExecutorImpl("", route28, true);

		final RoutePolicy routePolicy28 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor28.inform("end");
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
		SwitchableRouteDataTransfers route29 = new SwitchableRouteDataTransfers(
				"29", camelContext);
		final ExecutorImpl executor29 = new ExecutorImpl("", route29, true);

		final RoutePolicy routePolicy29 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor29.inform("end");
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
		SwitchableRouteDataTransfers route30 = new SwitchableRouteDataTransfers(
				"30", camelContext);
		final ExecutorImpl executor30 = new ExecutorImpl("", route30, true);

		final RoutePolicy routePolicy30 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor30.inform("end");
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
		SwitchableRouteDataTransfers route31 = new SwitchableRouteDataTransfers(
				"31", camelContext);
		final ExecutorImpl executor31 = new ExecutorImpl("", route31, true);

		final RoutePolicy routePolicy31 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor31.inform("end");
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
		SwitchableRouteDataTransfers route32 = new SwitchableRouteDataTransfers(
				"32", camelContext);
		final ExecutorImpl executor32 = new ExecutorImpl("", route32, true);

		final RoutePolicy routePolicy32 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor32.inform("end");
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
		SwitchableRouteDataTransfers route33 = new SwitchableRouteDataTransfers(
				"33", camelContext);
		final ExecutorImpl executor33 = new ExecutorImpl("", route33, true);

		final RoutePolicy routePolicy33 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor33.inform("end");
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
		SwitchableRouteDataTransfers route34 = new SwitchableRouteDataTransfers(
				"34", camelContext);
		final ExecutorImpl executor34 = new ExecutorImpl("", route34, true);

		final RoutePolicy routePolicy34 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor34.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

				from("file:inputfolder25?delete=true").routeId("25")
						.routePolicy(routePolicy25).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder25");

				from("file:inputfolder26?delete=true").routeId("26")
						.routePolicy(routePolicy26).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder26");

				from("file:inputfolder27?delete=true").routeId("27")
						.routePolicy(routePolicy27).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder27");

				from("file:inputfolder28?delete=true").routeId("28")
						.routePolicy(routePolicy28).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder28");

				from("file:inputfolder29?delete=true").routeId("29")
						.routePolicy(routePolicy29).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder29");

				from("file:inputfolder30?delete=true").routeId("30")
						.routePolicy(routePolicy30).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder30");

				from("file:inputfolder31?delete=true").routeId("31")
						.routePolicy(routePolicy31).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder31");

				from("file:inputfolder32?delete=true").routeId("32")
						.routePolicy(routePolicy32).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder32");

				from("file:inputfolder33?delete=true").routeId("33")
						.routePolicy(routePolicy33).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder33");

				from("file:inputfolder34?delete=true").routeId("34")
						.routePolicy(routePolicy34).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder34");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		route25.setCamelContext(camelContext);
		Thread t25 = new Thread(executor25, "SW25");
		executor25.setEngine(engine);
		executor25.register(engine);
		route26.setCamelContext(camelContext);
		Thread t26 = new Thread(executor26, "SW26");
		executor26.setEngine(engine);
		executor26.register(engine);
		route27.setCamelContext(camelContext);
		Thread t27 = new Thread(executor27, "SW27");
		executor27.setEngine(engine);
		executor27.register(engine);
		route28.setCamelContext(camelContext);
		Thread t28 = new Thread(executor28, "SW28");
		executor28.setEngine(engine);
		executor28.register(engine);
		route29.setCamelContext(camelContext);
		Thread t29 = new Thread(executor29, "SW29");
		executor29.setEngine(engine);
		executor29.register(engine);
		route30.setCamelContext(camelContext);
		Thread t30 = new Thread(executor30, "SW30");
		executor30.setEngine(engine);
		executor30.register(engine);
		route31.setCamelContext(camelContext);
		Thread t31 = new Thread(executor31, "SW31");
		executor31.setEngine(engine);
		executor31.register(engine);
		route32.setCamelContext(camelContext);
		Thread t32 = new Thread(executor32, "SW32");
		executor32.setEngine(engine);
		executor32.register(engine);
		route33.setCamelContext(camelContext);
		Thread t33 = new Thread(executor33, "SW33");
		executor33.setEngine(engine);
		executor33.register(engine);
		route34.setCamelContext(camelContext);
		Thread t34 = new Thread(executor34, "SW34");
		executor34.setEngine(engine);
		executor34.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
			t25.start();
			t26.start();
			t27.start();
			t28.start();
			t29.start();
			t30.start();
			t31.start();
			t32.start();
			t33.start();
			t34.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest39() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1950);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final ExecutorImpl executor25 = new ExecutorImpl("", route25, true);

		final RoutePolicy routePolicy25 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor25.inform("end");
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
		SwitchableRouteDataTransfers route26 = new SwitchableRouteDataTransfers(
				"26", camelContext);
		final ExecutorImpl executor26 = new ExecutorImpl("", route26, true);

		final RoutePolicy routePolicy26 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor26.inform("end");
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
		SwitchableRouteDataTransfers route27 = new SwitchableRouteDataTransfers(
				"27", camelContext);
		final ExecutorImpl executor27 = new ExecutorImpl("", route27, true);

		final RoutePolicy routePolicy27 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor27.inform("end");
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
		SwitchableRouteDataTransfers route28 = new SwitchableRouteDataTransfers(
				"28", camelContext);
		final ExecutorImpl executor28 = new ExecutorImpl("", route28, true);

		final RoutePolicy routePolicy28 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor28.inform("end");
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
		SwitchableRouteDataTransfers route29 = new SwitchableRouteDataTransfers(
				"29", camelContext);
		final ExecutorImpl executor29 = new ExecutorImpl("", route29, true);

		final RoutePolicy routePolicy29 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor29.inform("end");
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
		SwitchableRouteDataTransfers route30 = new SwitchableRouteDataTransfers(
				"30", camelContext);
		final ExecutorImpl executor30 = new ExecutorImpl("", route30, true);

		final RoutePolicy routePolicy30 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor30.inform("end");
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
		SwitchableRouteDataTransfers route31 = new SwitchableRouteDataTransfers(
				"31", camelContext);
		final ExecutorImpl executor31 = new ExecutorImpl("", route31, true);

		final RoutePolicy routePolicy31 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor31.inform("end");
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
		SwitchableRouteDataTransfers route32 = new SwitchableRouteDataTransfers(
				"32", camelContext);
		final ExecutorImpl executor32 = new ExecutorImpl("", route32, true);

		final RoutePolicy routePolicy32 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor32.inform("end");
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
		SwitchableRouteDataTransfers route33 = new SwitchableRouteDataTransfers(
				"33", camelContext);
		final ExecutorImpl executor33 = new ExecutorImpl("", route33, true);

		final RoutePolicy routePolicy33 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor33.inform("end");
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
		SwitchableRouteDataTransfers route34 = new SwitchableRouteDataTransfers(
				"34", camelContext);
		final ExecutorImpl executor34 = new ExecutorImpl("", route34, true);

		final RoutePolicy routePolicy34 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor34.inform("end");
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
		SwitchableRouteDataTransfers route35 = new SwitchableRouteDataTransfers(
				"35", camelContext);
		final ExecutorImpl executor35 = new ExecutorImpl("", route35, true);

		final RoutePolicy routePolicy35 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor35.inform("end");
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
		SwitchableRouteDataTransfers route36 = new SwitchableRouteDataTransfers(
				"36", camelContext);
		final ExecutorImpl executor36 = new ExecutorImpl("", route36, true);

		final RoutePolicy routePolicy36 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor36.inform("end");
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
		SwitchableRouteDataTransfers route37 = new SwitchableRouteDataTransfers(
				"37", camelContext);
		final ExecutorImpl executor37 = new ExecutorImpl("", route37, true);

		final RoutePolicy routePolicy37 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor37.inform("end");
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
		SwitchableRouteDataTransfers route38 = new SwitchableRouteDataTransfers(
				"38", camelContext);
		final ExecutorImpl executor38 = new ExecutorImpl("", route38, true);

		final RoutePolicy routePolicy38 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor38.inform("end");
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
		SwitchableRouteDataTransfers route39 = new SwitchableRouteDataTransfers(
				"39", camelContext);
		final ExecutorImpl executor39 = new ExecutorImpl("", route39, true);

		final RoutePolicy routePolicy39 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor39.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

				from("file:inputfolder25?delete=true").routeId("25")
						.routePolicy(routePolicy25).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder25");

				from("file:inputfolder26?delete=true").routeId("26")
						.routePolicy(routePolicy26).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder26");

				from("file:inputfolder27?delete=true").routeId("27")
						.routePolicy(routePolicy27).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder27");

				from("file:inputfolder28?delete=true").routeId("28")
						.routePolicy(routePolicy28).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder28");

				from("file:inputfolder29?delete=true").routeId("29")
						.routePolicy(routePolicy29).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder29");

				from("file:inputfolder30?delete=true").routeId("30")
						.routePolicy(routePolicy30).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder30");

				from("file:inputfolder31?delete=true").routeId("31")
						.routePolicy(routePolicy31).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder31");

				from("file:inputfolder32?delete=true").routeId("32")
						.routePolicy(routePolicy32).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder32");

				from("file:inputfolder33?delete=true").routeId("33")
						.routePolicy(routePolicy33).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder33");

				from("file:inputfolder34?delete=true").routeId("34")
						.routePolicy(routePolicy34).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder34");

				from("file:inputfolder35?delete=true").routeId("35")
						.routePolicy(routePolicy35).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder35");

				from("file:inputfolder36?delete=true").routeId("36")
						.routePolicy(routePolicy36).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder36");

				from("file:inputfolder37?delete=true").routeId("37")
						.routePolicy(routePolicy37).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder37");

				from("file:inputfolder38?delete=true").routeId("38")
						.routePolicy(routePolicy38).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder38");

				from("file:inputfolder39?delete=true").routeId("39")
						.routePolicy(routePolicy39).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder39");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		route25.setCamelContext(camelContext);
		Thread t25 = new Thread(executor25, "SW25");
		executor25.setEngine(engine);
		executor25.register(engine);
		route26.setCamelContext(camelContext);
		Thread t26 = new Thread(executor26, "SW26");
		executor26.setEngine(engine);
		executor26.register(engine);
		route27.setCamelContext(camelContext);
		Thread t27 = new Thread(executor27, "SW27");
		executor27.setEngine(engine);
		executor27.register(engine);
		route28.setCamelContext(camelContext);
		Thread t28 = new Thread(executor28, "SW28");
		executor28.setEngine(engine);
		executor28.register(engine);
		route29.setCamelContext(camelContext);
		Thread t29 = new Thread(executor29, "SW29");
		executor29.setEngine(engine);
		executor29.register(engine);
		route30.setCamelContext(camelContext);
		Thread t30 = new Thread(executor30, "SW30");
		executor30.setEngine(engine);
		executor30.register(engine);
		route31.setCamelContext(camelContext);
		Thread t31 = new Thread(executor31, "SW31");
		executor31.setEngine(engine);
		executor31.register(engine);
		route32.setCamelContext(camelContext);
		Thread t32 = new Thread(executor32, "SW32");
		executor32.setEngine(engine);
		executor32.register(engine);
		route33.setCamelContext(camelContext);
		Thread t33 = new Thread(executor33, "SW33");
		executor33.setEngine(engine);
		executor33.register(engine);
		route34.setCamelContext(camelContext);
		Thread t34 = new Thread(executor34, "SW34");
		executor34.setEngine(engine);
		executor34.register(engine);
		route35.setCamelContext(camelContext);
		Thread t35 = new Thread(executor35, "SW35");
		executor35.setEngine(engine);
		executor35.register(engine);
		route36.setCamelContext(camelContext);
		Thread t36 = new Thread(executor36, "SW36");
		executor36.setEngine(engine);
		executor36.register(engine);
		route37.setCamelContext(camelContext);
		Thread t37 = new Thread(executor37, "SW37");
		executor37.setEngine(engine);
		executor37.register(engine);
		route38.setCamelContext(camelContext);
		Thread t38 = new Thread(executor38, "SW38");
		executor38.setEngine(engine);
		executor38.register(engine);
		route39.setCamelContext(camelContext);
		Thread t39 = new Thread(executor39, "SW39");
		executor39.setEngine(engine);
		executor39.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
			t25.start();
			t26.start();
			t27.start();
			t28.start();
			t29.start();
			t30.start();
			t31.start();
			t32.start();
			t33.start();
			t34.start();
			t35.start();
			t36.start();
			t37.start();
			t38.start();
			t39.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(2250);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final ExecutorImpl executor25 = new ExecutorImpl("", route25, true);

		final RoutePolicy routePolicy25 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor25.inform("end");
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
		SwitchableRouteDataTransfers route26 = new SwitchableRouteDataTransfers(
				"26", camelContext);
		final ExecutorImpl executor26 = new ExecutorImpl("", route26, true);

		final RoutePolicy routePolicy26 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor26.inform("end");
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
		SwitchableRouteDataTransfers route27 = new SwitchableRouteDataTransfers(
				"27", camelContext);
		final ExecutorImpl executor27 = new ExecutorImpl("", route27, true);

		final RoutePolicy routePolicy27 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor27.inform("end");
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
		SwitchableRouteDataTransfers route28 = new SwitchableRouteDataTransfers(
				"28", camelContext);
		final ExecutorImpl executor28 = new ExecutorImpl("", route28, true);

		final RoutePolicy routePolicy28 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor28.inform("end");
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
		SwitchableRouteDataTransfers route29 = new SwitchableRouteDataTransfers(
				"29", camelContext);
		final ExecutorImpl executor29 = new ExecutorImpl("", route29, true);

		final RoutePolicy routePolicy29 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor29.inform("end");
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
		SwitchableRouteDataTransfers route30 = new SwitchableRouteDataTransfers(
				"30", camelContext);
		final ExecutorImpl executor30 = new ExecutorImpl("", route30, true);

		final RoutePolicy routePolicy30 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor30.inform("end");
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
		SwitchableRouteDataTransfers route31 = new SwitchableRouteDataTransfers(
				"31", camelContext);
		final ExecutorImpl executor31 = new ExecutorImpl("", route31, true);

		final RoutePolicy routePolicy31 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor31.inform("end");
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
		SwitchableRouteDataTransfers route32 = new SwitchableRouteDataTransfers(
				"32", camelContext);
		final ExecutorImpl executor32 = new ExecutorImpl("", route32, true);

		final RoutePolicy routePolicy32 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor32.inform("end");
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
		SwitchableRouteDataTransfers route33 = new SwitchableRouteDataTransfers(
				"33", camelContext);
		final ExecutorImpl executor33 = new ExecutorImpl("", route33, true);

		final RoutePolicy routePolicy33 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor33.inform("end");
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
		SwitchableRouteDataTransfers route34 = new SwitchableRouteDataTransfers(
				"34", camelContext);
		final ExecutorImpl executor34 = new ExecutorImpl("", route34, true);

		final RoutePolicy routePolicy34 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor34.inform("end");
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
		SwitchableRouteDataTransfers route35 = new SwitchableRouteDataTransfers(
				"35", camelContext);
		final ExecutorImpl executor35 = new ExecutorImpl("", route35, true);

		final RoutePolicy routePolicy35 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor35.inform("end");
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
		SwitchableRouteDataTransfers route36 = new SwitchableRouteDataTransfers(
				"36", camelContext);
		final ExecutorImpl executor36 = new ExecutorImpl("", route36, true);

		final RoutePolicy routePolicy36 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor36.inform("end");
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
		SwitchableRouteDataTransfers route37 = new SwitchableRouteDataTransfers(
				"37", camelContext);
		final ExecutorImpl executor37 = new ExecutorImpl("", route37, true);

		final RoutePolicy routePolicy37 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor37.inform("end");
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
		SwitchableRouteDataTransfers route38 = new SwitchableRouteDataTransfers(
				"38", camelContext);
		final ExecutorImpl executor38 = new ExecutorImpl("", route38, true);

		final RoutePolicy routePolicy38 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor38.inform("end");
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
		SwitchableRouteDataTransfers route39 = new SwitchableRouteDataTransfers(
				"39", camelContext);
		final ExecutorImpl executor39 = new ExecutorImpl("", route39, true);

		final RoutePolicy routePolicy39 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor39.inform("end");
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
		SwitchableRouteDataTransfers route40 = new SwitchableRouteDataTransfers(
				"40", camelContext);
		final ExecutorImpl executor40 = new ExecutorImpl("", route40, true);

		final RoutePolicy routePolicy40 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor40.inform("end");
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
		SwitchableRouteDataTransfers route41 = new SwitchableRouteDataTransfers(
				"41", camelContext);
		final ExecutorImpl executor41 = new ExecutorImpl("", route41, true);

		final RoutePolicy routePolicy41 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor41.inform("end");
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
		SwitchableRouteDataTransfers route42 = new SwitchableRouteDataTransfers(
				"42", camelContext);
		final ExecutorImpl executor42 = new ExecutorImpl("", route42, true);

		final RoutePolicy routePolicy42 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor42.inform("end");
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
		SwitchableRouteDataTransfers route43 = new SwitchableRouteDataTransfers(
				"43", camelContext);
		final ExecutorImpl executor43 = new ExecutorImpl("", route43, true);

		final RoutePolicy routePolicy43 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor43.inform("end");
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
		SwitchableRouteDataTransfers route44 = new SwitchableRouteDataTransfers(
				"44", camelContext);
		final ExecutorImpl executor44 = new ExecutorImpl("", route44, true);

		final RoutePolicy routePolicy44 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor44.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

				from("file:inputfolder25?delete=true").routeId("25")
						.routePolicy(routePolicy25).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder25");

				from("file:inputfolder26?delete=true").routeId("26")
						.routePolicy(routePolicy26).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder26");

				from("file:inputfolder27?delete=true").routeId("27")
						.routePolicy(routePolicy27).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder27");

				from("file:inputfolder28?delete=true").routeId("28")
						.routePolicy(routePolicy28).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder28");

				from("file:inputfolder29?delete=true").routeId("29")
						.routePolicy(routePolicy29).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder29");

				from("file:inputfolder30?delete=true").routeId("30")
						.routePolicy(routePolicy30).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder30");

				from("file:inputfolder31?delete=true").routeId("31")
						.routePolicy(routePolicy31).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder31");

				from("file:inputfolder32?delete=true").routeId("32")
						.routePolicy(routePolicy32).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder32");

				from("file:inputfolder33?delete=true").routeId("33")
						.routePolicy(routePolicy33).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder33");

				from("file:inputfolder34?delete=true").routeId("34")
						.routePolicy(routePolicy34).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder34");

				from("file:inputfolder35?delete=true").routeId("35")
						.routePolicy(routePolicy35).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder35");

				from("file:inputfolder36?delete=true").routeId("36")
						.routePolicy(routePolicy36).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder36");

				from("file:inputfolder37?delete=true").routeId("37")
						.routePolicy(routePolicy37).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder37");

				from("file:inputfolder38?delete=true").routeId("38")
						.routePolicy(routePolicy38).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder38");

				from("file:inputfolder39?delete=true").routeId("39")
						.routePolicy(routePolicy39).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder39");

				from("file:inputfolder40?delete=true").routeId("40")
						.routePolicy(routePolicy40).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder40");

				from("file:inputfolder41?delete=true").routeId("41")
						.routePolicy(routePolicy41).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder41");

				from("file:inputfolder42?delete=true").routeId("42")
						.routePolicy(routePolicy42).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder42");

				from("file:inputfolder43?delete=true").routeId("43")
						.routePolicy(routePolicy43).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder43");

				from("file:inputfolder44?delete=true").routeId("44")
						.routePolicy(routePolicy44).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder44");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		route25.setCamelContext(camelContext);
		Thread t25 = new Thread(executor25, "SW25");
		executor25.setEngine(engine);
		executor25.register(engine);
		route26.setCamelContext(camelContext);
		Thread t26 = new Thread(executor26, "SW26");
		executor26.setEngine(engine);
		executor26.register(engine);
		route27.setCamelContext(camelContext);
		Thread t27 = new Thread(executor27, "SW27");
		executor27.setEngine(engine);
		executor27.register(engine);
		route28.setCamelContext(camelContext);
		Thread t28 = new Thread(executor28, "SW28");
		executor28.setEngine(engine);
		executor28.register(engine);
		route29.setCamelContext(camelContext);
		Thread t29 = new Thread(executor29, "SW29");
		executor29.setEngine(engine);
		executor29.register(engine);
		route30.setCamelContext(camelContext);
		Thread t30 = new Thread(executor30, "SW30");
		executor30.setEngine(engine);
		executor30.register(engine);
		route31.setCamelContext(camelContext);
		Thread t31 = new Thread(executor31, "SW31");
		executor31.setEngine(engine);
		executor31.register(engine);
		route32.setCamelContext(camelContext);
		Thread t32 = new Thread(executor32, "SW32");
		executor32.setEngine(engine);
		executor32.register(engine);
		route33.setCamelContext(camelContext);
		Thread t33 = new Thread(executor33, "SW33");
		executor33.setEngine(engine);
		executor33.register(engine);
		route34.setCamelContext(camelContext);
		Thread t34 = new Thread(executor34, "SW34");
		executor34.setEngine(engine);
		executor34.register(engine);
		route35.setCamelContext(camelContext);
		Thread t35 = new Thread(executor35, "SW35");
		executor35.setEngine(engine);
		executor35.register(engine);
		route36.setCamelContext(camelContext);
		Thread t36 = new Thread(executor36, "SW36");
		executor36.setEngine(engine);
		executor36.register(engine);
		route37.setCamelContext(camelContext);
		Thread t37 = new Thread(executor37, "SW37");
		executor37.setEngine(engine);
		executor37.register(engine);
		route38.setCamelContext(camelContext);
		Thread t38 = new Thread(executor38, "SW38");
		executor38.setEngine(engine);
		executor38.register(engine);
		route39.setCamelContext(camelContext);
		Thread t39 = new Thread(executor39, "SW39");
		executor39.setEngine(engine);
		executor39.register(engine);
		route40.setCamelContext(camelContext);
		Thread t40 = new Thread(executor40, "SW40");
		executor40.setEngine(engine);
		executor40.register(engine);
		route41.setCamelContext(camelContext);
		Thread t41 = new Thread(executor41, "SW41");
		executor41.setEngine(engine);
		executor41.register(engine);
		route42.setCamelContext(camelContext);
		Thread t42 = new Thread(executor42, "SW42");
		executor42.setEngine(engine);
		executor42.register(engine);
		route43.setCamelContext(camelContext);
		Thread t43 = new Thread(executor43, "SW43");
		executor43.setEngine(engine);
		executor43.register(engine);
		route44.setCamelContext(camelContext);
		Thread t44 = new Thread(executor44, "SW44");
		executor44.setEngine(engine);
		executor44.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
			t25.start();
			t26.start();
			t27.start();
			t28.start();
			t29.start();
			t30.start();
			t31.start();
			t32.start();
			t33.start();
			t34.start();
			t35.start();
			t36.start();
			t37.start();
			t38.start();
			t39.start();
			t40.start();
			t41.start();
			t42.start();
			t43.start();
			t44.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest49() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(2450);
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
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers(
				"2", camelContext);
		final ExecutorImpl executor2 = new ExecutorImpl("", route2, true);

		final RoutePolicy routePolicy2 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor2.inform("end");
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
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers(
				"3", camelContext);
		final ExecutorImpl executor3 = new ExecutorImpl("", route3, true);

		final RoutePolicy routePolicy3 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor3.inform("end");
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
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers(
				"4", camelContext);
		final ExecutorImpl executor4 = new ExecutorImpl("", route4, true);

		final RoutePolicy routePolicy4 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor4.inform("end");
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
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers(
				"5", camelContext);
		final ExecutorImpl executor5 = new ExecutorImpl("", route5, true);

		final RoutePolicy routePolicy5 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor5.inform("end");
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
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers(
				"6", camelContext);
		final ExecutorImpl executor6 = new ExecutorImpl("", route6, true);

		final RoutePolicy routePolicy6 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor6.inform("end");
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
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers(
				"7", camelContext);
		final ExecutorImpl executor7 = new ExecutorImpl("", route7, true);

		final RoutePolicy routePolicy7 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor7.inform("end");
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
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers(
				"8", camelContext);
		final ExecutorImpl executor8 = new ExecutorImpl("", route8, true);

		final RoutePolicy routePolicy8 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor8.inform("end");
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
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers(
				"9", camelContext);
		final ExecutorImpl executor9 = new ExecutorImpl("", route9, true);

		final RoutePolicy routePolicy9 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor9.inform("end");
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
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers(
				"10", camelContext);
		final ExecutorImpl executor10 = new ExecutorImpl("", route10, true);

		final RoutePolicy routePolicy10 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor10.inform("end");
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
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers(
				"12", camelContext);
		final ExecutorImpl executor12 = new ExecutorImpl("", route12, true);

		final RoutePolicy routePolicy12 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor12.inform("end");
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
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers(
				"13", camelContext);
		final ExecutorImpl executor13 = new ExecutorImpl("", route13, true);

		final RoutePolicy routePolicy13 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor13.inform("end");
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
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers(
				"14", camelContext);
		final ExecutorImpl executor14 = new ExecutorImpl("", route14, true);

		final RoutePolicy routePolicy14 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor14.inform("end");
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
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers(
				"15", camelContext);
		final ExecutorImpl executor15 = new ExecutorImpl("", route15, true);

		final RoutePolicy routePolicy15 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor15.inform("end");
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
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers(
				"16", camelContext);
		final ExecutorImpl executor16 = new ExecutorImpl("", route16, true);

		final RoutePolicy routePolicy16 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor16.inform("end");
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
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers(
				"17", camelContext);
		final ExecutorImpl executor17 = new ExecutorImpl("", route17, true);

		final RoutePolicy routePolicy17 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor17.inform("end");
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
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers(
				"18", camelContext);
		final ExecutorImpl executor18 = new ExecutorImpl("", route18, true);

		final RoutePolicy routePolicy18 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor18.inform("end");
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
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers(
				"19", camelContext);
		final ExecutorImpl executor19 = new ExecutorImpl("", route19, true);

		final RoutePolicy routePolicy19 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor19.inform("end");
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
		SwitchableRouteDataTransfers route20 = new SwitchableRouteDataTransfers(
				"20", camelContext);
		final ExecutorImpl executor20 = new ExecutorImpl("", route20, true);

		final RoutePolicy routePolicy20 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor20.inform("end");
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
		SwitchableRouteDataTransfers route21 = new SwitchableRouteDataTransfers(
				"21", camelContext);
		final ExecutorImpl executor21 = new ExecutorImpl("", route21, true);

		final RoutePolicy routePolicy21 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor21.inform("end");
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
		SwitchableRouteDataTransfers route22 = new SwitchableRouteDataTransfers(
				"22", camelContext);
		final ExecutorImpl executor22 = new ExecutorImpl("", route22, true);

		final RoutePolicy routePolicy22 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor22.inform("end");
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
		SwitchableRouteDataTransfers route23 = new SwitchableRouteDataTransfers(
				"23", camelContext);
		final ExecutorImpl executor23 = new ExecutorImpl("", route23, true);

		final RoutePolicy routePolicy23 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor23.inform("end");
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
		SwitchableRouteDataTransfers route24 = new SwitchableRouteDataTransfers(
				"24", camelContext);
		final ExecutorImpl executor24 = new ExecutorImpl("", route24, true);

		final RoutePolicy routePolicy24 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor24.inform("end");
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final ExecutorImpl executor25 = new ExecutorImpl("", route25, true);

		final RoutePolicy routePolicy25 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor25.inform("end");
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
		SwitchableRouteDataTransfers route26 = new SwitchableRouteDataTransfers(
				"26", camelContext);
		final ExecutorImpl executor26 = new ExecutorImpl("", route26, true);

		final RoutePolicy routePolicy26 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor26.inform("end");
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
		SwitchableRouteDataTransfers route27 = new SwitchableRouteDataTransfers(
				"27", camelContext);
		final ExecutorImpl executor27 = new ExecutorImpl("", route27, true);

		final RoutePolicy routePolicy27 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor27.inform("end");
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
		SwitchableRouteDataTransfers route28 = new SwitchableRouteDataTransfers(
				"28", camelContext);
		final ExecutorImpl executor28 = new ExecutorImpl("", route28, true);

		final RoutePolicy routePolicy28 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor28.inform("end");
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
		SwitchableRouteDataTransfers route29 = new SwitchableRouteDataTransfers(
				"29", camelContext);
		final ExecutorImpl executor29 = new ExecutorImpl("", route29, true);

		final RoutePolicy routePolicy29 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor29.inform("end");
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
		SwitchableRouteDataTransfers route30 = new SwitchableRouteDataTransfers(
				"30", camelContext);
		final ExecutorImpl executor30 = new ExecutorImpl("", route30, true);

		final RoutePolicy routePolicy30 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor30.inform("end");
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
		SwitchableRouteDataTransfers route31 = new SwitchableRouteDataTransfers(
				"31", camelContext);
		final ExecutorImpl executor31 = new ExecutorImpl("", route31, true);

		final RoutePolicy routePolicy31 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor31.inform("end");
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
		SwitchableRouteDataTransfers route32 = new SwitchableRouteDataTransfers(
				"32", camelContext);
		final ExecutorImpl executor32 = new ExecutorImpl("", route32, true);

		final RoutePolicy routePolicy32 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor32.inform("end");
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
		SwitchableRouteDataTransfers route33 = new SwitchableRouteDataTransfers(
				"33", camelContext);
		final ExecutorImpl executor33 = new ExecutorImpl("", route33, true);

		final RoutePolicy routePolicy33 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor33.inform("end");
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
		SwitchableRouteDataTransfers route34 = new SwitchableRouteDataTransfers(
				"34", camelContext);
		final ExecutorImpl executor34 = new ExecutorImpl("", route34, true);

		final RoutePolicy routePolicy34 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor34.inform("end");
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
		SwitchableRouteDataTransfers route35 = new SwitchableRouteDataTransfers(
				"35", camelContext);
		final ExecutorImpl executor35 = new ExecutorImpl("", route35, true);

		final RoutePolicy routePolicy35 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor35.inform("end");
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
		SwitchableRouteDataTransfers route36 = new SwitchableRouteDataTransfers(
				"36", camelContext);
		final ExecutorImpl executor36 = new ExecutorImpl("", route36, true);

		final RoutePolicy routePolicy36 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor36.inform("end");
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
		SwitchableRouteDataTransfers route37 = new SwitchableRouteDataTransfers(
				"37", camelContext);
		final ExecutorImpl executor37 = new ExecutorImpl("", route37, true);

		final RoutePolicy routePolicy37 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor37.inform("end");
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
		SwitchableRouteDataTransfers route38 = new SwitchableRouteDataTransfers(
				"38", camelContext);
		final ExecutorImpl executor38 = new ExecutorImpl("", route38, true);

		final RoutePolicy routePolicy38 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor38.inform("end");
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
		SwitchableRouteDataTransfers route39 = new SwitchableRouteDataTransfers(
				"39", camelContext);
		final ExecutorImpl executor39 = new ExecutorImpl("", route39, true);

		final RoutePolicy routePolicy39 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor39.inform("end");
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
		SwitchableRouteDataTransfers route40 = new SwitchableRouteDataTransfers(
				"40", camelContext);
		final ExecutorImpl executor40 = new ExecutorImpl("", route40, true);

		final RoutePolicy routePolicy40 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor40.inform("end");
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
		SwitchableRouteDataTransfers route41 = new SwitchableRouteDataTransfers(
				"41", camelContext);
		final ExecutorImpl executor41 = new ExecutorImpl("", route41, true);

		final RoutePolicy routePolicy41 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor41.inform("end");
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
		SwitchableRouteDataTransfers route42 = new SwitchableRouteDataTransfers(
				"42", camelContext);
		final ExecutorImpl executor42 = new ExecutorImpl("", route42, true);

		final RoutePolicy routePolicy42 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor42.inform("end");
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
		SwitchableRouteDataTransfers route43 = new SwitchableRouteDataTransfers(
				"43", camelContext);
		final ExecutorImpl executor43 = new ExecutorImpl("", route43, true);

		final RoutePolicy routePolicy43 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor43.inform("end");
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
		SwitchableRouteDataTransfers route44 = new SwitchableRouteDataTransfers(
				"44", camelContext);
		final ExecutorImpl executor44 = new ExecutorImpl("", route44, true);

		final RoutePolicy routePolicy44 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor44.inform("end");
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
		SwitchableRouteDataTransfers route45 = new SwitchableRouteDataTransfers(
				"45", camelContext);
		final ExecutorImpl executor45 = new ExecutorImpl("", route45, true);

		final RoutePolicy routePolicy45 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor45.inform("end");
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
		SwitchableRouteDataTransfers route46 = new SwitchableRouteDataTransfers(
				"46", camelContext);
		final ExecutorImpl executor46 = new ExecutorImpl("", route46, true);

		final RoutePolicy routePolicy46 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor46.inform("end");
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
		SwitchableRouteDataTransfers route47 = new SwitchableRouteDataTransfers(
				"47", camelContext);
		final ExecutorImpl executor47 = new ExecutorImpl("", route47, true);

		final RoutePolicy routePolicy47 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor47.inform("end");
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
		SwitchableRouteDataTransfers route48 = new SwitchableRouteDataTransfers(
				"48", camelContext);
		final ExecutorImpl executor48 = new ExecutorImpl("", route48, true);

		final RoutePolicy routePolicy48 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor48.inform("end");
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
		SwitchableRouteDataTransfers route49 = new SwitchableRouteDataTransfers(
				"49", camelContext);
		final ExecutorImpl executor49 = new ExecutorImpl("", route49, true);

		final RoutePolicy routePolicy49 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor49.inform("end");
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

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder3");

				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder4");

				from("file:inputfolder5?delete=true").routeId("5")
						.routePolicy(routePolicy5).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder5");

				from("file:inputfolder6?delete=true").routeId("6")
						.routePolicy(routePolicy6).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder6");

				from("file:inputfolder7?delete=true").routeId("7")
						.routePolicy(routePolicy7).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder7");

				from("file:inputfolder8?delete=true").routeId("8")
						.routePolicy(routePolicy8).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder8");

				from("file:inputfolder9?delete=true").routeId("9")
						.routePolicy(routePolicy9).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder9");

				from("file:inputfolder10?delete=true").routeId("10")
						.routePolicy(routePolicy10).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder10");

				from("file:inputfolder11?delete=true").routeId("11")
						.routePolicy(routePolicy11).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder11");

				from("file:inputfolder12?delete=true").routeId("12")
						.routePolicy(routePolicy12).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder12");

				from("file:inputfolder13?delete=true").routeId("13")
						.routePolicy(routePolicy13).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder13");

				from("file:inputfolder14?delete=true").routeId("14")
						.routePolicy(routePolicy14).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder14");

				from("file:inputfolder15?delete=true").routeId("15")
						.routePolicy(routePolicy15).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder15");

				from("file:inputfolder16?delete=true").routeId("16")
						.routePolicy(routePolicy16).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder16");

				from("file:inputfolder17?delete=true").routeId("17")
						.routePolicy(routePolicy17).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder17");

				from("file:inputfolder18?delete=true").routeId("18")
						.routePolicy(routePolicy18).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder18");

				from("file:inputfolder19?delete=true").routeId("19")
						.routePolicy(routePolicy19).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder19");

				from("file:inputfolder20?delete=true").routeId("20")
						.routePolicy(routePolicy20).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder20");

				from("file:inputfolder21?delete=true").routeId("21")
						.routePolicy(routePolicy21).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder21");

				from("file:inputfolder22?delete=true").routeId("22")
						.routePolicy(routePolicy22).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder22");

				from("file:inputfolder23?delete=true").routeId("23")
						.routePolicy(routePolicy23).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder23");

				from("file:inputfolder24?delete=true").routeId("24")
						.routePolicy(routePolicy24).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder24");

				from("file:inputfolder25?delete=true").routeId("25")
						.routePolicy(routePolicy25).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder25");

				from("file:inputfolder26?delete=true").routeId("26")
						.routePolicy(routePolicy26).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder26");

				from("file:inputfolder27?delete=true").routeId("27")
						.routePolicy(routePolicy27).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder27");

				from("file:inputfolder28?delete=true").routeId("28")
						.routePolicy(routePolicy28).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder28");

				from("file:inputfolder29?delete=true").routeId("29")
						.routePolicy(routePolicy29).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder29");

				from("file:inputfolder30?delete=true").routeId("30")
						.routePolicy(routePolicy30).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder30");

				from("file:inputfolder31?delete=true").routeId("31")
						.routePolicy(routePolicy31).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder31");

				from("file:inputfolder32?delete=true").routeId("32")
						.routePolicy(routePolicy32).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder32");

				from("file:inputfolder33?delete=true").routeId("33")
						.routePolicy(routePolicy33).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder33");

				from("file:inputfolder34?delete=true").routeId("34")
						.routePolicy(routePolicy34).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder34");

				from("file:inputfolder35?delete=true").routeId("35")
						.routePolicy(routePolicy35).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder35");

				from("file:inputfolder36?delete=true").routeId("36")
						.routePolicy(routePolicy36).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder36");

				from("file:inputfolder37?delete=true").routeId("37")
						.routePolicy(routePolicy37).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder37");

				from("file:inputfolder38?delete=true").routeId("38")
						.routePolicy(routePolicy38).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder38");

				from("file:inputfolder39?delete=true").routeId("39")
						.routePolicy(routePolicy39).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder39");

				from("file:inputfolder40?delete=true").routeId("40")
						.routePolicy(routePolicy40).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder40");

				from("file:inputfolder41?delete=true").routeId("41")
						.routePolicy(routePolicy41).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder41");

				from("file:inputfolder42?delete=true").routeId("42")
						.routePolicy(routePolicy42).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder42");

				from("file:inputfolder43?delete=true").routeId("43")
						.routePolicy(routePolicy43).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder43");

				from("file:inputfolder44?delete=true").routeId("44")
						.routePolicy(routePolicy44).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder44");

				from("file:inputfolder45?delete=true").routeId("45")
						.routePolicy(routePolicy45).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder45");

				from("file:inputfolder46?delete=true").routeId("46")
						.routePolicy(routePolicy46).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder46");

				from("file:inputfolder47?delete=true").routeId("47")
						.routePolicy(routePolicy47).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder47");

				from("file:inputfolder48?delete=true").routeId("48")
						.routePolicy(routePolicy48).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder48");

				from("file:inputfolder49?delete=true").routeId("49")
						.routePolicy(routePolicy49).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder49");

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
		route2.setCamelContext(camelContext);
		Thread t2 = new Thread(executor2, "SW2");
		executor2.setEngine(engine);
		executor2.register(engine);
		route3.setCamelContext(camelContext);
		Thread t3 = new Thread(executor3, "SW3");
		executor3.setEngine(engine);
		executor3.register(engine);
		route4.setCamelContext(camelContext);
		Thread t4 = new Thread(executor4, "SW4");
		executor4.setEngine(engine);
		executor4.register(engine);
		route5.setCamelContext(camelContext);
		Thread t5 = new Thread(executor5, "SW5");
		executor5.setEngine(engine);
		executor5.register(engine);
		route6.setCamelContext(camelContext);
		Thread t6 = new Thread(executor6, "SW6");
		executor6.setEngine(engine);
		executor6.register(engine);
		route7.setCamelContext(camelContext);
		Thread t7 = new Thread(executor7, "SW7");
		executor7.setEngine(engine);
		executor7.register(engine);
		route8.setCamelContext(camelContext);
		Thread t8 = new Thread(executor8, "SW8");
		executor8.setEngine(engine);
		executor8.register(engine);
		route9.setCamelContext(camelContext);
		Thread t9 = new Thread(executor9, "SW9");
		executor9.setEngine(engine);
		executor9.register(engine);
		route10.setCamelContext(camelContext);
		Thread t10 = new Thread(executor10, "SW10");
		executor10.setEngine(engine);
		executor10.register(engine);
		route11.setCamelContext(camelContext);
		Thread t11 = new Thread(executor11, "SW11");
		executor11.setEngine(engine);
		executor11.register(engine);
		route12.setCamelContext(camelContext);
		Thread t12 = new Thread(executor12, "SW12");
		executor12.setEngine(engine);
		executor12.register(engine);
		route13.setCamelContext(camelContext);
		Thread t13 = new Thread(executor13, "SW13");
		executor13.setEngine(engine);
		executor13.register(engine);
		route14.setCamelContext(camelContext);
		Thread t14 = new Thread(executor14, "SW14");
		executor14.setEngine(engine);
		executor14.register(engine);
		route15.setCamelContext(camelContext);
		Thread t15 = new Thread(executor15, "SW15");
		executor15.setEngine(engine);
		executor15.register(engine);
		route16.setCamelContext(camelContext);
		Thread t16 = new Thread(executor16, "SW16");
		executor16.setEngine(engine);
		executor16.register(engine);
		route17.setCamelContext(camelContext);
		Thread t17 = new Thread(executor17, "SW17");
		executor17.setEngine(engine);
		executor17.register(engine);
		route18.setCamelContext(camelContext);
		Thread t18 = new Thread(executor18, "SW18");
		executor18.setEngine(engine);
		executor18.register(engine);
		route19.setCamelContext(camelContext);
		Thread t19 = new Thread(executor19, "SW19");
		executor19.setEngine(engine);
		executor19.register(engine);
		route20.setCamelContext(camelContext);
		Thread t20 = new Thread(executor20, "SW20");
		executor20.setEngine(engine);
		executor20.register(engine);
		route21.setCamelContext(camelContext);
		Thread t21 = new Thread(executor21, "SW21");
		executor21.setEngine(engine);
		executor21.register(engine);
		route22.setCamelContext(camelContext);
		Thread t22 = new Thread(executor22, "SW22");
		executor22.setEngine(engine);
		executor22.register(engine);
		route23.setCamelContext(camelContext);
		Thread t23 = new Thread(executor23, "SW23");
		executor23.setEngine(engine);
		executor23.register(engine);
		route24.setCamelContext(camelContext);
		Thread t24 = new Thread(executor24, "SW24");
		executor24.setEngine(engine);
		executor24.register(engine);
		route25.setCamelContext(camelContext);
		Thread t25 = new Thread(executor25, "SW25");
		executor25.setEngine(engine);
		executor25.register(engine);
		route26.setCamelContext(camelContext);
		Thread t26 = new Thread(executor26, "SW26");
		executor26.setEngine(engine);
		executor26.register(engine);
		route27.setCamelContext(camelContext);
		Thread t27 = new Thread(executor27, "SW27");
		executor27.setEngine(engine);
		executor27.register(engine);
		route28.setCamelContext(camelContext);
		Thread t28 = new Thread(executor28, "SW28");
		executor28.setEngine(engine);
		executor28.register(engine);
		route29.setCamelContext(camelContext);
		Thread t29 = new Thread(executor29, "SW29");
		executor29.setEngine(engine);
		executor29.register(engine);
		route30.setCamelContext(camelContext);
		Thread t30 = new Thread(executor30, "SW30");
		executor30.setEngine(engine);
		executor30.register(engine);
		route31.setCamelContext(camelContext);
		Thread t31 = new Thread(executor31, "SW31");
		executor31.setEngine(engine);
		executor31.register(engine);
		route32.setCamelContext(camelContext);
		Thread t32 = new Thread(executor32, "SW32");
		executor32.setEngine(engine);
		executor32.register(engine);
		route33.setCamelContext(camelContext);
		Thread t33 = new Thread(executor33, "SW33");
		executor33.setEngine(engine);
		executor33.register(engine);
		route34.setCamelContext(camelContext);
		Thread t34 = new Thread(executor34, "SW34");
		executor34.setEngine(engine);
		executor34.register(engine);
		route35.setCamelContext(camelContext);
		Thread t35 = new Thread(executor35, "SW35");
		executor35.setEngine(engine);
		executor35.register(engine);
		route36.setCamelContext(camelContext);
		Thread t36 = new Thread(executor36, "SW36");
		executor36.setEngine(engine);
		executor36.register(engine);
		route37.setCamelContext(camelContext);
		Thread t37 = new Thread(executor37, "SW37");
		executor37.setEngine(engine);
		executor37.register(engine);
		route38.setCamelContext(camelContext);
		Thread t38 = new Thread(executor38, "SW38");
		executor38.setEngine(engine);
		executor38.register(engine);
		route39.setCamelContext(camelContext);
		Thread t39 = new Thread(executor39, "SW39");
		executor39.setEngine(engine);
		executor39.register(engine);
		route40.setCamelContext(camelContext);
		Thread t40 = new Thread(executor40, "SW40");
		executor40.setEngine(engine);
		executor40.register(engine);
		route41.setCamelContext(camelContext);
		Thread t41 = new Thread(executor41, "SW41");
		executor41.setEngine(engine);
		executor41.register(engine);
		route42.setCamelContext(camelContext);
		Thread t42 = new Thread(executor42, "SW42");
		executor42.setEngine(engine);
		executor42.register(engine);
		route43.setCamelContext(camelContext);
		Thread t43 = new Thread(executor43, "SW43");
		executor43.setEngine(engine);
		executor43.register(engine);
		route44.setCamelContext(camelContext);
		Thread t44 = new Thread(executor44, "SW44");
		executor44.setEngine(engine);
		executor44.register(engine);
		route45.setCamelContext(camelContext);
		Thread t45 = new Thread(executor45, "SW45");
		executor45.setEngine(engine);
		executor45.register(engine);
		route46.setCamelContext(camelContext);
		Thread t46 = new Thread(executor46, "SW46");
		executor46.setEngine(engine);
		executor46.register(engine);
		route47.setCamelContext(camelContext);
		Thread t47 = new Thread(executor47, "SW47");
		executor47.setEngine(engine);
		executor47.register(engine);
		route48.setCamelContext(camelContext);
		Thread t48 = new Thread(executor48, "SW48");
		executor48.setEngine(engine);
		executor48.register(engine);
		route49.setCamelContext(camelContext);
		Thread t49 = new Thread(executor49, "SW49");
		executor49.setEngine(engine);
		executor49.register(engine);
		engine.specifyGlue(bipGlue);
		engine.start();
		try {
			tM.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();
			t8.start();
			t9.start();
			t10.start();
			t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();
			t18.start();
			t19.start();
			t20.start();
			t21.start();
			t22.start();
			t23.start();
			t24.start();
			t25.start();
			t26.start();
			t27.start();
			t28.start();
			t29.start();
			t30.start();
			t31.start();
			t32.start();
			t33.start();
			t34.start();
			t35.start();
			t36.start();
			t37.start();
			t38.start();
			t39.start();
			t40.start();
			t41.start();
			t42.start();
			t43.start();
			t44.start();
			t45.start();
			t46.start();
			t47.start();
			t48.start();
			t49.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}
}