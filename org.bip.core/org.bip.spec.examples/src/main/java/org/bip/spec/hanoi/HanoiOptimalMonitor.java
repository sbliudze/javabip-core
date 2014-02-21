/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */
package org.bip.spec.hanoi;

/*

For an even number of disks:

    make the legal move between pegs A and B
    make the legal move between pegs A and C
    make the legal move between pegs B and C
    repeat until complete

For an odd number of disks:

    make the legal move between pegs A and C
    make the legal move between pegs A and B
    make the legal move between pegs B and C
    repeat until complete

In each case, a total of 2ⁿ-1 moves are made.

*/

import java.util.ArrayList;

import org.bip.annotations.bipExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortBase;
import org.bip.executor.BehaviourBuilder;
import org.bip.impl.PortImpl;
import org.bip.impl.TransitionImpl;
import org.bip.spec.SwitchableRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanoiOptimalMonitor {

    Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

    private int size;

    int numberOfMoves = 0;

    public HanoiOptimalMonitor(int size) {
        this.size = size;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    // LeftHanoiPeg is denoted as A.
    // MiddleHanoiPeg is denoted as B.
    // RightHanoiPeg is denoted as C.

    @bipExecutableBehaviour
    public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

        String componentType = this.getClass().getCanonicalName();
        ArrayList<String> states = new ArrayList<String>();

        ArrayList<TransitionImpl> allTransitions = new ArrayList<TransitionImpl>();
        String currentState = null;

        if (size % 2 == 0) {
            /*
            For an even number of disks:

            make the legal move between pegs A and B
            make the legal move between pegs A and C
            make the legal move between pegs B and C
            repeat until complete
            */

            currentState = "state-AB";

            states.add("state-AB");
            states.add("state-AC");
            states.add("state-BC");

            // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("ab", "state-AB", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveAB")));

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("ac", "state-AC", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAC")));


            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("bc", "state-BC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveBC")));

        }
        else {

            /*
             For an odd number of disks:

             make the legal move between pegs A and C
             make the legal move between pegs A and B
             make the legal move between pegs B and C
             repeat until complete
            */

            currentState = "state-AC";

            states.add("state-AC");
            states.add("state-AB");
            states.add("state-BC");

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("ac", "state-AC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveAC")));

            // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("ab", "state-AB", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAB")));

            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
            allTransitions.add(new TransitionImpl("bc", "state-BC", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveBC")));


        }

        ArrayList<Port> allPorts = new ArrayList<Port>();

        // [Port=(id = sr, specType = null, type = spontaneous),
        allPorts.add(new PortImpl("ab", Port.Type.enforceable.toString(), this.getClass()));

        allPorts.add(new PortImpl("ac", Port.Type.enforceable.toString(), this.getClass()));

        allPorts.add(new PortImpl("bc", Port.Type.enforceable.toString(), this.getClass()));


        // [Guard=(name = isFinished, method = isFinished)]
        ArrayList<Guard> guards = new ArrayList<Guard>();

        BehaviourBuilder behaviourBuilder = new BehaviourBuilder(componentType,
                currentState,
                allTransitions, allPorts, states, guards, this);

        return behaviourBuilder;
    }

    public void moveAB() {
        numberOfMoves++;
        logger.debug("Left to Middle (AB) move is being triggered.");
    }

    public void moveAC() {
        numberOfMoves++;
        logger.debug("Left to Right (AC) move is being triggered.");
    }

    public void moveBC() {
        numberOfMoves++;
        logger.debug("Middle to Right (BC) move is being triggered.");
    }

}
