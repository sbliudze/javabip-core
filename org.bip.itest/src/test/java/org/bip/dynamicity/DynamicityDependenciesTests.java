package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.killEngine;
import static org.bip.dynamicity.HelperFunctions.sleep;

import java.util.concurrent.atomic.AtomicInteger;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.A;
import org.bip.spec.B;
import org.bip.spec.C;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityDependenciesTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;
	private static AtomicInteger engineID = new AtomicInteger();
	private BIPEngine engine;
	private BIPGlue glue;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("DependenciesSystem");
		engineFactory = new EngineFactory(system);
	}

	@Before
	public void setup() {
		glue = createGlue("src/test/resources/bipGlueDependenciesSystem.xml");
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

	@Ignore
	@Test
	public void validDependenciesSystemTest() {
		final int nbBCComponents = 5;

		A a = new A();
		B[] bComponents = new B[nbBCComponents];
		C[] cComponents = new C[nbBCComponents];

		// final BIPActor actorA =
		engine.register(a, "a", true);

		for (int i = 0; i < 5; ++i) {
			bComponents[i] = new B(String.format("%d", i));
			cComponents[i] = new C(String.format("%d", i));
			engine.register(bComponents[i], String.format("b%d", i), true);
			engine.register(cComponents[i], String.format("c%d", i), true);
		}

		sleep(2);
	}
}
