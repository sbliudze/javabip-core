package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "a0", type = PortType.enforceable), @Port(name = "a1", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.A")
public class A {

	private StringBuilder sb;
	
	public A() {
		this.sb = new StringBuilder();
	}
	
	@Transition(name = "a0", source = "zero", target = "zero")
	public void a0() {
		sb.append("Component a made transition a0\n");
	}

	@Transition(name = "a1", source = "zero", target = "zero")
	public void a1() {
		sb.append("Component a made transition a1\n");
	}
	
	public String getResult() {
		return sb.toString();
	}

}
