package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "a", type = "enforceable"), @bipPort(name = "b", type = "enforceable")})
@bipComponentType(initial = "zero", name = "org.bip.spec.TwoDataProvider2")
public class TwoDataProvider2 {
	Logger logger = LoggerFactory.getLogger(TwoDataProvider2.class);

	private int memoryZ = 80;
	private int memoryP = 40;

	@bipTransition(name = "a", source = "zero", target = "zero")
	public void componentBTransitionA() {
		logger.debug("Transition a of TwoDataProvider2 has been performed");
	}

	@bipTransition(name = "b", source = "zero", target = "zero")
	public void componentBTransitionB() {
		logger.debug("Transition b of TwoDataProvider2 has been performed");
	}

	@bipData(name = "memoryZ", accessTypePort = "any")
	public int memoryZ() {
		return memoryZ;
	}
	
	@bipData(name = "memoryP", accessTypePort = "any")
	public int memoryP() {
		return memoryP;
	}
}
