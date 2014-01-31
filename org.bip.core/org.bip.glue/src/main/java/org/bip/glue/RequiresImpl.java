package org.bip.glue;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bip.api.ComponentProvider;
import org.bip.api.Port;
import org.bip.api.Requires;
import org.bip.impl.ComponentProviderImpl;
import org.bip.impl.PortImpl;

class RequiresImpl implements Requires {

	@XmlElement(name = "effect")
	private PortImpl effect;

	@XmlJavaTypeAdapter(XmlGenericListAdapter.class)
	private List<List<PortImpl>> causes;

	public RequiresImpl() {
	}

	public RequiresImpl(Port effect, List<List<Port>> causes) {
		this.effect = new PortImpl(effect.getId(), effect.getType().toString(), effect.getSpecType(), (ComponentProvider) new ComponentProviderImpl(effect.component()));
		this.causes = new ArrayList<List<PortImpl>>();
		for (List<Port> smallCauses : causes) {
			ArrayList<PortImpl> causesInterface = new ArrayList<PortImpl>();
			for (Port port : smallCauses) {
				causesInterface.add(new PortImpl(port.getId(), port.getType().toString(), port.getSpecType(), (ComponentProvider) new ComponentProviderImpl(port.component())));
			}
			this.causes.add(causesInterface);
		}

	}

	public Port getEffect() {
		return effect;
	}

	public List<List<Port>> getCauses() {
		List<List<Port>> result = new ArrayList<List<Port>>();
		for (List<PortImpl> smallCauses : causes) {
			ArrayList<Port> causesInterface = new ArrayList<Port>();
			causesInterface.addAll(smallCauses);
			result.add(causesInterface);
		}
		return result;
	}

	public void addCause(List<Port> cause) {
		ArrayList<PortImpl> causesInterface = new ArrayList<PortImpl>();
		for (Port port : cause) {
			causesInterface.add(new PortImpl(port.getId(), port.getType().toString(), port.getSpecType(), (ComponentProvider) new ComponentProviderImpl(port.component())));
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
