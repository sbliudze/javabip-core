package org.bip.spec.telephonic;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ @Port(name = "dial", type = PortType.spontaneous),
	 @Port(name = "wait", type = PortType.spontaneous), 
	 @Port(name = "voice", type = PortType.spontaneous), 
	 @Port(name = "disc", type = PortType.spontaneous),
	 @Port(name = "notify", type = PortType.spontaneous)})
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.Checker")
public class Checker {
	
	private boolean talkingToOne = true;
	private boolean talkingToEachOther = true;
	
	public boolean talkingToOne()
	{
		return talkingToOne;
	}
	
	public boolean talkingToEachOther()
	{
		return talkingToEachOther;
	}
	
	
	private int n;
	AtomicIntegerArray dials;
	AtomicIntegerArray dialwaitsNotified;
	AtomicIntegerArray waits;
	AtomicIntegerArray voiceNotified;
	AtomicIntegerArray voices;
	AtomicIntegerArray discs;
	AtomicIntegerArray clientSteps;
	
	public Checker(int n)
	{
		this.n=n;
		dials = new AtomicIntegerArray(n);
		dialwaitsNotified = new AtomicIntegerArray(n);
		waits = new AtomicIntegerArray(n);
		voiceNotified = new AtomicIntegerArray(n);
		voices = new AtomicIntegerArray(n);
		discs = new AtomicIntegerArray(n);
		//in the beginning all the dialers are disconnected
		for (int i=0; i<n; i++){
			discs.set(i, 1);
		}
		clientSteps = new AtomicIntegerArray(n);
		//TODO do we need to set them to zero?
	}

	//here the component informing has the dialerId
	@Transition(name = "dial", source = "s0", target = "s0")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)	{
		dials.set(dialerId-1, waiterId);
		dialwaitsNotified.set(dialerId-1, 1); 
		if (discs.get(dialerId-1)==0 || waits.get(dialerId-1)!=0 || voices.get(dialerId-1)!=0) {
			talkingToOne=false;
//			System.err.println("dial "+ dialerId+ waiterId+" disc: " +discs.get(dialerId) + ", wait: "+  waits.get(dialerId)
//					+ ", voice: "+voices.get(dialerId));
			}
		discs.set(dialerId-1, 0); 
		//System.err.println(" Client "+ dialerId + " dialed client " + waiterId);
	}
	
	//here the component informing has the waiterId
	@Transition(name = "wait", source = "s0", target = "s0")
	public void waitCall(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId){
		waits.set(waiterId-1, dialerId);
		dialwaitsNotified.set(waiterId-1, 1); 
		if (discs.get(waiterId-1)==0 || dials.get(waiterId-1)!=0 || voices.get(waiterId-1)!=0) {
			talkingToOne=false;
			System.err.println("wait "+ dialerId+ waiterId);
			}
			
		discs.set(waiterId-1, 0); 
		//System.err.println(" Client "+ dialerId + " waited client " + waiterId);
	}
	
	//here the component informing has the id1
	@Transition(name = "voice", source = "s0", target = "s0")
	public void voice(@Data(name="id1") Integer dialerId, @Data(name="id2") Integer waiterId){
		voices.set(dialerId-1, waiterId);
		voiceNotified.set(dialerId-1, 1);
		if (discs.get(dialerId-1)!=0 || (dials.get(dialerId-1)==0 && waits.get(dialerId-1)==0) || 
				(dials.get(dialerId-1)!=0 && waits.get(dialerId-1)!=0)) {
			talkingToOne=false;
			System.err.println("voice "+ dialerId+ waiterId);
			}
		
		if ((dialwaitsNotified.get(dialerId-1)!=0 &&
				dialwaitsNotified.get(waiterId-1)!=0)){
			
		 if ((dials.get(dialerId-1)!=waiterId && waits.get(waiterId-1)!=dialerId)
			&& (dials.get(waiterId-1)!=dialerId && waits.get(dialerId-1)!=waiterId))
		{
			System.out.println("dials: "+ dials+ ", waits: "+waits);
			talkingToEachOther = false;
		}
		 dialwaitsNotified.set(dialerId-1, 0);
		 dialwaitsNotified.set(waiterId-1, 0);
		}
		//System.err.println(" Client "+ dialerId + " voiced client " + waiterId);
	}
	
	//here the component informing has the dialerId
	@Transition(name = "disc", source = "s0", target = "s0")
	public void disconnect(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId){
		discs.set(dialerId-1, waiterId);
		if (voices.get(dialerId-1)==0){
			talkingToOne=false;
			System.err.println("disc "+ dialerId+ waiterId);
			}
		
	
//		if ((voiceNotified.get(dialerId-1)!=0 &&
//				voiceNotified.get(waiterId-1)!=0)){
//			//System.out.println("voiceNot: "+ voiceNotified);
////		 if ((voices.get(dialerId-1)!=waiterId && voices.get(waiterId-1)!=dialerId))
////		{
////			//System.out.println("voice: "+ voices + (voices.get(dialerId-1)!=waiterId)+(voices.get(dialerId-1)!=waiterId));
////			//talkingToEachOther = false;
////			//System.exit(0);
////		} 
//		 voiceNotified.set(dialerId-1, 0);
//		 voiceNotified.set(waiterId-1, 0);
//		}
		voices.set(dialerId-1, 0);
		//System.err.println(" Client "+ dialerId + " disc client " + waiterId);
	}
	
	//here the component informing has the dialerId
		@Transition(name = "notify", source = "s0", target = "s0")
		public void notify(@Data(name="dialerId") Integer dialerId){
			waits.set(dialerId-1, 0); 
			dials.set(dialerId-1, 0); 
			//clientSteps.set(dialerId, step);
			//System.err.println(" Client "+ dialerId + " disc client " + waiterId);
		}
}
