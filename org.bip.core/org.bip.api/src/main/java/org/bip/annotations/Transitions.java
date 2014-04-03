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
 * It makes it possible to assign multiple transition annotations within one function. 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Transitions {
	
	/**
	 * It returns the array of transitions.
	 *
	 * @return array of transitions.
	 */
	Transition[] value();
}
