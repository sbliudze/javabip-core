package org.bip.spec.pubsub.typed;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "write", type = PortType.spontaneous), @Port(name = "addTopic", type = PortType.spontaneous),
		@Port(name = "removeTopic", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.ClientProxy")
public class ClientProxy implements ClientProxyInterface {

	private ArrayList<Topic> topics;
	private long id;
	private PrintWriter output;

	public ClientProxy(long id, OutputStream out) {
		this.topics = new ArrayList<Topic>(0);
		this.output = new PrintWriter(out, true);
		this.id = id;
	}

	@Transition(name = "write", source = "0", target = "0")
	public synchronized void write(@Data(name = "message") String msg) {
		output.println(msg);
	}
	
	@Transition(name = "addTopic", source = "0", target = "0")
	public synchronized void addTopic(@Data(name = "topicToAdd") Topic topic) {
		// TODO: make this a guard
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
		}
		System.err.println("Client proxy adding topic " + topic.toString());
	}

	@Transition(name = "removeTopic", source = "0", target = "0")
	public synchronized void removeTopic(@Data(name = "topicToRemove") Topic topic) {
		// TODO: make this a guard
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
		}
		System.err.println("Client proxy removing topic " + topic.toString());

	}
}
