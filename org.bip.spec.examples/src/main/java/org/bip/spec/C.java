package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "c0", type = PortType.enforceable), @Port(name = "c1", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.C")
public class C {

	private String id;
	public StringBuilder sb;

	public C(String id) {
		this.id = id;
		this.sb = new StringBuilder();
	}

	@Transition(name = "c0", source = "zero", target = "zero")
	public void c0() {
		sb.append(String.format("Component c%s made transition c0\n", id));
	}

	@Transition(name = "c1", source = "zero", target = "zero")
	public void c1() {
		sb.append(String.format("Component c%s made transition c1\n", id));
	}
	
	public String getResult() {
		return sb.toString();
	}

}
