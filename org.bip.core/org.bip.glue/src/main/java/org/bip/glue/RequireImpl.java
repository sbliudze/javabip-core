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

import org.bip.api.PortBase;
import org.bip.api.Requires;

class RequireImpl implements Requires {

	@XmlElement(name = "effect")
	private PortBaseImpl effect;

	@XmlJavaTypeAdapter(XmlGenericListAdapter.class)
	private List<List<PortBaseImpl>> causes;

	public RequireImpl() {
	}

	public RequireImpl(PortBase effect, List<List<PortBase>> causes) {
		this.effect = new PortBaseImpl(effect.getId(),  effect.getSpecType());
		this.causes = new ArrayList<List<PortBaseImpl>>();
		for (List<PortBase> smallCauses : causes) {
			ArrayList<PortBaseImpl> causesInterface = new ArrayList<PortBaseImpl>();
			for (PortBase port : smallCauses) {
				causesInterface.add(new PortBaseImpl(port.getId(),  port.getSpecType()));
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
