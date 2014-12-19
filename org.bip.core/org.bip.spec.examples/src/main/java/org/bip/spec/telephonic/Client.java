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
	private int i=0;
	BIPActor callerAgregationExecutor;
	BIPActor calleeAgregationExecutor;
	BIPActor voiceAgregator;
	BIPActor discAgregator;
	BIPActor myself;
	
	private Logger logger = LoggerFactory.getLogger(Client.class);
	
	public Client(int id, int n)
	{
		this.id=id;
		this.n = n;
	}
	
	public void setExecutorRefs(BIPActor caller, BIPActor callee, BIPActor voice,
			BIPActor disc, BIPActor client)
	{
		callerAgregationExecutor = caller;
		calleeAgregationExecutor = callee;
		voiceAgregator = voice;
		discAgregator = disc;
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

	@Transition(name = "notify", source = "init", target = "s0")
	public void notifyAgregatorInternal()	{
		System.err.println(i + " Client "+ this.id + " is notifying");
		i++;
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", id);
		 int calleeID = randomID();
		 dataMap.put("waiterId", calleeID);
		callerAgregationExecutor.inform("dialUp",dataMap);
		calleeAgregationExecutor.inform("waitUp",dataMap);
	}
	
	@Transition(name = "dial", source = "s0", target = "s1")
	public void dial(@Data(name="waiterId") Integer waiterId)	{
		System.err.println(i+" Client "+ id + " dialed client " + waiterId);
		i++;
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", id);
		 dataMap.put("waiterId", waiterId);
		 voiceAgregator.inform("voiceUp",dataMap);
	}
	
	@Transition(name = "wait", source = "s0", target = "s1")
	public void waitCall(@Data(name="dialerId") Integer dialerId){
		System.err.println(i+" Client "+ id + " received a call from " + dialerId);
		 i++;HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", dialerId);
		 dataMap.put("waiterId", id);
		voiceAgregator.inform("voiceUp",dataMap);
	}
	
	@Transition(name = "voice", source = "s1", target = "s2")
	public void voice(@Data(name="otherId") Integer otherId){
		System.err.println(i+" Client "+ this.id + " is voicing with "+ otherId );
		i++; HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("id1", id);
		 dataMap.put("id2", otherId);
		discAgregator.inform("discUp", dataMap);
	}
	
	@Transition(name = "disc", source = "s2", target = "init")
	public void disconnect(@Data(name="id1") Integer id1, @Data(name="id2") Integer id2){
		int otherId = (id1==id)?id2:id1;
		System.err.println(i+ " Client "+ this.id + " is disconnected from "+otherId );
		i++;myself.inform("notify");
	}
	
}
