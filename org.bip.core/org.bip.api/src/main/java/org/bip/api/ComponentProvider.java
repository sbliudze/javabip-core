/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * ComponentProvider is used for example by the Port to be able to have information about the component instance it belongs to.
 * In case, of ExecutableBehaviour it is not possible to provide the component information right away because the ports are created without the knowledge of the BIP component. 
 */
public interface ComponentProvider {

	// TODO, check the todo in Port class to see about the renaming suggestion as well as making Port subinterface of this interface.
	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	public BIPComponent getComponent();

}
