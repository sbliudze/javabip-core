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

import org.bip.annotations.*;
import org.bip.api.BIPSpec;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "add", type = PortType.enforceable), @Port(name = "rm", type = PortType.enforceable) })
@ComponentType(initial = "one", name = "org.bip.spec.MemoryMonitor")
public class MemoryMonitor implements BIPSpec {
	private Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);

	final private int memoryLimit;

	private int currentCapacity = 0;

	public MemoryMonitor(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Transition(name = "add", source = "one", target = "one", guard = "hasCapacity")
	public void addRoute(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity += deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@Transition(name = "rm", source = "one", target = "one", guard = "hasRouteRunning")
	public void removeRoute(@Data(name = "memoryUsage") Integer deltaMemory) {
		currentCapacity -= deltaMemory;
		logger.debug("Current capacity: " + currentCapacity + ", limit: " + memoryLimit);
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "memoryUsage") Integer memoryUsage) {
		logger.debug("currentCapacity + memoryUsage < memoryLimit: " + currentCapacity + " + " + memoryUsage + " < "
				+ memoryLimit + " " + (currentCapacity + memoryUsage < memoryLimit));
		return currentCapacity + memoryUsage < memoryLimit;
	}

	@Guard(name = "hasRouteRunning")
	public boolean hasRouteRunning() {
		return currentCapacity > 0;
	}
}
