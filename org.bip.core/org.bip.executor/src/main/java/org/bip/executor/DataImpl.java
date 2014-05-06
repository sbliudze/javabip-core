/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.util.HashSet;
import java.util.Map;
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
class DataImpl<T> implements DataOut<T> {

	private String name;
	private Class<T> clazz;

	private AccessType portAccessType;
	private Set<Port> allowedPorts = new HashSet<Port>();
	private String[] stringPorts;
	
	// TODO, BUG, DESIGN ISSUE, stringPorts and allowedPorts are not connected. Depending on the constructor
	// one of the attributes will be empty. 
	
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

	public DataImpl(String name, Class<T> clazz, AccessType type) {
		this(name, clazz);
		if (type == null)
			throw new BIPException("AccessType parameter for Data can not be null.");
		this.portAccessType = type;
	}

	public DataImpl(String name, Class<T> clazz, Set<Port> allowedPorts) throws BIPException {
		this(name, clazz);
		this.portAccessType = AccessType.allowed;
		if (allowedPorts == null)
			throw new BIPException("Allowed ports parameter for Data can not be null.");
		if (allowedPorts.isEmpty())
			throw new BIPException("Allowed ports parameter for Data can not be an empty set.");
		this.allowedPorts = allowedPorts;
	}

	// TODO, Given the Set of ports constructor given above, maybe there is no need for it after refactoring. 
	public DataImpl(String name, Class<T> clazz, AccessType type, String[] ports) throws BIPException {
		this(name, clazz, type);

		// TODO rewrite exception using constructor parameters when applicable. Rewrite into simple not nested ifs for each of the 
		// incorrect spec case.
		if ((this.portAccessType.equals(AccessType.any) || this.portAccessType.equals(AccessType.witness)) && 
			(ports != null && ports.length != 0 && (ports.length != 1 && ports[0] != ""))) {
			System.out.println(ports.length);
			throw new BIPException("With the type " + this.portAccessType + " the list of ports should not be specified.");
		} else if (this.portAccessType.equals(AccessType.allowed)) {
			this.stringPorts = ports;
		} else if (this.portAccessType.equals(AccessType.unallowed)) {
			this.stringPorts = ports;
		} else if (this.portAccessType.equals(AccessType.unknown)) {
			throw new BIPException("Unknow type " + type + " of port specification for data out named " + name + "\n The port types supported are: " + AccessType.any.toString() + ", "
					+ AccessType.witness.toString() + ", " + AccessType.allowed.toString() + ", " + AccessType.unallowed.toString() + ".");
		}
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

	public AccessType portSpecificationType() {
		return this.portAccessType;
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
	
	public void computeAllowedPort(Map<String, Port> allEnforceablePorts) {

		if (portAccessType.equals(AccessType.any))
			allowedPorts.addAll(allEnforceablePorts.values());
		
		if (portAccessType.equals(AccessType.allowed)) {
			for (String portName : stringPorts) {
				if (!allEnforceablePorts.containsKey(portName))
					throw new BIPException("There is no port instance specified in Ports for the port " + portName + " mentioned in data " + name);
				allowedPorts.add( allEnforceablePorts.get(portName) );
			}
		}
		
		if (portAccessType.equals(AccessType.unallowed)) {

			allowedPorts.addAll( allEnforceablePorts.values());
			
			for (String portName : stringPorts) {
				if (!allEnforceablePorts.containsKey(portName))
					throw new BIPException("There is no port instance specified in Ports for the port " + portName + " mentioned in data " + name);
				allowedPorts.remove( allEnforceablePorts.get(portName) );
			}
		}
		
	}
	
}
