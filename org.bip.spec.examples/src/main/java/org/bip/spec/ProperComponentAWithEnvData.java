package org.bip.spec;


import java.util.ArrayList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Ports({ @Port(name = "a", type = PortType.enforceable), 
		 @Port(name = "b", type = PortType.spontaneous) })
@ComponentType(initial = "zero", name = "org.bip.spec.ComponentA")
public class ProperComponentAWithEnvData implements ComponentAWithEnvDataInterface {
	Logger logger = LoggerFactory.getLogger(ProperComponentAWithEnvData.class);
	public int memoryLimit;
	private ArrayList<Integer> mailbox;

	private int currentCapacity = 0;

	public ProperComponentAWithEnvData(int memoryLimit) {
		this.memoryLimit = memoryLimit;
		this.mailbox = new ArrayList<Integer>();
	}

	@Transition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	public void spontaneousOfA(Integer memoryLimit) {
		this.mailbox.add(memoryLimit);
		logger.debug("Spontaneous event b of component A has arrived. ");
	}

	@Transition(name = "", source = "zero", target = "zero", guard = "eventExists")
	public void treatEvents() {
		logger.debug("Internal transition treatEvents is being executed. ");
		this.memoryLimit = this.mailbox.remove(0);
	}
	
	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < " + memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit && currentCapacity > -1));
		return ((currentCapacity + memoryUsage < memoryLimit) && (currentCapacity + memoryUsage >=0));
	}

	@Guard(name = "eventExists")
	public boolean eventExists() {
		return (!this.mailbox.isEmpty());
	}
}
