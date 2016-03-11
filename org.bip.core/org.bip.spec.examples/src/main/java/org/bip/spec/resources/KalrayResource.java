package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KalrayResource implements ResourceProvider  {

	private final String name;
	private final String resourceID;
	private String cost = "";
	private int capacity;
	private int currentCapacity;
	private boolean bounded;
	
	private Logger logger = LoggerFactory.getLogger(KalrayResource.class);

	public KalrayResource(String name, int capacity, boolean bounded) {
		this.capacity = capacity;
		this.name = name;
		this.resourceID = name;
		this.currentCapacity = capacity;
		this.bounded=bounded;
		//this.cost =  costString();
	}

	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		//this.cost = costString();
		//System.err.println("cost is now (+) " + cost);
	}

	@Override
	public String cost() {
		if (bounded)
		return name + ">=0 &" + name + "<=" + Integer.toString(currentCapacity);
		return name + ">=0";
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
		//this.cost = costString();
		//System.err.println("cost is now (-) " + cost);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String providedResourceID() {
		return resourceID;
	}

	@Override
	public ResourceType type() {
		return ResourceType.custom;
	}

}
