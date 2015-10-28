package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.ResourceUtility;
import org.bip.annotations.ResourcesRequired;
import org.bip.annotations.Transition;
import org.bip.annotations.ResourceRequired;
import org.bip.api.Executor;
import org.bip.api.PortType;
import org.bip.api.ResourceAware;
import org.bip.api.ResourceType;
import org.bip.api.DataOut.AccessType;

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.ComponentNeedingResource")
public class ComponentNeedingResource implements ResourceAware {

	private Executor allocatorExecutor;
	
	@Transition(name = "a", source = "0", target = "0", guard = "")
	@ResourcesRequired({ @ResourceRequired(label = "m1", type = ResourceType.memory, utility = "mFunc"), @ResourceRequired(label = "p1", type = ResourceType.processor, utility = "pFunc") })
	@ResourceUtility(utility="p1=1 & m1=128")
	//public void aTransition(@ResourceRequired(label = "m1", type = ResourceType.memory, utility = "mFunc") Memory m, @ResourceRequired(label = "p1", type = ResourceType.processor, utility = "pFunc")Processor p) {
	public void aTransition() {	
		System.out.println("Doing transition requiring resources");
	}

	@Data(name = "utility", accessTypePort = AccessType.any)
	public String utility() {
		return "p1=1 & m1=128";
	}
	
	@Override
	public void setAllocator(Executor allocatorExecutor) {
		this.allocatorExecutor = allocatorExecutor;
	}

}
