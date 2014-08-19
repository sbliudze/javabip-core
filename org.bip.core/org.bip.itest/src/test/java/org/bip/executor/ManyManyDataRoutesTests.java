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
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.junit.Test;

import akka.actor.ActorSystem;

public class ManyManyDataRoutesTests {

	@Test
	public void bipSwMultiTest64() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3250);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest69() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3450);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest74() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3750);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest79() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4050);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route75 = new SwitchableRouteDataTransfers(
				"75", camelContext);
		final Executor executor75 = factory.create(engine, route75, "75", true);

		final RoutePolicy routePolicy75 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor75.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route76 = new SwitchableRouteDataTransfers(
				"76", camelContext);
		final Executor executor76 = factory.create(engine, route76, "76", true);

		final RoutePolicy routePolicy76 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor76.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route77 = new SwitchableRouteDataTransfers(
				"77", camelContext);
		final Executor executor77 = factory.create(engine, route77, "77", true);

		final RoutePolicy routePolicy77 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor77.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route78 = new SwitchableRouteDataTransfers(
				"78", camelContext);
		final Executor executor78 = factory.create(engine, route78, "78", true);

		final RoutePolicy routePolicy78 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor78.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route79 = new SwitchableRouteDataTransfers(
				"79", camelContext);
		final Executor executor79 = factory.create(engine, route79, "79", true);

		final RoutePolicy routePolicy79 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor79.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

				from("file:inputfolder75?delete=true").routeId("75")
						.routePolicy(routePolicy75).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder75");

				from("file:inputfolder76?delete=true").routeId("76")
						.routePolicy(routePolicy76).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder76");

				from("file:inputfolder77?delete=true").routeId("77")
						.routePolicy(routePolicy77).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder77");

				from("file:inputfolder78?delete=true").routeId("78")
						.routePolicy(routePolicy78).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder78");

				from("file:inputfolder79?delete=true").routeId("79")
						.routePolicy(routePolicy79).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder79");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		route75.setCamelContext(camelContext);
		route76.setCamelContext(camelContext);
		route77.setCamelContext(camelContext);
		route78.setCamelContext(camelContext);
		route79.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest84() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4250);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route75 = new SwitchableRouteDataTransfers(
				"75", camelContext);
		final Executor executor75 = factory.create(engine, route75, "75", true);

		final RoutePolicy routePolicy75 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor75.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route76 = new SwitchableRouteDataTransfers(
				"76", camelContext);
		final Executor executor76 = factory.create(engine, route76, "76", true);

		final RoutePolicy routePolicy76 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor76.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route77 = new SwitchableRouteDataTransfers(
				"77", camelContext);
		final Executor executor77 = factory.create(engine, route77, "77", true);

		final RoutePolicy routePolicy77 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor77.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route78 = new SwitchableRouteDataTransfers(
				"78", camelContext);
		final Executor executor78 = factory.create(engine, route78, "78", true);

		final RoutePolicy routePolicy78 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor78.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route79 = new SwitchableRouteDataTransfers(
				"79", camelContext);
		final Executor executor79 = factory.create(engine, route79, "79", true);

		final RoutePolicy routePolicy79 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor79.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route80 = new SwitchableRouteDataTransfers(
				"80", camelContext);
		final Executor executor80 = factory.create(engine, route80, "80", true);

		final RoutePolicy routePolicy80 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor80.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route81 = new SwitchableRouteDataTransfers(
				"81", camelContext);
		final Executor executor81 = factory.create(engine, route81, "81", true);

		final RoutePolicy routePolicy81 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor81.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route82 = new SwitchableRouteDataTransfers(
				"82", camelContext);
		final Executor executor82 = factory.create(engine, route82, "82", true);

		final RoutePolicy routePolicy82 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor82.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route83 = new SwitchableRouteDataTransfers(
				"83", camelContext);
		final Executor executor83 = factory.create(engine, route83, "83", true);

		final RoutePolicy routePolicy83 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor83.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route84 = new SwitchableRouteDataTransfers(
				"84", camelContext);
		final Executor executor84 = factory.create(engine, route84, "84", true);

		final RoutePolicy routePolicy84 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor84.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

				from("file:inputfolder75?delete=true").routeId("75")
						.routePolicy(routePolicy75).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder75");

				from("file:inputfolder76?delete=true").routeId("76")
						.routePolicy(routePolicy76).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder76");

				from("file:inputfolder77?delete=true").routeId("77")
						.routePolicy(routePolicy77).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder77");

				from("file:inputfolder78?delete=true").routeId("78")
						.routePolicy(routePolicy78).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder78");

				from("file:inputfolder79?delete=true").routeId("79")
						.routePolicy(routePolicy79).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder79");

				from("file:inputfolder80?delete=true").routeId("80")
						.routePolicy(routePolicy80).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder80");

				from("file:inputfolder81?delete=true").routeId("81")
						.routePolicy(routePolicy81).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder81");

				from("file:inputfolder82?delete=true").routeId("82")
						.routePolicy(routePolicy82).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder82");

				from("file:inputfolder83?delete=true").routeId("83")
						.routePolicy(routePolicy83).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder83");

				from("file:inputfolder84?delete=true").routeId("84")
						.routePolicy(routePolicy84).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder84");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		route75.setCamelContext(camelContext);
		route76.setCamelContext(camelContext);
		route77.setCamelContext(camelContext);
		route78.setCamelContext(camelContext);
		route79.setCamelContext(camelContext);
		route80.setCamelContext(camelContext);
		route81.setCamelContext(camelContext);
		route82.setCamelContext(camelContext);
		route83.setCamelContext(camelContext);
		route84.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest89() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4550);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route75 = new SwitchableRouteDataTransfers(
				"75", camelContext);
		final Executor executor75 = factory.create(engine, route75, "75", true);

		final RoutePolicy routePolicy75 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor75.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route76 = new SwitchableRouteDataTransfers(
				"76", camelContext);
		final Executor executor76 = factory.create(engine, route76, "76", true);

		final RoutePolicy routePolicy76 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor76.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route77 = new SwitchableRouteDataTransfers(
				"77", camelContext);
		final Executor executor77 = factory.create(engine, route77, "77", true);

		final RoutePolicy routePolicy77 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor77.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route78 = new SwitchableRouteDataTransfers(
				"78", camelContext);
		final Executor executor78 = factory.create(engine, route78, "78", true);

		final RoutePolicy routePolicy78 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor78.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route79 = new SwitchableRouteDataTransfers(
				"79", camelContext);
		final Executor executor79 = factory.create(engine, route79, "79", true);

		final RoutePolicy routePolicy79 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor79.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route80 = new SwitchableRouteDataTransfers(
				"80", camelContext);
		final Executor executor80 = factory.create(engine, route80, "80", true);

		final RoutePolicy routePolicy80 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor80.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route81 = new SwitchableRouteDataTransfers(
				"81", camelContext);
		final Executor executor81 = factory.create(engine, route81, "81", true);

		final RoutePolicy routePolicy81 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor81.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route82 = new SwitchableRouteDataTransfers(
				"82", camelContext);
		final Executor executor82 = factory.create(engine, route82, "82", true);

		final RoutePolicy routePolicy82 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor82.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route83 = new SwitchableRouteDataTransfers(
				"83", camelContext);
		final Executor executor83 = factory.create(engine, route83, "83", true);

		final RoutePolicy routePolicy83 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor83.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route84 = new SwitchableRouteDataTransfers(
				"84", camelContext);
		final Executor executor84 = factory.create(engine, route84, "84", true);

		final RoutePolicy routePolicy84 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor84.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route85 = new SwitchableRouteDataTransfers(
				"85", camelContext);
		final Executor executor85 = factory.create(engine, route85, "85", true);

		final RoutePolicy routePolicy85 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor85.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route86 = new SwitchableRouteDataTransfers(
				"86", camelContext);
		final Executor executor86 = factory.create(engine, route86, "86", true);

		final RoutePolicy routePolicy86 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor86.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route87 = new SwitchableRouteDataTransfers(
				"87", camelContext);
		final Executor executor87 = factory.create(engine, route87, "87", true);

		final RoutePolicy routePolicy87 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor87.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route88 = new SwitchableRouteDataTransfers(
				"88", camelContext);
		final Executor executor88 = factory.create(engine, route88, "88", true);

		final RoutePolicy routePolicy88 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor88.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route89 = new SwitchableRouteDataTransfers(
				"89", camelContext);
		final Executor executor89 = factory.create(engine, route89, "89", true);

		final RoutePolicy routePolicy89 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor89.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

				from("file:inputfolder75?delete=true").routeId("75")
						.routePolicy(routePolicy75).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder75");

				from("file:inputfolder76?delete=true").routeId("76")
						.routePolicy(routePolicy76).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder76");

				from("file:inputfolder77?delete=true").routeId("77")
						.routePolicy(routePolicy77).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder77");

				from("file:inputfolder78?delete=true").routeId("78")
						.routePolicy(routePolicy78).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder78");

				from("file:inputfolder79?delete=true").routeId("79")
						.routePolicy(routePolicy79).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder79");

				from("file:inputfolder80?delete=true").routeId("80")
						.routePolicy(routePolicy80).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder80");

				from("file:inputfolder81?delete=true").routeId("81")
						.routePolicy(routePolicy81).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder81");

				from("file:inputfolder82?delete=true").routeId("82")
						.routePolicy(routePolicy82).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder82");

				from("file:inputfolder83?delete=true").routeId("83")
						.routePolicy(routePolicy83).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder83");

				from("file:inputfolder84?delete=true").routeId("84")
						.routePolicy(routePolicy84).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder84");

				from("file:inputfolder85?delete=true").routeId("85")
						.routePolicy(routePolicy85).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder85");

				from("file:inputfolder86?delete=true").routeId("86")
						.routePolicy(routePolicy86).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder86");

				from("file:inputfolder87?delete=true").routeId("87")
						.routePolicy(routePolicy87).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder87");

				from("file:inputfolder88?delete=true").routeId("88")
						.routePolicy(routePolicy88).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder88");

				from("file:inputfolder89?delete=true").routeId("89")
						.routePolicy(routePolicy89).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder89");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		route75.setCamelContext(camelContext);
		route76.setCamelContext(camelContext);
		route77.setCamelContext(camelContext);
		route78.setCamelContext(camelContext);
		route79.setCamelContext(camelContext);
		route80.setCamelContext(camelContext);
		route81.setCamelContext(camelContext);
		route82.setCamelContext(camelContext);
		route83.setCamelContext(camelContext);
		route84.setCamelContext(camelContext);
		route85.setCamelContext(camelContext);
		route86.setCamelContext(camelContext);
		route87.setCamelContext(camelContext);
		route88.setCamelContext(camelContext);
		route89.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest94() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4750);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route75 = new SwitchableRouteDataTransfers(
				"75", camelContext);
		final Executor executor75 = factory.create(engine, route75, "75", true);

		final RoutePolicy routePolicy75 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor75.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route76 = new SwitchableRouteDataTransfers(
				"76", camelContext);
		final Executor executor76 = factory.create(engine, route76, "76", true);

		final RoutePolicy routePolicy76 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor76.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route77 = new SwitchableRouteDataTransfers(
				"77", camelContext);
		final Executor executor77 = factory.create(engine, route77, "77", true);

		final RoutePolicy routePolicy77 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor77.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route78 = new SwitchableRouteDataTransfers(
				"78", camelContext);
		final Executor executor78 = factory.create(engine, route78, "78", true);

		final RoutePolicy routePolicy78 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor78.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route79 = new SwitchableRouteDataTransfers(
				"79", camelContext);
		final Executor executor79 = factory.create(engine, route79, "79", true);

		final RoutePolicy routePolicy79 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor79.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route80 = new SwitchableRouteDataTransfers(
				"80", camelContext);
		final Executor executor80 = factory.create(engine, route80, "80", true);

		final RoutePolicy routePolicy80 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor80.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route81 = new SwitchableRouteDataTransfers(
				"81", camelContext);
		final Executor executor81 = factory.create(engine, route81, "81", true);

		final RoutePolicy routePolicy81 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor81.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route82 = new SwitchableRouteDataTransfers(
				"82", camelContext);
		final Executor executor82 = factory.create(engine, route82, "82", true);

		final RoutePolicy routePolicy82 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor82.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route83 = new SwitchableRouteDataTransfers(
				"83", camelContext);
		final Executor executor83 = factory.create(engine, route83, "83", true);

		final RoutePolicy routePolicy83 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor83.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route84 = new SwitchableRouteDataTransfers(
				"84", camelContext);
		final Executor executor84 = factory.create(engine, route84, "84", true);

		final RoutePolicy routePolicy84 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor84.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route85 = new SwitchableRouteDataTransfers(
				"85", camelContext);
		final Executor executor85 = factory.create(engine, route85, "85", true);

		final RoutePolicy routePolicy85 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor85.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route86 = new SwitchableRouteDataTransfers(
				"86", camelContext);
		final Executor executor86 = factory.create(engine, route86, "86", true);

		final RoutePolicy routePolicy86 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor86.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route87 = new SwitchableRouteDataTransfers(
				"87", camelContext);
		final Executor executor87 = factory.create(engine, route87, "87", true);

		final RoutePolicy routePolicy87 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor87.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route88 = new SwitchableRouteDataTransfers(
				"88", camelContext);
		final Executor executor88 = factory.create(engine, route88, "88", true);

		final RoutePolicy routePolicy88 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor88.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route89 = new SwitchableRouteDataTransfers(
				"89", camelContext);
		final Executor executor89 = factory.create(engine, route89, "89", true);

		final RoutePolicy routePolicy89 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor89.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route90 = new SwitchableRouteDataTransfers(
				"90", camelContext);
		final Executor executor90 = factory.create(engine, route90, "90", true);

		final RoutePolicy routePolicy90 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor90.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route91 = new SwitchableRouteDataTransfers(
				"91", camelContext);
		final Executor executor91 = factory.create(engine, route91, "91", true);

		final RoutePolicy routePolicy91 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor91.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route92 = new SwitchableRouteDataTransfers(
				"92", camelContext);
		final Executor executor92 = factory.create(engine, route92, "92", true);

		final RoutePolicy routePolicy92 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor92.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route93 = new SwitchableRouteDataTransfers(
				"93", camelContext);
		final Executor executor93 = factory.create(engine, route93, "93", true);

		final RoutePolicy routePolicy93 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor93.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route94 = new SwitchableRouteDataTransfers(
				"94", camelContext);
		final Executor executor94 = factory.create(engine, route94, "94", true);

		final RoutePolicy routePolicy94 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor94.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

				from("file:inputfolder75?delete=true").routeId("75")
						.routePolicy(routePolicy75).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder75");

				from("file:inputfolder76?delete=true").routeId("76")
						.routePolicy(routePolicy76).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder76");

				from("file:inputfolder77?delete=true").routeId("77")
						.routePolicy(routePolicy77).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder77");

				from("file:inputfolder78?delete=true").routeId("78")
						.routePolicy(routePolicy78).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder78");

				from("file:inputfolder79?delete=true").routeId("79")
						.routePolicy(routePolicy79).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder79");

				from("file:inputfolder80?delete=true").routeId("80")
						.routePolicy(routePolicy80).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder80");

				from("file:inputfolder81?delete=true").routeId("81")
						.routePolicy(routePolicy81).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder81");

				from("file:inputfolder82?delete=true").routeId("82")
						.routePolicy(routePolicy82).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder82");

				from("file:inputfolder83?delete=true").routeId("83")
						.routePolicy(routePolicy83).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder83");

				from("file:inputfolder84?delete=true").routeId("84")
						.routePolicy(routePolicy84).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder84");

				from("file:inputfolder85?delete=true").routeId("85")
						.routePolicy(routePolicy85).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder85");

				from("file:inputfolder86?delete=true").routeId("86")
						.routePolicy(routePolicy86).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder86");

				from("file:inputfolder87?delete=true").routeId("87")
						.routePolicy(routePolicy87).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder87");

				from("file:inputfolder88?delete=true").routeId("88")
						.routePolicy(routePolicy88).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder88");

				from("file:inputfolder89?delete=true").routeId("89")
						.routePolicy(routePolicy89).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder89");

				from("file:inputfolder90?delete=true").routeId("90")
						.routePolicy(routePolicy90).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder90");

				from("file:inputfolder91?delete=true").routeId("91")
						.routePolicy(routePolicy91).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder91");

				from("file:inputfolder92?delete=true").routeId("92")
						.routePolicy(routePolicy92).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder92");

				from("file:inputfolder93?delete=true").routeId("93")
						.routePolicy(routePolicy93).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder93");

				from("file:inputfolder94?delete=true").routeId("94")
						.routePolicy(routePolicy94).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder94");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		route75.setCamelContext(camelContext);
		route76.setCamelContext(camelContext);
		route77.setCamelContext(camelContext);
		route78.setCamelContext(camelContext);
		route79.setCamelContext(camelContext);
		route80.setCamelContext(camelContext);
		route81.setCamelContext(camelContext);
		route82.setCamelContext(camelContext);
		route83.setCamelContext(camelContext);
		route84.setCamelContext(camelContext);
		route85.setCamelContext(camelContext);
		route86.setCamelContext(camelContext);
		route87.setCamelContext(camelContext);
		route88.setCamelContext(camelContext);
		route89.setCamelContext(camelContext);
		route90.setCamelContext(camelContext);
		route91.setCamelContext(camelContext);
		route92.setCamelContext(camelContext);
		route93.setCamelContext(camelContext);
		route94.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(80000);
	}

	@Test
	public void bipSwMultiTest99() throws BIPException, InterruptedException {
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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(5050);
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
		SwitchableRouteDataTransfers route25 = new SwitchableRouteDataTransfers(
				"25", camelContext);
		final Executor executor25 = factory.create(engine, route25, "25", true);

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
		final Executor executor26 = factory.create(engine, route26, "26", true);

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
		final Executor executor27 = factory.create(engine, route27, "27", true);

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
		final Executor executor28 = factory.create(engine, route28, "28", true);

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
		final Executor executor29 = factory.create(engine, route29, "29", true);

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
		final Executor executor30 = factory.create(engine, route30, "30", true);

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
		final Executor executor31 = factory.create(engine, route31, "31", true);

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
		final Executor executor32 = factory.create(engine, route32, "32", true);

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
		final Executor executor33 = factory.create(engine, route33, "33", true);

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
		final Executor executor34 = factory.create(engine, route34, "34", true);

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
		final Executor executor35 = factory.create(engine, route35, "35", true);

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
		final Executor executor36 = factory.create(engine, route36, "36", true);

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
		final Executor executor37 = factory.create(engine, route37, "37", true);

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
		final Executor executor38 = factory.create(engine, route38, "38", true);

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
		final Executor executor39 = factory.create(engine, route39, "39", true);

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
		final Executor executor40 = factory.create(engine, route40, "40", true);

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
		final Executor executor41 = factory.create(engine, route41, "41", true);

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
		final Executor executor42 = factory.create(engine, route42, "42", true);

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
		final Executor executor43 = factory.create(engine, route43, "43", true);

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
		final Executor executor44 = factory.create(engine, route44, "44", true);

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
		final Executor executor45 = factory.create(engine, route45, "45", true);

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
		final Executor executor46 = factory.create(engine, route46, "46", true);

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
		final Executor executor47 = factory.create(engine, route47, "47", true);

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
		final Executor executor48 = factory.create(engine, route48, "48", true);

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
		final Executor executor49 = factory.create(engine, route49, "49", true);

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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final Executor executor50 = factory.create(engine, route50, "50", true);

		final RoutePolicy routePolicy50 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor50.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route51 = new SwitchableRouteDataTransfers(
				"51", camelContext);
		final Executor executor51 = factory.create(engine, route51, "51", true);

		final RoutePolicy routePolicy51 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor51.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route52 = new SwitchableRouteDataTransfers(
				"52", camelContext);
		final Executor executor52 = factory.create(engine, route52, "52", true);

		final RoutePolicy routePolicy52 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor52.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route53 = new SwitchableRouteDataTransfers(
				"53", camelContext);
		final Executor executor53 = factory.create(engine, route53, "53", true);

		final RoutePolicy routePolicy53 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor53.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route54 = new SwitchableRouteDataTransfers(
				"54", camelContext);
		final Executor executor54 = factory.create(engine, route54, "54", true);

		final RoutePolicy routePolicy54 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor54.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route55 = new SwitchableRouteDataTransfers(
				"55", camelContext);
		final Executor executor55 = factory.create(engine, route55, "55", true);

		final RoutePolicy routePolicy55 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor55.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route56 = new SwitchableRouteDataTransfers(
				"56", camelContext);
		final Executor executor56 = factory.create(engine, route56, "56", true);

		final RoutePolicy routePolicy56 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor56.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route57 = new SwitchableRouteDataTransfers(
				"57", camelContext);
		final Executor executor57 = factory.create(engine, route57, "57", true);

		final RoutePolicy routePolicy57 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor57.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route58 = new SwitchableRouteDataTransfers(
				"58", camelContext);
		final Executor executor58 = factory.create(engine, route58, "58", true);

		final RoutePolicy routePolicy58 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor58.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route59 = new SwitchableRouteDataTransfers(
				"59", camelContext);
		final Executor executor59 = factory.create(engine, route59, "59", true);

		final RoutePolicy routePolicy59 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor59.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route60 = new SwitchableRouteDataTransfers(
				"60", camelContext);
		final Executor executor60 = factory.create(engine, route60, "60", true);

		final RoutePolicy routePolicy60 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor60.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route61 = new SwitchableRouteDataTransfers(
				"61", camelContext);
		final Executor executor61 = factory.create(engine, route61, "61", true);

		final RoutePolicy routePolicy61 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor61.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route62 = new SwitchableRouteDataTransfers(
				"62", camelContext);
		final Executor executor62 = factory.create(engine, route62, "62", true);

		final RoutePolicy routePolicy62 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor62.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route63 = new SwitchableRouteDataTransfers(
				"63", camelContext);
		final Executor executor63 = factory.create(engine, route63, "63", true);

		final RoutePolicy routePolicy63 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor63.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route64 = new SwitchableRouteDataTransfers(
				"64", camelContext);
		final Executor executor64 = factory.create(engine, route64, "64", true);

		final RoutePolicy routePolicy64 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor64.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route65 = new SwitchableRouteDataTransfers(
				"65", camelContext);
		final Executor executor65 = factory.create(engine, route65, "65", true);

		final RoutePolicy routePolicy65 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor65.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route66 = new SwitchableRouteDataTransfers(
				"66", camelContext);
		final Executor executor66 = factory.create(engine, route66, "66", true);

		final RoutePolicy routePolicy66 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor66.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route67 = new SwitchableRouteDataTransfers(
				"67", camelContext);
		final Executor executor67 = factory.create(engine, route67, "67", true);

		final RoutePolicy routePolicy67 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor67.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route68 = new SwitchableRouteDataTransfers(
				"68", camelContext);
		final Executor executor68 = factory.create(engine, route68, "68", true);

		final RoutePolicy routePolicy68 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor68.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route69 = new SwitchableRouteDataTransfers(
				"69", camelContext);
		final Executor executor69 = factory.create(engine, route69, "69", true);

		final RoutePolicy routePolicy69 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor69.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route70 = new SwitchableRouteDataTransfers(
				"70", camelContext);
		final Executor executor70 = factory.create(engine, route70, "70", true);

		final RoutePolicy routePolicy70 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor70.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route71 = new SwitchableRouteDataTransfers(
				"71", camelContext);
		final Executor executor71 = factory.create(engine, route71, "71", true);

		final RoutePolicy routePolicy71 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor71.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route72 = new SwitchableRouteDataTransfers(
				"72", camelContext);
		final Executor executor72 = factory.create(engine, route72, "72", true);

		final RoutePolicy routePolicy72 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor72.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route73 = new SwitchableRouteDataTransfers(
				"73", camelContext);
		final Executor executor73 = factory.create(engine, route73, "73", true);

		final RoutePolicy routePolicy73 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor73.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route74 = new SwitchableRouteDataTransfers(
				"74", camelContext);
		final Executor executor74 = factory.create(engine, route74, "74", true);

		final RoutePolicy routePolicy74 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor74.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route75 = new SwitchableRouteDataTransfers(
				"75", camelContext);
		final Executor executor75 = factory.create(engine, route75, "75", true);

		final RoutePolicy routePolicy75 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor75.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route76 = new SwitchableRouteDataTransfers(
				"76", camelContext);
		final Executor executor76 = factory.create(engine, route76, "76", true);

		final RoutePolicy routePolicy76 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor76.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route77 = new SwitchableRouteDataTransfers(
				"77", camelContext);
		final Executor executor77 = factory.create(engine, route77, "77", true);

		final RoutePolicy routePolicy77 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor77.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route78 = new SwitchableRouteDataTransfers(
				"78", camelContext);
		final Executor executor78 = factory.create(engine, route78, "78", true);

		final RoutePolicy routePolicy78 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor78.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route79 = new SwitchableRouteDataTransfers(
				"79", camelContext);
		final Executor executor79 = factory.create(engine, route79, "79", true);

		final RoutePolicy routePolicy79 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor79.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route80 = new SwitchableRouteDataTransfers(
				"80", camelContext);
		final Executor executor80 = factory.create(engine, route80, "80", true);

		final RoutePolicy routePolicy80 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor80.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route81 = new SwitchableRouteDataTransfers(
				"81", camelContext);
		final Executor executor81 = factory.create(engine, route81, "81", true);

		final RoutePolicy routePolicy81 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor81.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route82 = new SwitchableRouteDataTransfers(
				"82", camelContext);
		final Executor executor82 = factory.create(engine, route82, "82", true);

		final RoutePolicy routePolicy82 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor82.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route83 = new SwitchableRouteDataTransfers(
				"83", camelContext);
		final Executor executor83 = factory.create(engine, route83, "83", true);

		final RoutePolicy routePolicy83 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor83.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route84 = new SwitchableRouteDataTransfers(
				"84", camelContext);
		final Executor executor84 = factory.create(engine, route84, "84", true);

		final RoutePolicy routePolicy84 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor84.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route85 = new SwitchableRouteDataTransfers(
				"85", camelContext);
		final Executor executor85 = factory.create(engine, route85, "85", true);

		final RoutePolicy routePolicy85 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor85.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route86 = new SwitchableRouteDataTransfers(
				"86", camelContext);
		final Executor executor86 = factory.create(engine, route86, "86", true);

		final RoutePolicy routePolicy86 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor86.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route87 = new SwitchableRouteDataTransfers(
				"87", camelContext);
		final Executor executor87 = factory.create(engine, route87, "87", true);

		final RoutePolicy routePolicy87 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor87.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route88 = new SwitchableRouteDataTransfers(
				"88", camelContext);
		final Executor executor88 = factory.create(engine, route88, "88", true);

		final RoutePolicy routePolicy88 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor88.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route89 = new SwitchableRouteDataTransfers(
				"89", camelContext);
		final Executor executor89 = factory.create(engine, route89, "89", true);

		final RoutePolicy routePolicy89 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor89.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route90 = new SwitchableRouteDataTransfers(
				"90", camelContext);
		final Executor executor90 = factory.create(engine, route90, "90", true);

		final RoutePolicy routePolicy90 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor90.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route91 = new SwitchableRouteDataTransfers(
				"91", camelContext);
		final Executor executor91 = factory.create(engine, route91, "91", true);

		final RoutePolicy routePolicy91 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor91.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route92 = new SwitchableRouteDataTransfers(
				"92", camelContext);
		final Executor executor92 = factory.create(engine, route92, "92", true);

		final RoutePolicy routePolicy92 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor92.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route93 = new SwitchableRouteDataTransfers(
				"93", camelContext);
		final Executor executor93 = factory.create(engine, route93, "93", true);

		final RoutePolicy routePolicy93 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor93.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route94 = new SwitchableRouteDataTransfers(
				"94", camelContext);
		final Executor executor94 = factory.create(engine, route94, "94", true);

		final RoutePolicy routePolicy94 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor94.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route95 = new SwitchableRouteDataTransfers(
				"95", camelContext);
		final Executor executor95 = factory.create(engine, route95, "95", true);

		final RoutePolicy routePolicy95 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor95.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route96 = new SwitchableRouteDataTransfers(
				"96", camelContext);
		final Executor executor96 = factory.create(engine, route96, "96", true);

		final RoutePolicy routePolicy96 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor96.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route97 = new SwitchableRouteDataTransfers(
				"97", camelContext);
		final Executor executor97 = factory.create(engine, route97, "97", true);

		final RoutePolicy routePolicy97 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor97.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route98 = new SwitchableRouteDataTransfers(
				"98", camelContext);
		final Executor executor98 = factory.create(engine, route98, "98", true);

		final RoutePolicy routePolicy98 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor98.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
		SwitchableRouteDataTransfers route99 = new SwitchableRouteDataTransfers(
				"99", camelContext);
		final Executor executor99 = factory.create(engine, route99, "99", true);

		final RoutePolicy routePolicy99 = new RoutePolicy() {
			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				executor99.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
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

				from("file:inputfolder50?delete=true").routeId("50")
						.routePolicy(routePolicy50).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder50");

				from("file:inputfolder51?delete=true").routeId("51")
						.routePolicy(routePolicy51).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder51");

				from("file:inputfolder52?delete=true").routeId("52")
						.routePolicy(routePolicy52).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder52");

				from("file:inputfolder53?delete=true").routeId("53")
						.routePolicy(routePolicy53).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder53");

				from("file:inputfolder54?delete=true").routeId("54")
						.routePolicy(routePolicy54).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder54");

				from("file:inputfolder55?delete=true").routeId("55")
						.routePolicy(routePolicy55).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder55");

				from("file:inputfolder56?delete=true").routeId("56")
						.routePolicy(routePolicy56).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder56");

				from("file:inputfolder57?delete=true").routeId("57")
						.routePolicy(routePolicy57).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder57");

				from("file:inputfolder58?delete=true").routeId("58")
						.routePolicy(routePolicy58).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder58");

				from("file:inputfolder59?delete=true").routeId("59")
						.routePolicy(routePolicy59).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder59");

				from("file:inputfolder60?delete=true").routeId("60")
						.routePolicy(routePolicy60).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder60");

				from("file:inputfolder61?delete=true").routeId("61")
						.routePolicy(routePolicy61).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder61");

				from("file:inputfolder62?delete=true").routeId("62")
						.routePolicy(routePolicy62).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder62");

				from("file:inputfolder63?delete=true").routeId("63")
						.routePolicy(routePolicy63).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder63");

				from("file:inputfolder64?delete=true").routeId("64")
						.routePolicy(routePolicy64).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder64");

				from("file:inputfolder65?delete=true").routeId("65")
						.routePolicy(routePolicy65).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder65");

				from("file:inputfolder66?delete=true").routeId("66")
						.routePolicy(routePolicy66).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder66");

				from("file:inputfolder67?delete=true").routeId("67")
						.routePolicy(routePolicy67).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder67");

				from("file:inputfolder68?delete=true").routeId("68")
						.routePolicy(routePolicy68).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder68");

				from("file:inputfolder69?delete=true").routeId("69")
						.routePolicy(routePolicy69).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder69");

				from("file:inputfolder70?delete=true").routeId("70")
						.routePolicy(routePolicy70).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder70");

				from("file:inputfolder71?delete=true").routeId("71")
						.routePolicy(routePolicy71).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder71");

				from("file:inputfolder72?delete=true").routeId("72")
						.routePolicy(routePolicy72).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder72");

				from("file:inputfolder73?delete=true").routeId("73")
						.routePolicy(routePolicy73).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder73");

				from("file:inputfolder74?delete=true").routeId("74")
						.routePolicy(routePolicy74).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder74");

				from("file:inputfolder75?delete=true").routeId("75")
						.routePolicy(routePolicy75).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder75");

				from("file:inputfolder76?delete=true").routeId("76")
						.routePolicy(routePolicy76).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder76");

				from("file:inputfolder77?delete=true").routeId("77")
						.routePolicy(routePolicy77).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder77");

				from("file:inputfolder78?delete=true").routeId("78")
						.routePolicy(routePolicy78).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder78");

				from("file:inputfolder79?delete=true").routeId("79")
						.routePolicy(routePolicy79).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder79");

				from("file:inputfolder80?delete=true").routeId("80")
						.routePolicy(routePolicy80).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder80");

				from("file:inputfolder81?delete=true").routeId("81")
						.routePolicy(routePolicy81).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder81");

				from("file:inputfolder82?delete=true").routeId("82")
						.routePolicy(routePolicy82).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder82");

				from("file:inputfolder83?delete=true").routeId("83")
						.routePolicy(routePolicy83).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder83");

				from("file:inputfolder84?delete=true").routeId("84")
						.routePolicy(routePolicy84).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder84");

				from("file:inputfolder85?delete=true").routeId("85")
						.routePolicy(routePolicy85).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder85");

				from("file:inputfolder86?delete=true").routeId("86")
						.routePolicy(routePolicy86).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder86");

				from("file:inputfolder87?delete=true").routeId("87")
						.routePolicy(routePolicy87).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder87");

				from("file:inputfolder88?delete=true").routeId("88")
						.routePolicy(routePolicy88).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder88");

				from("file:inputfolder89?delete=true").routeId("89")
						.routePolicy(routePolicy89).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder89");

				from("file:inputfolder90?delete=true").routeId("90")
						.routePolicy(routePolicy90).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder90");

				from("file:inputfolder91?delete=true").routeId("91")
						.routePolicy(routePolicy91).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder91");

				from("file:inputfolder92?delete=true").routeId("92")
						.routePolicy(routePolicy92).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder92");

				from("file:inputfolder93?delete=true").routeId("93")
						.routePolicy(routePolicy93).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder93");

				from("file:inputfolder94?delete=true").routeId("94")
						.routePolicy(routePolicy94).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder94");

				from("file:inputfolder95?delete=true").routeId("95")
						.routePolicy(routePolicy95).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder95");

				from("file:inputfolder96?delete=true").routeId("96")
						.routePolicy(routePolicy96).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder96");

				from("file:inputfolder97?delete=true").routeId("97")
						.routePolicy(routePolicy97).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder97");

				from("file:inputfolder98?delete=true").routeId("98")
						.routePolicy(routePolicy98).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder98");

				from("file:inputfolder99?delete=true").routeId("99")
						.routePolicy(routePolicy99).process(new Processor() {
							public void process(Exchange exchange)
									throws Exception {
							}
						}).to("file:outputfolder99");

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
		route25.setCamelContext(camelContext);
		route26.setCamelContext(camelContext);
		route27.setCamelContext(camelContext);
		route28.setCamelContext(camelContext);
		route29.setCamelContext(camelContext);
		route30.setCamelContext(camelContext);
		route31.setCamelContext(camelContext);
		route32.setCamelContext(camelContext);
		route33.setCamelContext(camelContext);
		route34.setCamelContext(camelContext);
		route35.setCamelContext(camelContext);
		route36.setCamelContext(camelContext);
		route37.setCamelContext(camelContext);
		route38.setCamelContext(camelContext);
		route39.setCamelContext(camelContext);
		route40.setCamelContext(camelContext);
		route41.setCamelContext(camelContext);
		route42.setCamelContext(camelContext);
		route43.setCamelContext(camelContext);
		route44.setCamelContext(camelContext);
		route45.setCamelContext(camelContext);
		route46.setCamelContext(camelContext);
		route47.setCamelContext(camelContext);
		route48.setCamelContext(camelContext);
		route49.setCamelContext(camelContext);
		route50.setCamelContext(camelContext);
		route51.setCamelContext(camelContext);
		route52.setCamelContext(camelContext);
		route53.setCamelContext(camelContext);
		route54.setCamelContext(camelContext);
		route55.setCamelContext(camelContext);
		route56.setCamelContext(camelContext);
		route57.setCamelContext(camelContext);
		route58.setCamelContext(camelContext);
		route59.setCamelContext(camelContext);
		route60.setCamelContext(camelContext);
		route61.setCamelContext(camelContext);
		route62.setCamelContext(camelContext);
		route63.setCamelContext(camelContext);
		route64.setCamelContext(camelContext);
		route65.setCamelContext(camelContext);
		route66.setCamelContext(camelContext);
		route67.setCamelContext(camelContext);
		route68.setCamelContext(camelContext);
		route69.setCamelContext(camelContext);
		route70.setCamelContext(camelContext);
		route71.setCamelContext(camelContext);
		route72.setCamelContext(camelContext);
		route73.setCamelContext(camelContext);
		route74.setCamelContext(camelContext);
		route75.setCamelContext(camelContext);
		route76.setCamelContext(camelContext);
		route77.setCamelContext(camelContext);
		route78.setCamelContext(camelContext);
		route79.setCamelContext(camelContext);
		route80.setCamelContext(camelContext);
		route81.setCamelContext(camelContext);
		route82.setCamelContext(camelContext);
		route83.setCamelContext(camelContext);
		route84.setCamelContext(camelContext);
		route85.setCamelContext(camelContext);
		route86.setCamelContext(camelContext);
		route87.setCamelContext(camelContext);
		route88.setCamelContext(camelContext);
		route89.setCamelContext(camelContext);
		route90.setCamelContext(camelContext);
		route91.setCamelContext(camelContext);
		route92.setCamelContext(camelContext);
		route93.setCamelContext(camelContext);
		route94.setCamelContext(camelContext);
		route95.setCamelContext(camelContext);
		route96.setCamelContext(camelContext);
		route97.setCamelContext(camelContext);
		route98.setCamelContext(camelContext);
		route99.setCamelContext(camelContext);
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		Thread.sleep(100000);
	}
}