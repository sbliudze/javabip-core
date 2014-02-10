/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.api;

/**
 * It specifies that a BIP entity can publish itself using framework mechanisms and make it visible to other BIP entities.
 */
public interface Publishable extends Identifiable {

	/**
	 * It publishes a BIP entity in such a manner that it can be found using the framework mechanisms.
	 */
	void publish();

	/**
	 * It unpublishes a BIP entity so that is no longer possible to find using the framework mechanisms.
	 */
	void unpublish();
	
}
