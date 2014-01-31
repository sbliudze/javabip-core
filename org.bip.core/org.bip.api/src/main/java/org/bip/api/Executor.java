/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */
package org.bip.api;

public interface Executor extends BIPComponent {

	/**
	 * It tells the executor to register the component for which the executor was created in the specified BIP engine.
	 * 
	 * @param bipEngine
	 *            specifies the BIP engine where the component should be registered.
	 */
	void register(BIPEngine bipEngine);

	/**
	 * It tells the executor to deregister the component if the BIP engine has stopped working
	 */
	void deregister();
}
