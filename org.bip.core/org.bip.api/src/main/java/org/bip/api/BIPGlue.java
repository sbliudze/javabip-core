/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * It specifies functionality required from BIP Glue based on accepts/requires formalism.
 */
public interface BIPGlue {

	// TODO API CHANGE ArrayList into List interface.
	/**
	 * Gets the accept constraints.
	 * 
	 * @return the accept constraints
	 */
	public ArrayList<Accepts> getAcceptConstraints();

	/**
	 * Gets the requires constraints.
	 * 
	 * @return the requires constraints
	 */
	public ArrayList<Requires> getRequiresConstraints();

	/**
	 * Gets the data wires.
	 * 
	 * @return the data wires
	 */
	public ArrayList<DataWire> getDataWires();

	/**
	 * It writes xml representation of BIP glue to provided output stream.
	 * 
	 * @param outputStream
	 *            the output stream to which BIP glue xml representation is written to.
	 */
	public void toXML(OutputStream outputStream);

}
