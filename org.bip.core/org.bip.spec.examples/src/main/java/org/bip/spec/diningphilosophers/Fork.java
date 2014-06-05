package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "free", type = PortType.enforceable),
	 	 @Port(name = "hold", type = PortType.enforceable) })
@ComponentType(initial = "available", name = "org.bip.spec.diningphilosophers.Fork")
public class Fork {

	Logger logger = LoggerFactory.getLogger(Fork.class);
	
	private int forkId;

	private int noOfTimesUsed;
	
	public Fork(int forkId) {
		this.forkId = forkId;
		noOfTimesUsed = 0;
	}
	
	@Transition(name="hold", source="available", target="taken")
	public void hold() {
		logger.debug("Fork " + forkId + " transitions to taken state.");
		noOfTimesUsed++;
	}
	
	@Transition(name="free", source="taken", target="available")
	public void free() {
		logger.debug("Fork " + forkId + " transitions to available state.");
	}
	
	@Data(name="forkId")
	public int forkId() {
		return forkId;
	}
	
	public int noOfTimesUsed() {
		return noOfTimesUsed;
	}
	
}
