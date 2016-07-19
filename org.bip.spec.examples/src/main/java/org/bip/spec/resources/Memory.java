package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceHandle;
import org.bip.api.ResourceManager;
import org.bip.api.ResourceProvider;
import org.bip.api.ResourceProxy;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Port(name = "mPort", type = PortType.enforceable)
@ComponentType(initial = "0", name = "org.bip.spec.MemoryResource", resourceName = "m")
public class Memory implements ResourceManager  {

	private final String name = "m";
	private final String resourceID= "simpleMemory";
	private String cost = "";
	private int capacity;
	private int currentCapacity;
	
	private Logger logger = LoggerFactory.getLogger(Memory.class);

	public Memory(int capacity) {
		this.capacity = capacity;
		this.currentCapacity = capacity;
		this.cost =  costString();
	}

	@Transition(name = "mPort", source = "0", target = "0", guard = "g")
	public void aTransition() {
		System.out.println("m transition is executing");
	}
	
	@Guard(name="g")
	public boolean aGuard() {
		return false;
	}
	
	@Override
	public String constraint() {
		return this.cost;
	}
	
	@Override
	public String cost() {
		return "0, "+this.cost + ";";
	}

	private String costString() {
		return "m>=0 & m<=" + Integer.toString(currentCapacity);
	}

	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		this.cost = costString();
		System.err.println("cost is now (+) " + cost);

	}

	//TODO throw exception if the difference is less than zero in all methods
	@Override
	public ResourceHandle decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
		this.cost = costString();
		System.err.println("cost is now (-) " + cost);
		return new ResourceHandle() {
			
			@Override
			public String resourceID() {
				return "simpleM";
			}
			
			@Override
			public Object resource() {
				return null;
			}
		};
	}

	@Override
	public String resourceName() {
		return "m";
	}
}
