package org.bip.spec.pubsub.nativ;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommandBuffer3 {
	
	private LinkedList<Command3> commandList;
	private int max_size;
	private final Lock lock=new ReentrantLock();
	private final Condition not_full  = lock.newCondition(); 
	private final Condition not_empty = lock.newCondition(); 
	
	public CommandBuffer3(int max_size){
		this.max_size=max_size;
		this.commandList=new LinkedList<Command3>();
	}

	public Command3 getCommand(){
		this.lock.lock();
		try {
			while(this.commandList.isEmpty()){
				this.not_empty.await();
			}
			Command3 c=this.commandList.remove();
			this.not_full.signal();
			return c;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			this.lock.unlock();
		}
		return null;
	}
	
	public void putCommand(Command3 command){
		this.lock.lock();
		try {
			while(this.commandList.size()==this.max_size){
				this.not_full.await();
			}
			this.commandList.add(command);
			this.not_empty.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			this.lock.unlock();
		}

	}
}
