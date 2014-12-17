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
	AtomicIntegerArray waitersIds;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	AtomicIntegerArray dialerIds;
	
	public VoiceSync(int n)	{
		waitersIds = new AtomicIntegerArray(n);
		dialerIds = new AtomicIntegerArray(n);
	}

	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		voice1Actor = actorCaller;
		voice2Actor = actorCallee;
		
	}
	
	@Transition(name = "voice1", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)	{
		System.err.println("DialWaitSync: "+ dialerId +" wanting to dial " + waiterId
				+". waiters array is "  + waitersIds);
		dialerIds.set(waiterId-1, dialerId);
		if (waitersIds.get(waiterId-1)!=1)
		{return;}
			System.err.println("Chosen: "+ dialerId + " for "+ waiterId);
			//connect the dialer and the waiter
			dialerIds.set(waiterId-1, 0);
			waitersIds.set(waiterId-1, 0);
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", waiterId);
			 dataMap.put("dialerId", dialerId);
			 voice1Actor.inform("voiceDown", dataMap);
			 voice2Actor.inform("voiceDown", dataMap);
			System.err.println("Client "+ dialerId + " is being connected with "+ waiterId);
	}
	
	@Transition(name = "voice2", source = "s0", target = "s0", guard = "")
	public void waitCall(@Data(name="waiterId") Integer waiterId){
		waitersIds.set(waiterId-1, 1);
		System.err.println("DialWaitSync: "+ waiterId+" is ready to talk. dialer array is "  + dialerIds);
	}
}
