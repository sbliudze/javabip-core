package org.bip.spec.pubsub.typed;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPAcceptor {

	private static final int BUFFER_SIZE = 10;
	private static final int HANDLER_POOL_SIZE = 5;

	public static void main(String[] args) {
		ServerSocket tcpacceptor = null;
		try {
			tcpacceptor = new ServerSocket(7676);
		} catch (IOException e) {
			System.err.println("Fail to listen on port 7676");
			System.exit(-1);
		}

		CommandBuffer buff = new CommandBuffer(BUFFER_SIZE);
		TopicManager top_manager = new TopicManager();

		// starting the command handlers
		for (int i = 0; i < HANDLER_POOL_SIZE; i++) {
			new CommandHandler(top_manager);
		}

		long id = 0;

		while (true) {
			try {
				new TCPReader(tcpacceptor.accept(), id++, buff);
			} catch (IOException e) {
				System.err.println("Fail to accept client connection");
				System.exit(-1);
			}
		}
	}
}
