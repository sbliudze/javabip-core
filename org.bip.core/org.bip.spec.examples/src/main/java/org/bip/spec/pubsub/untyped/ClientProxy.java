package org.bip.spec.pubsub.untyped;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.bip.api.BIPActor;
import org.bip.api.BIPActorAware;

public class ClientProxy implements BIPActorAware {

	private PrintWriter output;
	private ArrayList<Topic> topics;
	private BIPActor bipActor;

	public void write(String msg) {
		output.println(msg);
	}
	
	public void addTopic(Topic topic) {
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
		}
	}

	public void removeTopic(Topic topic) {
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
		}
	}


	@Override
	public void setBIPActor(BIPActor actor) {
		this.bipActor = actor;
	}
}
