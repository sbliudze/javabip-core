package org.bip.spec.pubsub.nativ;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;




public class TopicManager3 {
	
	private final Lock mutex = new ReentrantLock();
	private final ReentrantReadWriteLock topics_rwlock= new ReentrantReadWriteLock();
	private final Lock topics_read_lock=topics_rwlock.readLock();
	private final Lock topics_write_lock=topics_rwlock.writeLock();
    private ArrayList<Topic3> topics;

    public TopicManager3(){
    	this.topics=new ArrayList<Topic3>();
    }
    
    private Topic3 getTopic(String topic_name)
    {
        int i=0;
        for(i=0;i<topics.size();i++){
            if(topics.get(i).getName().equals(topic_name)){
                return topics.get(i);
            }
        }
        return null;
    }

	
	public void executeCommand(Command3 command)
    {
        switch(command.getId()){
        case NEWCLIENT:
            break;
        case ENDOFCLIENT:
            command.getClientProxy().closeConnection();
            break;
        case SUBSCRIBE:
            subscribe(command.getClientProxy(),command.getTopic());
            break;
        case UNSUBSCRIBE:
            unsubscribe(command.getClientProxy(),command.getTopic());
            break;
        case PUBLISH:
            publish(command.getTopic(),command.getMessage());
            break;
        default:
            break;
        }
        
    }
	
    private void subscribe(ClientProxy3 client, String topic_name)
    {
		this.mutex.lock();
    	this.topics_read_lock.lock();
        Topic3 topic = getTopic(topic_name);
    	this.topics_read_lock.unlock();
    	
        if(topic == null){
        	this.topics_write_lock.lock();
        	topic=getTopic(topic_name);
        	if(topic==null){
        		topic = new Topic3(topic_name);
        		this.topics.add(topic);
        	}
        	this.topics_write_lock.unlock();
        }
        
        topic.addClient(client);
		this.mutex.unlock();
        
        
        
    }
    
    private void unsubscribe(ClientProxy3 client, String topic_name)
    {
		this.mutex.lock();
    	this.topics_read_lock.lock();
        Topic3 topic = getTopic(topic_name);
        this.topics_read_lock.unlock();
        if(topic !=null){
        		topic.removeClient(client);
        }
		this.mutex.unlock();
        

    }
    
    private void publish(String topic_name, String message)
    {
		this.mutex.lock();
    	this.topics_read_lock.lock();
        Topic3 topic = getTopic(topic_name);
    	this.topics_read_lock.unlock();

        if(topic != null){
            topic.publish(message);
        }
		this.mutex.unlock();
    }


}
