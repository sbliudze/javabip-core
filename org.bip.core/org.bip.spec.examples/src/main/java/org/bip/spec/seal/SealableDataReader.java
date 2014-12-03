package org.bip.spec.seal;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "read", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableDataReader")
public class SealableDataReader<T> {

	Logger logger = LoggerFactory.getLogger(SealableDataReader.class);

	public int noOfTransitions = 0;

	@Transition(name = "read", source = "initial", target = "initial")
	public void read(@Data(name = "input") T data) {
		logger.debug("Read data {}", data);
		noOfTransitions++;
	}

}
