package org.bip.glue;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

import javax.xml.bind.annotation.XmlElement;

import org.bip.api.DataWire;
import org.bip.api.Port;
import org.bip.impl.PortImpl;

class DataWireImpl implements DataWire {

	@XmlElement(name = "from")
	private PortImpl from;

	@XmlElement(name = "to")
	private PortImpl to;

	public DataWireImpl() {
	}

	public DataWireImpl(Port from, Port to) {
		this.from = new PortImpl(from.getId(), from.getType().toString(), from.getSpecType());
		this.to = new PortImpl(to.getId(), to.getType().toString(), to.getSpecType());
	}

	public Port getFrom() {
		return from;
	}

	public Port getTo() {
		return to;
	}

	/**
	 * Defines whether this wire provides this required data for this requiring component
	 * 
	 * @param inDataItem
	 * @param componentType
	 * @return
	 */
	public boolean isIncoming(String inDataItem, String componentType) {
		return (this.to.getId().equals(inDataItem) && this.to.getSpecType().equals(componentType));
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Require=(");
		result.append("from = " + from.toString());
		result.append(", to = " + to.toString());
		result.append(")");

		return result.toString();
	}

}
