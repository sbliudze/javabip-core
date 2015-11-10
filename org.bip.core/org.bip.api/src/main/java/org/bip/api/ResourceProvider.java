package org.bip.api;

public interface ResourceProvider {

	String cost();

	String name();
	
	ResourceType type();
	
//	void updateCost(String newCost);
	
	void augmentCost(String deltaCost);
	void decreaseCost(String deltaCost);

}
