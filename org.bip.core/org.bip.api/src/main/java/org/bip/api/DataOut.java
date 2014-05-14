/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/24/14
 */

package org.bip.api;

import java.util.Set;

/**
 * It specifies the interface for data being provided by BIP component.
 *
 * @param <T> the generic type
 */
public interface DataOut<T> extends Data<T> {

	/**
	 * It specifies the ports for which the specification of data out applies.
	 *
	 * @return the ports if type requires a list of ports.
	 */
	public Set<Port> allowedPorts();

	/**
	 * It specifies the type of data accessibility. 
	 *
	 * @return the type
	 */
	// TODO API CHANGE change the name to portAccessType.
	public AccessType portSpecificationType();

	/**
	 * The Enum PortType.
	 */
	public enum AccessType {

		/** The any specifies that data is available if any port is participating in the interaction. */
		any, 
		/** The witness specifies that data is available even if no port is participating in the interaction. */
		witness, 
		/** The allowed specifies that data is available only for the ports that are specified. */
		allowed, 
		/** The unallowed specifies that data is *not* available for the ports that are specified. */
		unallowed
	}
	
}
