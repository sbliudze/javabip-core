/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It annotates the function with the name of the guard.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface bipGuard {
	
	/**
	 * It returns the name of the guard.
	 *
	 * @return the name of the guard.
	 */
	String name();
}
