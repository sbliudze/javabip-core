package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.enforceable)})
@ComponentType(initial = "zero", name = "org.bip.spec.TwoDataProvider2")
public class TwoDataProvider2 {
	Logger logger = LoggerFactory.getLogger(TwoDataProvider2.class);

	private int memoryZ = 80;
	private int memoryP = 40;

	@Transition(name = "a", source = "zero", target = "zero")
	public void componentBTransitionA() {
		logger.debug("Transition a of TwoDataProvider2 has been performed");
	}

	@Transition(name = "b", source = "zero", target = "zero")
	public void componentBTransitionB() {
		logger.debug("Transition b of TwoDataProvider2 has been performed");
	}

	@Data(name = "memoryZ", accessTypePort = AccessType.any)
	public int memoryZ() {
		return memoryZ;
	}
	
	@Data(name = "memoryP", accessTypePort = AccessType.any)
	public int memoryP() {
		return memoryP;
	}
}
