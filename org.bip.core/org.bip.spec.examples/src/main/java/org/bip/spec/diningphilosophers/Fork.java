package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "free", type = PortType.enforceable),
	 	 @Port(name = "hold", type = PortType.enforceable) })
@ComponentType(initial = "available", name = "org.bip.spec.diningphilosophers.Fork")
public class Fork {

	private int forkId;

	public Fork(int forkId) {
		this.forkId = forkId;
	}
	
	@Transition(name="hold", source="available", target="taken")
	public void hold() {
		System.out.println("Fork " + forkId + " transitions to taken state.");
	}
	
	@Transition(name="free", source="taken", target="available")
	public void free() {
		System.out.println("Fork " + forkId + " transitions to available state.");
	}
	
	@Data(name="forkId")
	public int forkId() {
		return forkId;
	}
	
}
