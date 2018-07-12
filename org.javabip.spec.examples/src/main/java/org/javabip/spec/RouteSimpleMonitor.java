package org.javabip.spec;

import org.javabip.annotations.*;
import org.javabip.api.PortType;

@Ports({
        @Port(name = "add", type = PortType.enforceable),
        @Port(name = "rm", type = PortType.enforceable),
        @Port(name = "rmlast", type = PortType.enforceable),
        @Port(name = "switch", type = PortType.spontaneous)
})
@ComponentType(initial = "0", name = "org.bip.spec.RouteSimpleMonitor")
public class RouteSimpleMonitor {

    private int routeOnCounter = 0;
    private boolean workAllowed = true;

    public RouteSimpleMonitor () { }

    public RouteSimpleMonitor (boolean workAllowed) {
        this.workAllowed = workAllowed;
    }

    @Transitions({
            @Transition(name = "add", source = "0", target = "1", guard = "canWork"),
            @Transition(name = "add", source = "1", target = "1", guard = "canWork")
    })
    public void addRoute() {
        routeOnCounter++;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf(" routeOnCounter = %d,%n workAllowed = %b%n", routeOnCounter, workAllowed);
    }

    @Transitions({
            @Transition(name = "rm", source = "1", target = "1", guard = "!lastRoute"),
            @Transition(name = "rmlast", source = "1", target = "0", guard = "lastRoute")
    })
    public void removeRoute() {
        routeOnCounter--;

        System.out.printf(" routeOnCounter = %d,%n workAllowed = %b%n", routeOnCounter, workAllowed);
    }

    @Transitions({
            @Transition(name = "switch", source = "0", target = "0", guard = ""),
            @Transition(name = "switch", source = "1", target = "1", guard = "")
    })
    public void switchWorkAllowed() {
        workAllowed = !workAllowed;

        System.out.printf("Switch transition handler for Route Monitor is being executed.%n workAllowed = %b%n", workAllowed);
    }

    @Guard(name = "canWork")
    public boolean canWork() {
        return workAllowed;
    }

    @Guard(name = "lastRoute")
    public boolean lastRoute() {
        return routeOnCounter == 1;
    }
}
