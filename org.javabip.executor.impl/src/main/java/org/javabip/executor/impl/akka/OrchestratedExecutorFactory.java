/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.javabip.executor.impl.akka;

import org.javabip.api.Executor;

import akka.actor.ActorSystem;

public class OrchestratedExecutorFactory {

	ActorSystem actorSystem;

	public OrchestratedExecutorFactory(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;
	}

	// public Executor create(final BIPEngine engine, final Object bipComponent, final String id,
	// final boolean useSpec) {
	//
	// final ExecutorKernel executor = new ExecutorKernel(bipComponent, id, useSpec);
	//
	// OrchestratedExecutor actor = TypedActor.get(actorSystem).typedActorOf( new
	// TypedProps<ExecutorKernel>(ExecutorKernel.class,
	// new Creator<ExecutorKernel>() {
	// public ExecutorKernel create() { return executor; }
	// }),
	// executor.getId());
	//
	// executor.setProxy(actor);
	// System.out.print("registering executor with an engine");
	// System.out.flush();
	//
	// // First registering within the engine, as engine will send some message to get details
	// concerning the component
	// BIPActor bipActor = engine.register(actor, executor.getBehavior());
	// System.out.print("registered executor with an engine");
	// System.out.flush();
	// // Second register the engine within the actor so the actor can start sending messages
	// // to itself and do its work with the help of the BIP engine.
	// actor.register(engine);
	//
	// final AkkaOrchestratedExecutorImpl actorWithLifeCycle = new
	// AkkaOrchestratedExecutorImpl(actorSystem, actor);
	//
	// return actorWithLifeCycle;
	// }

	public boolean destroy(Executor executor) {

		// TODO EXTENSION when it is possible to deregister a component from BIP engine make sure it happens here.
		// executor.engine().deregister();

		if (executor instanceof AkkaOrchestratedExecutorImpl) {
			((AkkaOrchestratedExecutorImpl) executor).destroy();
			return true;
		}

		return false;

	}

}
