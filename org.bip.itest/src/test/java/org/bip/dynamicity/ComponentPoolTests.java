package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bip.api.BIPComponent;
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

//TODO Write the accept statements for the glue and test it with the engine
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

	/*
	 * Test on adding components
	 */

	@Test(expected = BIPEngineException.class)
	public void testNullComponent() {
		pool.addInstance(null);
	}

	@Test
	public void testAddInstanceToPool() {
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b0", true)).isEmpty());
	}

	@Test(expected = BIPEngineException.class)
	public void testAddInstanceTwiceToPool() {
		BIPComponent b = createComponent(new ExampleB(), "b0", true);
		assertTrue(pool.addInstance(b).isEmpty());
		pool.addInstance(b);
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
	public void testAddAfterValidSystemIsStillValid0() {
		assertFalse(pool.addInstance(createComponent(new ExampleE(), "e0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleD(), "d0", true)).isEmpty());
	}

	@Test
	public void testAddAfterValidSystemIsStillValid1() {
		assertFalse(pool.addInstance(createComponent(new ExampleE(), "e0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleD(), "d0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleA(), "a0", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleA(), "a1", true)).isEmpty());
		assertFalse(pool.addInstance(createComponent(new ExampleB(), "b0", true)).isEmpty());
	}

	/*
	 * Test on removing components
	 */

	@Test(expected = BIPEngineException.class)
	public void testRemoveNullComponent() {
		pool.removeInstance(null);
	}

	@Test
	public void testRemoveInInvalidSystem() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true);
		assertTrue(pool.addInstance(a0).isEmpty());
		assertFalse(pool.removeInstance(a0));
	}

	@Test(expected = BIPEngineException.class)
	public void testRemoveTwiceAComponent() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true);
		assertTrue(pool.addInstance(a0).isEmpty());
		assertFalse(pool.removeInstance(a0));
		pool.removeInstance(a0);
	}

	@Test
	public void testRemoveWithValidSystem() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		assertTrue(pool.addInstance(a0).isEmpty());
		assertTrue(pool.addInstance(a1).isEmpty());
		assertTrue(pool.addInstance(a2).isEmpty());
		assertTrue(pool.addInstance(b0).isEmpty());
		assertTrue(pool.addInstance(b1).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(a0));
		assertFalse(pool.removeInstance(b1));
	}
	
	@Test
	public void testRemoveWithBigValidSystem() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true), a3 = createComponent(new ExampleA(), "a3", true),
				a4 = createComponent(new ExampleA(), "a4", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true),
				b2 = createComponent(new ExampleB(), "b2", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true), c1 = createComponent(new ExampleC(), "c1", true);
		assertTrue(pool.addInstance(a0).isEmpty());
		assertTrue(pool.addInstance(a1).isEmpty());
		assertTrue(pool.addInstance(a2).isEmpty());
		assertTrue(pool.addInstance(a3).isEmpty());
		assertTrue(pool.addInstance(a4).isEmpty());
		assertTrue(pool.addInstance(b0).isEmpty());
		assertTrue(pool.addInstance(b1).isEmpty());
		assertTrue(pool.addInstance(b2).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		assertFalse(pool.addInstance(c1).isEmpty());
		assertTrue(pool.removeInstance(c1));
		assertTrue(pool.removeInstance(a3));
		assertTrue(pool.removeInstance(a4));
		assertTrue(pool.removeInstance(b2));
		assertFalse(pool.removeInstance(c0));
	}
}
