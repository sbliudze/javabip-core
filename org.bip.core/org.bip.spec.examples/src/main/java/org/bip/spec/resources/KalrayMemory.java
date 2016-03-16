package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ 
		@Port(name = "create", type = PortType.enforceable),
		@Port(name = "read", type = PortType.enforceable) })
@ComponentType(initial = "i", name = "org.bip.spec.resources.KalrayMemory")
public class KalrayMemory implements ResourceProxy {

	Logger logger = LoggerFactory.getLogger(KalrayMemory.class);

	private String data;
	private int counter;
	private String id;

	public KalrayMemory(String id) {
		this.id = id;
	}

	@Override
	public String resourceID() {
		return id;
	}

	@Transition(name = "create", source = "0", target = "0", guard = "")
	public void createData(String storedDataName, int readCount) {
		this.data = storedDataName;
		this.counter = readCount;
	}

	@Transition(name = "read", source = "0", target = "0", guard = "")
	public void readData() {
		counter--;
		if (counter == 0) {
			data = "";
		}
	}

}
