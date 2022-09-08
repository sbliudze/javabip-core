package org.javabip.spec.ping;

import org.javabip.annotations.*;
import org.javabip.api.PortType;

import static org.javabip.spec.deviation.Constants.INIT;
import static org.javabip.spec.ping.Constants.*;

@Ports({
        @Port(name = PING, type = PortType.enforceable),
        @Port(name = PONG, type = PortType.enforceable),
        @Port(name = BACK, type = PortType.enforceable)
})

@ComponentType(initial = INIT, name = PING_CMD_SPEC)
public class PingCmdSpec {
    @Transition(name = PING, source = INIT, target = DO) //, guard = CHECK_TIME)
    public void receivedTime(@Data(name = INCOMING_TIME) Integer time) throws InterruptedException {
        Thread.sleep(time);
        System.out.println("CMD: COMMAND RECEIVED");
    }

    @Transition(name = BACK, source = DO, target = INIT)
    public void goBack() {
        System.out.println("CMD: RESET");
    }

    @Transition(name = PONG, source = INIT, target = FAIL, guard = "!"+CHECK_TIME)
    public void receiveBadTime() {
        System.out.println("CMD: ERROR: TIME VALUE EXCEEDS THE LIMIT");
        System.exit(1);
    }

    @Guard(name = CHECK_TIME)
    public boolean checkTime(@Data(name = INCOMING_TIME) Integer time) {
        return time < 1000;
    }
}
