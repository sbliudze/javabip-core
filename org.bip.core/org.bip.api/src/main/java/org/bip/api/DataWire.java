/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/13
 */
package org.bip.api;

/**
 * Datawire is part of BIP glue to specify data transfers occurring between BIP components.
 */
public interface DataWire {
	
	/**
	 * Gets the port from which the data is obtained.
	 *
	 * @return the port from which the data is obtained.
	 */
	public PortBase getFrom();

	/**
	 * Gets the port to which the data is being sent to.
	 *
	 * @return the port to which the data is being sent to.
	 */
	public PortBase getTo();

	/**
	 * Checks if is incoming. 
	 * The function is used by the BIP Engine
	 *
	 * @param inDataItem the in data item
	 * @param componentType the component type
	 * @return true, if is incoming
	 */
	public boolean isIncoming(String inDataItem, String componentType);
}
