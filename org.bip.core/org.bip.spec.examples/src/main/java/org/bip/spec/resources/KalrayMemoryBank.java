package org.bip.spec.resources;

import java.util.HashSet;
import java.util.Set;

import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KalrayMemoryBank implements ResourceProvider  {

	private final String name;
	private final String resourceID;
	Set<Integer> processors;
	
	private Logger logger = LoggerFactory.getLogger(KalrayMemoryBank.class);

	public KalrayMemoryBank(String name) {
		this.name = name;
		this.resourceID = name;
		processors = new HashSet<Integer>();
	}

	
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		processors.remove(taken);
		//this.currentCapacity += taken;
		//System.err.println("cost is now (+) " + cost);
	}

	@Override
	public String cost() {
		if ((processors.contains(1) || processors.contains(2)) && !processors.contains(3) && !processors.contains(4))
			return name + "=0 | " + name + "=3 | " + name + "=4" ;
		if ((processors.contains(3) || processors.contains(4)) && !processors.contains(1) && !processors.contains(2))
			return name + "=0 | " + name + "=1 | " + name + "=2" ;
		if ((processors.contains(3) || processors.contains(4)) && (processors.contains(1) && processors.contains(2)))
			return name + "=0" ;
		return name + ">=0";
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		//this.currentCapacity -= taken;
		processors.add(taken);
		//System.err.println("cost is now (-) " + cost);
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
