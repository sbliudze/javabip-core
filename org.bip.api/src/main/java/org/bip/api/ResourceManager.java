package org.bip.api;

public interface ResourceManager {
	public String constraint();
	
	public String cost();

	//we should not need this, as the resource name is given in the component type annotation
	public String resourceName();

	//public ResourceType type();

	public void augmentCost(String amount);

	public ResourceHandle decreaseCost(String amount);

	//public String providedResourceID();
}
