package org.bip.spec.resources;

import java.util.ArrayList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.ResourceRelease;
import org.bip.annotations.ResourceRequired;
import org.bip.annotations.ResourceUtility;
import org.bip.annotations.ResourcesRequired;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "getResource", type = PortType.enforceable), 
	@Port(name = "process", type = PortType.enforceable),
	@Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.ComponentNeedingResource")
public class ComponentNeedingResource {

	private Logger logger = LoggerFactory.getLogger(ComponentNeedingResource.class);
	private final int memory;
	
	private int allocID;

	public ComponentNeedingResource(int memoryNeed) {
		this.memory = memoryNeed;
		this.utility =  "p=1 & m="+ memory;
	}

	// utility should use the resource labels, but not other names
	private final String utility;// = "p=1 & m=128";

	@Transition(name = "getResource", source = "0", target = "1", guard = "")
	@ResourcesRequired({ @ResourceRequired(label = "m1", type = ResourceType.memory),
			@ResourceRequired(label = "p1", type = ResourceType.processor) })
	@ResourceUtility(utility = "128, p=1 & m=128;") 
	// public void aTransition(@ResourceRequired(label = "m1", type = ResourceType.memory, utility = "mFunc") Memory m, @ResourceRequired(label = "p1", type =
	// ResourceType.processor, utility = "pFunc")Processor p) {
	public void getResource() {
		//TODO sysout to logger.info
		System.err.println("Asking for resources");
	}

	@Transition(name = "process", source = "1", target = "2", guard = "")
	public void process() {
		// here we must be using a resource
		System.err.println("Processing something using resources: ");
	}

	@Transition(name = "release", source = "2", target = "0", guard = "")
	@ResourceRelease(resources= {"p1", "m1"})
	public void release() {
		System.err.println("Releasing resources");
	}

	//@Data(name = "utility", accessTypePort = AccessType.any)
	public String utility() {
		return utility;
	}
	
	//@Data(name = "allocID", accessTypePort = AccessType.any)
	public int allocID() {
		return allocID;
	}

	//@Data(name = "resourceUnit", accessTypePort = AccessType.any)
	public ArrayList<String> releasedResources() {
		ArrayList<String> dataRelease = new ArrayList<String>();
		dataRelease.add("p");
		dataRelease.add("m");
		return dataRelease;
	}

}
