package org.bip.spec.resources;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KalrayData  implements ResourceProvider {

	private final String name;
	private final String resourceID;
	private boolean created;
	
	private Logger logger = LoggerFactory.getLogger(KalrayMemoryBank.class);

	public KalrayData(String name) {
		this.name = name;
		this.resourceID = name;
		this.created = false;
	}

	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
	}

	@Override
	public String cost() {
		if (!created) return name + "=0";
		return name + "=0 | " + name + "=1";
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String providedResourceID() {
		return resourceID;
	}

	@Override
	public ResourceType type() {
		return ResourceType.custom;
	}
	
	public void create() {
		created = true;
	}
}
