/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * It specifies the functionality of the BIP component port.
 */
public interface PortBase {

	/**
	 * It specifies the id of the port.
	 * 
	 * @return the id
	 */
	public String getId();
	

	/**
	 * It returns the spec type to which this port belongs to. Often it is fully qualified name of class specifying the BIP specification.
	 * 
	 * @return the spec type
	 */
	public String getSpecType();

}
