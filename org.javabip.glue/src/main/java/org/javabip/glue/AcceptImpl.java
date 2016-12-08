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

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.javabip.api.Accept;
import org.javabip.api.PortBase;

/**
 * Class implementing the functionality of the glue accept macro.
 * 
 */
class AcceptImpl implements Accept {

	@XmlElement(name = "effect")
	private PortBaseImpl effect;

	@XmlElementWrapper(name = "causes")
	@XmlElement(name = "port")
	private Collection<PortBaseImpl> causes;

	public AcceptImpl() {
	}

	public AcceptImpl(PortBase effect, Collection<PortBase> causes) {
		this.effect = new PortBaseImpl(effect.getId(), effect.getSpecType());

		this.causes = new ArrayList<PortBaseImpl>();
		for (PortBase cause : causes) {
			this.causes.add(new PortBaseImpl(cause.getId(), cause.getSpecType()));
		}

	}

	public PortBase getEffect() {
		return effect;
	}

	public Collection<PortBase> getCauses() {
		ArrayList<PortBase> causesInterface = new ArrayList<PortBase>();
		causesInterface.addAll(causes);
		return causesInterface;
	}

	public void addCauses(Collection<PortBase> causes) {
		for (PortBase cause : causes) {
			this.causes.add(new PortBaseImpl(cause.getId(), cause.getSpecType()));
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Accept=(");
		result.append("effect = " + effect.toString());
		result.append(", causes = " + causes);
		result.append(")");

		return result.toString();
	}

}
