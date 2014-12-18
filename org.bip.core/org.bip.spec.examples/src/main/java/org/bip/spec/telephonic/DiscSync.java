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

@Ports({ @Port(name = "disc1", type = PortType.spontaneous),
	 @Port(name = "disc2", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.DiscSync")
public class DiscSync {
	BIPActor disc1Actor;
	//BIPActor disc2Actor;
	
	//array with 1 on the places of those who are waiting for a call
	AtomicIntegerArray first;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	AtomicIntegerArray second;
	
	public DiscSync(int n)	{
		first = new AtomicIntegerArray(n);
		second = new AtomicIntegerArray(n);
	}
	
	public void setExecutorRefs(BIPActor actorCaller, BIPActor actorCallee) {
		disc1Actor = actorCaller;
		//disc2Actor = actorCallee;
		
	}
	
	@Transition(name = "disc1", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="id1") Integer id1, @Data(name="id2") Integer id2)	{
		//System.err.println("Disc: "+ id1 +" wanting to disconnect " + id2);
		
		if (first.get(id2-1)!=id1){
			first.set(id1-1, id2);
			return;
			}
			System.err.println("Disconnecting: "+ id1 + " from "+ id2);
			//second.set(id2-1, 0);
			first.set(id2-1, 0);
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("id2", id2);
			 dataMap.put("id1", id1);
			 disc1Actor.inform("discDown", dataMap);
			 //disc2Actor.inform("discDown", dataMap);
			System.err.println("Client "+ id1 + " is being disconnected with "+ id2);
	}
	
}
