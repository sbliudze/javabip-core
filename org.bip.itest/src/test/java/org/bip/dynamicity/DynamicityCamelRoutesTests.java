package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.killEngine;
import static org.bip.dynamicity.HelperFunctions.setupCamelContext;
import static org.bip.dynamicity.HelperFunctions.sleep;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRouteExecutableBehavior;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityCamelRoutesTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;
	private static AtomicInteger engineID = new AtomicInteger();
	private BIPEngine engine;
	private BIPGlue glue;
	private CamelContext context;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("CamelSystem");
		engineFactory = new EngineFactory(system);
	}

	@Before
	public void setup() {
		glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		engine = engineFactory.create("engine" + engineID.getAndIncrement(), glue);
		context = new DefaultCamelContext();
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
	public void testCamelRoutesEngineStartsAutomatically() {
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		r1.setCamelContext(context);

		setupCamelContext(context, new int[] { 1 });

		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(10), "monitor", true);

		sleep(2);
	}

	@Test
	public void testCamelRoutesAddRouteOnTheFly() {
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		r1.setCamelContext(context);
		r2.setCamelContext(context);

		setupCamelContext(context, new int[] { 1, 2 });

		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(2), "monitor", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(5);
	}

	@Test
	public void testCamelRoutesAddMultipleRoutesOnTheFly() {
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		SwitchableRouteExecutableBehavior r3 = new SwitchableRouteExecutableBehavior("r3");
		r1.setCamelContext(context);
		r2.setCamelContext(context);
		r3.setCamelContext(context);

		setupCamelContext(context, new int[] { 1, 2, 3 });

		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(3), "monitor", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(4);

		engine.register(r3, "r3", false);

		sleep(5);
	}

	@Test
	public void testCamelRoutesAddRoutesAndMonitors() {
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		SwitchableRouteExecutableBehavior r3 = new SwitchableRouteExecutableBehavior("r3");
		RouteOnOffMonitor m1 = new RouteOnOffMonitor(3);
		RouteOnOffMonitor m2 = new RouteOnOffMonitor(3);
		r1.setCamelContext(context);
		r2.setCamelContext(context);
		r3.setCamelContext(context);

		setupCamelContext(context, new int[] { 1, 2, 3 });

		engine.register(r1, "r1", false);
		engine.register(m1, "m1", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(4);

		engine.register(r3, "r3", false);

		sleep(5);

		engine.register(m2, "m2", true);

		sleep(4);
	}

	@Test
	public void testCamelRoutesRoutePausesWhenNoEnforceableTransitions() {
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		RouteOnOffMonitor m1 = new RouteOnOffMonitor(3);
		r1.setCamelContext(context);
		setupCamelContext(context, new int[] { 1 });
		
		engine.register(r1, "r1", false);
		engine.register(m1, "m1", true);
		
		sleep(4);
	}
}
