package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceHandle;
import org.bip.api.ResourceManager;

@Port(name = "bPort", type = PortType.enforceable)
@ComponentType(initial = "0", name = "org.bip.spec.BusResource", resourceName = "b")
public class Bus implements ResourceManager {

	private final String name = "b";
	private final String resourceID= "bus";
	private String cost = "";
	int capacity;
	int currentCapacity;

	public Bus(int capacity) {
		this.capacity = capacity;
		this.currentCapacity = capacity;
		this.cost = costString();
	}
	
	@Transition(name = "bPort", source = "0", target = "0", guard = "g")
	public void aTransition() {
		System.out.println("b transition is executing");
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
		return "0, " +cost + ";";
	}

	// we suppose that the allocator sends us the new value (or maybe rather the difference?)
	public void updateCost(String newCost) {
		int taken = Integer.parseInt(newCost);
		this.currentCapacity = capacity - taken;
		this.cost = costString();
		// TODO throw exception if the difference is less than zero
	}

	private String costString() {
		return "b>=0 & b<=" + Integer.toString(currentCapacity);
	}

	@Override
	public String resourceName() {
		// TODO Auto-generated method stub
		return "b";
	}

	@Override
	public void augmentCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		
	}

	@Override
	public ResourceHandle decreaseCost(String deltaCost) {
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
return new ResourceHandle() {
			
			@Override
			public String resourceID() {
				return "simpleB";
			}
			
			@Override
			public Object resource() {
				return null;
			}
		};
	}

}
