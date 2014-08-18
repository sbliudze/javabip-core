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

public class ManyManyDataRoutesTests {

	@Test
	public void bipSwMultiTest54() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(2650);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest59() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(2850);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest64() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3250);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest69() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3450);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest74() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3750);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest79() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(3950);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		final ExecutorImpl executor75 = new ExecutorImpl("", route75, true);

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
		final ExecutorImpl executor76 = new ExecutorImpl("", route76, true);

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
		final ExecutorImpl executor77 = new ExecutorImpl("", route77, true);

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
		final ExecutorImpl executor78 = new ExecutorImpl("", route78, true);

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
		final ExecutorImpl executor79 = new ExecutorImpl("", route79, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
		route75.setCamelContext(camelContext);
		Thread t75 = new Thread(executor75, "SW75");
		executor75.setEngine(engine);
		executor75.register(engine);
		route76.setCamelContext(camelContext);
		Thread t76 = new Thread(executor76, "SW76");
		executor76.setEngine(engine);
		executor76.register(engine);
		route77.setCamelContext(camelContext);
		Thread t77 = new Thread(executor77, "SW77");
		executor77.setEngine(engine);
		executor77.register(engine);
		route78.setCamelContext(camelContext);
		Thread t78 = new Thread(executor78, "SW78");
		executor78.setEngine(engine);
		executor78.register(engine);
		route79.setCamelContext(camelContext);
		Thread t79 = new Thread(executor79, "SW79");
		executor79.setEngine(engine);
		executor79.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
			t75.start();
			t76.start();
			t77.start();
			t78.start();
			t79.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest84() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4250);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		final ExecutorImpl executor75 = new ExecutorImpl("", route75, true);

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
		final ExecutorImpl executor76 = new ExecutorImpl("", route76, true);

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
		final ExecutorImpl executor77 = new ExecutorImpl("", route77, true);

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
		final ExecutorImpl executor78 = new ExecutorImpl("", route78, true);

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
		final ExecutorImpl executor79 = new ExecutorImpl("", route79, true);

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
		final ExecutorImpl executor80 = new ExecutorImpl("", route80, true);

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
		final ExecutorImpl executor81 = new ExecutorImpl("", route81, true);

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
		final ExecutorImpl executor82 = new ExecutorImpl("", route82, true);

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
		final ExecutorImpl executor83 = new ExecutorImpl("", route83, true);

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
		final ExecutorImpl executor84 = new ExecutorImpl("", route84, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
		route75.setCamelContext(camelContext);
		Thread t75 = new Thread(executor75, "SW75");
		executor75.setEngine(engine);
		executor75.register(engine);
		route76.setCamelContext(camelContext);
		Thread t76 = new Thread(executor76, "SW76");
		executor76.setEngine(engine);
		executor76.register(engine);
		route77.setCamelContext(camelContext);
		Thread t77 = new Thread(executor77, "SW77");
		executor77.setEngine(engine);
		executor77.register(engine);
		route78.setCamelContext(camelContext);
		Thread t78 = new Thread(executor78, "SW78");
		executor78.setEngine(engine);
		executor78.register(engine);
		route79.setCamelContext(camelContext);
		Thread t79 = new Thread(executor79, "SW79");
		executor79.setEngine(engine);
		executor79.register(engine);
		route80.setCamelContext(camelContext);
		Thread t80 = new Thread(executor80, "SW80");
		executor80.setEngine(engine);
		executor80.register(engine);
		route81.setCamelContext(camelContext);
		Thread t81 = new Thread(executor81, "SW81");
		executor81.setEngine(engine);
		executor81.register(engine);
		route82.setCamelContext(camelContext);
		Thread t82 = new Thread(executor82, "SW82");
		executor82.setEngine(engine);
		executor82.register(engine);
		route83.setCamelContext(camelContext);
		Thread t83 = new Thread(executor83, "SW83");
		executor83.setEngine(engine);
		executor83.register(engine);
		route84.setCamelContext(camelContext);
		Thread t84 = new Thread(executor84, "SW84");
		executor84.setEngine(engine);
		executor84.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
			t75.start();
			t76.start();
			t77.start();
			t78.start();
			t79.start();
			t80.start();
			t81.start();
			t82.start();
			t83.start();
			t84.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest89() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4450);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		final ExecutorImpl executor75 = new ExecutorImpl("", route75, true);

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
		final ExecutorImpl executor76 = new ExecutorImpl("", route76, true);

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
		final ExecutorImpl executor77 = new ExecutorImpl("", route77, true);

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
		final ExecutorImpl executor78 = new ExecutorImpl("", route78, true);

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
		final ExecutorImpl executor79 = new ExecutorImpl("", route79, true);

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
		final ExecutorImpl executor80 = new ExecutorImpl("", route80, true);

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
		final ExecutorImpl executor81 = new ExecutorImpl("", route81, true);

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
		final ExecutorImpl executor82 = new ExecutorImpl("", route82, true);

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
		final ExecutorImpl executor83 = new ExecutorImpl("", route83, true);

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
		final ExecutorImpl executor84 = new ExecutorImpl("", route84, true);

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
		final ExecutorImpl executor85 = new ExecutorImpl("", route85, true);

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
		final ExecutorImpl executor86 = new ExecutorImpl("", route86, true);

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
		final ExecutorImpl executor87 = new ExecutorImpl("", route87, true);

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
		final ExecutorImpl executor88 = new ExecutorImpl("", route88, true);

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
		final ExecutorImpl executor89 = new ExecutorImpl("", route89, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
		route75.setCamelContext(camelContext);
		Thread t75 = new Thread(executor75, "SW75");
		executor75.setEngine(engine);
		executor75.register(engine);
		route76.setCamelContext(camelContext);
		Thread t76 = new Thread(executor76, "SW76");
		executor76.setEngine(engine);
		executor76.register(engine);
		route77.setCamelContext(camelContext);
		Thread t77 = new Thread(executor77, "SW77");
		executor77.setEngine(engine);
		executor77.register(engine);
		route78.setCamelContext(camelContext);
		Thread t78 = new Thread(executor78, "SW78");
		executor78.setEngine(engine);
		executor78.register(engine);
		route79.setCamelContext(camelContext);
		Thread t79 = new Thread(executor79, "SW79");
		executor79.setEngine(engine);
		executor79.register(engine);
		route80.setCamelContext(camelContext);
		Thread t80 = new Thread(executor80, "SW80");
		executor80.setEngine(engine);
		executor80.register(engine);
		route81.setCamelContext(camelContext);
		Thread t81 = new Thread(executor81, "SW81");
		executor81.setEngine(engine);
		executor81.register(engine);
		route82.setCamelContext(camelContext);
		Thread t82 = new Thread(executor82, "SW82");
		executor82.setEngine(engine);
		executor82.register(engine);
		route83.setCamelContext(camelContext);
		Thread t83 = new Thread(executor83, "SW83");
		executor83.setEngine(engine);
		executor83.register(engine);
		route84.setCamelContext(camelContext);
		Thread t84 = new Thread(executor84, "SW84");
		executor84.setEngine(engine);
		executor84.register(engine);
		route85.setCamelContext(camelContext);
		Thread t85 = new Thread(executor85, "SW85");
		executor85.setEngine(engine);
		executor85.register(engine);
		route86.setCamelContext(camelContext);
		Thread t86 = new Thread(executor86, "SW86");
		executor86.setEngine(engine);
		executor86.register(engine);
		route87.setCamelContext(camelContext);
		Thread t87 = new Thread(executor87, "SW87");
		executor87.setEngine(engine);
		executor87.register(engine);
		route88.setCamelContext(camelContext);
		Thread t88 = new Thread(executor88, "SW88");
		executor88.setEngine(engine);
		executor88.register(engine);
		route89.setCamelContext(camelContext);
		Thread t89 = new Thread(executor89, "SW89");
		executor89.setEngine(engine);
		executor89.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
			t75.start();
			t76.start();
			t77.start();
			t78.start();
			t79.start();
			t80.start();
			t81.start();
			t82.start();
			t83.start();
			t84.start();
			t85.start();
			t86.start();
			t87.start();
			t88.start();
			t89.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest94() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(4750);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		final ExecutorImpl executor75 = new ExecutorImpl("", route75, true);

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
		final ExecutorImpl executor76 = new ExecutorImpl("", route76, true);

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
		final ExecutorImpl executor77 = new ExecutorImpl("", route77, true);

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
		final ExecutorImpl executor78 = new ExecutorImpl("", route78, true);

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
		final ExecutorImpl executor79 = new ExecutorImpl("", route79, true);

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
		final ExecutorImpl executor80 = new ExecutorImpl("", route80, true);

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
		final ExecutorImpl executor81 = new ExecutorImpl("", route81, true);

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
		final ExecutorImpl executor82 = new ExecutorImpl("", route82, true);

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
		final ExecutorImpl executor83 = new ExecutorImpl("", route83, true);

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
		final ExecutorImpl executor84 = new ExecutorImpl("", route84, true);

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
		final ExecutorImpl executor85 = new ExecutorImpl("", route85, true);

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
		final ExecutorImpl executor86 = new ExecutorImpl("", route86, true);

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
		final ExecutorImpl executor87 = new ExecutorImpl("", route87, true);

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
		final ExecutorImpl executor88 = new ExecutorImpl("", route88, true);

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
		final ExecutorImpl executor89 = new ExecutorImpl("", route89, true);

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
		final ExecutorImpl executor90 = new ExecutorImpl("", route90, true);

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
		final ExecutorImpl executor91 = new ExecutorImpl("", route91, true);

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
		final ExecutorImpl executor92 = new ExecutorImpl("", route92, true);

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
		final ExecutorImpl executor93 = new ExecutorImpl("", route93, true);

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
		final ExecutorImpl executor94 = new ExecutorImpl("", route94, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
		route75.setCamelContext(camelContext);
		Thread t75 = new Thread(executor75, "SW75");
		executor75.setEngine(engine);
		executor75.register(engine);
		route76.setCamelContext(camelContext);
		Thread t76 = new Thread(executor76, "SW76");
		executor76.setEngine(engine);
		executor76.register(engine);
		route77.setCamelContext(camelContext);
		Thread t77 = new Thread(executor77, "SW77");
		executor77.setEngine(engine);
		executor77.register(engine);
		route78.setCamelContext(camelContext);
		Thread t78 = new Thread(executor78, "SW78");
		executor78.setEngine(engine);
		executor78.register(engine);
		route79.setCamelContext(camelContext);
		Thread t79 = new Thread(executor79, "SW79");
		executor79.setEngine(engine);
		executor79.register(engine);
		route80.setCamelContext(camelContext);
		Thread t80 = new Thread(executor80, "SW80");
		executor80.setEngine(engine);
		executor80.register(engine);
		route81.setCamelContext(camelContext);
		Thread t81 = new Thread(executor81, "SW81");
		executor81.setEngine(engine);
		executor81.register(engine);
		route82.setCamelContext(camelContext);
		Thread t82 = new Thread(executor82, "SW82");
		executor82.setEngine(engine);
		executor82.register(engine);
		route83.setCamelContext(camelContext);
		Thread t83 = new Thread(executor83, "SW83");
		executor83.setEngine(engine);
		executor83.register(engine);
		route84.setCamelContext(camelContext);
		Thread t84 = new Thread(executor84, "SW84");
		executor84.setEngine(engine);
		executor84.register(engine);
		route85.setCamelContext(camelContext);
		Thread t85 = new Thread(executor85, "SW85");
		executor85.setEngine(engine);
		executor85.register(engine);
		route86.setCamelContext(camelContext);
		Thread t86 = new Thread(executor86, "SW86");
		executor86.setEngine(engine);
		executor86.register(engine);
		route87.setCamelContext(camelContext);
		Thread t87 = new Thread(executor87, "SW87");
		executor87.setEngine(engine);
		executor87.register(engine);
		route88.setCamelContext(camelContext);
		Thread t88 = new Thread(executor88, "SW88");
		executor88.setEngine(engine);
		executor88.register(engine);
		route89.setCamelContext(camelContext);
		Thread t89 = new Thread(executor89, "SW89");
		executor89.setEngine(engine);
		executor89.register(engine);
		route90.setCamelContext(camelContext);
		Thread t90 = new Thread(executor90, "SW90");
		executor90.setEngine(engine);
		executor90.register(engine);
		route91.setCamelContext(camelContext);
		Thread t91 = new Thread(executor91, "SW91");
		executor91.setEngine(engine);
		executor91.register(engine);
		route92.setCamelContext(camelContext);
		Thread t92 = new Thread(executor92, "SW92");
		executor92.setEngine(engine);
		executor92.register(engine);
		route93.setCamelContext(camelContext);
		Thread t93 = new Thread(executor93, "SW93");
		executor93.setEngine(engine);
		executor93.register(engine);
		route94.setCamelContext(camelContext);
		Thread t94 = new Thread(executor94, "SW94");
		executor94.setEngine(engine);
		executor94.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
			t75.start();
			t76.start();
			t77.start();
			t78.start();
			t79.start();
			t80.start();
			t81.start();
			t82.start();
			t83.start();
			t84.start();
			t85.start();
			t86.start();
			t87.start();
			t88.start();
			t89.start();
			t90.start();
			t91.start();
			t92.start();
			t93.start();
			t94.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}

	@Test
	public void bipSwMultiTest99() throws BIPException, InterruptedException {

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(5050);
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
		SwitchableRouteDataTransfers route50 = new SwitchableRouteDataTransfers(
				"50", camelContext);
		final ExecutorImpl executor50 = new ExecutorImpl("", route50, true);

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
		final ExecutorImpl executor51 = new ExecutorImpl("", route51, true);

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
		final ExecutorImpl executor52 = new ExecutorImpl("", route52, true);

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
		final ExecutorImpl executor53 = new ExecutorImpl("", route53, true);

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
		final ExecutorImpl executor54 = new ExecutorImpl("", route54, true);

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
		final ExecutorImpl executor55 = new ExecutorImpl("", route55, true);

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
		final ExecutorImpl executor56 = new ExecutorImpl("", route56, true);

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
		final ExecutorImpl executor57 = new ExecutorImpl("", route57, true);

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
		final ExecutorImpl executor58 = new ExecutorImpl("", route58, true);

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
		final ExecutorImpl executor59 = new ExecutorImpl("", route59, true);

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
		final ExecutorImpl executor60 = new ExecutorImpl("", route60, true);

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
		final ExecutorImpl executor61 = new ExecutorImpl("", route61, true);

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
		final ExecutorImpl executor62 = new ExecutorImpl("", route62, true);

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
		final ExecutorImpl executor63 = new ExecutorImpl("", route63, true);

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
		final ExecutorImpl executor64 = new ExecutorImpl("", route64, true);

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
		final ExecutorImpl executor65 = new ExecutorImpl("", route65, true);

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
		final ExecutorImpl executor66 = new ExecutorImpl("", route66, true);

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
		final ExecutorImpl executor67 = new ExecutorImpl("", route67, true);

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
		final ExecutorImpl executor68 = new ExecutorImpl("", route68, true);

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
		final ExecutorImpl executor69 = new ExecutorImpl("", route69, true);

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
		final ExecutorImpl executor70 = new ExecutorImpl("", route70, true);

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
		final ExecutorImpl executor71 = new ExecutorImpl("", route71, true);

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
		final ExecutorImpl executor72 = new ExecutorImpl("", route72, true);

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
		final ExecutorImpl executor73 = new ExecutorImpl("", route73, true);

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
		final ExecutorImpl executor74 = new ExecutorImpl("", route74, true);

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
		final ExecutorImpl executor75 = new ExecutorImpl("", route75, true);

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
		final ExecutorImpl executor76 = new ExecutorImpl("", route76, true);

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
		final ExecutorImpl executor77 = new ExecutorImpl("", route77, true);

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
		final ExecutorImpl executor78 = new ExecutorImpl("", route78, true);

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
		final ExecutorImpl executor79 = new ExecutorImpl("", route79, true);

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
		final ExecutorImpl executor80 = new ExecutorImpl("", route80, true);

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
		final ExecutorImpl executor81 = new ExecutorImpl("", route81, true);

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
		final ExecutorImpl executor82 = new ExecutorImpl("", route82, true);

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
		final ExecutorImpl executor83 = new ExecutorImpl("", route83, true);

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
		final ExecutorImpl executor84 = new ExecutorImpl("", route84, true);

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
		final ExecutorImpl executor85 = new ExecutorImpl("", route85, true);

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
		final ExecutorImpl executor86 = new ExecutorImpl("", route86, true);

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
		final ExecutorImpl executor87 = new ExecutorImpl("", route87, true);

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
		final ExecutorImpl executor88 = new ExecutorImpl("", route88, true);

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
		final ExecutorImpl executor89 = new ExecutorImpl("", route89, true);

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
		final ExecutorImpl executor90 = new ExecutorImpl("", route90, true);

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
		final ExecutorImpl executor91 = new ExecutorImpl("", route91, true);

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
		final ExecutorImpl executor92 = new ExecutorImpl("", route92, true);

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
		final ExecutorImpl executor93 = new ExecutorImpl("", route93, true);

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
		final ExecutorImpl executor94 = new ExecutorImpl("", route94, true);

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
		final ExecutorImpl executor95 = new ExecutorImpl("", route95, true);

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
		final ExecutorImpl executor96 = new ExecutorImpl("", route96, true);

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
		final ExecutorImpl executor97 = new ExecutorImpl("", route97, true);

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
		final ExecutorImpl executor98 = new ExecutorImpl("", route98, true);

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
		final ExecutorImpl executor99 = new ExecutorImpl("", route99, true);

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
		route50.setCamelContext(camelContext);
		Thread t50 = new Thread(executor50, "SW50");
		executor50.setEngine(engine);
		executor50.register(engine);
		route51.setCamelContext(camelContext);
		Thread t51 = new Thread(executor51, "SW51");
		executor51.setEngine(engine);
		executor51.register(engine);
		route52.setCamelContext(camelContext);
		Thread t52 = new Thread(executor52, "SW52");
		executor52.setEngine(engine);
		executor52.register(engine);
		route53.setCamelContext(camelContext);
		Thread t53 = new Thread(executor53, "SW53");
		executor53.setEngine(engine);
		executor53.register(engine);
		route54.setCamelContext(camelContext);
		Thread t54 = new Thread(executor54, "SW54");
		executor54.setEngine(engine);
		executor54.register(engine);
		route55.setCamelContext(camelContext);
		Thread t55 = new Thread(executor55, "SW55");
		executor55.setEngine(engine);
		executor55.register(engine);
		route56.setCamelContext(camelContext);
		Thread t56 = new Thread(executor56, "SW56");
		executor56.setEngine(engine);
		executor56.register(engine);
		route57.setCamelContext(camelContext);
		Thread t57 = new Thread(executor57, "SW57");
		executor57.setEngine(engine);
		executor57.register(engine);
		route58.setCamelContext(camelContext);
		Thread t58 = new Thread(executor58, "SW58");
		executor58.setEngine(engine);
		executor58.register(engine);
		route59.setCamelContext(camelContext);
		Thread t59 = new Thread(executor59, "SW59");
		executor59.setEngine(engine);
		executor59.register(engine);
		route60.setCamelContext(camelContext);
		Thread t60 = new Thread(executor60, "SW60");
		executor60.setEngine(engine);
		executor60.register(engine);
		route61.setCamelContext(camelContext);
		Thread t61 = new Thread(executor61, "SW61");
		executor61.setEngine(engine);
		executor61.register(engine);
		route62.setCamelContext(camelContext);
		Thread t62 = new Thread(executor62, "SW62");
		executor62.setEngine(engine);
		executor62.register(engine);
		route63.setCamelContext(camelContext);
		Thread t63 = new Thread(executor63, "SW63");
		executor63.setEngine(engine);
		executor63.register(engine);
		route64.setCamelContext(camelContext);
		Thread t64 = new Thread(executor64, "SW64");
		executor64.setEngine(engine);
		executor64.register(engine);
		route65.setCamelContext(camelContext);
		Thread t65 = new Thread(executor65, "SW65");
		executor65.setEngine(engine);
		executor65.register(engine);
		route66.setCamelContext(camelContext);
		Thread t66 = new Thread(executor66, "SW66");
		executor66.setEngine(engine);
		executor66.register(engine);
		route67.setCamelContext(camelContext);
		Thread t67 = new Thread(executor67, "SW67");
		executor67.setEngine(engine);
		executor67.register(engine);
		route68.setCamelContext(camelContext);
		Thread t68 = new Thread(executor68, "SW68");
		executor68.setEngine(engine);
		executor68.register(engine);
		route69.setCamelContext(camelContext);
		Thread t69 = new Thread(executor69, "SW69");
		executor69.setEngine(engine);
		executor69.register(engine);
		route70.setCamelContext(camelContext);
		Thread t70 = new Thread(executor70, "SW70");
		executor70.setEngine(engine);
		executor70.register(engine);
		route71.setCamelContext(camelContext);
		Thread t71 = new Thread(executor71, "SW71");
		executor71.setEngine(engine);
		executor71.register(engine);
		route72.setCamelContext(camelContext);
		Thread t72 = new Thread(executor72, "SW72");
		executor72.setEngine(engine);
		executor72.register(engine);
		route73.setCamelContext(camelContext);
		Thread t73 = new Thread(executor73, "SW73");
		executor73.setEngine(engine);
		executor73.register(engine);
		route74.setCamelContext(camelContext);
		Thread t74 = new Thread(executor74, "SW74");
		executor74.setEngine(engine);
		executor74.register(engine);
		route75.setCamelContext(camelContext);
		Thread t75 = new Thread(executor75, "SW75");
		executor75.setEngine(engine);
		executor75.register(engine);
		route76.setCamelContext(camelContext);
		Thread t76 = new Thread(executor76, "SW76");
		executor76.setEngine(engine);
		executor76.register(engine);
		route77.setCamelContext(camelContext);
		Thread t77 = new Thread(executor77, "SW77");
		executor77.setEngine(engine);
		executor77.register(engine);
		route78.setCamelContext(camelContext);
		Thread t78 = new Thread(executor78, "SW78");
		executor78.setEngine(engine);
		executor78.register(engine);
		route79.setCamelContext(camelContext);
		Thread t79 = new Thread(executor79, "SW79");
		executor79.setEngine(engine);
		executor79.register(engine);
		route80.setCamelContext(camelContext);
		Thread t80 = new Thread(executor80, "SW80");
		executor80.setEngine(engine);
		executor80.register(engine);
		route81.setCamelContext(camelContext);
		Thread t81 = new Thread(executor81, "SW81");
		executor81.setEngine(engine);
		executor81.register(engine);
		route82.setCamelContext(camelContext);
		Thread t82 = new Thread(executor82, "SW82");
		executor82.setEngine(engine);
		executor82.register(engine);
		route83.setCamelContext(camelContext);
		Thread t83 = new Thread(executor83, "SW83");
		executor83.setEngine(engine);
		executor83.register(engine);
		route84.setCamelContext(camelContext);
		Thread t84 = new Thread(executor84, "SW84");
		executor84.setEngine(engine);
		executor84.register(engine);
		route85.setCamelContext(camelContext);
		Thread t85 = new Thread(executor85, "SW85");
		executor85.setEngine(engine);
		executor85.register(engine);
		route86.setCamelContext(camelContext);
		Thread t86 = new Thread(executor86, "SW86");
		executor86.setEngine(engine);
		executor86.register(engine);
		route87.setCamelContext(camelContext);
		Thread t87 = new Thread(executor87, "SW87");
		executor87.setEngine(engine);
		executor87.register(engine);
		route88.setCamelContext(camelContext);
		Thread t88 = new Thread(executor88, "SW88");
		executor88.setEngine(engine);
		executor88.register(engine);
		route89.setCamelContext(camelContext);
		Thread t89 = new Thread(executor89, "SW89");
		executor89.setEngine(engine);
		executor89.register(engine);
		route90.setCamelContext(camelContext);
		Thread t90 = new Thread(executor90, "SW90");
		executor90.setEngine(engine);
		executor90.register(engine);
		route91.setCamelContext(camelContext);
		Thread t91 = new Thread(executor91, "SW91");
		executor91.setEngine(engine);
		executor91.register(engine);
		route92.setCamelContext(camelContext);
		Thread t92 = new Thread(executor92, "SW92");
		executor92.setEngine(engine);
		executor92.register(engine);
		route93.setCamelContext(camelContext);
		Thread t93 = new Thread(executor93, "SW93");
		executor93.setEngine(engine);
		executor93.register(engine);
		route94.setCamelContext(camelContext);
		Thread t94 = new Thread(executor94, "SW94");
		executor94.setEngine(engine);
		executor94.register(engine);
		route95.setCamelContext(camelContext);
		Thread t95 = new Thread(executor95, "SW95");
		executor95.setEngine(engine);
		executor95.register(engine);
		route96.setCamelContext(camelContext);
		Thread t96 = new Thread(executor96, "SW96");
		executor96.setEngine(engine);
		executor96.register(engine);
		route97.setCamelContext(camelContext);
		Thread t97 = new Thread(executor97, "SW97");
		executor97.setEngine(engine);
		executor97.register(engine);
		route98.setCamelContext(camelContext);
		Thread t98 = new Thread(executor98, "SW98");
		executor98.setEngine(engine);
		executor98.register(engine);
		route99.setCamelContext(camelContext);
		Thread t99 = new Thread(executor99, "SW99");
		executor99.setEngine(engine);
		executor99.register(engine);
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
			t50.start();
			t51.start();
			t52.start();
			t53.start();
			t54.start();
			t55.start();
			t56.start();
			t57.start();
			t58.start();
			t59.start();
			t60.start();
			t61.start();
			t62.start();
			t63.start();
			t64.start();
			t65.start();
			t66.start();
			t67.start();
			t68.start();
			t69.start();
			t70.start();
			t71.start();
			t72.start();
			t73.start();
			t74.start();
			t75.start();
			t76.start();
			t77.start();
			t78.start();
			t79.start();
			t80.start();
			t81.start();
			t82.start();
			t83.start();
			t84.start();
			t85.start();
			t86.start();
			t87.start();
			t88.start();
			t89.start();
			t90.start();
			t91.start();
			t92.start();
			t93.start();
			t94.start();
			t95.start();
			t96.start();
			t97.start();
			t98.start();
			t99.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		engine.execute();
		Thread.sleep(12000);
	}
}