package org.bip.spec.resources;


import org.bip.api.ResourceType;

public class Bus extends Resource {

	private final String name = "b";
	private String cost = "";
	int capacity;
	int currentCapacity;

	public Bus(int capacity) {
		this.capacity = capacity;
		this.currentCapacity = capacity;
		this.cost = costString();
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
		return ResourceType.bus;
	}

	// we suppose that the allocator sends us the new value (or maybe rather the difference?)
	public void updateCost(String newCost) {
		int taken = Integer.parseInt(newCost);
		this.currentCapacity = capacity - taken;
		this.cost = costString();
		// TODO throw exception if the difference is less than zero
	}

	private String costString() {
		return "b>=0 & b<=" + Integer.toString(currentCapacity);
	}

	@Override
	public void augmentCost(String deltaCost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseCost(String deltaCost) {
		// TODO Auto-generated method stub
		
	}

}
