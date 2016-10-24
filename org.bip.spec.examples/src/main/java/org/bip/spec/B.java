package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "b0", type = PortType.enforceable), @Port(name = "b1", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.B")
public class B {

	private String id;
	private StringBuilder sb;

	public B(String id) {
		this.id = id;
		this.sb = new StringBuilder();
	}

	@Transition(name = "b0", source = "zero", target = "zero")
	public void b0() {
		sb.append(String.format("Component b%s made transition b0\n", id));
	}

	@Transition(name = "b1", source = "zero", target = "zero")
	public void b1() {
		sb.append(String.format("Component b%s made transition b1\n", id));
	}
	
	public String getResult() {
		return sb.toString();
	}
}
