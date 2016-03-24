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
		assertFalse(pool.addInstance(createComponent(new ExampleB(), "b0", true)));
	}

	@Test(expected = BIPEngineException.class)
	public void testAddInstanceTwiceToPool() {
		BIPComponent b = createComponent(new ExampleB(), "b0", true);
		assertFalse(pool.addInstance(b));
		pool.addInstance(b);
	}

	@Test
	public void testValidSystem0() {
		assertFalse(pool.addInstance(createComponent(new ExampleA(), "a0", true)));
		assertFalse(pool.addInstance(createComponent(new ExampleA(), "a1", true)));
		assertFalse(pool.addInstance(createComponent(new ExampleA(), "a2", true)));
		assertFalse(pool.addInstance(createComponent(new ExampleB(), "b0", true)));
		assertFalse(pool.addInstance(createComponent(new ExampleB(), "b1", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleC(), "c0", true)));
	}

	@Test
	public void testValidSystem1() {
		assertFalse(pool.addInstance(createComponent(new ExampleB(), "b0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleC(), "c0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleC(), "c1", true)));
	}

	@Test
	public void testValidSystem2() {
		assertTrue(pool.addInstance(createComponent(new ExampleC(), "c0", true)));
	}

	@Test
	public void testValidSystem3() {
		assertTrue(pool.addInstance(createComponent(new ExampleE(), "e0", true)));
	}

	@Test
	public void testValidSystem4() {
		assertFalse(pool.addInstance(createComponent(new ExampleD(), "d0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleE(), "e0", true)));
	}

	@Test
	public void testAddAfterValidSystemIsStillValid0() {
		assertTrue(pool.addInstance(createComponent(new ExampleE(), "e0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleD(), "d0", true)));
	}

	@Test
	public void testAddAfterValidSystemIsStillValid1() {
		assertTrue(pool.addInstance(createComponent(new ExampleE(), "e0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleD(), "d0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleA(), "a0", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleA(), "a1", true)));
		assertTrue(pool.addInstance(createComponent(new ExampleB(), "b0", true)));
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
		assertFalse(pool.addInstance(a0));
		
		// REMOVE
		assertFalse(pool.removeInstance(a0));
	}

	@Test(expected = BIPEngineException.class)
	public void testRemoveTwiceAComponent() {
		BIPComponent a0 = createComponent(new ExampleA(), "a0", true);
		
		// ADD
		assertFalse(pool.addInstance(a0));
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(b0));
		assertFalse(pool.addInstance(b1));
		assertTrue(pool.addInstance(c0));
		
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(b0));
		assertFalse(pool.addInstance(b1));
		assertTrue(pool.addInstance(c0));
		
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(a3));
		assertFalse(pool.addInstance(a4));
		assertFalse(pool.addInstance(b0));
		assertFalse(pool.addInstance(b1));
		assertFalse(pool.addInstance(b2));
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(c1));
		
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(a3));
		assertFalse(pool.addInstance(a4));
		assertFalse(pool.addInstance(b0));
		assertFalse(pool.addInstance(b1));
		assertFalse(pool.addInstance(b2));
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(c1));
		
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(a3));
		assertFalse(pool.addInstance(a4));
		assertFalse(pool.addInstance(b0));
		assertFalse(pool.addInstance(b1));
		assertFalse(pool.addInstance(b2));
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(c1));
		
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
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(c1));
	}
	
	@Test
	public void testRemoveWithSmallValidSystem0() {
		BIPComponent b0 = createComponent(new ExampleB(), "b0", true);
		BIPComponent b1 = createComponent(new ExampleB(), "b1", true);
		BIPComponent c0 = createComponent(new ExampleC(), "c0", true);
		assertFalse(pool.addInstance(b0));
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(b1));
		
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
		assertFalse(pool.addInstance(b0));
		assertTrue(pool.addInstance(c0));
		assertTrue(pool.addInstance(b1));
		
		// REMOVE
		assertTrue(pool.removeInstance(b1));
		assertFalse(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(b0));
	}
	
	@Test
	public void testRemoveWithMiniSystem0() {
		BIPComponent e = createComponent(new ExampleE(), "e", true);
		
		// ADD
		assertTrue(pool.addInstance(e));
		
		// REMOVE
		assertFalse(pool.removeInstance(e));
	}
	
	@Test
	public void testRemoveWithMiniSystem1() {
		BIPComponent e = createComponent(new ExampleE(), "e", true);
		BIPComponent d = createComponent(new ExampleD(), "d", true);
		
		// ADD
		assertTrue(pool.addInstance(e));
		assertTrue(pool.addInstance(d));
		
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
		assertFalse(pool.addInstance(a0));
		assertFalse(pool.addInstance(a1));
		assertFalse(pool.addInstance(a2));
		assertFalse(pool.addInstance(b0));
		assertTrue(pool.addInstance(e0));
		assertTrue(pool.addInstance(b1));
		assertTrue(pool.addInstance(c0));
		
		// REMOVE
		assertTrue(pool.removeInstance(c0));
		assertFalse(pool.removeInstance(e0));
	}
}
