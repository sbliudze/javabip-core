package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;

public class Memory implements ResourceProvider {

	private final String name = "m";
	private String cost = "";

	public Memory(int capacity) {
		this.cost = "m>=0 & m<="+Integer.toString(capacity);
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
		return ResourceType.memory;
	}

}
