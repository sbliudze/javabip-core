package org.bip.spec.pubsub.typed;


public interface ClientProxyInterface {

	void write(String msg);

	void addTopic(Topic topic);

	void removeTopic(Topic topic);
}
