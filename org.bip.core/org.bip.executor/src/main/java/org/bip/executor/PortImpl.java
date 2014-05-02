/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.executor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.bip.api.BIPComponent;
import org.bip.api.ComponentProvider;
import org.bip.api.Port;
import org.bip.api.PortType;

/**
 * Provides information about the port: id, specification type and portType which can be enforceable or spontaneous.
 * 
 */
class PortImpl implements Port {

	@XmlTransient
	private PortType portType;

	@XmlAttribute
	protected String id;

	// TODO, Improvement, move this attribute to new class GlueSpecPort (?) and
	// put this class within glue?
	@XmlAttribute
	protected String specType;

	public String getId() {
		return id;
	}

	public String getSpecType() {
		return specType;
	}

	private PortImpl(String id, String specificationType) {
		if (id == null) {
			throw new IllegalArgumentException(
					"Port id cannot be null for specification portType "
							+ specificationType);
		}
		if (specificationType == null) {
				throw new IllegalArgumentException(
						"Port spec portType cannot be null for port id " + id);
		}
		this.id = id;
		this.specType = specificationType;
	}

	public PortType getType() {
		return portType;
	}

	private ComponentProvider componentProvider;

	public PortImpl() {
		// need it for the hashCode function
		portType = PortType.enforceable;
	}

	public PortImpl(String id, String type, Class<?> specificationType) {
		this (id, type, specificationType.getCanonicalName());
		if (specificationType.getCanonicalName() == null)
			throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
	}

	public PortImpl(String id, String type, String specificationType) {
		this (id, specificationType);
		this.portType = getType(type);
	}

	public PortImpl(String id, String type, String specificationType, ComponentProvider behaviourProvider) {
		this(id, type, specificationType);
		this.componentProvider = behaviourProvider;
	}

	private PortType getType(String portType) {
		if (portType.equals(PortType.enforceable.toString())) {
			return PortType.enforceable;
		} else if (portType.equals(PortType.spontaneous.toString())) {
			return PortType.spontaneous;
		} else if (portType.equals(PortType.internal.toString())) {
			return PortType.internal;
		}
		return PortType.unknown;
	}

	public BIPComponent component() {
		if (componentProvider == null) {
			return null;
		}
		return componentProvider.component();
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Port=(");
		result.append("id = " + id);
		result.append(", portType = " + portType);
		result.append(", specType = " + specType);
		result.append(")");

		return result.toString();
	}

	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (!(o instanceof Port)) {
			return false;
		}
		
		Port compareTo = (Port) o;
		
		if ( !this.getType().equals(compareTo.getType()))
			return false;

		if ( !this.getId().equals(compareTo.getId()))
			return false;

		if ( !this.getSpecType().equals(compareTo.getSpecType()))
			return false;
		
		if ( !this.component().equals(compareTo.component()))
			return false;
				
		return true;
	}
	
	// TODO, check if this is correct and proper implementation of hash function. If this one is incorrect
	// hash structures will work incorrectly.
	@Override
	public int hashCode() {
		int result = id.hashCode();
		if (specType != null)
			result = 31 * result + specType.hashCode();
		result = 31 * result + portType.hashCode();
		return result;
	}

}
