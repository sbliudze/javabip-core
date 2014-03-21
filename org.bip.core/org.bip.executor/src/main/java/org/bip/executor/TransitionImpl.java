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

public class TransitionImpl {
		
	protected String source;
	protected String target;
	protected String name;

	protected Method method;
	protected String guard;
	protected Iterable<Data<?>> dataRequired;

	// TODO, what if name and source state are switched in spec make a precomputation check

	/**
	 * Constructor to be used within a BIP Spec
	 * 
	 * @param name
	 * @param source
	 * @param target
	 * @param guard
	 * @param method
	 * @param dataIsNeeded
	 */
	public TransitionImpl(String name, String source, String target, String guard, Method method) {
		this.source = source;
		this.target = target;
		this.name = name;
		this.method = method;
		this.guard = guard;
		this.dataRequired = ReflectionHelper.extractParamAnnotations(method);
	}
	
	
	public TransitionImpl(TransitionImpl transition)
	{
		this.source = transition.source;
		this.target = transition.target;
		this.name = transition.name;
		this.method = transition.method;
		this.guard = transition.guard;
		this.dataRequired = transition.dataRequired;
	}
	
	public String source() {
		return this.source;
	}

	public String target() {
		return this.target;
	}
	
	public String name() {
		return this.name;
	}
		
}
