package org.bip.api;

public interface ResourceManager {
	public String constraint();
	
	public String cost();

	public String resourceName();

	//public ResourceType type();

	public void augmentCost(String amount);

	public ResourceHandle decreaseCost(String amount);

	//public String providedResourceID();
}
