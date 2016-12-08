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

@Ports({ @Port(name = "s1", type = PortType.spontaneous), @Port(name = "s2", type = PortType.spontaneous),
		@Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "org.bip.spec.PSSComponent")
public class PSSComponent {

	Logger logger = LoggerFactory.getLogger(PSSComponent.class);

	public int pCounter = 0;

	int pEnabled = 0;

	boolean needExternalEnable;

	public int spontaneousEnableCounter = 0;

	public int spontaneousDisableCounter = 0;

	public PSSComponent(boolean needExternalEnable) {

		this.needExternalEnable = needExternalEnable;
	}

	@Transition(name = "p", source = "start", target = "start", guard = "isPEnabled")
	public void enforceableP() {
		logger.debug("P transition is being executed.");
		pCounter++;
		pEnabled--;
	}

	@Guard(name = "isPEnabled")
	public boolean isPEnabled() {
		return !needExternalEnable || pEnabled > 0;
	}

	@Transition(name = "s1", source = "start", target = "start")
	public void enableP() {
		logger.debug("s1 transition is being executed.");
		pEnabled++;
		spontaneousEnableCounter++;
	}

	@Transition(name = "s2", source = "start", target = "start")
	public void disableP() {
		logger.debug("s2 transition is being executed.");
		pEnabled--;
		spontaneousDisableCounter++;
	}
}
