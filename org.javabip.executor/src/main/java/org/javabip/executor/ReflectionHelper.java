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

import org.javabip.api.Data;
import org.javabip.api.DataOut.AccessType;
import org.javabip.exceptions.BIPException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A helper class providing functionality to parse the data-related information, such as annotations to method and
 * method parameters.
 * 
 * @author Alina Zolotukhina
 * 
 */
class ReflectionHelper {

	// Separator between method name and parameter number to generate default data in name.
	private static final String SEPARATOR = ".";

	/**
	 * It reads data annotations for the method parameters. If one exists then it will use it to define the parameter.
	 * If no data annotation is provided then a default name constructed by name of the method + SEPARATOR +
	 * ${parameter.no} where ${parameter.no} denotes the number of the method parameter.
	 * 
	 * @param method
	 *            the method which has data annotated parameters
	 * @return a list of data variables
	 */
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
				if (annotation instanceof org.javabip.annotations.Data) {
					org.javabip.annotations.Data dataAnnotation = (org.javabip.annotations.Data) annotation;
					Data<?> data = createData(dataAnnotation.name(), paramTypes[i]);
					dataIn.add(data);
					break;
				}
			}
			// TODO, TEST, write a test that will deal with the case when an annotation is missing.
			// Maybe create a default name as specified in Data todo.
			if (dataIn.size() < i + 1) {
				// Data annotation is missing for i-th parameter, thus a default name is taken.
				dataIn.add(createData(method.getName() + SEPARATOR + i, paramTypes[i]));
			}
		}
		return dataIn;
	}

	/**
	 * Created a data variable given its name and type.
	 * 
	 * @param dataName
	 *            the name of the data
	 * @param type
	 *            the type of the data
	 * @return the new Data instance
	 */
	public static <T> Data<T> createData(String dataName, Class<T> type) {
		Data<T> toReturn = new DataImpl<T>(dataName, type);
		return toReturn;
	}

	/**
	 * Parses the annotation for the output data.
	 * 
	 * @param method
	 *            the method representing the output data
	 * @return a new instance of DataOut
	 */
	public static DataOutImpl<?> parseReturnDataAnnotation(Method method) {

		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof org.javabip.annotations.Data) { // DATA OUT
				return parseReturnDataAnnotation(method, (org.javabip.annotations.Data) annotation);
			}
		}

		throw new BIPException("Method " + method + " does not have BIPData annotation for Data Out");

	}

	/**
	 * Parses the annotation for the output data.
	 * 
	 * @param method
	 *            the method representing the output data
	 * @param dataAnnotation
	 *            the data annotation of the method containing the information
	 * @return a new instance of DataOut
	 */
	public static DataOutImpl<?> parseReturnDataAnnotation(Method method, org.javabip.annotations.Data dataAnnotation) {

		String name = dataAnnotation.name();
		AccessType type = dataAnnotation.accessTypePort();
		String[] ports = dataAnnotation.ports();
		return createData(name, method.getReturnType(), type, ports);

	}

	/**
	 * Created a new output data.
	 * 
	 * @param dataName
	 *            the name of the data
	 * @param type
	 *            the class type of the data
	 * @param accessType
	 *            the accessType of the data
	 * @param ports
	 *            the ports corresponding to the access type of the data
	 * @return a new instance of DataOut
	 */
	public static <T> DataOutImpl<T> createData(String dataName, Class<T> type, AccessType accessType, String[] ports) {
		return new DataOutImpl<T>(dataName, type, accessType, ports);
	}

}
