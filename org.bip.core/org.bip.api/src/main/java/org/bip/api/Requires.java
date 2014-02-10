/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.util.List;

/**
 * It specifies the requires within BIP Glue.
 */
public interface Requires {
	
	/**
	 * Gets the effect.
	 *
	 * @return the effect
	 */
	public Port getEffect();

	/**
	 * Gets the causes.
	 *
	 * @return the causes
	 */
	public List<List<Port>> getCauses();

	// TODO, Do we really need this mutability here in the interface? GlueBuilder can use RequiresImpl directly, so this function could be removed.
	/**
	 * It adds the cause to the requires of a given effect.
	 *
	 * @param cause the cause
	 */
	public void addCause(List<Port> cause);

}
