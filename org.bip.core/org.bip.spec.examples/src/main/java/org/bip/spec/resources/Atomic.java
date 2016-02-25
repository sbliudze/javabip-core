package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "work", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.Atomic")
public class Atomic {

	private int min;
	private int max;
	private int minIndex;
	private int maxIndex;

	private String id;
	private int[] array = {1,2}; //for n=2

	private Logger logger = LoggerFactory.getLogger(Atomic.class);
	
	public Atomic(int n) {
		
	}
	
	@Transition(name = "swap", source = "1", target = "2", guard = "")
	public void swap() {
		//this transition will synchronize only if swap is needed
		//which is checked at the checker component
		logger.info("The swapper component is comparing two items of different components");
	}
	
	@Transition(name = "getRoute", source = "2", target = "3", guard = "")
	public void getRoute(@Data(name="min") int min, @Data(name="max") int max) {
		
		//System.err.println("Storing the route resource: "+ resources);
		
	}

	@Data(name = "min", accessTypePort = AccessType.any)
	public Integer min() {
		return min;
	}
	
	@Data(name = "id", accessTypePort = AccessType.any)
	public String id() {
		return id;
	}
}
