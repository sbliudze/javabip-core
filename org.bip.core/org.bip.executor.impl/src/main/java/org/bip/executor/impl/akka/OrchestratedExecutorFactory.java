package org.bip.executor.impl.akka;

import org.bip.api.BIPEngine;
import org.bip.api.Executor;
import org.bip.api.OrchestratedExecutor;
import org.bip.executor.ExecutorKernel;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;

public class OrchestratedExecutorFactory {
	
	ActorSystem actorSystem;
	
	public OrchestratedExecutorFactory(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;		
	}
	
	public Executor create(final BIPEngine engine, final Object bipComponent, final String id, final boolean useSpec) {
		
		final ExecutorKernel executor = new ExecutorKernel(bipComponent, id, useSpec);
		
		OrchestratedExecutor actor = TypedActor.get(actorSystem).typedActorOf( new TypedProps<ExecutorKernel>(ExecutorKernel.class,
					new Creator<ExecutorKernel>() {
	    	    		public ExecutorKernel create() { return executor; }
	    	    	}),
	    	    	executor.getId());

		executor.setProxy(actor);
		
		// First registering within the engine, as engine will send some message to get details concerning the component
		engine.register(actor, executor.getBehavior());
		// Second register the engine within the actor so the actor can start sending messages 
		// to itself and do its work with the help of the BIP engine. 
		actor.register(engine);
		
		return actor;
	}

	public boolean destroy(Executor executor) {
		
		// TODO, when it is possible to deregister a component from BIP engine make sure it happens here.
		// executor.engine().deregister();
		
		if (TypedActor.get(actorSystem).isTypedActor(executor)) {
			executor.deregister();
			TypedActor.get(actorSystem).poisonPill(executor);
			return true;
		}
		else {
			return false;
		}

	}
	

}
