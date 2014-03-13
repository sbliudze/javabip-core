/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.util.Collection;

/**
 * It specifies the functionality of the accept.
 */
// TODO, Change to Accept?
public interface Accepts {

	/**
	 * It returns the effect of the accept.
	 * 
	 * @return the effect
	 */
	public PortBase getEffect();

	/**
	 * It returns causes for a given effect of accept.
	 * 
	 * @return the causes of the given accept.
	 */
	public Collection<PortBase> getCauses();

}
