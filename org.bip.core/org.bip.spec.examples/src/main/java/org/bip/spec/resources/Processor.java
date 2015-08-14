package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;

public class Processor implements ResourceProvider {

	private final String name = "p";
	private String cost = "";

	public Processor() {
		this.cost = "m>=0 | p=1";
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
		return ResourceType.processor;
	}

}
