/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import org.bip.api.Data;
import org.bip.exceptions.BIPException;

/**
 * Provides data, specified as dataIn or dataOut. DataIn will have a name and the value type, dataOut will have a name, the value type, the type of
 * port specification and the list of ports.
 * 
 * @param <T> The type of the data value
 */
class DataImpl<T> implements Data<T> {

	protected String name;
	protected Class<T> clazz;
	
	public DataImpl(String name, Class<T> clazz) {
		if (name == null)
			throw new BIPException("Data name parameter for Data can not be null.");
		if (name.equals("")) 
			throw new BIPException("Data name parameter for Data can not be an empty string.");
		if (clazz == null)
			throw new BIPException("Data type parameter for Data can not be null.");
		this.name = name;
		this.clazz = clazz;
	}

	public String name() {
		return this.name;
	}

	public Class<T> type() {
		return this.clazz;
	}


	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Data=(");
		result.append("name = " + this.name);
		result.append(", type = " + this.clazz);
		result.append(")");

		return result.toString();
	}
		
}
