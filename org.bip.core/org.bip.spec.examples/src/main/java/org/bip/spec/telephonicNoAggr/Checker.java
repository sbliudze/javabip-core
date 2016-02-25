package org.bip.spec.telephonicNoAggr;

import java.util.concurrent.atomic.AtomicInteger;
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
	 @Port(name = "error", type = PortType.spontaneous)})
@ComponentType(initial = "s0", name = "org.bip.spec.telephonic.Checker")
public class Checker {
	
	// checks that at each time a client talks only to one other client at a time
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
	private AtomicInteger errorCounter;
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
		errorCounter = new AtomicInteger(0);
		dialwaitsNotified = new AtomicIntegerArray(n);
		waits = new AtomicIntegerArray(n);
		voiceNotified = new AtomicIntegerArray(n);
		voices = new AtomicIntegerArray(n);
		discs = new AtomicIntegerArray(n);
		// in the beginning all the dialers are disconnected
		for (int i = 0; i < n; i++) {
			discs.set(i, n + 1);
		}
		discNotified = new AtomicIntegerArray(n);
	}

	//here the component informing has the dialerId
	@Transition(name = "dial", source = "s0", target = "s0")
	public void dial(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId,
			@Data(name="callId") Integer callNumber)	{

		//remember who dialed whom
		dials.set(dialerId-1, waiterId);
		//remember the call number of the dial
		dialwaitsNotified.set(dialerId-1, callNumber);
		//if the client is waiting at the same time, panic
		if (waits.get(dialerId-1)!=0) {
			System.out.println("Something wrong during dialing a call. Call ids are "+ dialwaitsNotified
					+ ". Dials are" + dials 
					+ ". Waits are" + waits +".\n Current is "+ dialerId + "-"+waiterId);
			talkingToOne=false;
			}
		
		//if there were both the dial and the wait procedure for this voice,
		// we do the checks and remove the data remembered
		if ((dialwaitsNotified.get(dialerId-1)!=0 &&
				dialwaitsNotified.get(waiterId-1)!=0)){
			
			int i = dialwaitsNotified.get(dialerId-1); int j = dialwaitsNotified.get(waiterId-1);
			//if dial to wait and wait to dial do not correspond to each other, pair is not connected correctly, panic
		 if ((dials.get(dialerId-1)!=waiterId && waits.get(waiterId-1)!=dialerId)
			//or if the call numbers of dial and wait do not correspond, panic
					|| dialwaitsNotified.getAndSet(dialerId - 1, 0) != dialwaitsNotified
							.getAndSet(waiterId - 1, 0)) {
				System.out.println(callNumber + " dials: " + dials
						+ ", waits: " + waits + " dialCall: " + i
						+ " , waitCall: " + j);
				connectingingToEachOther = false;
			}
			waits.set(waiterId - 1, 0);
			dials.set(dialerId - 1, 0);
		}

		discs.set(dialerId - 1, 0);
	}
	
	//here the component informing has the waiterId
	@Transition(name = "wait", source = "s0", target = "s0")
	public void waitCall(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId,
			@Data(name="callId") Integer callNumber){

		//remember who waits from whom
		waits.set(waiterId-1, dialerId);
		//remember the call number of the wait
		dialwaitsNotified.set(waiterId-1, callNumber); 
		//if the client is dialing at the same time, panic
		if (dials.get(waiterId-1)!=0) {
			System.out.println("Something wrong during waiting for a call. Call ids are "+ dialwaitsNotified
					+ ". Dials are" + dials 
					+ ". Waits are" + waits +".\n Current is "+ dialerId + "-"+waiterId);
			talkingToOne=false;
			}
		
		//if there were both the dial and the wait procedure for this voice,
		// we do the checks and remove the data remembered
		if ((dialwaitsNotified.get(dialerId-1)!=0 &&
				dialwaitsNotified.get(waiterId-1)!=0)){
			
			int i = dialwaitsNotified.get(dialerId-1); int j = dialwaitsNotified.get(waiterId-1);
			//if dial to wait and wait to dial do not correspond to each other, pair is not connected correctly, panic
		 if ((dials.get(dialerId-1)!=waiterId && waits.get(waiterId-1)!=dialerId)
			//or if the call numbers of dial and wait do not correspond, panic
					|| dialwaitsNotified.getAndSet(dialerId - 1, 0) != dialwaitsNotified
							.getAndSet(waiterId - 1, 0)) {
				System.out.println(callNumber + " dials: " + dials
						+ ", waits: " + waits + " dialCall: " + i
						+ " , waitCall: " + j);
				connectingingToEachOther = false;
			}
			waits.set(waiterId - 1, 0);
			dials.set(dialerId - 1, 0);
		}

		discs.set(waiterId - 1, 0);
	}
	
	//here the component informing has the id1
	@Transition(name = "voice", source = "s0", target = "s0")
	public void voice(@Data(name="id1") Integer dialerId, @Data(name="id2") Integer waiterId,
			@Data(name="callId") Integer callNumber){
		//remember who is voicing whom
		voices.set(dialerId-1, waiterId);
		//remember the call number of current voice
		voiceNotified.set(dialerId-1, callNumber);
		
		// if there were both voice procedures for the voice,
		if ((voiceNotified.get(dialerId - 1) != 0 && voiceNotified
				.get(waiterId - 1) != 0)) {
			int i = voiceNotified.get(dialerId - 1);
			int j = voiceNotified.get(waiterId - 1);
			// if they are not the same, panic
			if (((voices.get(dialerId - 1) != waiterId && voices.get(waiterId - 1) != dialerId))
					//or if the call numbers of dial and wait do not correspond, panic
					|| voiceNotified.getAndSet(dialerId-1,0)!=voiceNotified.getAndSet(waiterId-1,0)) {
			System.out.println("Something wrong during voicing of a call. Call ids are "+ voiceNotified
					+ ". Voices are" + voices 
					+ ".\n Current is "+ dialerId + "-"+waiterId
					+ ". dialCall: "+ i + " , waitCall: "+j);
		
				voicingToEachOther = false;
			}
			voices.set(dialerId - 1, 0);
			voices.set(waiterId - 1, 0);
		}
	}
	
	// here the component informing has the dialerId
	@Transition(name = "disc", source = "s0", target = "s0")
	public void disconnect(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId,
			@Data(name="callId") Integer callNumber){
		// remember who is disconnecting from whom
		discs.set(dialerId - 1, waiterId);
		// remember the call number of current disconnect
		discNotified.set(dialerId - 1, callNumber);

		// if there were both disc procedures for the disconnect,
		if ((discNotified.get(dialerId - 1) != 0 && discNotified.get(waiterId - 1) != 0)) {

			// if they are not the same, panic
			if ((discs.get(waiterId - 1) != dialerId && discs.get(dialerId - 1) != waiterId)
					|| discNotified.getAndSet(dialerId - 1, 0) != discNotified.getAndSet(waiterId - 1, 0)) {
				System.out.println("disc: " + discs + dialerId + waiterId + " "
						+ discNotified);
				discToEachOther = false;
			}
			discs.set(dialerId - 1, 0);
			discs.set(waiterId - 1, 0);
		}

	}
	
	@Transition(name = "error", source = "s0", target = "s0")
	public void notifyError() {
		errorCounter.incrementAndGet();
	}
	
	public boolean hasNoErrors()
	{
		return errorCounter.get()==0;
	}
}
