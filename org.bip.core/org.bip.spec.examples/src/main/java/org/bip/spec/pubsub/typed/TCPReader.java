/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */

package org.bip.spec.pubsub.typed;

import java.io.IOException;
import java.net.Socket;

import lsr.concurrence.provided.server.CommandID;
import lsr.concurrence.provided.server.InputFormatException;
import lsr.concurrence.provided.server.InputReader;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "giveCommandToBuffer", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.Client")
public class TCPReader {

	Logger logger = LoggerFactory.getLogger(TCPReader.class);
	
	private boolean connected;
	private ClientProxy cproxy;
	private Socket client_sock;
	private CommandBuffer command_buff;
	private InputReader reader;
	private Command currentCommand;

	private boolean stillHasCommands;
	
	public TCPReader(Socket sock, long id, CommandBuffer buff) throws IOException {
		this.cproxy = new ClientProxy(id);
		this.command_buff = buff;
		this.reader = new InputReader(this.client_sock.getInputStream());
		this.stillHasCommands = true;

	}

	@Transition(name = "giveCommandToBuffer", source = "0", target = "0", guard = "commandExists")
	public void giveCommandtoBuffer() {
		if (reader.getCommandId() == CommandID.ENDOFCLIENT)
			stillHasCommands = false;
	}

	@Data(name = "command")
	public Command getNextCommand() throws InputFormatException, IOException {
		reader.readCommand();
		currentCommand = new Command(cproxy, reader.getCommandId(), reader.getTopic(), reader.getMessage());
		return currentCommand;
	}

	@Guard(name = "commandExists")
	public boolean commandExists() {
		return stillHasCommands;
	}

	
}
