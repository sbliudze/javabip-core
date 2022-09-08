package org.javabip.spec;

import org.javabip.annotations.*;
import org.javabip.api.PortType;
import org.javabip.exceptions.BIPException;

import java.util.HashMap;
import java.util.Map;


@Ports({
        @Port(name = "add", type = PortType.enforceable),
        @Port(name = "rm", type = PortType.enforceable),
        @Port(name = "switch", type = PortType.spontaneous)
})
@ComponentType(initial = "0", name = "org.bip.spec.RouteOnOffMonitorWithData")
public class RouteOnOffMonitorWithData {

    private Map<String, String> compatible;

    private boolean workAllowed = true;

    private String runningRouteId1;
    private String runningRouteId2;

    public RouteOnOffMonitorWithData() {
        compatible = new HashMap<String, String> ();
        compatible.put("1", "3");
        compatible.put("2", "4");
        compatible.put("3", "1");
        compatible.put("4", "2");
    }

    public RouteOnOffMonitorWithData(boolean workAllowed) {
        this();
        this.workAllowed = workAllowed;
    }

    @Transitions({
            @Transition(name = "add", source = "0", target = "1", guard = "canWork & compatible"),
            @Transition(name = "add", source = "1", target = "2", guard = "canWork & compatible")
    })
    public void addRoute(@Data(name = "routeId") String routeId) throws BIPException {

        if (runningRouteId1 == null) {
            System.out.printf("Adding route %s as first route%n", routeId);
            runningRouteId1 = routeId;
        } else if (runningRouteId2 == null) {
            System.out.printf("Adding route %s as second route%n", routeId);
            runningRouteId2 = routeId;
        } else {
            throw new BIPException("Tried adding route " + routeId +
                    " even though two routes " + runningRouteId1 + " and " + runningRouteId2 +
                    " are already running");
        }

        System.out.printf(" workAllowed = %b%n", workAllowed);
    }

    @Transitions({
            @Transition(name = "rm", source = "2", target = "1", guard = "isRunning"),
            @Transition(name = "rm", source = "1", target = "0", guard = "isRunning")
    })
    public void removeRoute(@Data(name = "routeId") String routeId) throws BIPException {

        if (runningRouteId1 != null && runningRouteId1.equals(routeId)) {
            System.out.printf("Removing route %s as first route%n", routeId);
            System.out.printf("Moving route %s from second to first%n", runningRouteId2);
            runningRouteId1 = runningRouteId2;
            runningRouteId2 = null;
        } else if (runningRouteId2 != null && runningRouteId2.equals(routeId)) {
            System.out.printf("Removing route %s as second route%n", routeId);
            runningRouteId2 = null;
        } else {
            throw new BIPException("Tried removing route " + routeId +
                    " even though it is not running");
        }
        System.out.printf(" workAllowed = %b%n", workAllowed);
    }

    @Transitions({
            @Transition(name = "switch", source = "0", target = "0", guard = ""),
            @Transition(name = "switch", source = "1", target = "1", guard = ""),
            @Transition(name = "switch", source = "2", target = "2", guard = "")
    })
    public void switchWorkAllowed() {
        workAllowed = !workAllowed;

        System.out.printf("Switch transition handler for Route Monitor is being executed.%n workAllowed = %b%n", workAllowed);
    }

    @Guard(name = "canWork")
    public boolean canWork() {
        return workAllowed;
    }

    @Guard(name = "compatible")
    public boolean areRoutesCompatible (@Data(name = "routeId") String routeId
    ) {
        return (runningRouteId1 == null) ||
                (runningRouteId2 == null && compatible.get(runningRouteId1).equals(routeId));
    }

    @Guard(name = "isRunning")
    public boolean isRunning (@Data(name = "routeId") String routeId) {
        return (runningRouteId1 != null && runningRouteId1.equals(routeId)) ||
                (runningRouteId2 != null && runningRouteId2.equals(routeId));
    }
}
