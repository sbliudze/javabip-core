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
	
	AtomicIntegerArray waitersIds;
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
	
	@Transition(name = "dial", source = "s0", target = "s1", guard = "")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)	{
		System.out.println("DialWaitSync "+ " is notified of "+ dialerId +" wanting to speak with " + waiterId
				+" with waitersIds "  + waitersIds);
		dialerIds.set(waiterId-1, 1);
		System.out.println("set d ok");
		if (waitersIds.get(waiterId)!=dialerId)
		return;
			System.out.println("Chosen: "+ dialerId + " for "+ waiterId);
			//connect the dialer and the waiter
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", waiterId);
			 dataMap.put("dialerId", dialerId);
			callerAgregationExecutor.inform("dialDown", dataMap);
			callerAgregationExecutor.inform("waitDown", dataMap);
			System.out.println("Client "+ dialerId + " is being connected with "+ waiterId);
			
		
		
	}
	
	@Transition(name = "wait", source = "s0", target = "s1", guard = "")
	public void waitCall(@Data(name="waiterId") Integer waiterId){
		waitersIds.set(waiterId-1, 1);
		System.out.println("set w ok");
		System.out.println("DialWaitSync "+ " is notified of waiting of "+ waiterId+" with dialerIds "  + dialerIds);
		if (dialerIds.get(waiterId)>0)
		{
			System.out.println("Chosen: "+ dialerIds.get(waiterId) + " for "+ waiterId);
			//connect the dialer and the waiter
			callerAgregationExecutor.inform("dial"+dialerIds.get(waiterId));
			callerAgregationExecutor.inform("wait"+waiterId);
			System.out.println("Client "+ dialerIds.get(waiterId) + " is being connected with "+ waiterId);
		}
		logger.info("Client "+ waiterId + " is waiting for a call from "+0 );
		
	}
}
