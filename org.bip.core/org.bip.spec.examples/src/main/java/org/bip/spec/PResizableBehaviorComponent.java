/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.spec;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipExecutableBehaviour;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.api.Port;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@bipPorts({ @bipPort(name = "sr", type = "spontaneous"), @bipPort(name = "se", type = "spontaneous"), @bipPort(name = "p", type = "enforceable") })
@bipComponentType(initial = "state0", name = "org.bip.spec.PComponent")
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

	@bipExecutableBehaviour
	public BehaviourBuilder getExecutableBehavior() throws NoSuchMethodException {
		return behavior;
	}

	// @bipExecutableBehaviour
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
		for (int i = 0; i < size; i++) {

			behaviourBuilder.addState("state" + (i + 1));

			// ExecutorTransition=(name = on, source = off -> target = on, guard
			// = , method = public void
			// org.bip.spec.SwitchableRoute.startRoute() throws
			// java.lang.Exception),
			behaviourBuilder.addTransition("p", "state" + (i), "state" + (i + 1), "isPEnabled", PResizableBehaviorComponent.class.getMethod("enforceableP"));

			// ExecutorTransition=(name = off, source = on -> target = wait,
			// guard = , method = public void
			// org.bip.spec.SwitchableRoute.stopRoute() throws
			// java.lang.Exception),
			behaviourBuilder.addTransition("sr", "state" + (i + 1), "state" + (i), "", PResizableBehaviorComponent.class.getMethod("rollbackP"));

			// ExecutorTransition=(name = end, source = wait -> target = done,
			// guard = !isFinished, method = public void
			// org.bip.spec.SwitchableRoute.spontaneousEnd() throws
			// java.lang.Exception),
			behaviourBuilder.addTransition("se", "state" + (i), "state" + (i), "", PResizableBehaviorComponent.class.getMethod("enableP"));

		}

		// [Port=(id = sr, specType = null, type = spontaneous),
		behaviourBuilder.addPort("sr", Port.Type.spontaneous.toString(), this.getClass());

		behaviourBuilder.addPort("se", Port.Type.spontaneous.toString(), this.getClass());

		behaviourBuilder.addPort("p", Port.Type.enforceable.toString(), this.getClass());

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
//	@bipTransition(name = "p", source = "start", target = "start", guard = "isPEnabled")
	public void enforceableP() throws Exception {
		logger.debug("P transition is being executed.");
		pCounter++;
		pEnabled--;
	}

//	@bipGuard(name = "isPEnabled")
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
