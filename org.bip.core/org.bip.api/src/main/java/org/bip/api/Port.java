/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */
package org.bip.api;

public interface Port {

	public BIPComponent component();

	public String getId();

	public Type getType();

	public String getSpecType();

	public enum Type {
		enforceable, spontaneous, internal, unknown,
		// upon adding rewrite the getType method
	}
}
