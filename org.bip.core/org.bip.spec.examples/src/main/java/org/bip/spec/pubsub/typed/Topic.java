package org.bip.spec.pubsub.typed;

import java.util.HashSet;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "getName", type = PortType.spontaneous), @Port(name = "addClient", type = PortType.spontaneous),
		@Port(name = "removeClient", type = PortType.spontaneous), @Port(name = "publish", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.Topic")
public class Topic implements TopicInterface {
    private String name;
    private HashSet<ClientProxy> clients; 
    
    public Topic(String name) {

    	this.name = name;
        this.clients = new HashSet<ClientProxy>();
    }

	@Transition(name = "addClient", source = "0", target = "0")
    public void addClient(ClientProxy client) {
    	
        if(! clients.contains(client)){
        		
        	clients.add(client);
        	
        	try {
				client.addTopic(this);
				client.write("subscribe_ack " + this.name);
        	}
        	catch(Exception ex) {}

        }
    }

	@Transition(name = "removeClient", source = "0", target = "0")
	public void removeClient(ClientProxy client) {

        if( clients.contains(client) ){
  
            this.clients.remove(client);
            
            try {
				client.removeTopic(this);
				client.write("unsubscribe_ack " + this.name);
            }
            catch (Exception ex) {
			}
       	}
    }

	@Transition(name = "publish", source = "0", target = "0")
	public void publish(ClientProxy publishingClient, String message) {
		String outputMsg = this.name + " " + message;
		for (ClientProxy currentClient : clients) {
        	try {
				currentClient.write(message);
        	}
        	catch(Exception ex) {}
        }        

    }
    
}
