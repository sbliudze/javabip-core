/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bip.api.Data;
import org.bip.api.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides information about the guard of a transition. Has methods to evaluate the guard given the object to invoke method and the
 * arguments.
 * 
 */
class GuardImpl implements Guard {


	private Logger logger = LoggerFactory.getLogger(GuardImpl.class);

	private String name;
	private Method method;
	// The List is used here, because List interface maintains order of its items.
	// The order is very important since one Guard may have multiple dataIn-s of the same type
	// TODO, dataIsNeeded is a misleading name, as it is not of boolean type but a list type.
	private List<Data<?>> dataRequired = new ArrayList<Data<?>>();

	public GuardImpl(String name, Method method) {
		this.name = name;
		this.method = method;
		this.dataRequired = ReflectionHelper.extractParamAnnotations(method);
	}

	public String name() {
		return name;
	}

	public Method method() {
		return method;
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

	public boolean evaluateGuard(Object component, Object... args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		logger.debug("For component {}:", component.getClass());
		logger.debug("Evaluation of guard {} with args {}.", this.name, args);
		Object guardValue = method.invoke(component, args);
		// TODO, create a test that will fail if somebody annotates not boolean function with bipguard annotation.
		boolean res = (Boolean) guardValue;
		return res;
	}
}
