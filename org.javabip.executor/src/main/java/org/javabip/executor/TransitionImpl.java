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
 * Date: 27/01/14
 */
package org.javabip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import org.javabip.api.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stores the transition information about name, source state, target state, guard, method and required data.
 * 
 * @author Alina Zolotukhina
 * 
 */
class TransitionImpl {

	protected String name;
	protected String source;
	protected String target;
	// Empty string represents that there is no guard associated to this transition.
	protected String guard;
	protected Method method;
	protected MethodHandle methodHandle;
	protected Iterable<Data<?>> dataRequired;

	private Logger logger = LoggerFactory.getLogger(TransitionImpl.class);

	/**
	 * Constructor to be used within a BIP Spec
	 * 
	 * @param name
	 *            name of the transition.
	 * @param source
	 *            source state of the transition.
	 * @param target
	 *            target state of the transition.
	 * @param guard
	 *            the guard for the transition that must evaluate to true for the transition to be enabled.
	 * @param method
	 *            the method that is executed in order to perform the transition.
	 * @param dataRequired
	 *            a list of data items that are required by the transition, parameters in the method signature.
	 */
	public TransitionImpl(String name, String source, String target, String guard, Method method,
			Iterable<Data<?>> dataRequired) {
		if (guard == null)
			guard = "";
		this.name = name;
		this.source = source;
		this.target = target;
		this.guard = guard;
		this.method = method;
		this.methodHandle = getMethodHandleForTransition();
		this.dataRequired = dataRequired;
	}

	public TransitionImpl(TransitionImpl transition) {
		this(transition.name, transition.source, transition.target, transition.guard, transition.method,
				transition.dataRequired);
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

	private MethodHandle getMethodHandleForTransition() {
		MethodType methodType;
		MethodHandle methodHandle = null;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
		try {
			methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), methodType);
		} catch (NoSuchMethodException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (IllegalAccessException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		}
		return methodHandle;
	}
}
