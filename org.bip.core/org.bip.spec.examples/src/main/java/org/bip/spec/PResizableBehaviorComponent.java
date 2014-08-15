/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "sr", type = PortType.spontaneous), 
		 @Port(name = "se", type = PortType.spontaneous), 
		 @Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "state0", name = "org.bip.spec.PComponent")
public class PResizableBehaviorComponent {

	// sr spontaneous is rolling back to previous state.
	// se spontaneous is enabling transition to the next state.

	Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

	public int pCounter = 0;

	int pEnabled = 0;

	boolean needExternalEnable;
	private BehaviourBuilder behavior;

	public PResizableBehaviorComponent(boolean needExternalEnable, int size) throws NoSuchMethodException {

		this.needExternalEnable = needExternalEnable;
		behavior = initializeBehavior(size);
	}

	@ExecutableBehaviour
	public BehaviourBuilder getExecutableBehavior() throws NoSuchMethodException {
		return behavior;
	}

	// @ExecutableBehaviour
	public BehaviourBuilder initializeBehavior(int size) throws NoSuchMethodException {

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder();
		
		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());
		
		//String componentType = this.getClass().getCanonicalName();

		String currentState = "state0";

		behaviourBuilder.setInitialState(currentState);
		behaviourBuilder.addState(currentState);
		
		// [off, on, wait, done]
		/*ArrayList<String> states = new ArrayList<String>();
		states.add(currentState);
		ArrayList<TransitionImpl> allTransitions = new ArrayList<TransitionImpl>();
*/

		// [Port=(id = sr, specType = null, type = spontaneous),
		behaviourBuilder.addPort("sr", PortType.spontaneous, this.getClass());

		behaviourBuilder.addPort("se", PortType.spontaneous, this.getClass());

		behaviourBuilder.addPort("p", PortType.enforceable, this.getClass());

		for (int i = 0; i < size; i++) {

			behaviourBuilder.addState("state" + (i + 1));

			// ExecutorTransition=(name = on, source = off -> target = on, guard
			// = , method = public void
			// org.bip.spec.SwitchableRoute.startRoute() throws
			// java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("p", "state" + (i), "state" + (i + 1), "isPEnabled", PResizableBehaviorComponent.class.getMethod("enforceableP"));

			// ExecutorTransition=(name = off, source = on -> target = wait,
			// guard = , method = public void
			// org.bip.spec.SwitchableRoute.stopRoute() throws
			// java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("sr", "state" + (i + 1), "state" + (i), "", PResizableBehaviorComponent.class.getMethod("rollbackP"));

			// ExecutorTransition=(name = end, source = wait -> target = done,
			// guard = !isFinished, method = public void
			// org.bip.spec.SwitchableRoute.spontaneousEnd() throws
			// java.lang.Exception),
			behaviourBuilder.addTransitionAndStates("se", "state" + (i), "state" + (i), "", PResizableBehaviorComponent.class.getMethod("enableP"));

		}



		// [Guard=(name = isFinished, method = isFinished)]
		//ArrayList<Guard> guards = new ArrayList<Guard>();
		//guards.add(new GuardImpl("isPEnabled", this.getClass().getMethod("isPEnabled")));
		
		behaviourBuilder.addGuard("isPEnabled", this.getClass().getMethod("isPEnabled"));

		//BehaviourBuilder behaviourBuilder = new BehaviourBuilder(componentType, currentState, allTransitions, allPorts, states, guards, this);

		behaviourBuilder.setComponent(this);
		return behaviourBuilder;
	}

	/*
	 * Check what are the conditions for throwing the exception.
	 */
//	@Transition(name = "p", source = "start", target = "start", guard = "isPEnabled")
	public void enforceableP() throws Exception {
		logger.debug("P transition is being executed.");
		pCounter++;
		pEnabled--;
	}

//	@Guard(name = "isPEnabled")
	public boolean isPEnabled() {
		return !needExternalEnable || pEnabled > 0;
	}

	public void enableP() {
		pEnabled++;
	}

	public void rollbackP() {
		logger.debug("P transition is being roll backed.");
		pCounter--;
	}

}
