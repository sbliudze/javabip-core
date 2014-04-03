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
 * It specifies the name and the type of the port.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Port {
	
	/**
	 * It returns the name of the port.
	 *
	 * @return the name of the port.
	 */
	String name();
	
	/**
	 * It specifies the type of the port. It is either spontaneous or enforceable.
	 *
	 * @return the type of the port.
	 */
	String type(); 
	
}
