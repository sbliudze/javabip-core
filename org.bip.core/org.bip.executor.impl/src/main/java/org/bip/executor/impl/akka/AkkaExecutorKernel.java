package org.bip.executor.impl.akka;

import org.bip.exceptions.BIPException;
import org.bip.executor.ExecutorKernel;

import akka.actor.TypedActor;

public class AkkaExecutorKernel extends ExecutorKernel implements TypedActor.PostStop {
	
	
	public AkkaExecutorKernel(Object bipComponent, String id, boolean useSpec)
			throws BIPException {
		super(bipComponent, id, useSpec);
	}

	public AkkaExecutorKernel(Object bipComponent, String id)
			throws BIPException {
		super(bipComponent, id);
	}

	// TODO, there is also PostRestart lifecycle, can typedActor be restarted on its own? Investigate a bit to make sure 
	// no changes to the design are needed.
	@Override
	public void postStop() {
		proxy = null;
	}

}
