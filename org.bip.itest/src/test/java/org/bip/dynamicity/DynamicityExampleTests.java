package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.killEngine;
import static org.bip.dynamicity.HelperFunctions.sleep;

import java.util.concurrent.atomic.AtomicInteger;

import org.bip.api.BIPActor;
import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.ExampleE;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityExampleTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;
	private static AtomicInteger engineID = new AtomicInteger();
	private BIPEngine engine;
	private BIPGlue glue;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("ExampleSystem");
		engineFactory = new EngineFactory(system);
	}

	@Before
	public void setup() {
		glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
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
	public void testExampleEngineStartsAutomatically() {
		engine.register(new ExampleA(), "a0", true);
		engine.register(new ExampleA(), "a1", true);
		engine.register(new ExampleA(), "a2", true);
		engine.register(new ExampleB(), "b0", true);
		engine.register(new ExampleB(), "b1", true);
		engine.register(new ExampleC(), "c0", true);

		sleep(2);
	}

	@Test
	public void testExampleEngineStartsAutomaticallyWithMicroSystem() {
		engine.register(new ExampleE(), "e", true);

		sleep(2);
	}
	
	@Test
	public void testExampleDeregistration() {
		ExampleE e0 = new ExampleE();
		engine.register(e0, "e0", true);
		sleep(2);
		engine.deregister(e0);
	}
}
