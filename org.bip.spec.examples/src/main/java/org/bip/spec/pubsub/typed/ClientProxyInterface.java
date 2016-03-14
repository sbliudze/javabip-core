package org.bip.spec.pubsub.typed;


public interface ClientProxyInterface {

	void write(String msg);

	void addTopic(String topic);

	void removeTopic(String topic);
}
