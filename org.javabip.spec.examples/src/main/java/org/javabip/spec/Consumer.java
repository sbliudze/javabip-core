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
package org.javabip.spec;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "getData", type = PortType.enforceable), @Port(name = "useData", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.Consumer")
public class Consumer {
	Logger logger = LoggerFactory.getLogger(Consumer.class);

	final private int memoryLimit;

	private int currentCapacity = 0;

	public int noOfTransitions = 0;

	public Consumer(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Transition(name = "getData", source = "zero", target = "one", guard = "hasCapacity")
	public void addRoute(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
		noOfTransitions++;
	}

	@Transition(name = "useData", source = "one", target = "zero")
	public void addRoute() {
		currentCapacity = 0;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
		noOfTransitions++;
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < "
				+ memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit));
		return currentCapacity + memoryUsage < memoryLimit;
	}

}
