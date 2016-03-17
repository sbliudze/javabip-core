package org.bip.spec.resources;

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
		logger.info("The resource data " + this.resourceName
				+ " has been created.");
		created = true;
	}

	@Transition(name = "delete", source = "1", target = "0", guard = "idOK")
	public void deleteData() {
		logger.info("The resource data " + this.resourceName
				+ " has been deleted.");
		created = false;
	}

	@Guard(name = "idOK")
	public boolean interactionAllowed(@Data(name = "id") String givenId) {
		return this.resourceName == givenId;
	}

	@Override
	public String cost() {
		if (!created)
			return resourceName + "=0";
		return resourceName + "=0 | " + resourceName + "=1";
	}
}
