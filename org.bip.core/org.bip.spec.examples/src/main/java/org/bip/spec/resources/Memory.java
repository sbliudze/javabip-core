package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceProxy;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Memory implements ResourceProvider, ResourceProxy {

	private final String name = "m";
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
	public String cost() {
		return this.cost;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ResourceType type() {
		return ResourceType.memory;
	}

	// we suppose that the allocator sends us the new value (or maybe rather the difference?)
//	public void updateCost(String newCost) {
//		logger.debug("Cost of " + name + " updated to " + newCost);
//		int taken = Integer.parseInt(newCost);
//		this.currentCapacity = capacity-taken;
//		//TODO throw exception if the difference is less than zero
//	}

	private String costString() {
		return "m>=0 & m<=" + Integer.toString(currentCapacity);
	}

	@Override
	public ResourceProxy get() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void release(ResourceProxy proxy) {
		// TODO Auto-generated method stub
		
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
