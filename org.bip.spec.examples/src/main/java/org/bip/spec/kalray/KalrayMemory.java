package org.bip.spec.kalray;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.bip.api.ResourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "create", type = PortType.enforceable),
		@Port(name = "read", type = PortType.enforceable),
		@Port(name = "deleteData", type = PortType.enforceable)})
@ComponentType(initial = "free", name = "org.bip.spec.resources.KalrayMemory")
public class KalrayMemory implements ResourceProxy {

	Logger logger = LoggerFactory.getLogger(KalrayMemory.class);

	private String data = "";
	private int counter;
	private String id;

	private MemoryManager rManager;

	public KalrayMemory(String id) {
		this.id = id;
	}
	
	public KalrayMemory(MemoryManager manager) {
		this.id = manager.name();
		this.rManager = manager;
	}
	
	@Override
	public String resourceID() {
		return id;
	}

	@Transition(name = "create", source = "free", target = "hasData", guard = "idOK")
	public void createData(@Data(name = "data") String storedDataName,
			@Data(name = "count") int readCount) {
		this.data = storedDataName;
		this.counter = readCount;
		System.err.println("Data " + storedDataName + " created in memory "
				+ id);
		rManager.notifyCreation(storedDataName, readCount);
	}

	@Transition(name = "read", source = "hasData", target = "hasData", guard = "dataIdOK")
	public void readData() {
		counter--;
	}

	@Transition(name = "deleteData", source = "hasData", target = "free", guard = "dataNoLongerNeeded")
	public void deleteData() {
		this.data = "";
		rManager.notifyDeletion(data);
	}

	@Guard(name = "dataNoLongerNeeded")
	public boolean dataIsNotUsed() {
		return counter == 0;
	}

	@Guard(name = "idOK")
	public boolean interactionAllowed(@Data(name = "id") String givenId) {
		return this.id == givenId || ("d" + this.id == givenId );
	}
	
	@Guard(name = "dataIdOK")
	public boolean dinteractionAllowed(@Data(name = "dataID") String givenId) {
		return this.id.equals(givenId) || (givenId.equals("d" + this.id));
	}

	@Data(name = "dataName", accessTypePort = AccessType.allowed, ports = {"deleteData"})
	public String generatedDataName() {
		return data;
	}
	
}
