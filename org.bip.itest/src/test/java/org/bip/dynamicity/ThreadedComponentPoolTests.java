package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createComponent;
import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bip.api.BIPComponent;
import org.bip.api.BIPGlue;
import org.bip.engine.dynamicity.ComponentPool;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.ExampleD;
import org.bip.spec.ExampleE;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class ThreadedComponentPoolTests {
	private static ComponentPool pool;
	private Thread adder, remover;

	@BeforeClass
	public static void setup() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		pool = new ComponentPool(glue);
		pool.initialize();
	}

	@Test
	public void testAddRemoveThreaded() {
		final BIPComponent a0 = createComponent(new ExampleA(), "a0", true),
				a1 = createComponent(new ExampleA(), "a1", true), a2 = createComponent(new ExampleA(), "a2", true),
				a3 = createComponent(new ExampleA(), "a3", true), a4 = createComponent(new ExampleA(), "a4", true),
				a5 = createComponent(new ExampleA(), "a5", true), a6 = createComponent(new ExampleA(), "a6", true);
		final BIPComponent b0 = createComponent(new ExampleB(), "b0", true),
				b1 = createComponent(new ExampleB(), "b1", true), b2 = createComponent(new ExampleB(), "b2", true),
				b3 = createComponent(new ExampleB(), "b3", true), b4 = createComponent(new ExampleB(), "b4", true),
				b5 = createComponent(new ExampleB(), "b5", true), b6 = createComponent(new ExampleB(), "b6", true);
		final BIPComponent c0 = createComponent(new ExampleC(), "c0", true),
				c1 = createComponent(new ExampleC(), "c1", true), c2 = createComponent(new ExampleC(), "c2", true),
				c3 = createComponent(new ExampleC(), "c3", true), c4 = createComponent(new ExampleC(), "c4", true),
				c5 = createComponent(new ExampleC(), "c5", true), c6 = createComponent(new ExampleC(), "c6", true);
		final BIPComponent d0 = createComponent(new ExampleD(), "d0", true),
				d1 = createComponent(new ExampleD(), "d1", true), d2 = createComponent(new ExampleD(), "d2", true),
				d3 = createComponent(new ExampleD(), "d3", true), d4 = createComponent(new ExampleD(), "d4", true),
				d5 = createComponent(new ExampleD(), "d5", true), d6 = createComponent(new ExampleD(), "d6", true);
		final BIPComponent e0 = createComponent(new ExampleE(), "e0", true),
				e1 = createComponent(new ExampleE(), "e1", true), e2 = createComponent(new ExampleE(), "e2", true),
				e3 = createComponent(new ExampleE(), "e3", true), e4 = createComponent(new ExampleE(), "e4", true),
				e5 = createComponent(new ExampleE(), "e5", true), e6 = createComponent(new ExampleE(), "e6", true);
		pool.addInstance(a0);
		pool.addInstance(a1);
		pool.addInstance(a2);
		pool.addInstance(a3);
		pool.addInstance(b0);
		pool.addInstance(b1);
		pool.addInstance(b2);
		pool.addInstance(b3);
		pool.addInstance(c0);
		pool.addInstance(c1);
		pool.addInstance(c2);
		pool.addInstance(c3);
		pool.addInstance(d0);
		pool.addInstance(d1);
		pool.addInstance(d2);
		pool.addInstance(d3);
		pool.addInstance(d4);
		pool.addInstance(e0);
		pool.addInstance(e1);
		pool.addInstance(e2);
		pool.addInstance(e3);
		pool.addInstance(e4);
		
		adder = new Thread(new Runnable() {
			@Override
			public void run() {
				pool.addInstance(a4);
				pool.addInstance(a5);
				pool.addInstance(a6);
				pool.addInstance(b4);
				pool.addInstance(b5);
				pool.addInstance(b6);
				pool.addInstance(c4);
				pool.addInstance(c5);
				pool.addInstance(c6);
				pool.addInstance(d5);
				pool.addInstance(d6);
				pool.addInstance(e5);
				pool.addInstance(e6);
			}
		});
		
		remover = new Thread(new Runnable() {
			@Override
			public void run() {
				pool.removeInstance(a0);
				pool.removeInstance(a1);
				pool.removeInstance(a2);
				pool.removeInstance(a3);
				pool.removeInstance(b0);
				pool.removeInstance(b1);
				pool.removeInstance(b2);
				pool.removeInstance(b3);
				pool.removeInstance(c0);
				pool.removeInstance(c1);
				pool.removeInstance(c2);
				pool.removeInstance(c3);
				pool.removeInstance(d0);
				pool.removeInstance(d1);
				pool.removeInstance(d2);
				pool.removeInstance(d3);
				pool.removeInstance(d4);
				pool.removeInstance(e0);
				pool.removeInstance(e1);
				pool.removeInstance(e2);
				pool.removeInstance(e3);
				pool.removeInstance(e4);
			}
		});
		adder.start();
		remover.start();
		try {
			adder.join();
			remover.join();
		} catch (InterruptedException e) {
			LoggerFactory.getLogger(getClass()).error("Either adder or remover have been interrupted");
		}

		assertTrue(pool.removeInstance(e6));
		assertTrue(pool.removeInstance(e5));
		assertTrue(pool.removeInstance(d5));
		assertTrue(pool.removeInstance(d6));
		assertTrue(pool.removeInstance(a4));
		assertTrue(pool.removeInstance(a5));
		assertTrue(pool.removeInstance(a6));
		assertTrue(pool.removeInstance(b4));
		assertTrue(pool.removeInstance(c4));
		assertTrue(pool.removeInstance(c5));
		assertFalse(pool.removeInstance(c6));
		assertFalse(pool.removeInstance(b5));
		assertFalse(pool.removeInstance(b6));
	}

	@After
	public void cleanup() {
		pool.cleanup();
	}
}
