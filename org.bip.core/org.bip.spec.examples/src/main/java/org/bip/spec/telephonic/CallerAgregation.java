package org.bip.spec.telephonic;

import java.util.HashMap;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.BIPActor;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallerAgregation implements ClientCaller {
	
	private int n;
	BIPActor dialWaitExecutor;
	HashMap<Integer, BIPActor> clientActors;
	private Logger logger = LoggerFactory.getLogger(CallerAgregation.class);
	
	public CallerAgregation(int n)
	{
		this.n=n;
		clientActors = new HashMap<Integer, BIPActor>(n);
	}
	
	public void setSyncRefs(BIPActor dialWait)
	{
		dialWaitExecutor = dialWait;
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
      
        behaviourBuilder.addPort("dialUp", PortType.spontaneous, this.getClass());
        behaviourBuilder.addPort("dialDown", PortType.spontaneous, this.getClass());     
      		
        behaviourBuilder.addTransitionAndStates("dialUp","s0", "s0",  "", this.getClass().getMethod("dialUp",Integer.class, Integer.class));
        behaviourBuilder.addTransitionAndStates("dialDown","s0", "s0",  "", this.getClass().getMethod("dialDown",Integer.class, Integer.class));
     

        return behaviourBuilder;
    }
	
	public void dialUp(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId )
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", dialerId);
		 dataMap.put("waiterId", waiterId);
		dialWaitExecutor.inform("dial",dataMap);
	}
	
	public void dialDown(@Data(name="dialerId") Integer dialerId, @Data(name="waiterId") Integer waiterId)
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("waiterId", waiterId);
		clientActors.get(dialerId).inform("dial",dataMap);
	}

}
