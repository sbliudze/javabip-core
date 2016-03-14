package org.bip.spec.pubsub.typed;

import java.util.LinkedList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "putCommand", type = PortType.enforceable),
		@Port(name = "getCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.CommandBuffer")
public class CommandBuffer {
	
	private LinkedList<Command> commandList;
	private int bufferSize;
	
	public CommandBuffer(int bufferSize){
		this.bufferSize = bufferSize;
		this.commandList = new LinkedList<Command>();
	}


	@Transition(name = "getCommand", source = "0", target = "0", guard = "isBufferNotEmpty")
	public void getCommandToHandler() {
		commandList.remove();
	}

	@Guard(name = "isBufferNotEmpty")
	public boolean isBufferNotEmpty() {
		return !commandList.isEmpty();
	}
	
	@Transition(name = "putCommand", source = "0", target = "0", guard = "isBufferNotFull")
	public void putCommandFromReader(@Data(name = "input") Command command) {
		commandList.add(command);
	}
	
	@Guard(name = "isBufferNotFull")
	public boolean isBufferNotFull() {
		return commandList.size() < bufferSize;

	}
	
	@Data(name = "command")
	public Command getNextCommand() {
		return commandList.get(0);
	}

}
