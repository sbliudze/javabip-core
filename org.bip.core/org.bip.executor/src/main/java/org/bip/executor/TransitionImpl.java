/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.reflect.Method;
import java.util.List;

import org.bip.api.Data;

class TransitionImpl {
	
	protected String name;
	protected String source;
	protected String target;
	// TODO, choose one method of specifying that there is no guard and enforce it in the constructor.
	// Preferred manner is to use empty string.
	protected String guard;
	protected Method method;
	protected Iterable<Data<?>> dataRequired;

	// TODO, what if name and source state are switched in spec make a precomputation check

	/**
	 * Constructor to be used within a BIP Spec
	 * 
	 * @param name name of the transition.
	 * @param source source state of the transition.
	 * @param target target state of the transition.
	 * @param guard the guard for the transition that must evaluate to true for the transition to be enabled.
	 * @param method the method that is executed in order to perform the transition.
	 * @param dataRequired a list of data items that are required by the transition, parameters in the method signature.
	 */
	public TransitionImpl(String name, String source, String target, String guard, 
						  Method method, List<Data<?>> dataRequired) {
		this.name = name;
		this.source = source;
		this.target = target;
		this.guard = guard;
		this.method = method;
		this.dataRequired = dataRequired;
	}
	
	
	public TransitionImpl(TransitionImpl transition) {
		this.name = transition.name;
		this.source = transition.source;
		this.target = transition.target;
		this.guard = transition.guard;
		this.method = transition.method;
		this.dataRequired = transition.dataRequired;
	}

	public String name() {
		return this.name;
	}

	public String source() {
		return this.source;
	}

	public String target() {
		return this.target;
	}
			
}
