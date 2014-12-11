package org.bip.spec.pubsub.typed;

import lsr.concurrence.provided.server.CommandID;

public class Command
{
    private CommandID id;
	private String topic;
    private String message;
	private ClientProxyInterface client;
   
	public Command(ClientProxyInterface cproxy, CommandID commandID, String topic, String message) {
		this.client = cproxy;
        this.id = commandID;
		this.topic = topic;
        this.message = message;
    }

	public ClientProxyInterface getClient() {
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
