package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "register", type = PortType.enforceable),
		@Port(name = "speak", type = PortType.enforceable),
		@Port(name = "listen", type = PortType.enforceable),
		@Port(name = "unregister", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.Peer")
public class Peer {
	Logger logger = LoggerFactory.getLogger(Peer.class);

	private int trackerId;
	private int peerId;

	public Peer(int id) {
		this.trackerId = -1;
		peerId = id;
	}

	@Transition(name = "register", source = "zero", target = "one")
	public void registering(@Data(name = "trackerId") Integer id) {
		this.trackerId = id;
		System.out.println("Peer " + peerId + " registered with tracker " + id);
	}

	@Transition(name = "speak", source = "one", target = "one", guard = "canInteract")
	public void speaking() {
		System.out.println("Peer " + peerId + " speaking to " + trackerId);
	}

	@Transition(name = "listen", source = "one", target = "one", guard = "canInteract")
	public void listening() {
		System.out.println("Peer " + peerId + " listening to " + trackerId);
	}

	@Transition(name = "unregister", source = "one", target = "zero", guard = "canInteract")
	public void unregistering() {
		System.out.println("Peer " + peerId + " unregistered with tracker "
				+ this.trackerId);
		this.trackerId = -1;
	}

	@Guard(name = "canInteract")
	public boolean canInteract(@Data(name = "trackerId") Integer id) {
		System.out.println("Peer " + peerId + " registered with " + trackerId
				+ ", interacting with " + id + ": "
				+ (trackerId >= 0 && id == trackerId));
		return (trackerId >= 0 && id == trackerId);
	}
}
