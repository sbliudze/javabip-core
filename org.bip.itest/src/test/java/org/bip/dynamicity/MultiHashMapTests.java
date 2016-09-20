package org.bip.dynamicity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bip.engine.MultiHashMap;
import org.bip.engine.MultiMap;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultiHashMapTests {
	
	private static MultiMap<Integer, Double> map;
	
	@BeforeClass
	public static void setup() {
		map = new MultiHashMap<Integer, Double>();
	}
	
	@After
	public void cleanup() {
		map.clear();
	}
	
	@Test
	public void testEmptyContainsNothing() {
		assertTrue(map.entrySet().isEmpty());
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void testContainsKey() {
		map.put(0, 0.1);
		assertTrue(map.containsKey(0));
	}
	
	@Test
	public void testContainsValue() {
		map.put(0, 0.1);
		assertTrue(map.containsValue(0.1));
	}
	
	@Test
	public void testMapping() {
		map.put(0, 0.1);
		map.put(1, 1.2);
		map.put(2, 2.3);
		assertTrue(testEquality(map.get(0), 0.1));
		assertTrue(testEquality(map.get(1), 1.2));
		assertTrue(testEquality(map.get(2), 2.3));
	}
	
	@Test
	public void testMultiMapping() {
		map.put(0, 0.1);
		map.put(0, 0.2);
		map.put(0, 0.3);
		map.put(1, 1.1);
		map.put(1, 1.2);
		assertTrue(testEquality(map.get(0), 0.1, 0.2, 0.3));
		assertTrue(testEquality(map.values(), 0.1, 0.2, 0.3, 1.1, 1.2));
		assertFalse(map.containsValue(2.1));
		assertFalse(map.containsKey(2));
		assertTrue(testEquality(map.keySet(), 0, 1));
		assertTrue(map.size() == 2);
	}
	
	@Test
	public void testMultiInsertions() {
		map.putAll(0, new HashSet<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)));
		assertTrue(testEquality(map.get(0), 0.1, 0.2, 0.3, 0.4));
		assertTrue(testEquality(map.keySet(), 0));
		assertTrue(map.size() == 1);
		
		map.putAll(0, new HashSet<Double>(Arrays.asList(0.5, 0.6)));
		assertTrue(testEquality(map.get(0), 0.1, 0.2, 0.3, 0.4, 0.5, 0.6));
	}
	
	@Test
	public void testRemove() {
		map.putAll(0, new HashSet<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)));
		assertTrue(testEquality(map.get(0), 0.1, 0.2, 0.3, 0.4));
		assertTrue(testEquality(map.keySet(), 0));
		assertTrue(map.size() == 1);
		
		assertTrue(map.remove(0, 0.2));
		assertTrue(testEquality(map.get(0), 0.1, 0.3, 0.4));
		assertTrue(testEquality(map.keySet(), 0));
		assertTrue(map.size() == 1);
		
		assertFalse(map.remove(0, 0.2));
		assertFalse(map.remove(1, 1.1));
	}
	
	@Test
	public void testRemoveAll() {
		map.put(0, 0.1);
		map.put(0, 0.2);
		map.put(0, 0.3);
		map.put(1, 1.1);
		map.put(1, 1.2);
		assertTrue(testEquality(map.get(0), 0.1, 0.2, 0.3));
		assertTrue(testEquality(map.values(), 0.1, 0.2, 0.3, 1.1, 1.2));
		assertTrue(testEquality(map.keySet(), 0, 1));
		assertTrue(map.size() == 2);
		
		map.removeAll(0);
		assertTrue(testEquality(map.values(), 1.1, 1.2));
		assertTrue(testEquality(map.keySet(), 1));
		assertTrue(map.size() == 1);
	}
	
	private <K> boolean testEquality(Set<K> s, K... v) {
		return s.equals(new HashSet<K>(Arrays.asList(v)));
	}

}
