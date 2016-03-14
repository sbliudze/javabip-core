package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

/**
 * Quick Philosopher has the possibility to pickup two forks at the same time in one transition. This
 * alone should prevent any deadlocks.
 * Can we setup the priority that pickupBothForks has a higher priority than pickupFork?
 * @author radoslaw
 *
 */
@Ports({ @Port(name = "eat", type = PortType.enforceable),
	@Port(name = "pickupFork", type = PortType.enforceable),
	@Port(name = "putdownFork", type = PortType.enforceable),
	@Port(name = "pickupBothForks", type = PortType.enforceable)})
@ComponentType(initial = "thinking", name = "org.bip.spec.diningphilosophers.Philosopher")
public class QuickPhilosopher extends Philosophers {

	public QuickPhilosopher(int leftFork, int rightFork, boolean usePartialOrder) {
		super(leftFork, rightFork, usePartialOrder);
	}

	@Transition(name="pickupBothForks", source = "thinking", target = "holdingBothForks", guard="canPickupBothForks")
	public void pickupBothForks(@Data(name="forkId") int firstForkId, @Data(name="forkId") int secondForkId) {
		if ( (firstForkId == leftFork && secondForkId == rightFork) || 
				firstForkId == rightFork && secondForkId == leftFork) {
			holdsRightFork = true;
			holdsLeftFork = true;
		}
		throw new IllegalArgumentException("Fork ids do not much the left and right fork.");
	}

	@Guard(name="canPickupBothForks")
	public boolean canPickupFork(@Data(name="forkId") int firstForkId, 
			@Data(name="forkId") int secondForkId) {

		// Already holding one fork.
		if (holdsLeftFork || holdsRightFork)
			return false;

		if ( (firstForkId == leftFork && secondForkId == rightFork) || 
				firstForkId == rightFork && secondForkId == leftFork )
			return true;

		return false;

	}



}
