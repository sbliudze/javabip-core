package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bip.api.BIPComponent;
import org.bip.api.BIPGlue;
import org.bip.engine.dynamicity.ComponentPool;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRouteExecutableBehavior;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for Camel Routes system.
 * 
 * @author rutz
 */
public class ComponentPoolCamelRoutesTests {

	private static ComponentPool pool;

	@BeforeClass
	public static void setup() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		pool = new ComponentPool(glue);
		pool.initialize();
	}

	@After
	public void cleanup() {
		pool.cleanup();
	}

	/*
	 * Test on adding components
	 */

	@Test
	public void testAddMonitor() {
		BIPComponent monitor = createComponent(new RouteOnOffMonitor(500), "m0", true);
		assertFalse(pool.addInstance(monitor));
	}

	@Test
	public void testAddRoute() {
		BIPComponent route = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		assertFalse(pool.addInstance(route));
	}

	@Test
	public void testAddMonitorAndRoute() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);

		assertFalse(pool.addInstance(route0));
		assertTrue(pool.addInstance(monitor0));
	}

	@Test
	public void testAdd2MonitorsAnd2Routes() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent monitor1 = createComponent(new RouteOnOffMonitor(500), "m1", true);

		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(monitor1));
	}

	@Test
	public void testAddMonitorAnd3Routes() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);

		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(route2));
	}

	/*
	 * Test on removing components
	 */
//FLIP ASSERT FROM HERE
	@Test
	public void testRemoveOnlyMonitor() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);

		// ADD
		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(route2));

		// REMOVE
		assertFalse(pool.removeInstance(monitor0));
	}

	@Test
	public void testRemoveRedundantMonitor() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);
		BIPComponent monitor1 = createComponent(new RouteOnOffMonitor(500), "m1", true);

		// ADD
		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(monitor1));
		assertTrue(pool.addInstance(route2));

		// REMOVE
		assertTrue(pool.removeInstance(monitor0));
	}

	@Test
	public void testRemoveRedundantRoute() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);
		BIPComponent monitor1 = createComponent(new RouteOnOffMonitor(500), "m1", true);

		// ADD
		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(monitor1));
		assertTrue(pool.addInstance(route2));

		// REMOVE
		assertTrue(pool.removeInstance(route2));
	}

	@Test
	public void testRemoveRedundantRouteUntilInvalid() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);
		BIPComponent monitor1 = createComponent(new RouteOnOffMonitor(500), "m1", true);

		// ADD
		assertFalse(pool.addInstance(route0));
		assertFalse(pool.addInstance(route1));
		assertTrue(pool.addInstance(monitor0));
		assertTrue(pool.addInstance(monitor1));
		assertTrue(pool.addInstance(route2));

		// REMOVE
		assertTrue(pool.removeInstance(route2));
		assertTrue(pool.removeInstance(route1));
		assertFalse(pool.removeInstance(route0));
	}
}
