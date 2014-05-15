/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.reflect.Method;
import org.bip.api.Data;

class TransitionImpl {
	
	protected String name;
	protected String source;
	protected String target;
	// Empty string represents that there is no guard associated to this transition.
	protected String guard;
	protected Method method;
	protected Iterable<Data<?>> dataRequired;

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
						  Method method, Iterable<Data<?>> dataRequired) {
		if (guard == null) guard = "";
		this.name = name;
		this.source = source;
		this.target = target;
		this.guard = guard;
		this.method = method;
		this.dataRequired = dataRequired;
	}
	
	
	public TransitionImpl(TransitionImpl transition) {
		this(transition.name, transition.source, transition.target, 
			 transition.guard, transition.method, transition.dataRequired);
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
