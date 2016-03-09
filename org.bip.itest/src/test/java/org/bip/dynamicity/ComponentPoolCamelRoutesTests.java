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

	@Test
	public void testAddMonitor() {
		BIPComponent monitor = createComponent(new RouteOnOffMonitor(500), "m0", true);
		assertTrue(pool.addInstance(monitor).isEmpty());
	}

	@Test
	public void testAddRoute() {
		BIPComponent route = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		assertTrue(pool.addInstance(route).isEmpty());
	}

	@Test
	public void testAddMonitorAndRoute() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);

		assertTrue(pool.addInstance(route0).isEmpty());
		assertFalse(pool.addInstance(monitor0).isEmpty());
	}
	
	@Test
	public void testAdd2MonitorsAnd2Routes() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent monitor1 = createComponent(new RouteOnOffMonitor(500), "m1", true);

		assertTrue(pool.addInstance(route0).isEmpty());
		assertTrue(pool.addInstance(route1).isEmpty());
		assertFalse(pool.addInstance(monitor0).isEmpty());
		assertFalse(pool.addInstance(monitor1).isEmpty());
	}
	
	@Test
	public void testAddMonitorAnd3Routes() {
		BIPComponent route0 = createComponent(new SwitchableRouteExecutableBehavior("r0"), "r0", false);
		BIPComponent route1 = createComponent(new SwitchableRouteExecutableBehavior("r1"), "r1", false);
		BIPComponent route2 = createComponent(new SwitchableRouteExecutableBehavior("r2"), "r2", false);
		BIPComponent monitor0 = createComponent(new RouteOnOffMonitor(500), "m0", true);

		assertTrue(pool.addInstance(route0).isEmpty());
		assertTrue(pool.addInstance(route1).isEmpty());
		assertFalse(pool.addInstance(monitor0).isEmpty());
		assertFalse(pool.addInstance(route2).isEmpty());
	}
}
