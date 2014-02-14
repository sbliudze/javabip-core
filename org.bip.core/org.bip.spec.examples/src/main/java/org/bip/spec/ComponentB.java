package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@bipPorts({ @bipPort(name = "a", type = "enforceable"), @bipPort(name = "b", type = "enforceable")})
@bipComponentType(initial = "zero", name = "org.bip.spec.ComponentB")
public class ComponentB {
	Logger logger = LoggerFactory.getLogger(ComponentB.class);

	private int memoryY = 200;

	@bipTransition(name = "a", source = "zero", target = "zero")
	public void componentBTransitionA() {
		logger.debug("Transition a of ComponentB has been performed");
	}

	@bipTransition(name = "b", source = "zero", target = "zero")
	public void componentBTransitionB() {
		logger.debug("Transition b of ComponentB has been performed");
	}

	@bipData(name = "memoryY", accessTypePort = "allowed", ports = {"a"})
	public int memoryY() {
		return memoryY;
	}
}
