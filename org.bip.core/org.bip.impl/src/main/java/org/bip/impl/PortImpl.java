/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.impl;

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

	// TODO check if fields can be made private
	@XmlAttribute
	private String id;

	@XmlTransient
	private Type type;

	// TODO, Improvement, move this attribute to new class GlueSpecPort (?) and
	// put this class within glue?
	@XmlAttribute
	private String specType;

	public String getId() {
		return id;
	}

	public Port.Type getType() {
		return type;
	}

	public String getSpecType() {
		return specType;
	}

	private ComponentProvider componentProvider;

	// public enum Type {
	// enforceable, spontaneous, internal, unknown,
	// // upon adding rewrite the getType method
	// }

	public PortImpl() {
		// need it for the hashCode function
		type = Type.enforceable;
		componentProvider = new ComponentProviderImpl();
	}

	public PortImpl(String id, String type, Class<?> specificationType) {
		this.id = id;
		this.type = getType(type);
		if (specificationType.getCanonicalName() == null)
			throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
		this.specType = specificationType.getCanonicalName();
		componentProvider = new ComponentProviderImpl();
	}

	public PortImpl(String id, String type, String specificationType, ComponentProvider behaviourProvider) {
		this.id = id;
		this.type = getType(type);
		// if (specificationType.getCanonicalName() == null)
		// throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
		this.specType = specificationType;
		this.componentProvider = behaviourProvider;
	}

	public PortImpl(String id, Class<?> specificationType) {
		this.id = id;
		this.type = Type.enforceable;
		if (specificationType.getCanonicalName() == null)
			throw new IllegalArgumentException("The provided class " + specificationType + "has no cannonical name");
		this.specType = specificationType.getCanonicalName();
		componentProvider = new ComponentProviderImpl();
	}

	private Type getType(String portType) {
		if (portType.equals(Type.enforceable.toString())) {
			return Type.enforceable;
		} else if (portType.equals(Type.spontaneous.toString())) {
			return Type.spontaneous;
		} else if (portType.equals(Type.internal.toString())) {
			return Type.internal;
		}
		return Type.unknown;
	}

	public BIPComponent component() {
		return componentProvider.getComponent();
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
