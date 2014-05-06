/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bip.api.Data;
import org.bip.api.DataOut.AccessType;
import org.bip.exceptions.BIPException;

class ReflectionHelper {

	// Separator between method name and parameter number to generate default data in name.
	private static final String SEPARATOR = ".";
		
	public static List<Data<?>> parseDataAnnotations(Method method) {
		// deal with method parameters: there might be a dataIn
		ArrayList<Data<?>> dataIn = new ArrayList<Data<?>>();
		Class<?> paramTypes[] = method.getParameterTypes();
		if (paramTypes == null || paramTypes.length == 0) {
			return new ArrayList<Data<?>>();
		}
		
		Annotation[][] paramsAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < paramsAnnotations.length; i++) {
			for (Annotation annotation : paramsAnnotations[i]) {
				if (annotation instanceof org.bip.annotations.Data) {
					org.bip.annotations.Data dataAnnotation = (org.bip.annotations.Data) annotation;
					Data<?> data = createData(dataAnnotation.name(), paramTypes[i]);
					dataIn.add(data);
					break;
				}
			}
			// TODO, write a test that will deal with the case when an annotation is missing. 
			// Maybe create a default name as specified in Data todo.
			if (dataIn.size() < i + 1) {
				// BipData annotation is missing for i-th parameter, thus a default name is taken. 
				dataIn.add(createData(method.getName() + SEPARATOR + i, paramTypes[i]));
			}
		}
		return dataIn;
	}

	
	// TODO find a way not to copy this method among classes
	public static <T> Data<T> createData(String dataName, Class<T> type) {
		Data<T> toReturn = new DataImpl<T>(dataName, type);
		return toReturn;
	}
		
	public static DataImpl<?> parseReturnDataAnnotation(Method method) {

		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof org.bip.annotations.Data) { // DATA OUT
				return parseReturnDataAnnotation(method, (org.bip.annotations.Data)annotation);
			}
		}

		// TODO refactor DataOut interface to check for availability, so no need to store all the ports 
		// within the data, no need to give ports here.
		throw new BIPException("Method " + method + " does not have BIPData annotation for Data Out");

	}
		
	public static DataImpl<?> parseReturnDataAnnotation(Method method, org.bip.annotations.Data dataAnnotation) {

		String name = dataAnnotation.name();
		AccessType type = dataAnnotation.accessTypePort();
		String[] ports = dataAnnotation.ports();
		return createData(name, method.getReturnType(), type, ports);

	}
	
	public static <T> DataImpl<T> createData(String dataName, Class<T> type, AccessType accessType, String[] ports) {
		return new DataImpl<T>(dataName, type, accessType, ports);
	}	
	
}
