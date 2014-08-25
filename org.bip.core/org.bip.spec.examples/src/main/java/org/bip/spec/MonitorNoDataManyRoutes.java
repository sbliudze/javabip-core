package org.bip.spec;

import org.bip.annotations.*;
import org.bip.api.PortType;

@Ports({ @Port(name = "add", type = PortType.enforceable),
		    @Port(name = "rm", type = PortType.enforceable) })
@ComponentType(initial = "init", name = "org.bip.spec.RouteOnOffMonitor")
public class MonitorNoDataManyRoutes {

    final private int routeLimit;

    private int routeOnCounter = 0;

    public MonitorNoDataManyRoutes(int routeLimit) {
        this.routeLimit = routeLimit;
    }

    @Transition(name = "add", source = "init", target = "init", guard = "hasCapacity")
	public void addRoute() {
        routeOnCounter++;
	}

	@Transition(name = "rm", source = "init", target = "init", guard = "hasRouteRunning")
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
