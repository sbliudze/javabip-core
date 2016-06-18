package org.bip.resources;

public class VirtualResourceManager extends ResourceManager {

	public VirtualResourceManager(String name) {
		super(name);
	}
	
	@Override
	public String constraint() {
		return  resourceName + ">=0";
	}

	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("The amount of deallocated virtual resource " + resourceName + " is " + deltaCost);
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("The amount of allocated virtual resource " + resourceName + " is " + deltaCost);
	}
	
	@Override
	public String cost() {
		return "0, " + resourceName + ">=0" + ";";
		
	}

}
