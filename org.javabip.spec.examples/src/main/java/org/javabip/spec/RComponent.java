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

@Ports({ @Port(name = "s", type = PortType.spontaneous), @Port(name = "r", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "org.bip.spec.RComponent")
public class RComponent {

	Logger logger = LoggerFactory.getLogger(RComponent.class);

	public int rCounter = 0;

	boolean rEnabled = false;

	/*
	 * Check what are the conditions for throwing the exception.
	 */
	@Transition(name = "r", source = "start", target = "start", guard = "isREnabled")
	public void enforceableR() throws Exception {
		logger.debug("R transition is being executed.");
		rCounter++;
		rEnabled = false;
	}

	@Guard(name = "isREnabled")
	public boolean isREnabled() {
		return rEnabled;
	}

	@Transition(name = "s", source = "start", target = "start", guard = "!isREnabled")
	public void enableR() {
		logger.debug("S transition is being executed.");
		rEnabled = true;
	}

}
