package org.bip.spec.telephonic;

import java.util.HashMap;
import java.util.Random;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "dial", type = PortType.spontaneous),
	 @Port(name = "wait", type = PortType.spontaneous), 
	 @Port(name = "voice", type = PortType.spontaneous), 
	 @Port(name = "notify", type = PortType.spontaneous), 
@Port(name = "disc", type = PortType.spontaneous) })
@ComponentType(initial = "init", name = "org.bip.spec.telephonic.Client")
public class Client {
	
	private int id=0;
	private int n=1;
	BIPActor callerAgregationExecutor;
	BIPActor calleeAgregationExecutor;
	BIPActor voiceAgregator1;
	BIPActor voiceAgregator2;
	BIPActor discAgregator1;
	BIPActor discAgregator2;
	BIPActor myself;
	
	private Logger logger = LoggerFactory.getLogger(Client.class);
	
	public Client(int id, int n)
	{
		this.id=id;
		this.n = n;
	}
	
	public void setExecutorRefs(BIPActor caller, BIPActor callee, BIPActor voice1, BIPActor voice2,
			BIPActor disc1, BIPActor disc2, BIPActor client)
	{
		callerAgregationExecutor = caller;
		calleeAgregationExecutor = callee;
		voiceAgregator1 = voice1;
		voiceAgregator2 = voice2;
		discAgregator1 = disc1;
		discAgregator2 = disc2;
		myself = client;
	}
	
	public int randomID()
	{
		Random randomGenerator = new Random();
	    int randomInt = id;
	    while(randomInt == id)	{
	    	randomInt= randomGenerator.nextInt(n)+1;}
		return randomInt;
	}

	@Transition(name = "notify", source = "init", target = "s0", guard = "")
	public void notifyAgregatorInternal()	{
		System.out.println(" Client "+ this.id + " is notifying");
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", id);
		 dataMap.put("waiterId", randomID());
		callerAgregationExecutor.inform("dialUp",dataMap);
		calleeAgregationExecutor.inform("waitUp",dataMap);
	}
	
	@Transition(name = "dial", source = "s0", target = "s1", guard = "")
	public void dial(@Data(name="waiterId") Integer waiterId)	{
		System.out.println("Client "+ id + " dialed client " + waiterId);
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", id);
		 dataMap.put("waiterId", waiterId);
		 voiceAgregator1.inform("voice",dataMap);
	}
	
	@Transition(name = "wait", source = "s0", target = "s1", guard = "")
	public void waitCall(@Data(name="dialerId") Integer dialerId){
		System.out.println("Client "+ id + " received a call from " + dialerId);
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", dialerId);
		 dataMap.put("waiterId", id);
		voiceAgregator2.inform("voice",dataMap);
	}
	
	@Transition(name = "voice", source = "s1", target = "s2", guard = "")
	public void talk(){
		logger.info("Client "+ this.id + "is talking with "+0 );
		discAgregator1.inform("disconnect");
	}
	
	@Transition(name = "disc", source = "s2", target = "s0", guard = "")
	public void disconnect(){
		logger.info("Client "+ this.id + "is disconnected from "+0 );
		myself.inform("notify");
	}
	
}
