package org.bip.glue;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.bip.api.Accept;
import org.bip.api.PortBase;

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
			this.causes.add(new PortBaseImpl(cause.getId(),  cause.getSpecType()));
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
