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

import org.bip.api.Accepts;
import org.bip.api.ComponentProvider;
import org.bip.api.Port;
import org.bip.impl.ComponentProviderImpl;
import org.bip.impl.PortImpl;

class AcceptsImpl implements Accepts {

	@XmlElement(name = "effect")
	private PortImpl effect;

	@XmlElementWrapper(name = "causes")
	@XmlElement(name = "port")
	private Collection<PortImpl> causes;

	public AcceptsImpl() {
	}

	public AcceptsImpl(Port effect, Collection<Port> causes) {
		this.effect = new PortImpl(effect.getId(), effect.getType().toString(), effect.getSpecType(), (ComponentProvider) new ComponentProviderImpl(effect.component()));

		this.causes = new ArrayList<PortImpl>();
		for (Port cause : causes) {
			this.causes.add(new PortImpl(cause.getId(), cause.getType().toString(), cause.getSpecType(), (ComponentProvider) new ComponentProviderImpl(cause.component())));
		}

	}

	public Port getEffect() {
		return effect;
	}

	public Collection<Port> getCauses() {
		ArrayList<Port> causesInterface = new ArrayList<Port>();
		causesInterface.addAll(causes);
		return causesInterface;
	}

	public void addCauses(Collection<Port> causes) {
		for (Port cause : causes) {
			this.causes.add(new PortImpl(cause.getId(), cause.getType().toString(), cause.getSpecType(), (ComponentProvider) new ComponentProviderImpl(cause.component())));
		}
	}

	public String toString()
	{
		StringBuilder result = new StringBuilder();

		result.append("Accept=(");
		result.append("effect = " + effect.toString());
		result.append(", causes = " + causes);
		result.append(")");

		return result.toString();
	}

}
