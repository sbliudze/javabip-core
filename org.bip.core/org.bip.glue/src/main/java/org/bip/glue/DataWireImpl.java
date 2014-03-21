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
import org.bip.api.PortBase;

class DataWireImpl implements DataWire {

	@XmlElement(name = "from")
	private PortBaseImpl from;

	@XmlElement(name = "to")
	private PortBaseImpl to;

	public DataWireImpl() {
	}

	public DataWireImpl(PortBase from, PortBase to) {
		this.from = new PortBaseImpl(from.getId(), from.getSpecType());
		this.to = new PortBaseImpl(to.getId(), to.getSpecType());
	}

	public PortBase getFrom() {
		return from;
	}

	public PortBase getTo() {
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
