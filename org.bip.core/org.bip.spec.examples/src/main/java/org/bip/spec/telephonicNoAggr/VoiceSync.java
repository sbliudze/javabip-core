package org.bip.spec.telephonicNoAggr;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.PortType;

@Ports({ @Port(name = "voice", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonicNoAggr.VoiceSync")
public class VoiceSync implements ClientCaller {
	
	//BIPActor voice1Actor;
	
	//array where index is a dialer and value is a waiter
	AtomicIntegerArray voice1;
	
	public VoiceSync(int n)	{
		voice1 = new AtomicIntegerArray(n);
		clientActors = new HashMap<Integer, BIPActor>(n);
	}

	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		//voice1Actor = actorCaller;
		
	}
	
	HashMap<Integer, BIPActor> clientActors;	
	
	public void setClientRefs(BIPActor client, int id) {
		clientActors.put(id, client);
	}
	
	@Transition(name = "voice", source = "s0", target = "s0")
	public void voice(@Data(name="dialerId") Integer voice1Id, @Data(name="waiterId") Integer voice2Id, 
			@Data(name="callId") Integer callNumber)	{
		if (voice1.get(voice1Id - 1) != voice2Id) {
			voice1.set(voice1Id - 1, voice2Id);
			return;
		}

		// connect the dialer and the waiter
		voice1.set(voice1Id - 1, 0);
		HashMap<String, Object> dataMap1 = new HashMap<String, Object>();
		dataMap1.put("otherId", voice2Id);
		dataMap1.put("callId", callNumber);
		clientActors.get(voice1Id).inform("voice", dataMap1);

		HashMap<String, Object> dataMap2 = new HashMap<String, Object>();
		dataMap2.put("otherId", voice1Id);
		dataMap2.put("callId", callNumber);
		clientActors.get(voice2Id).inform("voice", dataMap2);
		// voice1Actor.inform("voiceDown", dataMap);
	}
}
