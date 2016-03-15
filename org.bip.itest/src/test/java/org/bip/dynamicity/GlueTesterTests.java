package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;

import java.util.Arrays;
import java.util.Collection;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import akka.actor.ActorSystem;
/**
 * Test class for glues. Registers the glue with the engine and runs it to check whether the glue is correct.
 * @author rutz
 */
@RunWith(value = Parameterized.class)
public class GlueTesterTests {
	private ActorSystem system;
	private EngineFactory engineFactory;
	private BIPGlue glue;
	
	public GlueTesterTests(BIPGlue glue, Object expected) {
		this.glue = glue;
	}

	@Parameters
	public static Collection<BIPGlue[]> data() {
		return Arrays.asList(new BIPGlue[][] { { createGlue("src/test/resources/bipGlueExampleSystem.xml"), null },
				{ createGlue("src/test/resources/bipGlueExecutableBehaviour.xml"), null },
				{ createGlue("src/test/resources/trackerPeerGlue.xml"), null } });
	}

	@Before
	public void setup() {
		this.system = ActorSystem.create();
		this.engineFactory = new EngineFactory(system);
	}
	@Test
	public void testGlueIsValid() {
		BIPEngine engine = engineFactory.create("engine", glue);
		engine.start();
		engine.execute();
		engine.stop();
		engineFactory.destroy(engine);
	}
}
