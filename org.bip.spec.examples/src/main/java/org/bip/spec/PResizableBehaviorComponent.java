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

@Ports({ @Port(name = "sr", type = PortType.spontaneous), @Port(name = "se", type = PortType.spontaneous),
		@Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "state0", name = "org.bip.spec.PComponent")
public class PResizableBehaviorComponent {

	// sr spontaneous is rolling back to previous state.
	// se spontaneous is enabling transition to the next state.

	Logger logger = LoggerFactory.getLogger(PResizableBehaviorComponent.class);

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

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

		String currentState = "state0";

		behaviourBuilder.setInitialState(currentState);
		behaviourBuilder.addState(currentState);

		// [off, on, wait, done]
		/*
		 * ArrayList<String> states = new ArrayList<String>(); states.add(currentState); ArrayList<TransitionImpl>
		 * allTransitions = new ArrayList<TransitionImpl>();
		 */

		// [Port=(id = sr, specType = null, type = spontaneous),
		behaviourBuilder.addPort("sr", PortType.spontaneous, this.getClass());

		behaviourBuilder.addPort("se", PortType.spontaneous, this.getClass());

		behaviourBuilder.addPort("p", PortType.enforceable, this.getClass());

		for (int i = 0; i < size; i++) {

			behaviourBuilder.addState("state" + (i + 1));

			behaviourBuilder.addTransitionAndStates("p", "state" + (i), "state" + (i + 1), "isPEnabled",
					PResizableBehaviorComponent.class.getMethod("enforceableP"));

			behaviourBuilder.addTransitionAndStates("sr", "state" + (i + 1), "state" + (i), "",
					PResizableBehaviorComponent.class.getMethod("rollbackP"));

			behaviourBuilder.addTransitionAndStates("se", "state" + (i), "state" + (i), "",
					PResizableBehaviorComponent.class.getMethod("enableP"));

		}

		behaviourBuilder.addGuard("isPEnabled", this.getClass().getMethod("isPEnabled"));

		return behaviourBuilder;
	}

	/*
	 * Check what are the conditions for throwing the exception.
	 */
	// @Transition(name = "p", source = "start", target = "start", guard = "isPEnabled")
	public void enforceableP() throws Exception {
		logger.debug("P transition is being executed.");
		pCounter++;
		pEnabled--;
	}

	// @Guard(name = "isPEnabled")
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
