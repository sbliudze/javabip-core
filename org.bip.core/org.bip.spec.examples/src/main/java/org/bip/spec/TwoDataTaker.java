package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "a", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.TwoDataTaker")
public class TwoDataTaker {
	Logger logger = LoggerFactory.getLogger(TwoDataTaker.class);
	final private int memoryLimit;

	private int currentCapacity = 0;
	public int noOfTransitions;

	public TwoDataTaker(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Transition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@Data(name = "memoryUsageX") Integer memoryUsage1, @Data(name = "memoryUsageR") Integer memoryUsage2) {
		currentCapacity += (memoryUsage1+memoryUsage2);
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
		noOfTransitions++;
	}
	
	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsageX") Integer memoryUsage1, @Data(name = "memoryUsageR") Integer memoryUsage2) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage1+memoryUsage2 + " < " + memoryLimit + " " );
		return ((currentCapacity + memoryUsage1+memoryUsage2 < memoryLimit) );
	}
}
