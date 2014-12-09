package org.bip.spec.pubsub.typed;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.CommandHandler")
public class CommandHandler {
	
	private TopicManager topicManager;

	public CommandHandler(TopicManager topicManager) {
		this.topicManager = topicManager;
	}

	@Transition(name = "handleCommand", source = "0", target = "0")
	public void handleCommand(@Data(name = "command") Command command) {
		topicManager.executeCommand(command);
	}
		
}
