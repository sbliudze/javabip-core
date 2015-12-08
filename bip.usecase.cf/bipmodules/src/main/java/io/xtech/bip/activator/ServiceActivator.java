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
import org.bip.api.BIPSpec;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.osgi.framework.*;

import java.util.Hashtable;

public class ServiceActivator implements BundleActivator {

    ServiceReference[] refs;

    BIPEngine engine = null;

    public void start(BundleContext context) throws Exception {

        System.out.println("Bundle providing BIP Modules for Simple usecase is being started.");

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


            Hashtable<String, Object> properties = new Hashtable<String, Object>();
            properties.put("domain.name", "switchable.route");
            properties.put("component.name", "1");
            properties.put("annotations", true);

            System.out.println("Registering route 1");
            ServiceRegistration serviceRegistration1 =
                    context.registerService(BIPSpec.class.getName(), route1, properties);

            properties.put("component.name", "2");
            ServiceRegistration serviceRegistration2 =
                    context.registerService(BIPSpec.class.getName(), route2, properties);

            properties.put("component.name", "3");
            ServiceRegistration serviceRegistration3 =
                    context.registerService(BIPSpec.class.getName(), route3, properties);

            properties.put("component.name", "monitor");
            MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
            ServiceRegistration serviceRegistration4 =
                    context.registerService(BIPSpec.class.getName(), routeOnOffMonitor, properties);

            System.out.println("Searching BIP Actors");

            String filterActor = "(" + Constants.OBJECTCLASS + "="
                    + BIPActor.class.getName() + ")";

            ServiceReference[] refs;
            int counter = 0;
            while (true) {
                refs = context.getServiceReferences(null, filterActor);
                if (refs.length == 4)
                    break;
                Thread.sleep(1000);
                counter++;
                if (counter == 60)
                    throw new Exception("Failed to collect all BIPActors");
            }

            BIPActor actor1 = null, actor2 = null, actor3 = null, actor4 = null;

            System.out.println("Found 4 BIP actor services.");
/*
            BIPActor actor1 = engine.register(route1, "1", true);
            BIPActor actor2 = engine.register(route2, "2", true);
            BIPActor actor3 = engine.register(route3, "3", true);
            final BIPActor actor4 = engine.register(routeOnOffMonitor, "monitor", true);
*/

            for (ServiceReference ref : refs) {
                String componentName = ref.getProperty("component.name").toString();

                System.out.println("Componentname of BIPActor" + componentName);

                if (componentName.equals("1")) actor1 = (BIPActor)context.getService(ref);
                if (componentName.equals("2")) actor2 = (BIPActor)context.getService(ref);
                if (componentName.equals("3")) actor3 = (BIPActor)context.getService(ref);
                if (componentName.equals("monitor")) actor4 = (BIPActor)context.getService(ref);

            }

            System.out.println("RouteMonitor register and actor reference " + actor4);

            final RoutePolicy routePolicy1 = createRoutePolicy(actor1);
            final RoutePolicy routePolicy2 = createRoutePolicy(actor2);
            final RoutePolicy routePolicy3 = createRoutePolicy(actor3);

            RouteBuilder builder = new RouteBuilder() {

                @Override
                public void configure() throws Exception {
                    from("file:inputfolder1?delete=true").routeId("1")
                            .routePolicy(routePolicy1).to("file:inputfolder2");

                    from("file:inputfolder2?delete=true").routeId("2")
                            .routePolicy(routePolicy2).to("file:inputfolder3");

                    from("file:inputfolder3?delete=true").routeId("3")
                            .routePolicy(routePolicy3).to("file:inputfolder1");
                }

            };

            try {
                camelContext.addRoutes(builder);
                camelContext.start();
            } catch (Exception e) {
                e.printStackTrace();
            }



            System.out.println("Bundle providing BIP Modules for Simple usecase is started.");

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