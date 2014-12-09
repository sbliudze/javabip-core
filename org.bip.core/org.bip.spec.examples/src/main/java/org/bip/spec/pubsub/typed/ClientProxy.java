package org.bip.spec.pubsub.typed;

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
@ComponentType(initial = "0", name = "org.bip.spec.ClientProxy")
public class ClientProxy {

	private PrintWriter output;
	private ArrayList<Topic> topics;
	private long id;

	public ClientProxy(long id) {
		this.topics = new ArrayList<Topic>(0);
		this.id = id;
	}

	@Transition(name = "write", source = "0", target = "0")
	public void write(@Data(name = "message") String msg) {
		output.println(msg);
	}
	
	@Transition(name = "getName", source = "0", target = "0")
	public void addTopic(@Data(name = "topicToAdd") Topic topic) {
		// TODO: make this a guard
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
		}
	}

	@Transition(name = "removeTopic", source = "0", target = "0")
	public void removeTopic(@Data(name = "topicToRemove") Topic topic) {
		// TODO: make this a guard
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
		}
	}
}
