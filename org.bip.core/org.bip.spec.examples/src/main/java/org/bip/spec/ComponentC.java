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
@ComponentType(initial = "zero", name = "org.bip.spec.ComponentC")
public class ComponentC {
	Logger logger = LoggerFactory.getLogger(ComponentC.class);

	private int memoryZ = -200;

	@Transition(name = "a", source = "zero", target = "zero")
	public void componentCTransitionA() {
		logger.debug("Transition a of ComponentC has been performed");
	}

	@Transition(name = "b", source = "zero", target = "zero")
	public void componentCTransitionB() {
		logger.debug("Transition b of ComponentC has been performed");
	}

	@Data(name = "memoryZ", accessTypePort = AccessType.allowed, ports = {"b"})
	public int memoryZ() {
		return memoryZ;
	}
}
