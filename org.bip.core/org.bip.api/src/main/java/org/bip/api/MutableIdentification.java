/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * It specifies that a given class respects a given BIP specification.
 */
public interface MutableIdentification extends Identifiable {

	/**
	 * Sets the id.
	 *
	 * @param uniqueGlobalId the new id of the identifiable entity.
	 */
	public void setId(String uniqueGlobalId);

}
