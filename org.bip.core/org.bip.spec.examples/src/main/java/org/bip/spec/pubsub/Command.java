package org.bip.spec.pubsub;

public class Command
{

	public enum ID {
		SUBSCRIBE, UNSUBSCRIBE, PUBLISH
	}
	
    private ID id;
    private String topic;
    private Message message;
    private Client client;
   
    public Command(Client client, ID id, String topic, Message message) {
        this.client = client;
        this.id = id;
        this.topic = topic;
        this.message = message;
    }

    public Client getClient() {
        return client;
    }

    public ID getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }
   
    public Message getMessage() {
        return message;
    }    

}
