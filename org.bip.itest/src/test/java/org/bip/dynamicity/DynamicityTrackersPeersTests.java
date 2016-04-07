package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.killEngine;
import static org.bip.dynamicity.HelperFunctions.sleep;

import java.util.concurrent.atomic.AtomicInteger;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.Peer;
import org.bip.spec.Tracker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityTrackersPeersTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;
	private static AtomicInteger engineID = new AtomicInteger();
	private BIPEngine engine;
	private BIPGlue glue;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("TrackersPeersSystem");
		engineFactory = new EngineFactory(system);
	}

	@Before
	public void setup() {
		glue = createGlue("src/test/resources/trackerPeerGlue.xml");
		engine = engineFactory.create("engine" + engineID.getAndIncrement(), glue);
	}

	@AfterClass
	public static void cleanup() {
		system.shutdown();
	}

	@After
	public void teardown() {
		killEngine(engineFactory, engine);
	}

	@Test
	public void testTrackersPeersStartsAutomatically() {
		Tracker t1 = new Tracker(1);
		Peer p1 = new Peer(1);

		engine.register(p1, "p1", true);
		engine.register(t1, "t1", true);
		sleep(5);
	}

}
