package org.bip.spec.resources;

import org.bip.api.ResourceType;
import org.bip.exceptions.BIPException;

public class Processor extends Resource {
	private final String name = "p";
	private String cost = "";

	public Processor() {
		this.cost = "p=0 | p=1";
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
	
//	public void updateCost(String newCost) {
//		int taken = Integer.parseInt(newCost);
//		if (taken ==1)
//		{
//			this.cost = "p=0";
//		}
//	}


	@Override
	public void augmentCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		if (taken == 1 || taken ==0) {this.cost = "p=0 | p=1";}
		else {throw new BIPException("Processor cost can be only changed by one or zero");}

	}

	//TODO throw exception if the difference is less than zero in all methods
	@Override
	public void decreaseCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		if (taken == 1) {this.cost = "p=0";}
		else {throw new BIPException("Processor cost can be only changed by one or zero");}
	}

	
//	public void updateCost(String newCost) {
//		int taken = Integer.parseInt(newCost);
//		if (taken ==1)
//		{
//			this.cost = "p=0";
//		}
//	}

}
