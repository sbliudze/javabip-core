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

@Ports({ @Port(name = "voice", type = PortType.spontaneous),
	 @Port(name = "voice2", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.VoiceSync")
public class VoiceSync {
	
	BIPActor voice1Actor;
	
	//array where index is a dialer and value is a waiter
	AtomicIntegerArray voice1;
	
	public VoiceSync(int n)	{
		voice1 = new AtomicIntegerArray(n);
	}

	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		voice1Actor = actorCaller;
		
	}
	
	@Transition(name = "voice", source = "s0", target = "s0", guard = "")
	public void voice(@Data(name="dialerId") Integer voice1Id, @Data(name="waiterId") Integer voice2Id, 
			@Data(name="callId") Integer callNumber)	{
		if (voice1.get(voice1Id-1)!=voice2Id)		{
			voice1.set(voice1Id-1, voice2Id);
			return;
			}
			
			//connect the dialer and the waiter
			voice1.set(voice1Id-1, 0);
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", voice2Id);
			 dataMap.put("dialerId", voice1Id);
			 dataMap.put("callId", callNumber);
			 voice1Actor.inform("voiceDown", dataMap);
	}
}
