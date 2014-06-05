package org.bip.spec.diningphilosophers;

import org.bip.glue.TwoSynchronGlueBuilder;

/**
 * Basic glue builder that may not prevent Philosophers starvation alone.
 * @author radoslaw
 *
 */
public class DiningPhilosophersGlueBuilder extends TwoSynchronGlueBuilder {

	@Override
	public void configure() {
		
		synchron(Philosophers.class, "pickupFork").to(Fork.class, "hold");
		synchron(Philosophers.class, "putdownFork").to(Fork.class, "free");
		
		data(Fork.class, "forkId").to(Philosophers.class, "forkId");

	}

}
