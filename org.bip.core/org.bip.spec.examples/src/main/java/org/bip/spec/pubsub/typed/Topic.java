package org.bip.spec.pubsub.typed;

import java.util.HashSet;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "getName", type = PortType.spontaneous), @Port(name = "addClient", type = PortType.spontaneous),
		@Port(name = "removeClient", type = PortType.spontaneous), @Port(name = "publish", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.Topic")
public class Topic implements TopicInterface {
    private String name;
	private HashSet<ClientProxyInterface> clients;

    
    public Topic(String name) {

    	this.name = name;
		this.clients = new HashSet<ClientProxyInterface>();
    }

	@Transition(name = "addClient", source = "0", target = "0")
	public void addClient(@Data(name = "client") ClientProxyInterface client) {
    	
        if(! clients.contains(client)){
        		
        	clients.add(client);
        	
        	try {
				client.addTopic(name);
				client.write("subscribe_ack " + this.name);
        	}
        	catch(Exception ex) {}

        }
    }

	@Transition(name = "removeClient", source = "0", target = "0")
	public void removeClient(@Data(name = "client2") ClientProxyInterface client) {

        if( clients.contains(client) ){
  
            this.clients.remove(client);
            
            try {
				client.removeTopic(name);
				client.write("unsubscribe_ack " + this.name);
            }
            catch (Exception ex) {
			}
       	}
    }

	@Transition(name = "publish", source = "0", target = "0")
	public void publish(@Data(name = "client3") ClientProxyInterface publishingClient,
			@Data(name = "msg") String message) {
		String outputMsg = this.name + " " + message;
		for (ClientProxyInterface currentClient : clients) {
        	try {
				currentClient.write(outputMsg);
        	}
        	catch(Exception ex) {}
        }        

    }
    
}
