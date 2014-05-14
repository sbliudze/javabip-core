/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.util.List;

/**
 * It specifies the requires within BIP Glue.
 */
// TODO API CHANGE change name to Require
public interface Requires {

	/**
	 * Gets the effect.
	 * 
	 * @return the effect
	 */
	public PortBase getEffect();

	/**
	 * Gets the causes.
	 * 
	 * @return the causes
	 */
	public List<List<PortBase>> getCauses();

}
