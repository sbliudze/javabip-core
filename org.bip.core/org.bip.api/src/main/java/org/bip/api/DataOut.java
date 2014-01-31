/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/24/14
 */
package org.bip.api;

public interface DataOut<T> extends Data<T> {
	/**
	 * 
	 * @return The ports specified in the annotation of data Out. Can be both allowed or disallowed.
	 */
	public String[] stringPorts();

	public Type portSpecificationType();

	public enum Type {
		any, witness, allowed, unallowed, unknown,
		// upon adding rewrite the getType method of Data implementation
	}
}
