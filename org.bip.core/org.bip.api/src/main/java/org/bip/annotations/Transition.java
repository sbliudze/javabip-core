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
 * It annotates function with information about transition it is performing.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Transition {
	
	/**
	 * It returns the name of the transition. 
	 *
	 * @return the name of the transition.
	 */
	String name();
	
	/**
	 * It returns the source state of the transition.
	 *
	 * @return the source state of the transition.
	 */
	String source();
	
	/**
	 * It returns the target state of the transition.
	 *
	 * @return the target state of the transition.
	 */
	String target();
	
	/**
	 * It returns the guard expression associated with this transition.
	 *
	 * @return the guard expression of the transition.
	 */
	String guard() default "";

}
