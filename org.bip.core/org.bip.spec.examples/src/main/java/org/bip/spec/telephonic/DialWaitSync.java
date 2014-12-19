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
	//array with the waiters on position of dialers wanting them
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
	

	private int i=0;
	
	@Transition(name = "dial", source = "s0", target = "s0", guard = "")
	public void dial(@Data(name="dialerId") Integer x, @Data(name="waiterId") Integer y)	{
		//if x has been discarded because it is already been called, return
		//System.out.println(i+" waiters = "+waitersIds+", dialers = "+ dialerIds);
		
		if (talkShouldBeDeleted.compareAndSet(x-1, 1, 0)) {
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
			dialerIds.set(x-1, 0);
			if (dialerIds.getAndSet(y-1, 0)==0)
			{
				talkShouldBeDeleted.set(y-1, 1);
			}
			
			//do the informing
			HashMap<String, Object> dataMap = new HashMap<String, Object>();
			 dataMap.put("waiterId", y);
			 dataMap.put("dialerId", x);
			callerAgregationExecutor.inform("dialDown", dataMap);
			calleeAgregationExecutor.inform("waitDown", dataMap);
			i++;
		}
		else // y hasn't notified, store that y wants to be talked to by x
		{
			dialerIds.set(x-1, y);
		}
	}
	
	private int findDialerWantingWater(int waiter)
	{
		for (int i=0;i<dialerIds.length(); i++){
			if (dialerIds.get(i)==waiter)
				return i+1;
		}
		return -1;
	}
	
	
	@Transition(name = "wait", source = "s0", target = "s0", guard = "")
	public void waitCall(@Data(name="waiterId") Integer y){
		//System.out.println(i+" waiters = "+ waitersIds + ", dialers = "+ dialerIds);
		//if y has been discarded because it is already calling, return
				if (talkShouldBeDeleted.compareAndSet(y-1, 1, 0)) {
					return;
					}
				//is there someone wanting to talk to me?
				int dialer = findDialerWantingWater(y);
				if (dialer>0) //need to send informs
				{
					// if dialer hasn't notified of talking yet, we need to discard its future notification
					if (waitersIds.get(dialer-1)==0)
					{
						talkShouldBeDeleted.set(dialer-1, 1);
					}
					waitersIds.set(y-1, 0);
					waitersIds.set(dialer-1, 0);
					dialerIds.set(dialer-1, 0);
					if (dialerIds.getAndSet(y-1, 0)==0)
					{
						talkShouldBeDeleted.set(y-1, 1);
					}
					
					//do the informing
					HashMap<String, Object> dataMap = new HashMap<String, Object>();
					 dataMap.put("waiterId", y);
					 dataMap.put("dialerId", dialer);
					callerAgregationExecutor.inform("dialDown", dataMap);
					calleeAgregationExecutor.inform("waitDown", dataMap);
					i++;
				}
				else // y hasn't notified, store that y wants to talk
				{
					waitersIds.set(y-1, 1);
				}
	}
	
}
