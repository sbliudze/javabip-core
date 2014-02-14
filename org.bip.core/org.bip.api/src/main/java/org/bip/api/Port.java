/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * It specifies the functionality of the BIP component port.
 */
public interface Port extends ComponentProvider {

	/**
	 * It specifies the id of the port.
	 * 
	 * @return the id
	 */
	public String getId();

	/**
	 * It returns the type of the port.
	 * 
	 * @return the type
	 */
	public Type getType();

	/**
	 * It returns the spec type to which this port belongs to. Often it is fully qualified name of class specifying the BIP specification.
	 * 
	 * @return the spec type
	 */
	public String getSpecType();

	// TODO, There are no internal ports yet still we have an internal type within Type enum within Port class. Transition specificities are
	// leaking into Port interface.

	/**
	 * The Enum Type to encode different port/transition types within a BIP component.
	 */
	public enum Type {

		/** This port is associated with enforceable transition. */
		enforceable,
		/** This port is associated with spontaneous transition. */
		spontaneous,
		/** This port is associated with internal transition that does not require any external trigger. */
		internal,
		/** This type is simply to make it possible to lazily instantiate the actual type of the port. */
		unknown,
	}

}
