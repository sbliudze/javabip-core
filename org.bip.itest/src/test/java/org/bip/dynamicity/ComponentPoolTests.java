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
/**
 * Test class for the component pool on system in the wiki.
 * 
 * @author rutz
 */
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
		// REMOVE
		pool.removeInstance(null);
	}

	@Test
	public void testRemoveInInvalidSystem() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true);
		
		// ADD
		assertTrue(pool.addInstance(a0).isEmpty());
		
		// REMOVE
		assertFalse(pool.removeInstance(a0));
	}

	@Test(expected = BIPEngineException.class)
	public void testRemoveTwiceAComponent() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true);
		
		// ADD
		assertTrue(pool.addInstance(a0).isEmpty());
		assertFalse(pool.removeInstance(a0));
		pool.removeInstance(a0);
	}

	@Test
	public void testRemoveWithValidSystem0() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		
		// ADD
		assertTrue(pool.addInstance(a0).isEmpty());
		assertTrue(pool.addInstance(a1).isEmpty());
		assertTrue(pool.addInstance(a2).isEmpty());
		assertTrue(pool.addInstance(b0).isEmpty());
		assertTrue(pool.addInstance(b1).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		
		// REMOVE
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(a0));
		assertFalse(pool.removeInstance(b1));
	}
	
	@Test
	public void testRemoveWithValidSystem1() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true);
		
		// ADD
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		assertTrue(pool.addInstance(a0).isEmpty());
		assertTrue(pool.addInstance(a1).isEmpty());
		assertTrue(pool.addInstance(a2).isEmpty());
		assertTrue(pool.addInstance(b0).isEmpty());
		assertTrue(pool.addInstance(b1).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		
		// REMOVE
		assertTrue(pool.removeInstance(b1));
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(a0));
	}
	
	@Test
	public void testRemoveWithBigValidSystem0() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true), a3 = createComponent(new ExampleA(), "a3", true),
				a4 = createComponent(new ExampleA(), "a4", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true),
				b2 = createComponent(new ExampleB(), "b2", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true), c1 = createComponent(new ExampleC(), "c1", true);
		
		// ADD
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
		
		// REMOVE
		assertTrue(pool.removeInstance(c1));
		assertTrue(pool.removeInstance(a3));
		assertTrue(pool.removeInstance(a4));
		assertTrue(pool.removeInstance(b2));
		assertFalse(pool.removeInstance(c0));
	}
	
	public void testRemoveWithBigValidSystem1() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true), a3 = createComponent(new ExampleA(), "a3", true),
				a4 = createComponent(new ExampleA(), "a4", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true),
				b2 = createComponent(new ExampleB(), "b2", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true), c1 = createComponent(new ExampleC(), "c1", true);
		
		// ADD
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
		
		// REMOVE
		assertTrue(pool.removeInstance(c1));
		assertTrue(pool.removeInstance(a3));
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(a4));
		assertFalse(pool.removeInstance(b2));
	}
	
	@Test
	public void testRemoveWithBigValidSystem2() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true), a3 = createComponent(new ExampleA(), "a3", true),
				a4 = createComponent(new ExampleA(), "a4", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true),
				b2 = createComponent(new ExampleB(), "b2", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true), c1 = createComponent(new ExampleC(), "c1", true);
		
		// ADD
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
		
		// REMOVE
		assertTrue(pool.removeInstance(a0));
		assertTrue(pool.removeInstance(a1));
		assertTrue(pool.removeInstance(a2));
		assertTrue(pool.removeInstance(a3));
		assertTrue(pool.removeInstance(a4));
		assertTrue(pool.removeInstance(c1));
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(b2));
		
		// ADD
		assertFalse(pool.addInstance(c0).isEmpty());
		assertFalse(pool.addInstance(c1).isEmpty());
	}
	
	@Test
	public void testRemoveWithSmallValidSystem0() {
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true);
		BIPComponent b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		assertTrue(pool.addInstance(b0).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		assertFalse(pool.addInstance(b1).isEmpty());
		
		// REMOVE
		assertTrue(pool.removeInstance(b1));
		assertTrue(pool.removeInstance(b0));
		assertFalse(pool.removeInstance(c0));
	}
	
	@Test
	public void testRemoveWithSmallValidSystem1() {
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true);
		BIPComponent b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		assertTrue(pool.addInstance(b0).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		assertFalse(pool.addInstance(b1).isEmpty());
		
		// REMOVE
		assertTrue(pool.removeInstance(b1));
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(b0));
	}
	
	@Test
	public void testRemoveWithMiniSystem0() {
		BIPComponent e = createComponent(new ExampleE(), "e", true);
		
		// ADD
		assertFalse(pool.addInstance(e).isEmpty());
		
		// REMOVE
		assertFalse(pool.removeInstance(e));
	}
	
	@Test
	public void testRemoveWithMiniSystem1() {
		BIPComponent e = createComponent(new ExampleE(), "e", true);
		BIPComponent d = createComponent(new ExampleD(), "d", true);
		
		// ADD
		assertFalse(pool.addInstance(e).isEmpty());
		assertFalse(pool.addInstance(d).isEmpty());
		
		// REMOVE
		assertFalse(pool.removeInstance(e));
	}
	
	@Test
	public void testRemoveWithOptimization() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true), a1 = createComponent(new ExampleA(), "a1", true),
				a2 = createComponent(new ExampleA(), "a2", true);
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true), b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		BIPComponent e0 = createComponent(new ExampleE(), "e0", true);
		
		// ADD
		assertTrue(pool.addInstance(a0).isEmpty());
		assertTrue(pool.addInstance(a1).isEmpty());
		assertTrue(pool.addInstance(a2).isEmpty());
		assertTrue(pool.addInstance(b0).isEmpty());
		assertFalse(pool.addInstance(e0).isEmpty());
		assertFalse(pool.addInstance(b1).isEmpty());
		assertFalse(pool.addInstance(c0).isEmpty());
		
		// REMOVE
		assertTrue(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(e0));
	}
}
