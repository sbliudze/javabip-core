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
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.javabip.api.PortBase;
import org.javabip.api.Require;

/**
 * Class implementing the functionality of the glue require macro.
 * 
 */
class RequireImpl implements Require {

	@XmlElement(name = "effect")
	private PortBaseImpl effect;

	@XmlJavaTypeAdapter(XmlGenericListAdapter.class)
	private List<List<PortBaseImpl>> causes;

	public RequireImpl() {
	}

	public RequireImpl(PortBase effect, List<List<PortBase>> causes) {
		this.effect = new PortBaseImpl(effect.getId(), effect.getSpecType());
		this.causes = new ArrayList<List<PortBaseImpl>>();
		for (List<PortBase> smallCauses : causes) {
			ArrayList<PortBaseImpl> causesInterface = new ArrayList<PortBaseImpl>();
			for (PortBase port : smallCauses) {
				causesInterface.add(new PortBaseImpl(port.getId(), port.getSpecType()));
			}
			this.causes.add(causesInterface);
		}

	}

	public PortBase getEffect() {
		return effect;
	}

	public List<List<PortBase>> getCauses() {
		List<List<PortBase>> result = new ArrayList<List<PortBase>>();
		for (List<PortBaseImpl> smallCauses : causes) {
			ArrayList<PortBase> causesInterface = new ArrayList<PortBase>();
			causesInterface.addAll(smallCauses);
			result.add(causesInterface);
		}
		return result;
	}

	public void addCause(List<PortBase> cause) {
		ArrayList<PortBaseImpl> causesInterface = new ArrayList<PortBaseImpl>();
		for (PortBase port : cause) {
			causesInterface.add(new PortBaseImpl(port.getId(), port.getSpecType()));
		}
		this.causes.add(causesInterface);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Require=(");
		result.append("effect = " + effect.toString());
		result.append(", causes = " + causes);
		result.append(")");

		return result.toString();
	}

}
