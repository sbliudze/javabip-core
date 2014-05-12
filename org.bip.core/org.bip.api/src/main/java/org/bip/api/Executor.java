/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * It specifies the interface for the executor that governs the execution of the BIP component. 
 */
public interface Executor extends BIPComponent {

	/**
	 * It tells the executor to register the component for which the executor was created in the specified BIP engine.
	 * 
	 * @param bipEngine specifies the BIP engine where the component should be registered.
	 */
	void register(BIPEngine bipEngine);
		
	/**
	 * It tells the executor that it is no longer collaborating with a previously registered BIP engine.
	 */
	void deregister();
		
	/**
	 * It returns the BIP engine that Executor uses.
	 */
	BIPEngine engine();
	
}
