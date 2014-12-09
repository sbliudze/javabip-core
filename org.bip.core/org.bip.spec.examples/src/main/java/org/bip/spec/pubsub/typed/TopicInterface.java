package org.bip.spec.pubsub.typed;

public interface TopicInterface {

	void addClient(ClientProxy client);

	void removeClient(ClientProxy client);

	void publish(ClientProxy publishingClient, String message);
	
	String getName();
}
