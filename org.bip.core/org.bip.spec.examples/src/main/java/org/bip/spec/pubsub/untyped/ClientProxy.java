package org.bip.spec.pubsub.untyped;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.BIPActorAware;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;

@Ports({ @Port(name = "addTopic", type = PortType.spontaneous), 
		 @Port(name = "removeTopic", type = PortType.spontaneous),
		 @Port(name = "write", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.ClientProxy")
public class ClientProxy implements BIPActorAware {

	private PrintWriter output;
	private ArrayList<String> topics;
	private BIPActor bipActor;

	@Transition(name="write", source="0", target="0")
	public void write(@Data(name="msg") String msg) {
		output.println(msg);
	}
	
	@Transition(name="addTopic", source="0", target="0")
	public void addTopic(@Data(name="topic") String topic) {
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
		}
	}

	@Transition(name="removeTopic", source="0", target="0")
	public void removeTopic(@Data(name="topic") String topic) {
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
		}
	}

	@Override
	public void setBIPActor(BIPActor actor) {
		this.bipActor = actor;
	}
	
	// TODO : This and BIPActorProxyAware may not be needed in this example.
	@Data(name="bipActor", accessTypePort=AccessType.any)
	public BIPActor getBIPActor() {
		return bipActor;
	}
	
}
