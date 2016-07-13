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
 * It specifies/aggregates the ports for a given BIP specification.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ports {
	
	/**
	 * The ports of the BIP specification.
	 *
	 * @return the array containing the ports of the BIP specification.
	 */
	Port[] value();
}
