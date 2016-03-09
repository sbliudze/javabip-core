package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bip.api.BIPGlue;
import org.bip.engine.dynamicity.ComponentPool;
import org.bip.exceptions.BIPEngineException;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.ExampleD;
import org.bip.spec.ExampleE;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

//TODO Write the accept statements fo rthe glue and test it with the engine
public class ComponentPoolTests {
	private static ComponentPool pool;

	@BeforeClass
	public static void setup() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		pool = new ComponentPool(glue);
		pool.initialize();
	}

	@After
	public void cleanup() {
		pool.cleanup();
	}

	@Test(expected = BIPEngineException.class)
	public void testNullComponent() {
		pool.addInstance(null);
	}

	@Test
	public void testAddInstanceToPool() {
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b0", true)).isEmpty());
	}

	@Test
	public void testValidSystem0() {
		assertTrue(pool.addInstance(createComponent(new ExampleA(), "a0", true)).isEmpty());
		assertTrue(pool.addInstance(createComponent(new ExampleA(), "a1", true)).isEmpty());
		assertTrue(pool.addInstance(createComponent(new ExampleA(), "a2", true)).isEmpty());
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b0", true)).isEmpty());
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b1", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleC(), "c0", true)).isEmpty());
	}

	@Test
	public void testValidSystem1() {
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleC(), "c0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleC(), "c1", true)).isEmpty());
	}

	@Test
	public void testValidSystem2() {
		assertFalse(pool.addInstance(createComponent(new ExampleC(), "c0", true)).isEmpty());
	}

	@Test
	public void testValidSystem3() {
		assertFalse(pool.addInstance(createComponent(new ExampleE(), "e0", true)).isEmpty());
	}

	@Test
	public void testValidSystem4() {
		assertTrue(pool.addInstance(createComponent(new ExampleD(), "d0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleE(), "e0", true)).isEmpty());
	}

	@Test
	public void testAddAfterValidSystemIsStillValid() {
		assertFalse(pool.addInstance(createComponent(new ExampleE(), "e0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleD(), "d0", true)).isEmpty());
	}

}
