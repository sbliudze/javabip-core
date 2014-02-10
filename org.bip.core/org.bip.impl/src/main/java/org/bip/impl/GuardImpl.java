package org.bip.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bip.annotations.bipData;
import org.bip.api.Data;
import org.bip.api.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides information about the guard of a transition. Has methods to evaluate the guard given the object to invoke method and the arguments.
 * 
 */
public class GuardImpl implements Guard {

	private Logger logger = LoggerFactory.getLogger(GuardImpl.class);

	private String name;
	private Method method;
	// The List is used here, because List interface maintains order of its items.
	// The order is very important since one Guard may have multiple dataIn-s of the same type
	// TODO, dataIsNeeded is a misleading name, as it is not of boolean type but a list type.
	private List<Data<?>> dataIsNeeded = new ArrayList<Data<?>>();

	public GuardImpl(String name, Method method) {
		this.name = name;
		this.method = method;
		this.dataIsNeeded = extractParamAnnotations(method);
	}

	private List<Data<?>> extractParamAnnotations(Method method) {
		// deal with method parameters: there might be a dataIn
		ArrayList<Data<?>> dataIn = new ArrayList<Data<?>>();
		Class<?> paramTypes[] = method.getParameterTypes();
		if (paramTypes == null || paramTypes.length == 0) {
			return new ArrayList<Data<?>>();
		}
		Annotation[][] paramsAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < paramsAnnotations.length; i++) {
			for (Annotation annotation : paramsAnnotations[i]) {
				if (annotation instanceof bipData) {
					bipData dataAnnotation = (bipData) annotation;
					Data<?> data = createData(dataAnnotation.name(), paramTypes[i]);
					dataIn.add(data);
				}
			}
		}
		return dataIn;
	}

	// TODO find a way not to copy this method among classes
	<T> Data<T> createData(String dataName, Class<T> type) {
		Data<T> toReturn = new DataImpl<T>(dataName, type);
		return toReturn;
	}

	public String name() {
		return name;
	}

	public Method method() {
		return method;
	}

	public Collection<Data<?>> dataRequired() {
		return dataIsNeeded;
	}

	public Boolean hasData() {
		return !dataIsNeeded.isEmpty();
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Guard=(");
		result.append("name = " + name());
		result.append(", method = " + method.getName());
		result.append(")");

		return result.toString();
	}

	public boolean evaluateGuard(Object component) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object guardValue = method.invoke(component);
		boolean res = (Boolean) guardValue;
		return res;
	}

	public boolean evaluateGuard(Object component, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.debug("For component {}:", component.getClass());
		logger.debug("Evaluation of guard {} with args {}.", this.name, args);
		Object guardValue = method.invoke(component, args);
		boolean res = (Boolean) guardValue;
		return res;
	}
}
