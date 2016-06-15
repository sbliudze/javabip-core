package org.bip.constraint;

import java.util.HashMap;

public interface ResourceAllocation {

	public String resourceAmount(String resource);

	public boolean hasResource(String unit);

	HashMap<String, Integer> resourceAmounts();
}
