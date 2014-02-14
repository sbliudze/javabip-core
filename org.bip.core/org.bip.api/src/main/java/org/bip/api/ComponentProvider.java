/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * ComponentProvider is used for example by the Port to be able to have information about the component instance it belongs to. In case, of
 * ExecutableBehaviour it is not possible to provide the component information right away because the ports are created without the
 * knowledge of the BIP component.
 */
// TODO, maybe we rename ComponentProvider interface into something else, like ComponentPart (?)
public interface ComponentProvider {

	/**
	 * Gets the component.
	 * 
	 * @return the component
	 */
	public BIPComponent component();

}
