package org.bip.executor.pubsub;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

//import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
//import lsr.concurrence.provided.tests.InputChecker;

import org.junit.Test;

public class PubSub5topicsFirstRunnable implements Runnable {

	private Socket connection;
	private ArrayList<String> topics = new ArrayList<String>();
	private ArrayList<String> msgs = new ArrayList<String>();
	
	public PubSub5topicsFirstRunnable(ArrayList<String> topics, ArrayList<String> messages) {
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
				output.subscribeTo(topics.get(0));
				// error += inputCheck.checkSubscribe(topics.get(0));
				output.subscribeTo(topics.get(1));
				// error += inputCheck.checkSubscribe(topics.get(1));
				output.publish(topics.get(0), msgs.get(0));
				// error += inputCheck.checkPublish(topics.get(0), msgs.get(0));
				output.publish(topics.get(1), msgs.get(1));
				// error += inputCheck.checkPublish(topics.get(1), msgs.get(1));

				output.unsubscribeTo(topics.get(0));
				//error += inputCheck.checkUnsubscribe(topics.get(0));

				//output.publish(topics.get(0), msgs.get(1)); // no check as we are not supposed to
				//receive anything
				output.publish(topics.get(1), msgs.get(0));
				// error += inputCheck.checkPublish(topics.get(1), msgs.get(0));

				output.unsubscribeTo(topics.get(1));
				// error += inputCheck.checkUnsubscribe(topics.get(1));
			
				
				//output.subscribeTo(topics.get(2));
				// error += inputCheck.checkSubscribe(topics.get(1));

				//output.publish(topics.get(2), msgs.get(2));
				// error += inputCheck.checkPublish(topics.get(1), msgs.get(1));

				//output.unsubscribeTo(topics.get(2));
				//error += inputCheck.checkUnsubscribe(topics.get(0));

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
