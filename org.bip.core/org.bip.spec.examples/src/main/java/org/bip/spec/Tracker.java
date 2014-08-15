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

@Ports({ @Port(name = "log", type = PortType.enforceable),
		@Port(name = "broadcast", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.Tracker")
public class Tracker {

	Logger logger = LoggerFactory.getLogger(Tracker.class);
	private int trackerId;

	public Tracker(int id) {
		trackerId = id;
	}

	@Transition(name = "log", source = "zero", target = "zero")
	public void logging() {
		System.out.println("Peer has updates his status");
	}

	@Transition(name = "broadcast", source = "zero", target = "zero")
	public void broadcasting() {
		System.out.println("Broadcasting " + trackerId);
	}

	@Data(name = "trackerId", accessTypePort = AccessType.any)
	public int trackerId() {
		return trackerId;
	}
	
}
