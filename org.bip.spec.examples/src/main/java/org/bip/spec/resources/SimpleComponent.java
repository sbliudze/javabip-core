package org.bip.spec.resources;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "a", type = PortType.enforceable), 
	@Port(name = "b", type = PortType.enforceable),
	@Port(name = "c", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.SimpleComponent")
public class SimpleComponent {
	
	private int someField;
	public SimpleComponent(int i) {
		someField=i;
	}
	
	@Transition(name = "a", source = "0", target = "0")
	public void portA() {
		System.err.println("port a being executed");
	}
	
	@Transition(name = "b", source = "0", target = "0")
	public void portB() {
		System.err.println("port b being executed");
	}
	
	@Transition(name = "c", source = "0", target = "0")
	public void portC() {
		System.err.println("port c being executed");
	}

}
