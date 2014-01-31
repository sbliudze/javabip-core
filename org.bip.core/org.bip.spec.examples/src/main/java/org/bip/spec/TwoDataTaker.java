package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipGuard;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "a", type = "enforceable") })
@bipComponentType(initial = "zero", name = "org.bip.spec.TwoDataTaker")
public class TwoDataTaker {
	Logger logger = LoggerFactory.getLogger(TwoDataTaker.class);
	final private int memoryLimit;

	private int currentCapacity = 0;

	public TwoDataTaker(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@bipTransition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@bipData(name = "memoryUsageX") Integer memoryUsage1, @bipData(name = "memoryUsageR") Integer memoryUsage2) {
		currentCapacity += (memoryUsage1+memoryUsage2);
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

//	@bipTransition(name = "b", source = "zero", target = "zero")
//	public void spontaneousOfA() {
//		logger.debug("Spontaneous transition b of component TwoDataTaker is being executed. ");
//	}
	
	@bipGuard(name = "hasCapacity")
	public boolean hasCapacity(@bipData(name = "memoryUsageX") Integer memoryUsage1, @bipData(name = "memoryUsageR") Integer memoryUsage2) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage1+memoryUsage2 + " < " + memoryLimit + " " );
		return ((currentCapacity + memoryUsage1+memoryUsage2 < memoryLimit) );
	}
}
