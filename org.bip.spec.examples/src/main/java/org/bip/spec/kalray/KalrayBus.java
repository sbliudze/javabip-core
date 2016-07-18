package org.bip.spec.kalray;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KalrayBus implements ResourceProvider {

	
	private final String name;
	private final String resourceID;
	private boolean resourceTaken;
	
	private Logger logger = LoggerFactory.getLogger(KalrayBus.class);

	public KalrayBus(String name) {
		this.name = name;
		this.resourceID = name;
		resourceTaken = false;
	}
	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		resourceTaken = false;
	}

	@Override
	public String constraint() {
		if (resourceTaken) return name + "=0";
		return name + ">=0";
	}
	
	@Override
	public String cost() {
		if (resourceTaken) return "0,"+name + "=0;";
		return "0,"+name + ">=0;";
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		resourceTaken = true;
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

}
