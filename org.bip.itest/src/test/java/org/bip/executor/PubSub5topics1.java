package org.bip.executor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

//import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
//import lsr.concurrence.provided.tests.InputChecker;

import org.junit.Test;

public class PubSub5topics1 implements Runnable {

	private Socket connection;
	private ArrayList<String> topics = new ArrayList<String>();
	private ArrayList<String> msgs = new ArrayList<String>();
	
	public PubSub5topics1(ArrayList<String> topics, ArrayList<String> messages) {
		this.topics = topics;
		this.msgs = messages;
	}
	
	@Test
	public void run() {

		String host = "localhost";
		int port = 7676;

		try {
			connection = new Socket(host, port);

			ClientOutputWriter output = new ClientOutputWriter(connection.getOutputStream());
			
			try {
				Thread.sleep(85);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 2000; i++) {
				output.subscribeTo(topics.get(3));
				// error += inputCheck.checkSubscribe(topics.get(3));
				output.subscribeTo(topics.get(4));
				// error += inputCheck.checkSubscribe(topics.get(4));
				output.publish(topics.get(3), msgs.get(2));
				// error += inputCheck.checkPublish(topics.get(3), msgs.get(2));
				output.publish(topics.get(4), msgs.get(1));
				// error += inputCheck.checkPublish(topics.get(4), msgs.get(1));

				output.unsubscribeTo(topics.get(3));
				//error += inputCheck.checkUnsubscribe(topics.get(3));

				//output.publish(topics.get(3), msgs.get(4)); // no check as we are not supposed to
				//receive anything
				output.publish(topics.get(4), msgs.get(2));
				// error += inputCheck.checkPublish(topics.get(4), msgs.get(2));

				output.unsubscribeTo(topics.get(4));
				// error += inputCheck.checkUnsubscribe(topics.get(4));
				
			}
			
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
            connection.getInputStream().skip(connection.getInputStream().available());
			connection.close();
		
			//System.err.println("start " + startTime);
			
		} catch (IOException e) {
			System.err.println("Fail to accept client connection");

		}
	}

}
