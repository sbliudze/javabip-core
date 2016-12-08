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

import javax.xml.bind.annotation.XmlAttribute;

import org.javabip.api.PortBase;

/**
 * The implementation of the BIP Component port.
 * 
 */
class PortBaseImpl implements PortBase {

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

	public PortBaseImpl(String id, String specificationType) {
		if (id == null) {
			throw new IllegalArgumentException("Port id cannot be null for specification type " + specificationType);
		}
		if (specificationType == null) {
			throw new IllegalArgumentException("Port spec type cannot be null for port id " + id);
		}
		this.id = id;
		this.specType = specificationType;
	}

	public PortBaseImpl() {
	}

	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (!(o instanceof PortBase)) {
			return false;
		}

		PortBase compareTo = (PortBase) o;

		if (!this.getId().equals(compareTo.getId()))
			return false;

		if (!this.getSpecType().equals(compareTo.getSpecType()))
			return false;

		return true;
	}

}
