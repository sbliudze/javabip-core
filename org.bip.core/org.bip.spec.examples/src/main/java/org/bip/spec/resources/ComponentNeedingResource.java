package org.bip.spec.resources;

import java.util.HashMap;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.ResourceUtility;
import org.bip.annotations.ResourcesRequired;
import org.bip.annotations.Transition;
import org.bip.annotations.ResourceRequired;
import org.bip.api.BIPActor;
import org.bip.api.Executor;
import org.bip.api.PortType;
import org.bip.api.ResourceAware;
import org.bip.api.ResourceType;
import org.bip.api.DataOut.AccessType;

@Ports({ @Port(name = "getResource", type = PortType.enforceable), @Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.ComponentNeedingResource")
public class ComponentNeedingResource implements ResourceAware {

	private BIPActor allocatorExecutor;
	//TODO utility should use the resource labels, but not other names
	private final String utility =  "p=1 & m=128";
	
	@Transition(name = "getResource", source = "0", target = "1", guard = "")
	@ResourcesRequired({ @ResourceRequired(label = "m1", type = ResourceType.memory, utility = "mFunc"), @ResourceRequired(label = "p1", type = ResourceType.processor, utility = "pFunc") })
	@ResourceUtility(utility=utility)
	//public void aTransition(@ResourceRequired(label = "m1", type = ResourceType.memory, utility = "mFunc") Memory m, @ResourceRequired(label = "p1", type = ResourceType.processor, utility = "pFunc")Processor p) {
	public void getResource() {	
		System.err.println("Asking for resources");
	}
	
	@Transition(name = "", source = "1", target = "2", guard = "")
	public void process() {	
		//here we must be using a resourcs
		System.err.println("Processing something using resources");
	}
	
	//TODO if we make this transition spontaneous, then we need to call it ourselves,
	// then we have to have a link to our own executor
	@Transition(name = "", source = "2", target = "0", guard = "")
	public void release() {	
		System.err.println("Releasing resources");
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		HashMap<String, Object> data = new HashMap<String, Object>();
		//data.put("resourceUnit", "m");
		allocatorExecutor.inform("release", data);
		data.clear();
		data.put("resourceUnit", "p");
		allocatorExecutor.inform("release", data);
		//allocatorExecutor.setData("resourceUnit", "p");
		//TODO can we release some resources and keep others?
		//because the problem is, we know nothing about the bus..
	}

	@Data(name = "utility", accessTypePort = AccessType.any)
	public String utility() {
		return utility;
	}
	
	@Override
	public void setAllocator(BIPActor allocatorExecutor) {
		this.allocatorExecutor = allocatorExecutor;
	}

}
