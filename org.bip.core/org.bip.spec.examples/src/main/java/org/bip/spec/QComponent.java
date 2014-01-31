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

@bipPorts({ @bipPort(name = "s", type = "spontaneous"), @bipPort(name = "q", type = "enforceable") })
@bipComponentType(initial = "start", name = "org.bip.spec.QComponent")
public class QComponent {

    Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

    public int qCounter = 0;

    boolean qEnabled = false;

    /*
      * Check what are the conditions for throwing the exception.
      */
    @bipTransition(name = "q", source = "start", target = "start", guard = "isQEnabled")
    public void enforceableQ() throws Exception {
        logger.debug("Q transition is being executed.");
        qCounter++;
        qEnabled = false;
    }

    @bipGuard(name = "isQEnabled")
    public boolean isQEnabled() {
        return qEnabled;
    }

    @bipTransition(name = "s", source = "start", target = "start", guard = "!isQEnabled")
    public void enableQ() {
        qEnabled = true;
    }

}
