package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.sleep;
import static org.bip.dynamicity.HelperFunctions.killEngine;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.ExampleD;
import org.bip.spec.ExampleE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import akka.actor.ActorSystem;

/**
 * Test class for glues. Registers the glue with the engine and runs it to check
 * whether the glue is correct.
 * 
 * @author rutz
 */
@RunWith(value = Parameterized.class)
public class GlueTesterTests {
	private ActorSystem system;
	private EngineFactory engineFactory;
	private BIPGlue glue;
	private List<Object> instances;
	private List<String> ids;

	public GlueTesterTests(BIPGlue glue, List<Object> instances, List<String> ids) {
		this.glue = glue;
		this.instances = instances;
		this.ids = ids;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { createGlue("src/test/resources/bipGlueExampleSystem.xml"),
				Arrays.asList(new ExampleA(), new ExampleA(), new ExampleA(), new ExampleB(), new ExampleB(),
						new ExampleC(), new ExampleC(), new ExampleD(), new ExampleE()),
				Arrays.asList("a0", "a1", "a2", "b0", "b1", "c0", "c1", "d0", "e0") } });
	}

	@Before
	public void setup() {
		this.system = ActorSystem.create();
		this.engineFactory = new EngineFactory(system);
	}

	@Test
	public void testGlueIsValid() {
		BIPEngine engine = engineFactory.create("engine", glue);
		for (int i = 0; i < ids.size(); i++) {
			engine.register(instances.get(i), ids.get(i), true);
		}
		engine.start();

		sleep(2);

		engine.execute();
		
		sleep(2);
		
		killEngine(engineFactory, engine);
	}
}
