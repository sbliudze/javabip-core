package org.bip.spec.pubsub;

import java.util.HashMap;

import org.bip.api.BIPActor;

public class CommandHandler {
	
	private BIPActor topicManager;
	
	Command currentCommand;
	
	public void getCommand(Command command) {
		this.currentCommand = command;
	}
	
	public boolean hasCommandToHandle() {
		return currentCommand != null;
	}
	
	// internal transition.
	public void handleCommand() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("value", currentCommand);
		topicManager.inform("execute", data);
		currentCommand = null;
	}
	
	// transition from initial state to work state.
	public void getTopicManager(BIPActor topicManager) {
		this.topicManager = topicManager;
	}
		
}
