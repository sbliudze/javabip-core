package org.bip.spec.pubsub;

public class Message {
	
	final private String content;
	final private String topicName;
	
	public Message(String content, String topicName) {
		this.content = content;
		this.topicName = topicName;
	}

	public String getContent() {
		return content;
	}

	public String getTopic() {
		return topicName;
	}
}
