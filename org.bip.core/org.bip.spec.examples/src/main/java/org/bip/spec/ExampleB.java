package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "b0", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.ExampleB")
public class ExampleB {
	
	@Transition(name="b0", source="zero", target="zero")
	public void testExampleB() {
		
	}

}
