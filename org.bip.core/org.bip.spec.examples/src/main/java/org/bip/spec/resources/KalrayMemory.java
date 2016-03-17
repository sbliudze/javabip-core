package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "create", type = PortType.enforceable),
		@Port(name = "read", type = PortType.enforceable),
		@Port(name = "deleteData", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.resources.KalrayMemory")
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

	@Transition(name = "create", source = "0", target = "1", guard = "idOK")
	public void createData(@Data(name = "data") String storedDataName,
			@Data(name = "count") int readCount) {
		this.data = storedDataName;
		this.counter = readCount;
		System.err.println("Data " + storedDataName + " created in memory "
				+ id);
	}

	@Transition(name = "read", source = "1", target = "1", guard = "idOK")
	public void readData() {
		counter--;
		if (counter == 0) {
			this.data = "";
		}
	}

	@Transition(name = "deleteData", source = "1", target = "0", guard = "noUse")
	public void deleteData() {
		this.data = "";
	}

	@Guard(name = "noUse")
	public boolean dataIsNotUsed() {
		return counter == 0;
	}

	@Guard(name = "idOK")
	public boolean interactionAllowed(@Data(name = "id") String givenId) {
		return this.id == givenId;
	}

}
