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

import java.util.List;
import java.util.Map;

import org.javabip.api.BIPEngine;
import org.javabip.api.OrchestratedExecutor;
import org.javabip.api.PortBase;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;

public class AkkaOrchestratedExecutorImpl implements AkkaOrchestratedExecutor {

	private OrchestratedExecutor executor;
	private ActorSystem actorSystem;

	public AkkaOrchestratedExecutorImpl(final ActorSystem actorSystem, final OrchestratedExecutor executor) {
		this.executor = executor;
		this.actorSystem = actorSystem;
	}

	@Override
	public void step() {
		executor.step();
	}

	@Override
	public void register(BIPEngine bipEngine) {
		executor.register(bipEngine);
	}

	@Override
	public void deregister() {
		executor.deregister();
	}

	@Override
	public BIPEngine engine() {
		return executor.engine();
	}

	@Override
	public void execute(String portID) {
		executor.execute(portID);
	}

	@Override
	public void inform(String portID) {
		executor.inform(portID);
	}

	@Override
	public <T> T getData(String name, Class<T> clazz) {
		return executor.getData(name, clazz);
	}

	@Override
	public List<Boolean> checkEnabledness(PortBase port, List<Map<String, Object>> data) {
		return executor.checkEnabledness(port, data);
	}

	@Override
	public void setData(String dataName, Object value) {
		executor.setData(dataName, value);
	}

	@Override
	public String getId() {
		return executor.getId();
	}

	@Override
	public String getType() {
		return executor.getType();
	}

	@Override
	public void init() {
	}

	@Override
	public void destroy() {

		executor.deregister();
		if (TypedActor.get(actorSystem).isTypedActor(executor)) {
			TypedActor.get(actorSystem).poisonPill(executor);
		}

	}

	@Override
	public void inform(String portID, Map<String, Object> data) {
		executor.inform(portID, data);
	}

}
