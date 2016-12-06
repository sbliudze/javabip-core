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

package org.bip.spec;

import java.util.ArrayList;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.spontaneous) })
@ComponentType(initial = "zero", name = "org.bip.spec.ComponentA")
public class ProperComponentAWithEnvData implements ComponentAWithEnvDataInterface {
	Logger logger = LoggerFactory.getLogger(ProperComponentAWithEnvData.class);
	public int memoryLimit;
	private ArrayList<Integer> mailbox;

	private int currentCapacity = 0;

	public ProperComponentAWithEnvData(int memoryLimit) {
		this.memoryLimit = memoryLimit;
		this.mailbox = new ArrayList<Integer>();
	}

	@Transition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	public void spontaneousOfA(Integer memoryLimit) {
		this.mailbox.add(memoryLimit);
		logger.debug("Spontaneous event b of component A has arrived. ");
	}

	@Transition(name = "", source = "zero", target = "zero", guard = "eventExists")
	public void treatEvents() {
		logger.debug("Internal transition treatEvents is being executed. ");
		this.memoryLimit = this.mailbox.remove(0);
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < "
				+ memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit && currentCapacity > -1));
		return ((currentCapacity + memoryUsage < memoryLimit) && (currentCapacity + memoryUsage >= 0));
	}

	@Guard(name = "eventExists")
	public boolean eventExists() {
		return (!this.mailbox.isEmpty());
	}
}
