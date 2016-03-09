package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.bip.api.BIPComponent;
import org.bip.api.BIPGlue;
import org.bip.engine.dynamicity.ComponentPool;
import org.bip.spec.Peer;
import org.bip.spec.Tracker;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ComponentPoolTrackersPeersTests {

	private static ComponentPool pool;

	@BeforeClass
	public static void setup() {
		BIPGlue glue = createGlue("src/test/resources/trackerPeerGlue.xml");
		pool = new ComponentPool(glue);
		pool.initialize();
	}

	@After
	public void cleanup() {
		pool.cleanup();
	}

	@Test
	public void testAddTracker() {
		BIPComponent tracker = createComponent(new Tracker(0), "t0", true);
		assertTrue(pool.addInstance(tracker).isEmpty());
	}
	
	@Test
	public void testAddPeer() {
		BIPComponent peer = createComponent(new Peer(0), "p0", true);
		assertTrue(pool.addInstance(peer).isEmpty());
	}
	
	@Test
	public void testAddTrackerAndPeer() {
		BIPComponent peer = createComponent(new Peer(0), "p0", true);
		BIPComponent tracker = createComponent(new Tracker(0), "t0", true);
		
		assertTrue(pool.addInstance(peer).isEmpty());
		assertFalse(pool.addInstance(tracker).isEmpty());
	}
}
