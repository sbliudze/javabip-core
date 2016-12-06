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
package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

/**
 * Quick Philosopher has the possibility to pickup two forks at the same time in one transition. This alone should
 * prevent any deadlocks. Can we setup the priority that pickupBothForks has a higher priority than pickupFork?
 * 
 * @author radoslaw
 * 
 */
@Ports({ @Port(name = "eat", type = PortType.enforceable), @Port(name = "pickupFork", type = PortType.enforceable),
		@Port(name = "putdownFork", type = PortType.enforceable),
		@Port(name = "pickupBothForks", type = PortType.enforceable) })
@ComponentType(initial = "thinking", name = "org.bip.spec.diningphilosophers.Philosopher")
public class QuickPhilosopher extends Philosophers {

	public QuickPhilosopher(int leftFork, int rightFork, boolean usePartialOrder) {
		super(leftFork, rightFork, usePartialOrder);
	}

	@Transition(name = "pickupBothForks", source = "thinking", target = "holdingBothForks", guard = "canPickupBothForks")
	public void pickupBothForks(@Data(name = "forkId") int firstForkId, @Data(name = "forkId") int secondForkId) {
		if ((firstForkId == leftFork && secondForkId == rightFork) || firstForkId == rightFork
				&& secondForkId == leftFork) {
			holdsRightFork = true;
			holdsLeftFork = true;
		}
		throw new IllegalArgumentException("Fork ids do not much the left and right fork.");
	}

	@Guard(name = "canPickupBothForks")
	public boolean canPickupFork(@Data(name = "forkId") int firstForkId, @Data(name = "forkId") int secondForkId) {

		// Already holding one fork.
		if (holdsLeftFork || holdsRightFork)
			return false;

		if ((firstForkId == leftFork && secondForkId == rightFork) || firstForkId == rightFork
				&& secondForkId == leftFork)
			return true;

		return false;

	}

}
