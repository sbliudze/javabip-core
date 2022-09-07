package org.javabip.spec.ping;

import akka.actor.ActorSystem;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.TwoSynchronGlueBuilder;

import static org.javabip.spec.ping.Constants.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM);
        EngineFactory engineFactory = new EngineFactory(system);

        BIPGlue glue = new TwoSynchronGlueBuilder() {
            @Override
            public void configure() {
                synchron(PingUserSpec.class, PING).to(PingCmdSpec.class, PING);
                synchron(PingUserSpec.class, PING).to(PingCmdSpec.class, PONG);
                data(PingUserSpec.class, OUTGOING_TIME).to(PingCmdSpec.class, INCOMING_TIME);
            }
        }.build();

        BIPEngine engine = engineFactory.create(ENGINE, glue);
        engine.register(new PingUserSpec(), PING_USER_SPEC, true);
        engine.register(new PingCmdSpec(), PING_CMD_SPEC, true);

        engine.start();
        engine.execute();

        Thread.sleep(10000);

        engine.stop();
        engineFactory.destroy(engine);
        system.shutdown();
    }
}
