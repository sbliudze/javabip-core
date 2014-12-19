package org.bip.spec.telephonic;

import java.util.HashMap;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.BIPActor;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;

public class DiscAgregation implements ClientCaller {
	 
	BIPActor discSync;
	HashMap<Integer, BIPActor> clientActors;
	
	public DiscAgregation(int n)
	{
		clientActors = new HashMap<Integer, BIPActor>(n);
	}
	
	public void setSyncRefs(BIPActor discSync)
	{
		this.discSync = discSync;
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
      
        behaviourBuilder.addPort("discUp", PortType.spontaneous, this.getClass());
        behaviourBuilder.addPort("discDown", PortType.spontaneous, this.getClass());     
      		
        behaviourBuilder.addTransitionAndStates("discUp","s0", "s0",  "", this.getClass().getMethod("discUp",Integer.class, Integer.class));
        behaviourBuilder.addTransitionAndStates("discDown","s0", "s0",  "", this.getClass().getMethod("discDown",Integer.class, Integer.class));
     

        return behaviourBuilder;
    }
	
	public void discUp(@Data(name="id1") Integer dialerId, @Data(name="id2") Integer waiterId )
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("id1", dialerId);
		 dataMap.put("id2", waiterId);
		 discSync.inform("disc1",dataMap);
	}
	
	public void discDown(@Data(name="id1") Integer dialerId, @Data(name="id2") Integer waiterId)
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("id1", dialerId);
		 dataMap.put("id2", waiterId);
		 clientActors.get(dialerId).inform("disc",dataMap);
		 clientActors.get(waiterId).inform("disc",dataMap);
	}
}
