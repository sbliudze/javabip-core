package org.bip.executor;

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
import org.bip.engine.api.EngineFactory;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.MemoryMonitor;
import org.junit.Test;

import akka.actor.ActorSystem;

public class ManyDataRoutesTests {

	@Test
	public void bipSwMultiTest24() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1250);
		final Executor executorM = factory.create(engine, routeOnOffMonitor,
				"monitor", true);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers(
				"1", camelContext);
		final Executor executor1 = factory.create(engine, route1, "1", true);

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
		final Executor executor2 = factory.create(engine, route2, "2", true);

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
		final Executor executor3 = factory.create(engine, route3, "3", true);

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
		final Executor executor4 = factory.create(engine, route4, "4", true);

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
		final Executor executor5 = factory.create(engine, route5, "5", true);

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
		final Executor executor6 = factory.create(engine, route6, "6", true);

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
		final Executor executor7 = factory.create(engine, route7, "7", true);

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
		final Executor executor8 = factory.create(engine, route8, "8", true);

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
		final Executor executor9 = factory.create(engine, route9, "9", true);

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
		final Executor executor10 = factory.create(engine, route10, "10", true);

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
		final Executor executor11 = factory.create(engine, route11, "11", true);

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
		final Executor executor12 = factory.create(engine, route12, "12", true);

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
		final Executor executor13 = factory.create(engine, route13, "13", true);

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
		final Executor executor14 = factory.create(engine, route14, "14", true);

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
		final Executor executor15 = factory.create(engine, route15, "15", true);

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
		final Executor executor16 = factory.create(engine, route16, "16", true);

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
		final Executor executor17 = factory.create(engine, route17, "17", true);

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
		final Executor executor18 = factory.create(engine, route18, "18", true);

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
		final Executor executor19 = factory.create(engine, route19, "19", true);

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
		final Executor executor20 = factory.create(engine, route20, "20", true);

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
		final Executor executor21 = factory.create(engine, route21, "21", true);

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
		final Executor executor22 = factory.create(engine, route22, "22", true);

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
		final Executor executor23 = factory.create(engine, route23, "23", true);

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
		final Executor executor24 = factory.create(engine, route24, "24", true);

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
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		route1.setCamelContext(camelContext);
		route2.setCamelContext(camelContext);
		route3.setCamelContext(camelContext);
		route4.setCamelContext(camelContext);
		route5.setCamelContext(camelContext);
		route6.setCamelContext(camelContext);
		route7.setCamelContext(camelContext);
		route8.setCamelContext(camelContext);
		route9.setCamelContext(camelContext);
		route10.setCamelContext(camelContext);
		route11.setCamelContext(camelContext);
		route12.setCamelContext(camelContext);
		route13.setCamelContext(camelContext);
		route14.setCamelContext(camelContext);
		route15.setCamelContext(camelContext);
		route16.setCamelContext(camelContext);
		route17.setCamelContext(camelContext);
		route18.setCamelContext(camelContext);
		route19.setCamelContext(camelContext);
		route20.setCamelContext(camelContext);
		route21.setCamelContext(camelContext);
		route22.setCamelContext(camelContext);
		route23.setCamelContext(camelContext);
		route24.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(12000);
	}
}