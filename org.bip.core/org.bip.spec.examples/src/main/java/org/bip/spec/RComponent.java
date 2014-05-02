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

@Ports({ @Port(name = "s", type = PortType.spontaneous), @Port(name = "r", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "org.bip.spec.RComponent")
public class RComponent {

    Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

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
