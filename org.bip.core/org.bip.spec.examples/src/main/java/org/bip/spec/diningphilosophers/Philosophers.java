package org.bip.spec.diningphilosophers;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.annotations.Transitions;
import org.bip.api.PortType;

/**
 * Philosopher that may use partial order to decide which fork first to pick up. Partial order alone may 
 * already prevent deadlocks. Philosopher has to pickup one fork at a time.
 * @author radoslaw
 *
 */
@Ports({ @Port(name = "eat", type = PortType.enforceable),
		 @Port(name = "pickupFork", type = PortType.enforceable),
		 @Port(name = "putdownFork", type = PortType.enforceable) })
@ComponentType(initial = "thinking", name = "org.bip.spec.diningphilosophers.Philosopher")
public class Philosophers {

	protected int leftFork;
	protected int rightFork;

	protected boolean holdsLeftFork;
	protected boolean holdsRightFork;
	
	protected boolean usePartialOrder;
	protected boolean leftForkFirst;
	protected boolean rightForkFirst;
	
	public Philosophers(int leftFork, int rightFork, boolean usePartialOrder) {
		if (leftFork == rightFork)
			throw new IllegalArgumentException("Left and Right fork can not have the same id.");
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.usePartialOrder = usePartialOrder;
		if (usePartialOrder) {
			if (leftFork < rightFork)
				leftForkFirst = true;
			else
				rightForkFirst = true;
		}
	}
	
	@Transitions({@Transition(name="pickupFork", source = "thinking", target = "holdingOneFork", guard="canPickupFork"),
				  @Transition(name="pickupFork", source = "holdingOneFork", target = "holdingBothForks", guard="canPickupFork")})
	public void pickupFork(@Data(name="forkId") int forkId) {
		if (forkId == leftFork) {
			System.out.println("Picked up left fork.");
			holdsLeftFork = true;
		}
		if (forkId == rightFork) {
			System.out.println("Picked up right fork.");
			holdsRightFork = true;
		}
		throw new IllegalArgumentException("Fork to be picked up is neither left or right.");
	}

	@Guard(name="canPickupFork")
	public boolean canPickupFork(@Data(name="forkId") int forkId) {
		if (!usePartialOrder) {
			if (leftFork == forkId && holdsLeftFork == false) 
				return true;
			if (rightFork == forkId && holdsRightFork == false)
				return true;
		}
		else {
			// usePartialOrder == true, one of the first is true.
			if (leftFork == forkId && ( leftForkFirst || holdsRightFork) && holdsLeftFork == false)
				return true;
			if (rightFork == forkId && ( rightForkFirst || holdsLeftFork ) && holdsRightFork == false)
				return true;
		}
		return false;
	}

	@Transition(name="eat", source="holdingBothForks", target="hasEaten", guard="holdsBothForks")
	public void eat() {
		System.out.println("Eating... Delicious. Thank you.");
	}
	
	@Guard(name="holdsBothForks")
	public boolean holdsBothForks() {
		return holdsLeftFork && holdsRightFork;
	}
	
	@Transitions({@Transition(name="putdownFork", source = "hasEaten", target = "holdingOneFork", guard="canPutdownFork"),
		  @Transition(name="putdownFork", source = "holdingOneFork", target = "thinking", guard="canPutdownFork")})	
	public void putDownFork(@Data(name="forkId") int forkId) {
		if (forkId == leftFork) {
			System.out.println("Putdown left fork.");
			holdsLeftFork = false;
		}
		if (forkId == rightFork) {
			System.out.println("Putdown right fork.");
			holdsRightFork = false;
		}
		throw new IllegalArgumentException("Fork to be putdown is neither left or right.");		
	}
	
	@Guard(name="canPutdownFork")
	public boolean canPutdownFork(@Data(name="forkId") int forkId) {
		if (leftFork == forkId && holdsLeftFork == true) 
			return true;
		if (rightFork == forkId && holdsRightFork == true)
			return true;
		return false;
	}
	
}
