package org.bip.spec.pubsub;

public class Message {
	
	final private String content;

	public Message(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
