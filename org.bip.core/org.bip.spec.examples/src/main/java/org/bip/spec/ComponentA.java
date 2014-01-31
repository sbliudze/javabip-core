package org.bip.spec;


import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipGuard;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@bipPorts({ @bipPort(name = "a", type = "enforceable"), @bipPort(name = "b", type = "spontaneous") })
@bipComponentType(initial = "zero", name = "org.bip.spec.ComponentA")
public class ComponentA {
	Logger logger = LoggerFactory.getLogger(ComponentA.class);
	final private int memoryLimit;

	private int currentCapacity = 0;

	public ComponentA(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@bipTransition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@bipData(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@bipTransition(name = "b", source = "zero", target = "zero")
	public void spontaneousOfA() {
		logger.debug("Spontaneous transition b of component A is being executed. ");
	}
	
	@bipGuard(name = "hasCapacity")
	public boolean hasCapacity(@bipData(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < " + memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit && currentCapacity > -1));
		return ((currentCapacity + memoryUsage < memoryLimit) && (currentCapacity + memoryUsage >=0));
	}
}
