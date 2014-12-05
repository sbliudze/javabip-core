package org.bip.spec.pubsub;

import java.util.HashSet;

public class Topic
{
    private String name;
    private HashSet<Client> clients; 
    
    public Topic(String name) {

    	this.name = name;
        this.clients = new HashSet<Client>();

    }

    public String getName() {
        return name;
    }

    public void addClient(Client client) {
    	
        if(! clients.contains(client)){
        		
        	clients.add(client);
        	
        	try {
        		client.subscribeAck(name);
        	}
        	catch(Exception ex) {}

        }
  
    }
    
    public void removeClient(Client client) {

        if( clients.contains(client) ){
  
            this.clients.remove(client);
            
            try {
            	client.unSubscribeAck(name);
            }
            catch(Exception ex) {}
            
       	}

    }

    public void publish(Client publishingClient, Message message) {
    	
    	publishingClient.publishAck(message);
        for(Client currentClient : clients) {
        	try {
        		currentClient.receiveMessage(name, message);
        	}
        	catch(Exception ex) {}
        }        
        
    }
    
}
