/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/13
 */

package org.bip.api;

/**
 * Interface used to specify the identity and type of the data required by transitions/guards.
 *
 * @param <T> the generic type
 */
public interface Data<T> {

	/**
	 * It returns the name of the data.
	 *
	 * @return the string
	 */
	public String name();

	/**
	 * It returns the type of the data.
	 *
	 * @return the class
	 */
	public Class<T> type();
	
}
