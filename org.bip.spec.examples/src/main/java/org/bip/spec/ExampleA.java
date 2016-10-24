package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "portA", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.ExampleA")
public class ExampleA {
	
	@Transition(name="portA", source="zero", target="zero")
	public void testExampleA() {
		
	}

}
