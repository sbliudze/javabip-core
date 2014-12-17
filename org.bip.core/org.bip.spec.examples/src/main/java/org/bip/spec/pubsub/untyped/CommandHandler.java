package org.bip.spec.pubsub.untyped;

import java.util.HashMap;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.PortType;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.CommandHandler")
public class CommandHandler {
	
	private BIPActor topicManager;
	
	public CommandHandler(BIPActor topicManager) {
		this.topicManager = topicManager;
	}

	@Transition(name = "handleCommand", source = "0", target = "0")
	public void handleCommand(@Data(name = "command") Command command) {
		// System.out.println("Handling command: " + command.getId());
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("value", command);
		topicManager.inform("executeCommand", data);
	}

}
