package org.bip.spec.telephonicNoAggr;

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
	 @Port(name = "talk", type = PortType.spontaneous), 
	 @Port(name = "listen", type = PortType.spontaneous), 
	 @Port(name = "notify", type = PortType.spontaneous), 
@Port(name = "disc", type = PortType.spontaneous) })
@ComponentType(initial = "init", name = "org.bip.spec.telephonicNoAggr.Client")
public class Client implements AgregatorClient {
	
	private int id = 0;
	private int n = 1;
	private int step = 0;
	private int currectInterlocutor = 0;
	private boolean meDialing;
	
	BIPActor dialWaitSyncActor;
	BIPActor voiceSyncActor;
	BIPActor discSyncActor;
	
//	BIPActor callerAgregationExecutor;
//	BIPActor calleeAgregationExecutor;
//	BIPActor voiceAgregator;
//	BIPActor discAgregator;
	
	BIPActor myself;
	BIPActor checker;
	HashMap<Integer, BIPActor> clientActors;
	
	private Logger logger = LoggerFactory.getLogger(Client.class);
	
	public Client(int id, int n) {
		this.id = id;
		this.n = n;
		clientActors = new HashMap<Integer, BIPActor>(n);
	}
	
	public void setClientRefs(BIPActor client, int id) {
		clientActors.put(id, client);
	}
	
	public void setExecutorRefs(BIPActor dialwaitSync, BIPActor voiceSync,
			BIPActor discSync, BIPActor client, BIPActor checker) {
		dialWaitSyncActor = dialwaitSync;
		voiceSyncActor = voiceSync;
		discSyncActor = discSync;
		myself = client;
		this.checker = checker;
	}
	
	public int randomID() {
		Random randomGenerator = new Random();
		int randomInt = id;
		while (randomInt == id) {
			randomInt = randomGenerator.nextInt(n) + 1;
		}
		return randomInt;
	}

	@Transition(name = "notify", source = "init", target = "s0")
	public void notifyAgregatorInternal()	{
		System.err.println(step + " Client "+ this.id + " is notifying");
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dialerId", id);
		int calleeID = randomID();
		dataMap.put("waiterId", calleeID);
		dialWaitSyncActor.inform("dial", dataMap);
		dialWaitSyncActor.inform("wait", dataMap);
	}
	
	@Transition(name = "dial", source = "s0", target = "s1")
	public void dial(@Data(name="waiterId") Integer waiterId, 
			@Data(name="callId") Integer callNumber)	{
		meDialing = true;
		System.err.println(callNumber+" Client "+ id + " dialed client " + waiterId);
		step = callNumber;
		currectInterlocutor = waiterId;
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dialerId", id);
		dataMap.put("waiterId", waiterId);
		dataMap.put("callId", callNumber);
		voiceSyncActor.inform("voice", dataMap);
		checker.inform("dial", dataMap);
	}
	
	@Transition(name = "wait", source = "s0", target = "s1")
	public void waitCall(@Data(name="dialerId") Integer dialerId, 
			@Data(name="callId") Integer callNumber){
		meDialing = false;
		System.err.println(callNumber+" Client "+ id + " received a call from " + dialerId);
		step = callNumber;
		currectInterlocutor = dialerId;
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dialerId", dialerId);
		dataMap.put("waiterId", this.id);
		dataMap.put("callId", callNumber);
		voiceSyncActor.inform("voice", dataMap);
		checker.inform("wait", dataMap);
	}
	
	@Transition(name = "voice", source = "s1", target = "s2")
	public void voice(@Data(name="otherId") Integer otherId, 
			@Data(name="callId") Integer callNumber){
		System.err.println(callNumber+" Client "+ this.id + " is voicing with "+ otherId );
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id1", this.id);
		dataMap.put("id2", otherId);
		dataMap.put("callId", callNumber);
		if (meDialing) {
			myself.inform("talk", dataMap);
		}
		checker.inform("voice", dataMap);
	}
	
	@Transition(name = "talk", source = "s2", target = "s3")
	public void talk(@Data(name = "id2") Integer otherId,
			@Data(name = "callId") Integer callNumber) {
		HashMap<String, Object> talkData = new HashMap<String, Object>();
		talkData.put("message", "Hi there!");
		talkData.put("sender", this.id);
		talkData.put("callId", callNumber);
		clientActors.get(otherId).inform("listen", talkData);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id1", this.id);
		dataMap.put("id2", otherId);
		dataMap.put("callId", callNumber);
		discSyncActor.inform("disc", dataMap);
	}
	
	@Transition(name = "listen", source = "s2", target = "s3")
	public void listen(@Data(name = "message") String message, @Data(name = "sender") Integer senderId,
			@Data(name="callId") Integer callNumber) {
		if (currectInterlocutor != senderId) {
			checker.inform("error");
		}
		//System.out.println(message+ " from "+ senderId + " at step "+ callNumber);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id1", this.id);
		dataMap.put("id2", senderId);
		dataMap.put("callId", callNumber);
		discSyncActor.inform("disc", dataMap);
	}
	
	@Transition(name = "disc", source = "s3", target = "init")
	public void disconnect(@Data(name="id1") Integer id1, @Data(name="id2") Integer id2, 
			@Data(name="callId") Integer callNumber){
		int otherId = (id1==id)?id2:id1;
		System.err.println(callNumber+ " Client "+ this.id + " is disconnected from "+otherId );
		currectInterlocutor = 0;
		myself.inform("notify");
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dialerId", this.id);
		dataMap.put("waiterId", otherId);
		dataMap.put("callId", callNumber);
		checker.inform("disc", dataMap);
	}
		
}
