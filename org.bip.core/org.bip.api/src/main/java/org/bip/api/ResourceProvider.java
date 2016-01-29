package org.bip.api;

public interface ResourceProvider {

	public String cost();

	public String name();

	public ResourceType type();

	public void augmentCost(String deltaCost);

	public void decreaseCost(String deltaCost);

	public String providedResourceID();
}
