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
 * It specifies the type of the BIP component.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentType {
	
	/**
	 * It returns the name of the type of the component.
	 *
	 * @return the type of the BIP component.
	 */
	String name();
	
	/**
	 * It returns the name of the initial state in which the BIP spec is in after instantiation.
	 *
	 * @return the name of the initial state.
	 */
	String initial();
	
    /**
     * It returns the name of the managed resource for the Resource Manager component
     * and an empty string for a regular component.
     * @return the name of the managed resource
     */
    String resourceName() default "";
}
