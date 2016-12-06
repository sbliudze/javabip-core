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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanoiMonitor {

	Logger logger = LoggerFactory.getLogger(HanoiMonitor.class);

	private int size;

	int numberOfMoves = 0;

	public HanoiMonitor(int size) {
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

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

		behaviourBuilder.addState("state-AB");
		behaviourBuilder.addState("state-AC");
		behaviourBuilder.addState("state-BC");

		behaviourBuilder.addPort("ab", PortType.enforceable, this.getClass());

		behaviourBuilder.addPort("ac", PortType.enforceable, this.getClass());

		behaviourBuilder.addPort("bc", PortType.enforceable, this.getClass());

		if (size % 2 == 0) {
			/*
			 * For an even number of disks:
			 * 
			 * make the legal move between pegs A and B make the legal move between pegs A and C make the legal move
			 * between pegs B and C repeat until complete
			 */

			behaviourBuilder.setInitialState("state-AB");

			// ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void
			// org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-AC", "",
					HanoiMonitor.class.getMethod("moveAB"));

			// ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void
			// org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-BC", "",
					HanoiMonitor.class.getMethod("moveAC"));

			// ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void
			// org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AB", "",
					HanoiMonitor.class.getMethod("moveBC"));

		} else {

			/*
			 * For an odd number of disks:
			 * 
			 * make the legal move between pegs A and C make the legal move between pegs A and B make the legal move
			 * between pegs B and C repeat until complete
			 */

			behaviourBuilder.setInitialState("state-AC");

			// ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void
			// org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-AB", "",
					HanoiMonitor.class.getMethod("moveAC"));

			// ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void
			// org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-BC", "",
					HanoiMonitor.class.getMethod("moveAB"));

			// ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void
			// org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AC", "",
					HanoiMonitor.class.getMethod("moveBC"));

		}

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
