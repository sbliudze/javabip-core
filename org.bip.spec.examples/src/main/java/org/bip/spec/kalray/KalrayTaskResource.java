package org.bip.spec.kalray;


import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KalrayTaskResource  implements ResourceProvider{
	private final String name;
	private final String resourceID;
	private boolean executed;
	private KalrayData dataKalray;
	
	private Logger logger = LoggerFactory.getLogger(KalrayMemoryBank.class);

	public KalrayTaskResource(String name) {
		this.name = name;
		this.resourceID = name;
		this.executed = false;
	}

	public void setData(KalrayData dataKalray) {
		this.dataKalray = dataKalray;
	}
	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
	}

	@Override
	public String constraint() {
		if (executed) return name + "=0";
		return name + "=0 | " + name + "=1";
	}

	@Override
	public String cost() {
		if (executed) return "0, " + name + "=0;";
		return "0, " + name + "=0 | " + name + "=1;";
	}
	
	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		if (taken>0){	this.executed = true;}
		if (dataKalray!=null & taken>0) dataKalray.create();
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
