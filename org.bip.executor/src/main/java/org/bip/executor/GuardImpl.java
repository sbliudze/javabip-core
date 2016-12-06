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

package org.bip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bip.api.Data;
import org.bip.api.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides information about the guard of a transition. Contains functionality to evaluate the guard given the object
 * to invoke the method on and the arguments.
 * 
 * @author Alina Zolotukhina
 * 
 */
class GuardImpl implements Guard {

	private Logger logger = LoggerFactory.getLogger(GuardImpl.class);

	private String name;
	private Method method;
	private MethodHandle methodHandle;

	// The List is used here, because List interface maintains order of its items.
	// The order is very important since one Guard may have multiple dataIn-s of the same type.
	private List<Data<?>> dataRequired = new ArrayList<Data<?>>();

	public GuardImpl(String name, Method method, List<Data<?>> dataRequired) {
		this.name = name;
		this.method = method;
		this.dataRequired = dataRequired;
		this.methodHandle = getMethodHandleForGuard();
	}

	public String name() {
		return name;
	}

	public Method method() {
		return method;
	}

	public MethodHandle methodHandle() {
		return this.methodHandle;
	}

	public Collection<Data<?>> dataRequired() {
		return dataRequired;
	}

	public Boolean hasData() {
		return !dataRequired.isEmpty();
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Guard=(");
		result.append("name = " + name());
		result.append(", method = " + method.getName());
		result.append(")");

		return result.toString();
	}

	/*
	 * There is no component passed here, as the object on which the evaluation is performed is passed as the first
	 * argument
	 */
	public boolean evaluateGuard(Object... args) {
		logger.debug("For component {}:", args[0].getClass());
		logger.debug("Evaluation of guard {} with args {}.", this.name, args);

		Object guardValue = false; // initialization added because of the try catch
		try {
			guardValue = methodHandle.invokeWithArguments(args);
		} catch (Throwable e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		}

		// TODO TEST, create a test that will fail if somebody annotates not boolean function with bipguard annotation.
		boolean res = (Boolean) guardValue;
		return res;
	}

	private MethodHandle getMethodHandleForGuard() {
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
