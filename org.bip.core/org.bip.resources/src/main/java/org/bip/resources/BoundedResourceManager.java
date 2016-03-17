package org.bip.resources;

public class BoundedResourceManager extends ResourceManager {

	private int capacity;
	private int currentCapacity;
	
	public BoundedResourceManager(String name, int capacity) {
		super(name);
		this.capacity = capacity;
		this.currentCapacity = capacity;
	}
	
	@Override
	public String cost() {
		return resourceName + ">=0 &" + resourceName + "<=" + Integer.toString(currentCapacity);
	}

	@Override
	public void augmentCost(String amount) {
		logger.debug("The amount of deallocated bounded resource " + resourceName + " is " + amount);
		int taken = Integer.parseInt(amount);
		this.currentCapacity += taken;
	}

	@Override
	public void decreaseCost(String amount) {
		logger.debug("The amount of deallocated bounded resource " + resourceName + " is " + amount);
		int taken = Integer.parseInt(amount);
		this.currentCapacity -= taken;
	}
	
}
