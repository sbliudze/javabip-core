package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;

import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.A;
import org.bip.spec.B;
import org.bip.spec.C;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("MySystem");
		engineFactory = new EngineFactory(system);
	}

	@AfterClass
	public static void cleanup() {
		system.shutdown();
	}

	@Test
	public void validDependenciesSystemTest() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueDependenciesSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine", glue);

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
		//
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		engine.execute();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		engine.stop();
		// System.out.println(String.format("We have %d accept constraints",
		// glue.getAcceptConstraints().size()));
		// System.out.println(String.format("We have %d require constraints",
		// glue.getRequiresConstraints().size()));
		// System.out.println(bComponents[0].getResult());
		// System.out.println(cComponents[0].getResult());
		// System.out.println(a.getResult());
		engineFactory.destroy(engine);
	}

	@Ignore
	@Test
	public void testEngineStartsAutomatically() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine", glue);

		engine.register(new ExampleA(), "a0", true);
		engine.register(new ExampleA(), "a1", true);
		engine.register(new ExampleA(), "a2", true);
		engine.register(new ExampleB(), "b0", true);
		engine.register(new ExampleB(), "b1", true);
		engine.register(new ExampleC(), "c0", true);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		engine.stop();
		engineFactory.destroy(engine);
	}
}
