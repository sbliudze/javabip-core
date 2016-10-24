package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "portE", type = PortType.enforceable) })

@ComponentType(initial = "zero", name = "org.bip.spec.ExampleE")
public class ExampleE {
	
	@Transition(name="portE", source="zero", target="zero")
	public void testExampleE() {
		
	}

}
