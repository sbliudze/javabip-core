package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Resource implements ResourceProvider {

	private Logger logger = LoggerFactory.getLogger(ResourceProvider.class);
	
	private String name = "resource";
	protected int capacity;
	protected int currentCapacity;
	protected String cost;

//	@Override
//	public void updateCost(String newCost) {
//		logger.debug("Cost of " + name + " updated to " + newCost);
//		int taken = Integer.parseInt(newCost);
//		this.currentCapacity = capacity-taken;
//		//TODO throw exception if the difference is less than zero
//	}
	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
		
	}

}
