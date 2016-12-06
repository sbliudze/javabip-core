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

//Used in DataAvailabilityTest

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.ComponentC")
public class ComponentC {
	Logger logger = LoggerFactory.getLogger(ComponentC.class);

	private int memoryZ = -200;

	public int noOfTransitions;

	@Transition(name = "a", source = "zero", target = "zero")
	public void componentCTransitionA() {
		logger.debug("Transition a of ComponentC has been performed");
		noOfTransitions++;
	}

	@Transition(name = "b", source = "zero", target = "zero")
	public void componentCTransitionB() {
		logger.debug("Transition b of ComponentC has been performed");
		noOfTransitions++;
	}

	@Data(name = "memoryZ", accessTypePort = AccessType.allowed, ports = { "b" })
	public int memoryZ() {
		return memoryZ;
	}
}
