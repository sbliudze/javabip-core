package org.bip.spec.pubsub.nativ;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ClientProxy3
{
    private long id;
    private PrintWriter output;
    private ArrayList<Topic3> topics;
    private final Lock mutex=new ReentrantLock();
    private final Lock topics_mutex=new ReentrantLock(); // we assume little concurrency here so no need for a RWlock
	public int noOfTransitions;
	private long bgtime;

    public ClientProxy3(long id, OutputStream out)
    {
        this.id=id;
        this.output=new PrintWriter(out,true);
        this.topics=new ArrayList<Topic3>(0);

    }

    public long getId()
    {
        return id;
    }
    

    public void write(String msg)
    {
    	mutex.lock();
        output.println(msg);
		if (noOfTransitions == 0) {
			this.bgtime = System.currentTimeMillis();
		}
		noOfTransitions++;
		mutex.unlock();

    }
    
    public void addTopic(Topic3 topic)
    {

    	this.topics_mutex.lock();
        if(!this.topics.contains(topic)){
			if (noOfTransitions == 0) {
				this.bgtime = System.currentTimeMillis();
			}
			this.topics.add(topic);

			noOfTransitions++;
        }
        this.topics_mutex.unlock();
    }

    public void removeTopic(Topic3 topic)
    {
    	this.topics_mutex.lock();
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
			System.out.println("Transitions: " + noOfTransitions);

				System.out.printf("In the time of cholera: %s", System.currentTimeMillis() - bgtime);
			noOfTransitions++;
        }
        this.topics_mutex.unlock();
    }

    public void closeConnection()
    {
        int i =0;
        this.topics_mutex.lock();
        for(i=0;i<topics.size();i++){
            topics.get(i).removeClient(this);
        }
        this.topics_mutex.unlock();

        this.mutex.lock();
        output.close();
        this.mutex.unlock();
    }
}
