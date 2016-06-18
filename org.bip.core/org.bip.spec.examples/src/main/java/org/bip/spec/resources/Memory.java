package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceProxy;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Memory implements ResourceProvider  {

	private final String name = "m";
	private final String resourceID= "simpleMemory";
	private String cost = "";
	private int capacity;
	private int currentCapacity;
	
	private Logger logger = LoggerFactory.getLogger(Memory.class);

	public Memory(int capacity) {
		this.capacity = capacity;
		this.currentCapacity = capacity;
		this.cost =  costString();
	}

	@Override
	public String constraint() {
		return this.cost;
	}
	
	@Override
	public String cost() {
		return "0, "+this.cost + ";";
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ResourceType type() {
		return ResourceType.memory;
	}

	private String costString() {
		return "m>=0 & m<=" + Integer.toString(currentCapacity);
	}

	@Override
	public String providedResourceID() {
		return resourceID;
	}

	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		this.cost = costString();
		System.err.println("cost is now (+) " + cost);

	}

	//TODO throw exception if the difference is less than zero in all methods
	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
		this.cost = costString();
		System.err.println("cost is now (-) " + cost);
	}
}
