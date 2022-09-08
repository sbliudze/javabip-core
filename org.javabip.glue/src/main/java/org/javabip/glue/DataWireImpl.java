/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.javabip.glue;

import org.javabip.api.DataWire;
import org.javabip.api.PortBase;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class implementing the data wire functionality. Data wires are sued to specify the data connections between different
 * components.
 * 
 */
class DataWireImpl implements DataWire {

	@XmlElement(name = "from")
	private PortBaseImpl from;

	@XmlElement(name = "to")
	private PortBaseImpl to;

	private Boolean isCopy;

	public DataWireImpl() {
	}

	public DataWireImpl(PortBase from, PortBase to, Boolean copy) {
		this.from = new PortBaseImpl(from.getId(), from.getSpecType());
		this.to = new PortBaseImpl(to.getId(), to.getSpecType());
		this.isCopy = copy;
	}

	public PortBase getFrom() {
		return from;
	}

	public PortBase getTo() {
		return to;
	}

	/**
	 * Gets the boolean value indicating if the data shall be copied or passed by reference
	 *
	 * @return true if the value shall be copied, false otherwise
	 */
	@Override
	public Boolean isCopy() {
		return isCopy;
	}

	/**
	 * Defines whether this wire provides this required data for this requiring component.
	 * 
	 * @param inDataItem
	 *            the required data.
	 * @param componentType
	 *            the requiring component.
	 * @return true, if the wire provides the data for the component.
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
