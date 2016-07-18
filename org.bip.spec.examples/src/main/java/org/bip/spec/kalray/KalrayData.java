package org.bip.spec.kalray;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.resources.SingleResourceManager;

@Ports({ @Port(name = "create", type = PortType.enforceable),
		@Port(name = "delete", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.resources.KalrayData")
public class KalrayData extends SingleResourceManager {

	private boolean created;

	public KalrayData(String name) {
		super(name);
		this.created = false;
	}

	@Transition(name = "create", source = "0", target = "1", guard = "idOK")
	public void create() {
		System.err.println("The resource data " + this.resourceName
				+ " has been created.");
		created = true;
	}

	@Transition(name = "delete", source = "1", target = "0", guard = "idOK")
	public void deleteData() {
		System.err.println("The resource data " + this.resourceName
				+ " has been deleted.");
		created = false;
	}

	@Guard(name = "idOK")
	//TODO looks like the guard is never called, because the data wire is not specified
	// and no exceptions are thrown - what is happening down there in executor-engine?
	public boolean interactionAllowed(@Data(name = "id") String givenId) {
		return this.resourceName == givenId;
	}

	@Override
	public String cost() {
		if (!created)
			return "0, " + resourceName + "=0;";
		return "0, " + resourceName + "=0 | " + resourceName + "="+Integer.parseInt(name().substring(1)) + ";";
	}
	
	@Override
	public String constraint() {
		if (!created)
			return resourceName + "=0";
		return resourceName + "=0 | " + resourceName + "="+Integer.parseInt(name().substring(1));
	}
}
