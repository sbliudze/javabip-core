package org.bip.resources;

public class SingleResourceManager extends ResourceManager {

	
	public SingleResourceManager(String name) {
		super(name);
	}
	
	@Override
	public String cost() {
		return resourceName + "=0 | " + resourceName + "=1";
	}

	@Override
	public void augmentCost(String amount) {
		logger.debug("The amount of deallocated virtual resource " + resourceName + " is " + amount);

	}

	@Override
	public void decreaseCost(String amount) {
		logger.debug("The amount of allocated virtual resource " + resourceName + " is " + amount);

	}

}
