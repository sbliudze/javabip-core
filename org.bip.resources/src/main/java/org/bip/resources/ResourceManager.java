package org.bip.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceManager implements ResourceProvider {

	protected String resourceName;
	private final String resourceID;
	
	public ResourceManager(String name) {
		this.resourceName = name;
		this.resourceID = name;
	}
	
	protected Logger logger = LoggerFactory.getLogger(ResourceManager.class);
	
	@Override
	public String name() {
		return resourceName;
	}

	@Override
	public ResourceType type() {
		return ResourceType.custom;
	}

	@Override
	public String providedResourceID() {
		return resourceID;
	}

//	public void augmentCost(String deltaCost) {
//	public void decreaseCost(String deltaCost) {
//	public String cost() {
	
}
