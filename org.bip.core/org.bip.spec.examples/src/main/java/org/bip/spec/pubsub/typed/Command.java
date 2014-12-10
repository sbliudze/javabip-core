package org.bip.spec.pubsub.typed;

import lsr.concurrence.provided.server.CommandID;

public class Command
{
    private CommandID id;
	private String topic;
    private String message;
	private ClientProxy client;
   
	public Command(ClientProxy client, CommandID commandID, String topic, String message) {
        this.client = client;
        this.id = commandID;
		this.topic = topic;
        this.message = message;
    }

    public ClientProxy getClient() {
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
