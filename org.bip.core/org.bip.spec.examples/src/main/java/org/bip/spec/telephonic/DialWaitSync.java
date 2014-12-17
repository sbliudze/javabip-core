package org.bip.spec.telephonic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

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
	 @Port(name = "wait", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.DialWaitSync")
public class DialWaitSync {

	private Logger logger = LoggerFactory.getLogger(Client.class);
	BIPActor callerAgregationExecutor;
	BIPActor calleeAgregationExecutor;
	
	//array with 1 on the places of those who are waiting for a call
	AtomicIntegerArray waitersIds;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	AtomicIntegerArray dialerIds;
	
	public DialWaitSync(int n)	{
		waitersIds = new AtomicIntegerArray(n);
		dialerIds = new AtomicIntegerArray(n);
	}
	
	public void setExecutorRefs(BIPActor caller, BIPActor callee)
	{
		callerAgregationExecutor = caller;
		calleeAgregationExecutor = callee;
	}
	
	@Transition(name = "dial", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)	{
		System.err.println("DialWait: "+ dialerId +" wanting to dial " + waiterId
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
			callerAgregationExecutor.inform("dialDown", dataMap);
			calleeAgregationExecutor.inform("waitDown", dataMap);
			System.err.println("Client "+ dialerId + " is being connected with "+ waiterId);
	}
	
	@Transition(name = "wait", source = "s0", target = "s0", guard = "")
	public void waitCall(@Data(name="waiterId") Integer waiterId){
		waitersIds.set(waiterId-1, 1);
		System.err.println("DialWait: "+ waiterId+" is ready to talk. dialer array is "  + dialerIds);
//		if (dialerIds.get(waiterId-1)<=0)
//		{return;}
//		int dialer = dialerIds.get(waiterId-1);
//		dialerIds.set(waiterId-1, 0);
//		waitersIds.set(waiterId-1, 0);
//			System.err.println("Chosen: "+ dialer + " for "+ waiterId);
//			//connect the dialer and the waiter
//			HashMap<String, Object> dataMap = new HashMap<String, Object>();
//			 dataMap.put("waiterId", waiterId);
//			 dataMap.put("dialerId", dialer);
//			callerAgregationExecutor.inform("dialDown", dataMap);
//			calleeAgregationExecutor.inform("waitDown", dataMap);
//			System.err.println("Client "+ dialer + " is being connected with "+ waiterId);
//		
//		logger.info("Client "+ waiterId + " is waiting for a call from "+0 );
//		
	}
}
