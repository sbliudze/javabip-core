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

import org.bip.api.DataOut.AccessType;

/**
 * It is used to annotated data sent between BIP components. It can be used as annotation on the code 
 * directly or created programmatically and passed to BehaviourBuilder API.  
 * 
 * Data can be specified as annotations within BIP Specification class. Even if behavior spec is 
 * specified pragrammatically it is still possible to use data annotation parser. If no data 
 * information is provided then the name is assumed to be equal to Method.name() + SEPARATOR + parameter.no
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {

	/**
	 * It returns the name of the data.
	 * 
	 * @return the string
	 */
	String name();

	/**
	 * It returns the access type of the data. The default value is any that means that data can be
	 * obtained within any transition. 
	 * 
	 * @return the string
	 */
	// TODO TEST create a test for Specifications with unallowed ports
	AccessType accessTypePort() default AccessType.any; // any, witness, allowed, unallowed

	/**
	 * It returns the ports (if required) for a specific access type being used.
	 * 
	 * @return the array of ports.
	 */
	String[] ports() default {};

}
