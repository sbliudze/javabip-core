package org.javabip.spec.ping;

import org.javabip.annotations.*;
import org.javabip.api.PortType;

import static org.javabip.spec.ping.Constants.*;

@Ports({
        @Port(name = PING, type = PortType.enforceable)
})

@ComponentType(initial = INIT, name = PING_USER_SPEC)
public class PingUserSpec {
    Integer time = 10000;
    @Transition(name = PING, source = INIT, target = INIT)
    public void ping() throws InterruptedException {
        System.out.println("USER: PING -i 1");
        Thread.sleep(1000);
    }

    @Data(name = OUTGOING_TIME)
    public Integer getTime() {
        return time;
    }
}
