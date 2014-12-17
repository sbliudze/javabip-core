package org.bip.spec.pubsub.nativ;


import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Topic3
{
    private String name;
    private ArrayList<ClientProxy3> clients;
	private final Lock mutex = new ReentrantLock();
	private final ReentrantReadWriteLock clients_rwlock= new ReentrantReadWriteLock();
	private final Lock clients_read_lock=clients_rwlock.readLock();
	private final Lock clients_write_lock=clients_rwlock.writeLock();

    
    public Topic3(String name)
    {
        this.name=name;
        this.clients= new ArrayList<ClientProxy3>(0);
    }

    public String getName()
    {
        return name;
    }

    public void addClient(ClientProxy3 client)
    {
		this.mutex.lock();
    	this.clients_read_lock.lock();
    	boolean res=this.clients.contains(client);
    	this.clients_read_lock.unlock();
    	
        if(!res){
        	this.clients_write_lock.lock();
        	if(!this.clients.contains(client)){
				// System.out.println("Adding client "+client.getId()+" to topic "+this.getName());
        		this.clients.add(client);
        		client.addTopic(this);
        		String ack = "subscribe_ack "+ this.getName();
                client.write(ack);
        	}
        	this.clients_write_lock.unlock();

        }
		this.mutex.unlock();
    }
    
    public void removeClient(ClientProxy3 client)
    {
		this.mutex.lock();
    	this.clients_read_lock.lock();
        int i = this.clients.indexOf(client);
    	this.clients_read_lock.unlock();
    	
        if(i > -1){
        	this.clients_write_lock.lock();
        	int ii = this.clients.indexOf(client);
        	if(ii> -1){
				// System.out.println("Removing client "+client.getId()+" from topic "+this.getName());
                this.clients.remove(ii);
                client.removeTopic(this);
                String ack = "unsubscribe_ack "+ this.getName();
                client.write(ack);
        	}
        	this.clients_write_lock.unlock();
        }
		this.mutex.unlock();
    }

    public void publish(String message)
    {
		this.mutex.lock();
        String outputMsg = this.name + " " + message;
        this.clients_read_lock.lock();
        for(int i=0;i<clients.size();i++){
			// System.out.println("Sending Msg : " + outputMsg +
			// " to client "+clients.get(i).getId());
            clients.get(i).write(outputMsg);
        }
        this.clients_read_lock.unlock();
		this.mutex.unlock();
    }
    
}
