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

@bipPorts({ @bipPort(name = "s", type = "spontaneous"), @bipPort(name = "p", type = "enforceable") })
@bipComponentType(initial = "start", name = "org.bip.spec.TestSpecEnforceableSpontaneous")
public class TestSpecEnforceableSpontaneous {

    Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

    public int pCounter = 0;
    public int sCounter = 0;

    boolean pLast = false;

    /*
      * Check what are the conditions for throwing the exception.
      */
    @bipTransition(name = "p", source = "start", target = "start", guard = "!isPLast")
    public void enforceableP() throws Exception {
        logger.debug("P transition is being executed.");
        pCounter++;
        pLast = true;
    }

    @bipTransition(name = "s", source = "start", target = "start", guard = "isPLast")
    public void spontaneousS() throws Exception {
        logger.info("Received s notification ");
        sCounter++;
        pLast = false;
    }

    @bipGuard(name = "isPLast")
    public boolean isPLast() {
        return pLast;
    }

    public int getsCounter() {
        return sCounter;
    }

}
