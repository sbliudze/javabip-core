package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "a", type = "enforceable"), @bipPort(name = "b", type = "enforceable")})
@bipComponentType(initial = "zero", name = "org.bip.spec.ComponentC")
public class ComponentC {
	Logger logger = LoggerFactory.getLogger(ComponentC.class);

	private int memoryZ = -200;

	@bipTransition(name = "a", source = "zero", target = "zero")
	public void componentCTransitionA() {
		logger.debug("Transition a of ComponentC has been performed");
	}

	@bipTransition(name = "b", source = "zero", target = "zero")
	public void componentCTransitionB() {
		logger.debug("Transition b of ComponentC has been performed");
	}

	@bipData(name = "memoryZ", accessTypePort = "allowed", ports = {"b"})
	public int memoryZ() {
		return memoryZ;
	}
}
