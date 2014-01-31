package org.bip.api;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

/**
 * It specifies that a given class respects a given BIP specification.
 */
public interface MutableIdentification extends Identifiable{

	public void setId(String uniqueGlobalId);

}
