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
// TODO, How do we specify data? Maybe always as annotation and no need to specify it programmatically? 
// If annotation is not specified then what is the default name? maybe name of the method.1 for 
// input parameters and method.out for the return parameter? Moreover, what is the default value for accessType
// port?
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {

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
	// TODO, why default is empty string and not for example "any"?
	String accessTypePort() default ""; // any, witness, list

	/**
	 * It returns the ports (if required) for a specific access type being used.
	 * 
	 * @return the array of ports.
	 */
	String[] ports() default "";

}
