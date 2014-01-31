/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipGuard;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "s", type = "spontaneous"), @bipPort(name = "r", type = "enforceable") })
@bipComponentType(initial = "start", name = "org.bip.spec.RComponent")
public class RComponent {

    Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

    public int rCounter = 0;

    boolean rEnabled = false;

    /*
      * Check what are the conditions for throwing the exception.
      */
    @bipTransition(name = "r", source = "start", target = "start", guard = "isREnabled")
    public void enforceableR() throws Exception {
        logger.debug("R transition is being executed.");
        rCounter++;
        rEnabled = false;
    }

    @bipGuard(name = "isREnabled")
    public boolean isREnabled() {
        return rEnabled;
    }

    @bipTransition(name = "s", source = "start", target = "start", guard = "!isREnabled")
    public void enableR() {
    	logger.debug("S transition is being executed.");
        rEnabled = true;
    }

}
