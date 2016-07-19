package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceHandle;
import org.bip.api.ResourceManager;
import org.bip.api.ResourceType;
import org.bip.exceptions.BIPException;

@Port(name = "pPort", type = PortType.enforceable)
@ComponentType(initial = "0", name = "org.bip.spec.ProcessorResource", resourceName = "p")
public class Processor implements ResourceManager {
	private final String name = "p";
	private final String resourceID= "proc";
	private String cost = "";

	public Processor() {
		this.cost = "p=0 | p=1";
	}
	
	@Transition(name = "pPort", source = "0", target = "0", guard = "g")
	public void aTransition() {
		System.out.println("p transition is executing");
	}

	@Guard(name="g")
	public boolean aGuard() {
		return false;
	}
	
	
	@Override
	public String constraint() {
		return cost;
	}
	
	@Override
	public String cost() {
		return "0, "+cost + ";";
	}
	
	@Override
	public void augmentCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		if (taken == 1 || taken ==0) {this.cost = "p=0 | p=1";}
		else {throw new BIPException("Processor cost can be only changed by one or zero");}

	}

	//TODO throw exception if the difference is less than zero in all methods
	@Override
	public ResourceHandle decreaseCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		if (taken == 1) {this.cost = "p=0";}
		else {throw new BIPException("Processor cost can be only changed by one or zero");}
		return new ResourceHandle() {
			
			@Override
			public String resourceID() {
				return "simpleP";
			}
			
			@Override
			public Object resource() {
				return null;
			}
		};
	}

	@Override
	public String resourceName() {
		return "p";
	}

}
