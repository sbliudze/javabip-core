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
package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "free", type = PortType.enforceable), @Port(name = "hold", type = PortType.enforceable) })
@ComponentType(initial = "available", name = "org.bip.spec.diningphilosophers.Fork")
public class Fork {

	Logger logger = LoggerFactory.getLogger(Fork.class);

	private int forkId;

	private int noOfTimesUsed;

	public Fork(int forkId) {
		this.forkId = forkId;
		noOfTimesUsed = 0;
	}

	@Transition(name = "hold", source = "available", target = "taken")
	public void hold() {
		logger.debug("Fork " + forkId + " transitions to taken state.");
		noOfTimesUsed++;
	}

	@Transition(name = "free", source = "taken", target = "available")
	public void free() {
		logger.debug("Fork " + forkId + " transitions to available state.");
	}

	@Data(name = "forkId")
	public int forkId() {
		return forkId;
	}

	public int noOfTimesUsed() {
		return noOfTimesUsed;
	}

}
