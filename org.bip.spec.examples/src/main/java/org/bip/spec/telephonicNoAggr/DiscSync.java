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

@Ports({ @Port(name = "disc", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonicNoAggr.DiscSync")
public class DiscSync implements ClientCaller  {
	//BIPActor discActor;
	
	//array with 1 on the places of those who are waiting for a call
	AtomicIntegerArray first;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	
	public DiscSync(int n)	{
		first = new AtomicIntegerArray(n);
		clientActors = new HashMap<Integer, BIPActor>(n);
	}
	
	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		//discActor = actorCaller;
		
	}
	
	HashMap<Integer, BIPActor> clientActors;	
	
	public void setClientRefs(BIPActor client, int id) {
		clientActors.put(id, client);
	}
	
	@Transition(name = "disc", source = "s0", target = "s0")
	public void dial(@Data(name="id1") Integer id1, @Data(name="id2") Integer id2, 
			@Data(name="callId") Integer callNumber)	{
		
		if (first.get(id2 - 1) != id1) {
			first.set(id1 - 1, id2);
			return;
		}
		first.set(id2 - 1, 0);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id2", id2);
		dataMap.put("id1", id1);
		dataMap.put("callId", callNumber);
		clientActors.get(id1).inform("disc", dataMap);
		clientActors.get(id2).inform("disc", dataMap);
		// TODO guess we should send down there different data maps as the names are different
		// discActor.inform("discDown", dataMap);
	}
	
}
