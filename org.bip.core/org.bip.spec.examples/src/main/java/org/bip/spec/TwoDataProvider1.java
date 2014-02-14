package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "a", type = "enforceable"), @bipPort(name = "b", type = "enforceable")})
@bipComponentType(initial = "zero", name = "org.bip.spec.TwoDataProvider1")
public class TwoDataProvider1 {
	Logger logger = LoggerFactory.getLogger(TwoDataProvider1.class);

	private int memoryY = 20;
	private int memoryQ = 90;

	@bipTransition(name = "a", source = "zero", target = "zero")
	public void componentBTransitionA() {
		logger.debug("Transition a of TwoDataProvider1 has been performed");
	}

	@bipTransition(name = "b", source = "zero", target = "zero")
	public void componentBTransitionB() {
		logger.debug("Transition b of TwoDataProvider1 has been performed");
	}

	@bipData(name = "memoryY", accessTypePort = "any")
	public int memoryY() {
		return memoryY;
	}
	
	@bipData(name = "memoryQ", accessTypePort = "any")
	public int memoryQ() {
		return memoryQ;
	}
}
