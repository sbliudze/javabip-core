package org.bip.spec.telephonic;

import java.util.HashMap;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.BIPActor;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoiceAgregation1 implements ClientCaller {
	private int n;
	BIPActor voiceSync;
	HashMap<Integer, BIPActor> clientActors;
	
	public VoiceAgregation1(int n)
	{
		this.n=n;
		clientActors = new HashMap<Integer, BIPActor>(n);
	}
	
	public void setSyncRefs(BIPActor voiceSync)
	{
		this.voiceSync = voiceSync;
	}
	
	public void setClientRefs(BIPActor client, int id)
	{
		clientActors.put(id, client);
	}

	@ExecutableBehaviour
    public BehaviourBuilder getExecutableBehavior() throws NoSuchMethodException {

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);
				
		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

        String currentState = "s0";

        behaviourBuilder.setInitialState(currentState);
      
        behaviourBuilder.addPort("voiceUp", PortType.spontaneous, this.getClass());
        behaviourBuilder.addPort("voiceDown", PortType.spontaneous, this.getClass());     
      		
        behaviourBuilder.addTransitionAndStates("voiceUp","s0", "s0",  "", this.getClass().getMethod("voiceUp",Integer.class, Integer.class));
        behaviourBuilder.addTransitionAndStates("voiceDown","s0", "s0",  "", this.getClass().getMethod("voiceDown",Integer.class, Integer.class));
     

        return behaviourBuilder;
    }
	
	public void voiceUp(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId )
	{
	 	 System.out.println("VoiceAgregation "+ " is notified of "+ dialerId+" speaking to " + waiterId);
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", dialerId);
		 dataMap.put("waiterId", waiterId);
		 voiceSync.inform("voice1",dataMap);
	}
	
	public void voiceDown(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)
	{
		 System.out.println("VoiceAgregation "+ " is trasferring voice to client "+ dialerId);
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("waiterId", waiterId);
		 clientActors.get(dialerId).inform("voice",dataMap);
	}

}
