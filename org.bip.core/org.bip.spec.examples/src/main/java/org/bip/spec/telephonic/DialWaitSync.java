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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "dial", type = PortType.spontaneous),
	 @Port(name = "wait", type = PortType.spontaneous) })
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.DialWaitSync")
public class DialWaitSync {

	private Logger logger = LoggerFactory.getLogger(DialWaitSync.class);
	BIPActor callerAgregationExecutor;
	BIPActor calleeAgregationExecutor;
	
	//array with 1 on the places of those who are waiting for a call
	AtomicIntegerArray waitersIds;
	//array with the corresponding dealerId on places of those  who the dialers want to talk to
	AtomicIntegerArray dialerIds;
	AtomicIntegerArray talkShouldBeDeleted;
	
	public DialWaitSync(int n)	{
		waitersIds = new AtomicIntegerArray(n);
		dialerIds = new AtomicIntegerArray(n);
		talkShouldBeDeleted = new AtomicIntegerArray(n);
		for (int i=0; i<n; i++)
		{
			talkShouldBeDeleted.set(i, 0);
		}
	}
	
	public void setExecutorRefs(BIPActor caller, BIPActor callee)
	{
		callerAgregationExecutor = caller;
		calleeAgregationExecutor = callee;
	}
	
	private boolean resetDealerInWaiters(int dialerId)
	{
		for (int i=0; i<dialerIds.length(); i++)
		{
			if (dialerIds.compareAndSet(i, dialerId, 0)) return true;
		}
		return false;
	}
	
	@Transition(name = "dial", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="dialerId") Integer x, @Data(name="waiterId") Integer y)	{
		//if x has been discarded because it is already been called, return
		if (talkShouldBeDeleted.get(x-1) == 1) {
			talkShouldBeDeleted.set(x-1,0); 
			return;
			}
		//if y has notified, remove y talking, x talking and y dialing
		if (waitersIds.get(y-1)!=0)
		{
			// if x hasn't notified of talking yet, we need to discard its future notification
			if (waitersIds.get(x-1)==0)
			{
				talkShouldBeDeleted.set(x-1, 1);
			}
			waitersIds.set(y-1, 0);
			waitersIds.set(x-1, 0);
			if (!resetDealerInWaiters(y))
			{
				talkShouldBeDeleted.set(y-1, 1);
			}
			
			//do the informing
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", y);
			 dataMap.put("dialerId", x);
			callerAgregationExecutor.inform("dialDown", dataMap);
			calleeAgregationExecutor.inform("waitDown", dataMap);
			//System.err.println("Client "+ x + " is being connected with "+ y);
		}
		else // y hasn't notified, store that y wants to be talked to by x
		{
			dialerIds.set(y-1, x);
		}
	}
	
	
	@Transition(name = "wait", source = "s0", target = "s0", guard = "")
	public void waitCall(@Data(name="waiterId") Integer y){
		//if y has been discarded because it is already calling, return
				if (talkShouldBeDeleted.get(y-1)== 1) {
					talkShouldBeDeleted.set(y-1,0); 
					return;
					}
				//if someone has called y, remove y talking, dialer talking and y dialing
				if (dialerIds.get(y-1)!=0)
				{
					int dialer = dialerIds.get(y-1);
					// if dialer hasn't notified of talking yet, we need to discard its future notification
					if (waitersIds.get(dialer-1)==0)
					{
						talkShouldBeDeleted.set(dialer-1, 1);
					}
					waitersIds.set(y-1, 0);
					waitersIds.set(dialer-1, 0);
					if (!resetDealerInWaiters(y))
					{
						talkShouldBeDeleted.set(y-1, 1);
					}
					
					//do the informing
					HashMap<String, Object> dataMap = new HashMap<String, Object>();
					 dataMap.put("waiterId", y);
					 dataMap.put("dialerId", dialer);
					callerAgregationExecutor.inform("dialDown", dataMap);
					calleeAgregationExecutor.inform("waitDown", dataMap);
					//System.err.println("Client "+ dialer + " is being connected with "+ y);
				}
				else // y hasn't notified, store that y wants to talk
				{
					waitersIds.set(y-1, 1);
				}
	}
	
}
