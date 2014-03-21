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

/**
 * Provides information about the port: id, specification type and port type which can be enforceable or spontaneous.
 * 
 */
public class PortImpl implements Port {

	@XmlTransient
	private Port.Type type;

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
					"Port id cannot be null for specification type "
							+ specificationType);
		}
		if (specificationType == null) {
				throw new IllegalArgumentException(
						"Port spec type cannot be null for port id " + id);
		}
		this.id = id;
		this.specType = specificationType;
	}

	public Port.Type getType() {
		return type;
	}

	private ComponentProvider componentProvider;

	public PortImpl() {
		// need it for the hashCode function
		type = Port.Type.enforceable;
	}

	public PortImpl(String id, String type, Class<?> specificationType) {
		this (id, type, specificationType.getCanonicalName());
		if (specificationType.getCanonicalName() == null)
			throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
	}

	public PortImpl(String id, String type, String specificationType) {
		this (id, specificationType);
		this.type = getType(type);
	}

	public PortImpl(String id, String type, String specificationType, ComponentProvider behaviourProvider) {
		this(id, type, specificationType);
		this.componentProvider = behaviourProvider;
	}

	private Port.Type getType(String portType) {
		if (portType.equals(Port.Type.enforceable.toString())) {
			return Port.Type.enforceable;
		} else if (portType.equals(Port.Type.spontaneous.toString())) {
			return Port.Type.spontaneous;
		} else if (portType.equals(Port.Type.internal.toString())) {
			return Port.Type.internal;
		}
		return Port.Type.unknown;
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
		result.append(", type = " + type);
		result.append(", specType = " + specType);
		result.append(")");

		return result.toString();
	}

	// TODO: In future components should give a unique ID when registered to the engine that can be used with
	// the portId as a unique key for hashTables
	// TODO: When the above problems are fixed, uncomment the equals method.

	// @Override
	// public boolean equals(Object o) {
	// if (!(o instanceof Port)) {
	//
	// return false;
	// }
	// if (this == o){
	// return true;
	// }
	// Port argument = (Port) o;
	// if (this.id != argument.id){
	// return false;
	// }
	// if (this.specType != argument.specType){
	// return false;
	// }
	// if (this.type != argument.type){
	// return false;
	// }
	// return true;
	// }

	// TODO, check if this is correct and proper implementation of hash function. If this one is incorrect
	// hash structures will work incorrectly.
	@Override
	public int hashCode() {
		int result = id.hashCode();
		if (specType != null)
			result = 31 * result + specType.hashCode();
		result = 31 * result + type.hashCode();
		return result;
	}

}
