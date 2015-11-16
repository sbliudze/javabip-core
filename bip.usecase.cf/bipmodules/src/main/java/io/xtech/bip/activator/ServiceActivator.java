package io.xtech.bip.activator;

/**
 * User: radoslaw szymanek
 * Date: 5/31/12
 * Copyright 2012 Crossing-Tech
 */

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServiceActivator implements BundleActivator {

    ServiceReference[] refs;

    BIPEngine engine = null;

    public void start(BundleContext context) throws Exception {

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.setAutoStartup(false);

        SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
        SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
        SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);

        String filter = "(domain.name=switchable.route)";

        refs = context.getServiceReferences(BIPEngine.class.getName(), filter);

        if (refs != null && refs[0] != null) {

            engine = (BIPEngine) context.getService(refs[0]);

            if (engine == null) {
                System.out.println("Engine service not found");
                return;
            }


            BIPActor actor1 = engine.register(route1, "1", true);
            BIPActor actor2 = engine.register(route2, "2", true);
            BIPActor actor3 = engine.register(route3, "3", true);

            final RoutePolicy routePolicy1 = createRoutePolicy(actor1);
            final RoutePolicy routePolicy2 = createRoutePolicy(actor2);
            final RoutePolicy routePolicy3 = createRoutePolicy(actor3);

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

            try {
                camelContext.addRoutes(builder);
                camelContext.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
            final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);

            System.out.println("Bundle providing BIP Modules for Simple usecase is started.");

            engine.start();

            engine.execute();

        }

    }

    public void stop(BundleContext ctx) {

        System.out.println("Bundle providing BIP Modules for Simple usecase is stopped.");

        if (engine != null)
            engine.stop();

    }

    private RoutePolicy createRoutePolicy(final BIPActor actor1) {

        return  new RoutePolicy() {

            public void onInit(Route route) {
            }

            public void onExchangeDone(Route route, Exchange exchange) {

                actor1.inform("end");
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