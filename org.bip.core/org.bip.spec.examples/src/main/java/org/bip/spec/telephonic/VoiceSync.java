package org.bip.spec.telephonic;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.PortType;

@Ports({ @Port(name = "voice1", type = PortType.spontaneous),
	 @Port(name = "voice2", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.VoiceSync")
public class VoiceSync {
	
	BIPActor voice1Actor;
	BIPActor voice2Actor;
	
	//array with 1 on the places of those who are waiting for a call
	AtomicIntegerArray voice1;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	AtomicIntegerArray voice2;
	
	public VoiceSync(int n)	{
		voice1 = new AtomicIntegerArray(n);
		voice2 = new AtomicIntegerArray(n);
	}

	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		voice1Actor = actorCaller;
		voice2Actor = actorCallee;
		
	}
	
	@Transition(name = "voice1", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="dialerId") Integer voice1Id, @Data(name="waiterId") Integer voice2Id)	{
		System.err.println("Voice1: "+ voice1Id +" ready to voice " + voice2Id);
		
		if (voice2.get(voice2Id-1)!=1)
		{voice1.set(voice1Id-1, 1);
		return;}
			System.err.println("Voicing: "+ voice1Id + " with "+ voice2Id);
			//connect the dialer and the waiter
			voice2.set(voice2Id-1, 0);
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", voice2Id);
			 dataMap.put("dialerId", voice1Id);
			 voice1Actor.inform("voiceDown", dataMap);
			 voice2Actor.inform("voiceDown", dataMap);
			System.err.println("Client "+ voice1Id + " is being voiced with "+ voice2Id);
	}
	
	@Transition(name = "voice2", source = "s0", target = "s0", guard = "")
	public void waitCall(@Data(name="dialerId") Integer voice1Id, @Data(name="waiterId") Integer voice2Id){
		System.err.println("Voice2: "+ voice1Id +" ready to voice " + voice2Id);
		if (voice1.get(voice1Id-1)!=1)
		{voice2.set(voice2Id-1, 1);
		return;}
			System.err.println("Voicing: "+ voice1Id + " with "+ voice2Id);
			//connect the dialer and the waiter
			voice1.set(voice1Id-1, 0);
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", voice2Id);
			 dataMap.put("dialerId", voice1Id);
			 voice1Actor.inform("voiceDown", dataMap);
			 voice2Actor.inform("voiceDown", dataMap);
			System.err.println("Client "+ voice1Id + " is being voiced with "+ voice2Id);
	}
}
