package org.bip.spec;

import org.bip.annotations.*;
import org.bip.api.PortType;

@Ports({ @Port(name = "add", type = PortType.enforceable),
		    @Port(name = "rm", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "monitor")

public class RouteOnOffMonitorSplitted {

    final private int routeLimit;

    private int routeOnCounter = 0;

    public RouteOnOffMonitorSplitted(int routeLimit) {
        this.routeLimit = routeLimit;
    }

    @Transitions({
    @Transition(name = "add", source = "0", target = "1a", guard = "hasCapacity"),
    @Transition(name = "add", source = "1a", target = "2", guard = "hasCapacity")})
	public void addRoute() {
        routeOnCounter++;
	}

	@Transitions({
	@Transition(name = "rm", source = "2", target = "1r", guard = "hasRouteRunning"),
	@Transition(name = "rm", source = "1r", target = "0", guard = "hasRouteRunning")})
	public void removeRoute() {
        routeOnCounter--;
	}

    @Guard(name = "hasCapacity")
    public boolean hasCapacity() {
        return routeOnCounter < routeLimit;
    }

    @Guard(name = "hasRouteRunning")
    public boolean hasRouteRunning() {
        return routeOnCounter > 0;
    }
}
