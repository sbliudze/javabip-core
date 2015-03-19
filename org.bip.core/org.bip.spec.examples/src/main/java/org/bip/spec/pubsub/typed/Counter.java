package org.bip.spec.pubsub.typed;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "up", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.Counter")
public class Counter implements CounterInterface {
	private int transitionCount = 0;

	@Transition(name = "up", source = "0", target = "0")
	public synchronized void up() {
		long time = System.currentTimeMillis();
		if (transitionCount == 0) {
			System.out.println("start "+time);
		}
		transitionCount++;
		// TODO here you change the number of messages
		if (transitionCount == 1000) {
			System.out.println("  end "+time);
			System.exit(-1);
		}
		
	}
}
