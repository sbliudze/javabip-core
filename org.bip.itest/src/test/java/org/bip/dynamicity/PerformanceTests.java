package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.killEngine;
import static org.bip.dynamicity.HelperFunctions.setupCamelContext;
import static org.bip.dynamicity.HelperFunctions.sleep;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.Peer;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRouteExecutableBehavior;
import org.bip.spec.Tracker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;

public class PerformanceTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("CamelSystem");
		engineFactory = new EngineFactory(system);
	}

	@AfterClass
	public static void cleanup() {
		system.shutdown();
	}

	@Test
	public void testPerformanceExampleRegisterBeforeStart() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("engineExample", glue);
		ExampleA a = new ExampleA();

		long p0 = System.currentTimeMillis();
		engine.register(a, "a", true);
		long p1 = System.currentTimeMillis();

		System.out.println("Register before engine start time: " + (p1 - p0) + " ms");

		engine.register(new ExampleC(), "c", true);
		sleep(2);

		killEngine(engineFactory, engine);
	}

	@Test
	public void testPerformanceExampleRegisterWithStart() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("engineExample", glue);
		ExampleC c = new ExampleC();

		long p0 = System.currentTimeMillis();
		engine.register(c, "c", true);
		long p1 = System.currentTimeMillis();
		System.out.println("Register with engine start time: " + (p1 - p0) + " ms");
		sleep(2);

		killEngine(engineFactory, engine);
	}

	@Test
	public void testPerformanceExampleRegisterAfterStart() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("engineExample", glue);
		ExampleB b0 = new ExampleB();
		ExampleC c0 = new ExampleC();

		engine.register(c0, "c", true);
		sleep(1);

		long p0 = System.currentTimeMillis();
		engine.register(b0, "b0", true);
		long p1 = System.currentTimeMillis();
		System.out.println("Register after engine start 1 time: " + (p1 - p0) + " ms");
		sleep(1);
		
		p0 = System.currentTimeMillis();
		engine.deregister(b0);
		p1 = System.currentTimeMillis();
		System.out.println("Deregister with no engine pause: " + (p1 - p0) + " ms");
		sleep(1);
		
		p0 = System.currentTimeMillis();
		engine.deregister(c0);
		p1 = System.currentTimeMillis();
		System.out.println("Deregister with engine pause: " + (p1 - p0) + " ms");
		sleep(1);
		
		

		killEngine(engineFactory, engine);
	}

	@Test
	public void testPerformanceCamelRegister() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("engineCamel", glue);
		CamelContext ctx = new DefaultCamelContext();
		RouteOnOffMonitor monitor = new RouteOnOffMonitor(10);
		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		setupCamelContext(ctx, new int[] { 1, 2 });

		long p0 = System.currentTimeMillis();
		engine.register(monitor, "m", true);
		long p1 = System.currentTimeMillis();
		System.out.println("Register before engine start: " + (p1 - p0) + " ms");
		sleep(2);

		p0 = System.currentTimeMillis();
		engine.register(r1, "r1", false);
		p1 = System.currentTimeMillis();
		System.out.println("Register with engine start: " + (p1 - p0) + " ms");
		sleep(5);

		p0 = System.currentTimeMillis();
		engine.register(r2, "r2", false);
		p1 = System.currentTimeMillis();
		System.out.println("Register after engine start: " + (p1 - p0) + " ms");
		sleep(5);

		killEngine(engineFactory, engine);
	}

	@Test
	public void testPerformanceTrackersPeers() {
		BIPGlue glue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("engineTrackerPeer", glue);

		Tracker t = new Tracker(0);
		Peer p0 = new Peer(0), p1 = new Peer(1);

		long l0 = System.currentTimeMillis();
		engine.register(t, "t0", true);
		long l1 = System.currentTimeMillis();
		System.out.println("Register before engine start: " + (l1 - l0) + "ms");
		sleep(1);

		l0 = System.currentTimeMillis();
		engine.register(p0, "p0", true);
		l1 = System.currentTimeMillis();
		System.out.println("Register with engine start: " + (l1 - l0) + "ms");
		sleep(2);

		l0 = System.currentTimeMillis();
		engine.register(p1, "p1", true);
		l1 = System.currentTimeMillis();
		System.out.println("Register after engine start: " + (l1 - l0) + "ms");
		sleep(2);

		l0 = System.currentTimeMillis();
		engine.deregister(p0);
		l1 = System.currentTimeMillis();
		System.out.println("deregister with no engine pause: " + (l1 - l0) + "ms");
		sleep(2);

		l0 = System.currentTimeMillis();
		engine.deregister(t);
		l1 = System.currentTimeMillis();
		System.out.println("deregister with engine pause: " + (l1 - l0) + "ms");
		sleep(2);
		
		
		
		killEngine(engineFactory, engine);
	}
}
