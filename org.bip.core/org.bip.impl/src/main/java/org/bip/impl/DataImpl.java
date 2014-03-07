/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.impl;

import java.util.HashSet;
import java.util.Set;

import org.bip.api.DataOut;
import org.bip.api.Port;
import org.bip.exceptions.BIPException;

/**
 * Provides data, specified as dataIn or dataOut. DataIn will have a name and the value type, dataOut will have a name, the value type, the type of
 * port specification and the list of ports.
 * 
 * @param <T>
 *            The type of the data value
 */
public class DataImpl<T> implements DataOut<T> {

	private String name;
	private Class<T> clazz;

	private Type portSpecificationType;
	private Set<Port> allowedPorts = new HashSet<Port>();
	private String[] stringPorts;

	/**
	 * 
	 * For type any: all the ports can give the data For type witness: data is given without ports participating For type allowedPorts: specify the
	 * ports that are allowed to give the data For type unallowedPorts: specify the ports that are not allowed to give the data
	 */
	// public enum Type {
	// any, witness, allowed, unallowed, unknown,
	// // upon adding rewrite the getType method
	// }

	public DataImpl(String name, Class<T> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public DataImpl(String name, Class<T> clazz, String type) {
		this(name, clazz);
		this.portSpecificationType = getType(type);
	}

	public DataImpl(String name, Class<T> clazz, Set<Port> ports) throws BIPException {
		this(name, clazz);
		this.portSpecificationType = Type.allowed;
		this.allowedPorts = ports;
	}

	public DataImpl(String name, Class<T> clazz, String type, String[] ports) throws BIPException {
		this(name, clazz, type);

		if ((this.portSpecificationType.equals(Type.any) || this.portSpecificationType.equals(Type.witness)) && (ports != null && ports.length != 0 && (ports.length != 1 && ports[0] != ""))) {
			System.out.println(ports.length);
			throw new BIPException("With the type " + this.portSpecificationType + " the list of ports should not be specified.");
		} else if (this.portSpecificationType.equals(Type.allowed)) {
			this.stringPorts = ports;
		} else if (this.portSpecificationType.equals(Type.unallowed)) {
			this.stringPorts = ports;
		} else if (this.portSpecificationType.equals(Type.unknown)) {
			throw new BIPException("Unknow type " + type + " of port specification for data out named " + name + "\n The port types supported are: " + Type.any.toString() + ", "
					+ Type.witness.toString() + ", " + Type.allowed.toString() + ", " + Type.unallowed.toString() + ".");
		}
	}

	private Type getType(String type) {
		if (type.equals(Type.any.toString())) {
			return Type.any;
		} else if (type.equals(Type.witness.toString())) {
			return Type.witness;
		} else if (type.equals(Type.allowed.toString())) {
			return Type.allowed;
		} else if (type.equals(Type.unallowed.toString())) {
			return Type.unallowed;
		}
		return Type.unknown;
	}

	public String name() {
		return this.name;
	}

	public Class<T> type() {
		return this.clazz;
	}

	public Set<Port> allowedPorts() {
		return this.allowedPorts;
	}

	public String[] stringPorts() {
		return this.stringPorts;
	}

//	public void addPorts(List<Port> allowedPorts) {
//		this.allowedPorts.addAll(allowedPorts);
//	}

	public Type portSpecificationType() {
		return this.portSpecificationType;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Data=(");
		result.append("name = " + this.name);
		result.append(", type = " + this.clazz);
		result.append(", alowed ports = " + this.allowedPorts);
		result.append(")");

		return result.toString();
	}
}
