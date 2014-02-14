package org.bip.spec;

import org.bip.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "giveY", type = "enforceable"), @bipPort(name = "giveZ", type = "enforceable"), @bipPort(name = "returnY", type = "enforceable"),
		@bipPort(name = "returnZ", type = "enforceable") })
@bipComponentType(initial = "zero", name = "org.bip.spec.Feeder")
public class Feeder {
	Logger logger = LoggerFactory.getLogger(Feeder.class);

	private int memoryY = 200;
	private int memoryZ = 300;

	@bipTransition(name = "giveY", source = "zero", target = "oneY")
	public void changeY() {
		logger.debug("Transition Y has been performed");
	}

	@bipTransition(name = "returnY", source = "oneY", target = "zero")
	public void returnY() {
		logger.debug("Transition from oneY to zero has been performed");
	}

	@bipTransition(name = "giveZ", source = "zero", target = "oneZ")
	public void changeZ() {
		logger.debug("Transition Z has been performed");
	}

	@bipTransition(name = "returnZ", source = "oneZ", target = "zero")
	public void returnZ() {
		logger.debug("Transition from oneZ to zero has been performed");
	}

	@bipData(name = "memoryY", accessTypePort = "allowed", ports = { "giveY" })
	public int memoryY() {
		return memoryY;
	}

	@bipData(name = "memoryZ", accessTypePort = "allowed", ports = { "giveZ" })
	public int memoryZ() {
		return memoryZ;
	}
}
