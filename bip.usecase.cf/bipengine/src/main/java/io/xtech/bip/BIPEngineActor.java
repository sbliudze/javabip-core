package io.xtech.bip;

import java.util.Map;
import java.util.Set;

import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Behaviour;
import org.bip.api.Port;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;

public class BIPEngineActor implements BIPEngine {

	BIPEngine internal;
	ActorSystem actorSystem;
	
	public BIPEngineActor(final BIPEngine internal, final ActorSystem actorSystem, final String id) {
		
		BIPEngine actor = TypedActor.get(actorSystem).typedActorOf(
				new TypedProps<BIPEngine>(BIPEngine.class,
						new Creator<BIPEngine>() {
							public BIPEngine create() {
								return internal;
							}
						}), id);
		
		this.internal = actor;
		this.actorSystem = actorSystem;
	}
	
	@Override
	public void specifyGlue(BIPGlue glue) {
		internal.specifyGlue(glue);
	}

	@Override
	public void register(BIPComponent component, Behaviour behaviour) {
		internal.register(component, behaviour);
	}

	@Override
	public void inform(BIPComponent component, String currentState,
			Set<Port> disabledPorts) {
		internal.inform(component, currentState, disabledPorts);
	}

	@Override
	public void informSpecific(BIPComponent decidingComponent,
			Port decidingPort, Map<BIPComponent, Set<Port>> disabledCombinations) {
		internal.informSpecific(decidingComponent, decidingPort, disabledCombinations);
	}

	@Override
	public void start() {
		internal.start();
	}

	@Override
	public void stop() {
		internal.stop();
	}

	@Override
	public void execute() {
		internal.execute();
	}	

	public void init() {
		internal.start();
	}
	
	public void destroy() {
		
		internal.stop();
		
		if (TypedActor.get(actorSystem).isTypedActor(internal)) {
			TypedActor.get(actorSystem).poisonPill(internal);
		}
		else {
			throw new IllegalStateException("Actor system does not recognize an Actor Engine as an Actor.");
		}

	}

}
