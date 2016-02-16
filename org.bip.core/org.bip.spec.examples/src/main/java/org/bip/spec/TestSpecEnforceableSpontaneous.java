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

@Ports({ @Port(name = "s", type = PortType.spontaneous), @Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "start", name = "TestSpecEnforceableSpontaneous")
public class TestSpecEnforceableSpontaneous {

    Logger logger = LoggerFactory.getLogger(TestSpecEnforceableSpontaneous.class);

    public int pCounter = 0;
    public int sCounter = 0;

    boolean pLast = false;

    /*
      * Check what are the conditions for throwing the exception.
      */
    @Transition(name = "p", source = "start", target = "start", guard = "!isPLast")
    public void enforceableP() throws Exception {
        logger.debug("P transition is being executed.");
        pCounter++;
        pLast = true;
    }

    @Transition(name = "s", source = "start", target = "start", guard = "isPLast")
    public void spontaneousS() throws Exception {
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
