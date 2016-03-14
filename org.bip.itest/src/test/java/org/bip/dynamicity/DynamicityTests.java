package org.bip.dynamicity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.glue.GlueBuilder;
import org.bip.spec.A;
import org.bip.spec.B;
import org.bip.spec.C;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityTests {

	ActorSystem system;
	EngineFactory engineFactory;

	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem");
		engineFactory = new EngineFactory(system);

	}

	@After
	public void cleanup() {

		system.shutdown();

	}

	@Ignore
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
//		System.out.println(String.format("We have %d accept constraints", glue.getAcceptConstraints().size()));
//		System.out.println(String.format("We have %d require constraints", glue.getRequiresConstraints().size()));
//		System.out.println(bComponents[0].getResult());
//		System.out.println(cComponents[0].getResult());
//		System.out.println(a.getResult());
		engineFactory.destroy(engine);
	}

	private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;

	}
}
