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

	@XmlAttribute
	protected String specType;

	public String getId() {
		return id;
	}

	public String getSpecType() {
		return specType;
	}

	public PortType getType() {
		return portType;
	}

	private ComponentProvider componentProvider;

	public PortImpl(String id, PortType type, Class<?> specificationType) {
		this (id, type, specificationType.getCanonicalName());
		if (specificationType.getCanonicalName() == null)
			throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
	}

	public PortImpl(String id, PortType type, String specificationType) {
		if (id == null) {
			if (specificationType != null)
				throw new IllegalArgumentException(
						"Port id cannot be null for specification "
						+ specificationType);
			else
				throw new IllegalArgumentException("Port id cannot be null for specification ");
		}
		if (specificationType == null) {
				throw new IllegalArgumentException(
						"Port spec portType cannot be null for port id " + id);
		}
		if (type == null) {
			throw new IllegalArgumentException("Port portType cannot be null for port id " + id);
		}
		this.id = id;
		this.portType = type;
		this.specType = specificationType;
	}

	public PortImpl(String id, PortType type, String specificationType, ComponentProvider behaviourProvider) {
		this(id, type, specificationType);
		this.componentProvider = behaviourProvider;
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
	
	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + specType.hashCode();
		result = 31 * result + portType.hashCode();
		return result;
	}

}
