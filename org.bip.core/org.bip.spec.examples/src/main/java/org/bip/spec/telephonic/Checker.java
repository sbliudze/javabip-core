package org.bip.spec.telephonic;

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
	
	//checks that at each time a client talks only to one other client at a time
	private boolean talkingToOne = true;
	private boolean connectingingToEachOther = true;	
	private boolean voicingToEachOther = true;
	private boolean discToEachOther = true;
	
	public boolean talkingToOne() {
		return talkingToOne;
	}

	public boolean connectingToEachOther() {
		return connectingingToEachOther;
	}

	public boolean voicingToEachOther() {
		return voicingToEachOther;
	}

	public boolean discToEachOther() {
		return discToEachOther;
	}
	
	
	private int n;
	AtomicIntegerArray dials;
	AtomicIntegerArray dialwaitsNotified;
	AtomicIntegerArray waits;
	AtomicIntegerArray voiceNotified;
	AtomicIntegerArray voices;
	AtomicIntegerArray discs;
	AtomicIntegerArray discNotified;
	
	public Checker(int n) {
		this.n = n;
		dials = new AtomicIntegerArray(n);
		dialwaitsNotified = new AtomicIntegerArray(n);
		waits = new AtomicIntegerArray(n);
		voiceNotified = new AtomicIntegerArray(n);
		voices = new AtomicIntegerArray(n);
		discs = new AtomicIntegerArray(n);
		// in the beginning all the dialers are disconnected
		for (int i = 0; i < n; i++) {
			discs.set(i, n+1);
		}
		discNotified = new AtomicIntegerArray(n);
	}

	//here the component informing has the dialerId
	@Transition(name = "dial", source = "s0", target = "s0")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)	{
		dials.set(dialerId-1, waiterId);
		dialwaitsNotified.set(dialerId-1, 1); //one client has dialed 
		//if the client is disconnecting or waiting or voicing, panic
		if (discs.get(dialerId-1)==0 || waits.get(dialerId-1)!=0 || voices.get(dialerId-1)!=0) {
			talkingToOne=false;
			}
		discs.set(dialerId-1, 0); 
	}
	
	//here the component informing has the waiterId
	@Transition(name = "wait", source = "s0", target = "s0")
	public void waitCall(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId){
		waits.set(waiterId-1, dialerId);
		dialwaitsNotified.set(waiterId-1, 1); 
		//if the client is disconnecting or dialing or voicing, panic
		if (discs.get(waiterId-1)==0 || dials.get(waiterId-1)!=0 || voices.get(waiterId-1)!=0) {
			talkingToOne=false;
			System.err.println("wait "+ dialerId+ waiterId);
			}
			
		discs.set(waiterId-1, 0); 
	}
	
	//here the component informing has the id1
	@Transition(name = "voice", source = "s0", target = "s0")
	public void voice(@Data(name="id1") Integer dialerId, @Data(name="id2") Integer waiterId){
		voices.set(dialerId-1, waiterId);
		voiceNotified.set(dialerId-1, 1);
		//if the client is disconnecting, or not dialing and not waiting, or dialing and waiting at the same time, panic
		if (discs.get(dialerId-1)!=0 || (dials.get(dialerId-1)==0 && waits.get(dialerId-1)==0) || 
				(dials.get(dialerId-1)!=0 && waits.get(dialerId-1)!=0)) {
			talkingToOne=false;
			System.err.println("voice "+ dialerId+ waiterId);
			}
		
		//if there were both the dial and the wait procedure for this voice,
		if ((dialwaitsNotified.get(dialerId-1)!=0 &&
				dialwaitsNotified.get(waiterId-1)!=0)){
			
			//if dial to wait and wait to dial do not correspond to each other, pair is not connected correctly, panic
		 if ((dials.get(dialerId-1)!=waiterId && waits.get(waiterId-1)!=dialerId)
			&& (dials.get(waiterId-1)!=dialerId && waits.get(dialerId-1)!=waiterId))
		{
			System.out.println("dials: "+ dials+ ", waits: "+waits);
			connectingingToEachOther = false;
		}
		 dialwaitsNotified.set(dialerId-1, 0);
		 dialwaitsNotified.set(waiterId-1, 0);
		}
	}
	
	//here the component informing has the dialerId
	@Transition(name = "disc", source = "s0", target = "s0")
	public void disconnect(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId){
		discs.set(dialerId-1, waiterId);
		discNotified.set(dialerId-1, 1);
		if (voices.get(dialerId-1)==0){
			talkingToOne=false;
			System.err.println("disc "+ dialerId+ waiterId);
			}
		
		//if there were both voice procedures for the voice,
		if ((voiceNotified.get(dialerId-1)!=0 &&
				voiceNotified.get(waiterId-1)!=0)){
			//if they are not the same, panic
			if ((voices.get(dialerId - 1) != waiterId && voices.get(waiterId - 1) != dialerId)) {
			System.out.println("voice: "+ voices + (voices.get(waiterId-1)!=dialerId)+(voices.get(dialerId-1)!=waiterId));
			voicingToEachOther = false;
		} 
		 voiceNotified.set(dialerId-1, 0);
		 voiceNotified.set(waiterId-1, 0);
		}
		voices.set(dialerId-1, 0);
	}
	
	//here the component informing has the dialerId
		@Transition(name = "notify", source = "s0", target = "s0")
		public void notify(@Data(name="dialerId") Integer dialerId){
			waits.set(dialerId-1, 0); 
			dials.set(dialerId-1, 0); 
			
			int otherId = discs.get(dialerId-1);
			if (otherId == n+1) {return;}
			if (otherId ==0) { //wrong operation order
				talkingToOne=false;
			}
			if ((discNotified.get(dialerId-1)!=0 &&
					discNotified.get(otherId-1)!=0)){
				
				//if they are not the same, panic
				if (discs.get(otherId - 1) != dialerId) {
				System.out.println("disc: "+ discs + dialerId+otherId+ " "+ discNotified);
				discToEachOther = false;
			  } 
				discNotified.set(dialerId-1, 0);
			    discNotified.set(otherId-1, 0);
			}
			
		}
}
