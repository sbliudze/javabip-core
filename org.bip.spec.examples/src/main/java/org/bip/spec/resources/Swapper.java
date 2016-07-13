package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ 
	@Port(name = "askFirstResource", type = PortType.enforceable), 
	@Port(name = "askSecondResource", type = PortType.enforceable), 
	@Port(name = "swap", type = PortType.enforceable), 
	@Port(name = "release", type = PortType.enforceable), 
	@Port(name = "rollback2", type = PortType.enforceable), 
	@Port(name = "rollback1", type = PortType.enforceable)
	})
@ComponentType(initial = "0", name = "org.bip.spec.resources.Swapper")
public class Swapper {
	
	@Transition(name = "askFirstResource", source = "0", target = "1", guard = "")
	public void askFirstResource(@Data(name="firstDataPath") String filePathToSwap) {
		System.err.println("askFirstResource");
	}
	
	@Transition(name = "askSecondResource", source = "1", target = "2", guard = "")
	public void askSecondResource() {
		System.err.println("askSecondResource");
	}

	@Transition(name = "swap", source = "2", target = "3", guard = "")
	public void swap() {
		System.err.println("swap");
	}

	@Transition(name = "release", source = "3", target = "0", guard = "")
	public void release() {
		System.err.println("release");
	}

	@Transition(name = "rollback1", source = "1", target = "0", guard = "")
	public void rollback1() {
		System.err.println("rollback1");
	}
	
}
