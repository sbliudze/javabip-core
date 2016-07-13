package org.bip.resources;

public class SingleResourceManager extends ResourceManager {

	private String cost = resourceName + "=0 | " + resourceName + "=1";
	
	public SingleResourceManager(String name) {
		super(name);
	}
	
	
	@Override
	public String cost() {
		return "0, " + cost + ";";
		
	}

	@Override
	public void augmentCost(String amount) {
		logger.debug("The amount of deallocated virtual resource " + resourceName + " is " + amount);
		cost =  resourceName + "=0 | " + resourceName + "=1";

	}

	@Override
	public void decreaseCost(String amount) {
		logger.debug("The amount of allocated virtual resource " + resourceName + " is " + amount);
		 cost = resourceName + "=0 | ";
	}
	
	@Override
	public String constraint() {
		return cost;
	}

}
