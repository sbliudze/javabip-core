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

public class DeregistrationCamelRoutesTests {

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
	public void testCamelRoutesDeregistrationRoute() {
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r0 = new SwitchableRouteExecutableBehavior("r0");

		r0.setCamelContext(context);
		setupCamelContext(context, new int[] { 0 });

		engine.register(monitor, "m", true);
		engine.register(r0, "r0", false);

		sleep(3);

		engine.deregister(r0);

		sleep(1);
	}

	@Test
	public void testCamelRoutesDeregistrationMonitor() {
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r0 = new SwitchableRouteExecutableBehavior("r0");

		r0.setCamelContext(context);
		setupCamelContext(context, new int[] { 0 });

		engine.register(monitor, "m", true);
		engine.register(r0, "r0", false);

		sleep(3);

		engine.deregister(monitor);

		sleep(1);
	}

	@Test
	public void testCamelRoutesDeregistrationUnnecessaryComponents() {
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r0 = new SwitchableRouteExecutableBehavior("r0"),
				r1 = new SwitchableRouteExecutableBehavior("r1");

		r0.setCamelContext(context);
		r1.setCamelContext(context);
		setupCamelContext(context, new int[] { 0, 1 });

		engine.register(monitor, "m", true);
		engine.register(r0, "r0", false);
		
		sleep(1);
		
		engine.register(r1, "r1", false);

		sleep(3);

		engine.deregister(r0);

		sleep(3);
	}

	@Test
	public void testCamelRoutesDeregistrationNecessaryComponents() {
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r0 = new SwitchableRouteExecutableBehavior("r0"),
				r1 = new SwitchableRouteExecutableBehavior("r1");

		r0.setCamelContext(context);
		r1.setCamelContext(context);
		setupCamelContext(context, new int[] { 0, 1 });

		engine.register(monitor, "m", true);
		engine.register(r0, "r0", false);
		
		sleep(1);
		
		engine.register(r1, "r1", false);

		sleep(3);

		engine.deregister(monitor);

		sleep(3);
	}

	@Test
	public void testCamelRoutesDeregistrationAndRegistrationStopsAndRestartsEngine() {
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r0 = new SwitchableRouteExecutableBehavior("r0"),
				r1 = new SwitchableRouteExecutableBehavior("r1");

		r0.setCamelContext(context);
		r1.setCamelContext(context);
		setupCamelContext(context, new int[] { 0, 1 });

		engine.register(monitor, "m", true);
		engine.register(r0, "r0", false);

		sleep(3);

		engine.deregister(r0);

		sleep(2);

		engine.register(r1, "r1", false);

		sleep(5);
	}
}
