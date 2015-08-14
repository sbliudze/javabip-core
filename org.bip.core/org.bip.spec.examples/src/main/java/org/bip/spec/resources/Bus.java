package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;

public class Bus implements ResourceProvider {

	private final String name = "b";
	private String cost = "";

	public Bus(int capacity) {
		this.cost = "b>=0 & b<="+Integer.toString(capacity);
	}

	@Override
	public String cost() {
		return cost;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ResourceType type() {
		return ResourceType.bus;
	}

}
