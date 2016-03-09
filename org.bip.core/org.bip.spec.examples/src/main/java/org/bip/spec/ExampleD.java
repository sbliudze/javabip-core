package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "d0", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.ExampleD")
public class ExampleD {
	
	@Transition(name="d0", source="zero", target="zero")
	public void testExampleD() {
		
	}

}
