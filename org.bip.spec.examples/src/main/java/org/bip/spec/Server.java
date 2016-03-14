package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "turnon", type = PortType.enforceable), @Port(name = "crash", type = PortType.enforceable),
		@Port(name = "repair", type = PortType.enforceable), @Port(name = "turnoff", type = PortType.enforceable),
		@Port(name = "softrepair", type = PortType.enforceable) })
@ComponentType(initial = "off", name = "org.bip.spec.Server")
public class Server {

	Logger logger = LoggerFactory.getLogger(Tracker.class);
	private int serverId;
	private int turnOnServerId;
	private int crashServerId;

	public Server(int id) {
		serverId = id;
		turnOnServerId = -1;
		crashServerId = -1;

	}

	@Transition(name = "turnon", source = "off", target = "on")
	public void turningon(@Data(name = "serverId") Integer id) {
		this.crashServerId = id;
		// System.out.println("Server with id " + serverId + " is on. Server with id " +
		// crashServerId + " has crashed.");
	}

	@Transition(name = "crash", source = "on", target = "crash")
	public void crashing(@Data(name = "sId") Integer id) {
		this.turnOnServerId = id;
		// System.out.println("Server with id " + serverId + " has crashed. Server with id " +
		// turnOnServerId + " is on.");
	}

	@Transition(name = "repair", source = "crash", target = "on", guard = "h1CanInteract")
	public void repairing() {
		turnOnServerId = -1;
		// System.out.println("Server with id " + serverId + " is repaired");
	}

	@Transition(name = "turnoff", source = "on", target = "off", guard = "h2CanInteract")
	public void turningoff() {
		crashServerId = -1;
		// System.out.println("Server with id " + serverId + " is off");
	}

	@Transition(name = "softrepair", source = "crash", target = "off", guard = "h1CanInteract&h2CanInteract")
	public void softrepairing() {
		turnOnServerId = -1;
		crashServerId = -1;
		// System.out.println("Server with id " + serverId + " is softrepaired");
	}

	@Data(name = "serverId", accessTypePort = AccessType.any)
	public int serverId() {
		return serverId;
	}

	@Data(name = "sId", accessTypePort = AccessType.any)
	public int sId() {
		return serverId;
	}

	@Guard(name = "h1CanInteract")
	public boolean canInteract1(@Data(name = "sId") Integer id) {
		return (crashServerId >= 0 && id == crashServerId);
	}

	@Guard(name = "h2CanInteract")
	public boolean canInteract2(@Data(name = "serverId") Integer id) {
		return (turnOnServerId >= 0 && id == turnOnServerId);
	}

}

