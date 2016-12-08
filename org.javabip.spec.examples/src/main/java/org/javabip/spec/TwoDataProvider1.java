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
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "a", type = PortType.enforceable), @Port(name = "b", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.TwoDataProvider1")
public class TwoDataProvider1 {
	Logger logger = LoggerFactory.getLogger(TwoDataProvider1.class);

	private int memoryY = 20;
	private int memoryQ = 90;

	public int noOfTransitions;

	@Transition(name = "a", source = "zero", target = "zero")
	public void componentBTransitionA() {
		logger.debug("Transition a of TwoDataProvider1 has been performed");
		noOfTransitions++;
	}

	@Transition(name = "b", source = "zero", target = "zero")
	public void componentBTransitionB() {
		logger.debug("Transition b of TwoDataProvider1 has been performed");
		noOfTransitions++;
	}

	@Data(name = "memoryY", accessTypePort = AccessType.any)
	public int memoryY() {
		return memoryY;
	}

	@Data(name = "memoryQ", accessTypePort = AccessType.any)
	public int memoryQ() {
		return memoryQ;
	}
}
