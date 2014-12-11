package org.bip.spec.pubsub.typed;

public interface TopicInterface {

	void addClient(ClientProxyInterface clientProxy);

	void removeClient(ClientProxyInterface clientProxy);

	void publish(ClientProxyInterface clientProxy, String message);
	
}
