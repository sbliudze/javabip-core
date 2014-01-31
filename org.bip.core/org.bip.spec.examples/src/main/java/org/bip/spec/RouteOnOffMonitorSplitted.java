package org.bip.spec;

import org.bip.annotations.*;

@bipPorts({ @bipPort(name = "add", type = "enforceable"),
		    @bipPort(name = "rm", type = "enforceable") })
@bipComponentType(initial = "0", name = "monitor")

public class RouteOnOffMonitorSplitted {

    final private int routeLimit;

    private int routeOnCounter = 0;

    public RouteOnOffMonitorSplitted(int routeLimit) {
        this.routeLimit = routeLimit;
    }

    @bipTransitions({
    @bipTransition(name = "add", source = "0", target = "1a", guard = "hasCapacity"),
    @bipTransition(name = "add", source = "1a", target = "2", guard = "hasCapacity")})
	public void addRoute() {
        routeOnCounter++;
	}

	@bipTransitions({
	@bipTransition(name = "rm", source = "2", target = "1r", guard = "hasRouteRunning"),
	@bipTransition(name = "rm", source = "1r", target = "0", guard = "hasRouteRunning")})
	public void removeRoute() {
        routeOnCounter--;
	}

    @bipGuard(name = "hasCapacity")
    public boolean hasCapacity() {
        return routeOnCounter < routeLimit;
    }

    @bipGuard(name = "hasRouteRunning")
    public boolean hasRouteRunning() {
        return routeOnCounter > 0;
    }
}
