/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.api;

/**
 * It specifies that a given BIP entity has an id that can be used to identify
 * it.
 */
public interface Identifiable {

	/**
	 * Gets the id of the entity so it can be used to identify it.
	 * 
	 * @return the id
	 */
	public String getId();

	public String getType();

}
