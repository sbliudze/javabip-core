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

import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
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

    @ExecutableBehaviour
    public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

    	BehaviourBuilder behaviourBuilder = new BehaviourBuilder();

    	behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );

        behaviourBuilder.addState("state-AB");
        behaviourBuilder.addState("state-AC");
        behaviourBuilder.addState("state-BC");

        behaviourBuilder.addPort("ab", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("ac", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("bc", PortType.enforceable, this.getClass());

        if (size % 2 == 0) {
            /*
            For an even number of disks:

            make the legal move between pegs A and B
            make the legal move between pegs A and C
            make the legal move between pegs B and C
            repeat until complete
            */

        	behaviourBuilder.setInitialState("state-AB");

           // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveAB"));

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAC"));


            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveBC"));

        }
        else {

            /*
             For an odd number of disks:

             make the legal move between pegs A and C
             make the legal move between pegs A and B
             make the legal move between pegs B and C
             repeat until complete
            */

        	behaviourBuilder.setInitialState("state-AC");

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveAC"));

            // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAB"));

            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveBC"));


        }
        
        behaviourBuilder.setComponent(this);

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
