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
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "s", type = PortType.spontaneous), @Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "TestSpecEnforceableSpontaneous")
public class TestSpecEnforceableSpontaneous {

	Logger logger = LoggerFactory.getLogger(TestSpecEnforceableSpontaneous.class);

	public int pCounter = 0;
	public int sCounter = 0;

	boolean pLast = false;

	@Transition(name = "p", source = "start", target = "start", guard = "!isPLast")
	public void enforceableP() {
		logger.debug("P transition is being executed.");
		pCounter++;
		pLast = true;
	}

	@Transition(name = "s", source = "start", target = "start", guard = "isPLast")
	public void spontaneousS() {
		logger.info("Received s notification ");
		sCounter++;
		pLast = false;
	}

	@Guard(name = "isPLast")
	public boolean isPLast() {
		return pLast;
	}

	public int getsCounter() {
		return sCounter;
	}

}
