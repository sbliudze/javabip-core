package org.bip.spec.telephonic;

import java.util.HashMap;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.BIPActor;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalleeAgregation implements ClientCaller {

	BIPActor dialWaitExecutor;
	HashMap<Integer, BIPActor> clientActors;
	private Logger logger = LoggerFactory.getLogger(CalleeAgregation.class);
	
	public CalleeAgregation(int n)
	{
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
       
        behaviourBuilder.addPort("waitUp", PortType.spontaneous, this.getClass());
        behaviourBuilder.addPort("waitDown", PortType.spontaneous, this.getClass());
        
        behaviourBuilder.addTransitionAndStates("waitUp","s0", "s0",  "", this.getClass().getMethod("waitUp",Integer.class));
        behaviourBuilder.addTransitionAndStates("waitDown","s0", "s0",  "", this.getClass().getMethod("waitDown",Integer.class, Integer.class, Integer.class));
        
        return behaviourBuilder;
    }
	
	//dialer can also wait - here we are saying that is is ready to do so as well
	public void waitUp(@Data(name="dialerId") Integer waiterId)
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("waiterId", waiterId);
		 logger.info("Callee received wait request from " +waiterId);
		dialWaitExecutor.inform("wait",dataMap);
	}
	
	public void waitDown(@Data(name="waiterId") Integer waiterId, @Data(name="dialerId") Integer dialerId, 
			@Data(name="callId") Integer callNumber)
	{
		 HashMap<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("dialerId", dialerId);
		 dataMap.put("callId", callNumber);
		 clientActors.get(waiterId).inform("wait",dataMap);
	}
}
