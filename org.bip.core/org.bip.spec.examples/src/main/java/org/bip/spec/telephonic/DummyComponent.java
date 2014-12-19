package org.bip.spec.telephonic;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "aPort", type = PortType.enforceable) })
@ComponentType(initial = "init", name = "org.bip.spec.telephonic.DummyComponent")
public class DummyComponent {
	
	@Transition(name = "aPort", source = "init", target = "init")
	public void doSomething()
	{
		System.out.println("doing something");
	}

}
