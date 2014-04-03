package org.bip.spec;

import org.bip.annotations.*;

@Ports({ @Port(name = "add", type = "enforceable"),
		    @Port(name = "rm", type = "enforceable") })
@ComponentType(initial = "0", name = "org.bip.spec.RouteOnOffMonitorPositive")
public class RouteOnOffMonitorPositive {

    final private int routeLimit;

    private int routeOnCounter = 0;

    public RouteOnOffMonitorPositive(int routeLimit) {
        this.routeLimit = routeLimit;
    }

    @Transitions({
    @Transition(name = "add", source = "0", target = "1", guard = "hasCapacity"),
    @Transition(name = "add", source = "1", target = "2", guard = "hasCapacity")})
	public void addRoute() {
        routeOnCounter++;
	}

	@Transitions({
	@Transition(name = "rm", source = "2", target = "1", guard = "hasRouteRunning")})
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
