package org.bip.api;

public interface ResourceProvider {

	public String cost();

	public String name();

	public ResourceType type();

	public void augmentCost(String amount);

	public void decreaseCost(String amount);

	public String providedResourceID();
}
