package org.bip.spec.pubsub;

import java.util.HashMap;

public class TopicManager {
	
    private HashMap<String, Topic> topics;

    public TopicManager() {
    	this.topics = new HashMap<String, Topic>();
    }
    
	// Transition for port execute.
	public void executeCommand(Command command) {

        switch(command.getId()){
        case SUBSCRIBE:
            subscribe(command.getClient(),command.getTopic());
            break;
        case UNSUBSCRIBE:
            unsubscribe(command.getClient(),command.getTopic());
            break;
        case PUBLISH:
            publish(command.getClient(), command.getTopic(),command.getMessage());
            break;
        default:
            break;
        }
        
    }
	
    private void subscribe(Client client, String topicName) {
    	
        Topic topic = topics.get(topicName);
        topic.addClient(client);
        
    }
    
    private void unsubscribe(Client client, String topicName) {

        Topic topic = topics.get(topicName);
    	topic.removeClient(client);

    }
         
    private void publish(Client client, String topicName, Message message) {

        Topic topic = topics.get(topicName);
        topic.publish(client, message);

    }


}
