package org.bip.spec.pubsub.untyped;

import java.util.HashMap;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.PortType;

@Ports({ @Port(name = "execute", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.TopicManager")
public class TopicManager {
	
    private HashMap<String, BIPActor> topics;

    public TopicManager(HashMap<String, BIPActor> topics) {
    	this.topics = topics;
    }
    
	// Transition for port execute.
    @Transition(name = "execute", source = "0", target = "0")
	public void executeCommand(@Data(name = "value") Command command) {

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
	
	private void subscribe(BIPActor client, String topicName) {
    	
        BIPActor topic = topics.get(topicName);
		topic.addClient(client);
        
    }
    
	private void unsubscribe(BIPActor client, String topicName) {

		BIPActor topic = topics.get(topicName);
		topic.removeClient(client);

    }
         
	private void publish(BIPActor client, String topicName, String message) {

		BIPActor topic = topics.get(topicName);
        topic.publish(client, message);

    }


}
