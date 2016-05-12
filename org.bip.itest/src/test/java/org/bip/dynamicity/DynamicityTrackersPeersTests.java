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
		engine.register(new Peer(1), "p1", true);
		engine.register(new Tracker(1), "t1", true);
		
		sleep(5);
	}
	
	@Test
	public void testAddingTrackers() {
		engine.register(new Tracker(0), "t0", true);
		engine.register(new Peer(0), "p0", true);
		
		sleep(3);
		
		engine.register(new Tracker(1), "t1", true);
		
		sleep(2);
		
		engine.register(new Tracker(2), "t2", true);
		
		sleep(3);
	}
	
	@Test
	public void testAddingPeer() {
		engine.register(new Tracker(0), "t0", true);
		engine.register(new Peer(0), "p0", true);
		
		sleep(3);
		
		engine.register(new Peer(1), "p1", true);
		
		sleep(2);
	}
	
	@Test
	public void testAddingPeers() {
		engine.register(new Tracker(0), "t0", true);
		engine.register(new Peer(0), "p0", true);
		
		sleep(3);
		
		engine.register(new Peer(1), "p1", true);
		
		sleep(2);
		
		engine.register(new Peer(2), "p2", true);
		
		sleep(1);
		
		engine.register(new Peer(3), "p3", true);
		
		sleep(3);
		
		engine.register(new Peer(4), "p4", true);
		
		sleep(2);
	}
}
