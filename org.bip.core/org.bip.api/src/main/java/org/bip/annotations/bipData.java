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
 * It is used to annotated data sent between BIP components.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface bipData {

	/**
	 * It returns the name of the data.
	 * 
	 * @return the string
	 */
	String name();

	/**
	 * It returns the access type of the data.
	 * 
	 * @return the string
	 */
	// TODO, use Enum instead of String?
	String accessTypePort() default ""; // any, witness, list

	/**
	 * It returns the ports (if required) for a specific access type being used.
	 * 
	 * @return the array of ports.
	 */
	String[] ports() default "";

}
