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

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Used in DataAvailabilityTest

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.spontaneous) })
@ComponentType(initial = "zero", name = "org.bip.spec.ComponentA")
public class ComponentA {
	Logger logger = LoggerFactory.getLogger(ComponentA.class);
	final private int memoryLimit;

	private int currentCapacity = 0;

	public int noOfTransitions = 0;

	public ComponentA(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Transition(name = "a", source = "zero", target = "zero", guard = "hasCapacity")
	public void enforceableOfA(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
		noOfTransitions++;
	}

	@Transition(name = "b", source = "zero", target = "zero")
	public void spontaneousOfA() {
		logger.debug("Spontaneous transition b of component A is being executed. ");
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < "
				+ memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit && currentCapacity > -1));
		return ((currentCapacity + memoryUsage < memoryLimit) && (currentCapacity + memoryUsage >= 0));
	}
}
