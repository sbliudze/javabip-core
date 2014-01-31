package org.bip.spec;

import org.bip.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "add", type = "enforceable"), @bipPort(name = "rm", type = "enforceable") })
@bipComponentType(initial = "one", name = "org.bip.spec.MemoryMonitor")
public class MemoryMonitor {
	private Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
	
	final private int memoryLimit;

	private int currentCapacity = 0;

	public MemoryMonitor(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@bipTransition(name = "add", source = "one", target = "one", guard = "hasCapacity")
	public void addRoute(@bipData(name="memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@bipTransition(name = "rm", source = "one", target = "one", guard = "hasRouteRunning")
	public void removeRoute(@bipData(name="memoryUsage") Integer deltaMemory) {
		currentCapacity -= deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@bipGuard(name = "hasCapacity")
	public boolean hasCapacity(@bipData(name="memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < " + memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit));
		return currentCapacity + memoryUsage < memoryLimit;
	}

	@bipGuard(name = "hasRouteRunning")
	public boolean hasRouteRunning() {
		return currentCapacity > 0;
	}
}
