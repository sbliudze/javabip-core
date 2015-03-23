package org.bip.spec;

import org.bip.annotations.*;
import org.bip.api.PortType;

@Ports({ @Port(name = "add", type = PortType.enforceable),
		    @Port(name = "rm", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.RouteOnOffMonitor")
public class RouteOnOffMonitor {

    final private int routeLimit;

    private int routeOnCounter = 0;
    CounterInterface counter;

    public RouteOnOffMonitor(int routeLimit, CounterInterface counter) {
        this.routeLimit = routeLimit;
        this.counter=counter;
    }

    @Transitions({
    @Transition(name = "add", source = "0", target = "1", guard = "hasCapacity"),
    @Transition(name = "add", source = "1", target = "2", guard = "hasCapacity")})
	public void addRoute() {
        routeOnCounter++;
        //counter.up();
	}

	@Transitions({
	@Transition(name = "rm", source = "2", target = "1", guard = "hasRouteRunning"),
	@Transition(name = "rm", source = "1", target = "0", guard = "hasRouteRunning")})
	public void removeRoute() {
        routeOnCounter--;
        //counter.up();
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
