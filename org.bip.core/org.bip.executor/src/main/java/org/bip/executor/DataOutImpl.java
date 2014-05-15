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
class DataOutImpl<T> extends DataImpl<T> implements DataOut<T> {

	private AccessType portAccessType;
	private Set<Port> allowedPorts = new HashSet<Port>();
	private String[] stringPorts;
	 
	public DataOutImpl(String name, Class<T> clazz, AccessType portAccessType, String[] ports) throws BIPException {
		
		super(name, clazz);
		if (portAccessType == null)
			throw new BIPException("AccessType parameter for Data can not be null.");

		
		if (portAccessType.equals(AccessType.any) || portAccessType.equals(AccessType.witness)) {
			
			if (ports != null && ports.length != 0)
				throw new BIPException("With the type " + this.portAccessType + " the list of ports should not be specified.");
			
			
		} else {
			this.stringPorts = ports;
		}
		
		this.portAccessType = portAccessType;
		
	}

	public Set<Port> allowedPorts() {
		if (allowedPorts.isEmpty())
			throw new BIPException("DataOut is being used while it has not been constructed properly, annotation is not properly specified and/or missing call to computeAllowedPort function.");
		return this.allowedPorts;
	}

	public AccessType portSpecificationType() {
		return this.portAccessType;
	}
	
	// It needs to be called in order to finish the construction of Data, so it is wired properly to Port instances 
	// and not just having string based information about ports.
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
