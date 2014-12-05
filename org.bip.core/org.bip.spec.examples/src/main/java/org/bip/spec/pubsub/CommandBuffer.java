package org.bip.spec.pubsub;

import java.util.LinkedList;

public class CommandBuffer {
	
	private LinkedList<Command> commandList;

	private int bufferSize;
	
	public CommandBuffer(int bufferSize){
		this.bufferSize = bufferSize;
		this.commandList = new LinkedList<Command>();
	}

	public Command getCommand(){
		return commandList.remove();
	}
	
	public boolean existsCommand() {
		return !commandList.isEmpty();
	}
	
	public void putCommand(Command command){
		commandList.add(command);
	}
	
	public boolean isBufferFull() {
		return commandList.size() == bufferSize;
	}
	
}
