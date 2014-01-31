/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/13
 */
package org.bip.api;

public interface DataWire {
	public Port getFrom();

	public Port getTo();

	public boolean isIncoming(String inDataItem, String componentType);
}
