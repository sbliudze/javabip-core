package org.javabip.spec;

import org.javabip.annotations.*;
import org.javabip.api.PortType;

@Ports({
        @Port(name = "add", type = PortType.enforceable),
        @Port(name = "rm", type = PortType.enforceable),
        @Port(name = "switch", type = PortType.spontaneous)
})
@ComponentType(initial = "0", name = "org.bip.spec.RouteStarter")
public class RouteStarter {

    private boolean workAllowed = true;

    public RouteStarter() { }

    public RouteStarter(boolean workAllowed) {
        this.workAllowed = workAllowed;
    }

    @Transitions({
            @Transition(name = "add", source = "0", target = "1", guard = "canWork"),
    })
    public void addRoute() {
        System.out.printf(" workAllowed = %b%n", workAllowed);
    }

    @Transitions({
            @Transition(name = "rm", source = "1", target = "0", guard = "!canWork"),
    })
    public void removeRoute() {
        System.out.printf(" workAllowed = %b%n", workAllowed);
    }

    @Transitions({
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
}
