package org.bip.spec.pubsub.typed;

import java.util.HashMap;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "executeCommand", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.TopicManager")
public class TopicManager implements TopicManagerInterface {
	

	private static final HashMap<String, TopicInterface> topics = new HashMap<String, TopicInterface>();;

	public TopicManager(TopicInterface proxyForTopic1, TopicInterface proxyForTopic2) {
		topics.put("epfl", proxyForTopic1);
		topics.put("concurrence", proxyForTopic2);
    }
    
	@Transition(name = "executeCommand", source = "0", target = "0")
	public void executeCommand(@Data(name = "value") Command command) {
		switch (command.getId()) {
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

	private void subscribe(ClientProxyInterface clientProxy, String topicName) {
    	
		TopicInterface topic = topics.get(topicName);
		topic.addClient(clientProxy);
    }
    
	private void unsubscribe(ClientProxyInterface clientProxy, String topicName) {

		TopicInterface topic = topics.get(topicName);
		topic.removeClient(clientProxy);
    }
         
	private void publish(ClientProxyInterface clientProxy, String topicName, String message) {

		TopicInterface topic = topics.get(topicName);
		topic.publish(clientProxy, message);
    }
}
