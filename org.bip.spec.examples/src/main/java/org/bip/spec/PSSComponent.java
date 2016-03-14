/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "s1", type = PortType.spontaneous),
		@Port(name = "s2", type = PortType.spontaneous),
		@Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "org.bip.spec.PSSComponent")
public class PSSComponent {

	Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

	public int pCounter = 0;

	int pEnabled = 0;

	boolean needExternalEnable;

	public int spontaneousEnableCounter = 0;

	public int spontaneousDisableCounter = 0;

	public PSSComponent(boolean needExternalEnable) {

		this.needExternalEnable = needExternalEnable;
	}

	/*
	 * Check what are the conditions for throwing the exception.
	 */
	@Transition(name = "p", source = "start", target = "start", guard = "isPEnabled")
	public void enforceableP() throws Exception {
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
