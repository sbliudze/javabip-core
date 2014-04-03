package org.bip.spec;

import org.bip.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "add", type = "enforceable"), @Port(name = "rm", type = "enforceable") })
@ComponentType(initial = "one", name = "org.bip.spec.MemoryMonitor")
public class MemoryMonitor {
	private Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
	
	final private int memoryLimit;

	private int currentCapacity = 0;

	public MemoryMonitor(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Transition(name = "add", source = "one", target = "one", guard = "hasCapacity")
	public void addRoute(@Data(name="memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@Transition(name = "rm", source = "one", target = "one", guard = "hasRouteRunning")
	public void removeRoute(@Data(name="memoryUsage") Integer deltaMemory) {
		currentCapacity -= deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name="memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < " + memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit));
		return currentCapacity + memoryUsage < memoryLimit;
	}

	@Guard(name = "hasRouteRunning")
	public boolean hasRouteRunning() {
		return currentCapacity > 0;
	}
}
