package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipGuard;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "getData", type = "enforceable"), @bipPort(name = "useData", type = "enforceable") })
@bipComponentType(initial = "zero", name = "org.bip.spec.Consumer")
public class Consumer {
	Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	final private int memoryLimit;

	private int currentCapacity = 0;

	public Consumer(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@bipTransition(name = "getData", source = "zero", target = "one", guard = "hasCapacity")
	public void addRoute(@bipData(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@bipTransition(name = "useData", source = "one", target = "zero")
	public void addRoute() {
		currentCapacity = 0;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@bipGuard(name = "hasCapacity")
	public boolean hasCapacity(@bipData(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < " + memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit));
		return currentCapacity + memoryUsage < memoryLimit;
	}

}
