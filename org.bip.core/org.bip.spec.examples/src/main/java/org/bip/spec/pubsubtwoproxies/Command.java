package org.bip.spec.pubsubtwoproxies;

public class Command
{

	public enum CommandID {
		SUBSCRIBE, UNSUBSCRIBE, PUBLISH
	}
	
    private CommandID id;
	private String topic;
    private String message;
    private ClientProxy client;
   
	public Command(ClientProxy client, CommandID id, String topic, String message) {
        this.client = client;
        this.id = id;
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
