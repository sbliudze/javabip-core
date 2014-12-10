package org.bip.spec.pubsub.untyped;

import org.bip.api.BIPActor;

public class Command
{

	public enum CommandID {
		SUBSCRIBE, UNSUBSCRIBE, PUBLISH
	}
	
    private CommandID id;
	private String topic;
    private String message;
    private BIPActor client; // BIP Actor representing client issuing the command.
   
	public Command(BIPActor client, CommandID id, String topic, String message) {
        this.client = client;
        this.id = id;
		this.topic = topic;
        this.message = message;
    }

    public BIPActor getClient() {
		return this.client;
    }

    public CommandID getId() {
		return this.id;
    }

    public String getTopic() {
		return this.topic;
    }
   
	public String getMessage() {
		return this.message;
    }    

}
