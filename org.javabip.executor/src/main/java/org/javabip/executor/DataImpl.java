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
import org.javabip.exceptions.BIPException;

/**
 * Provides data, specified as dataIn or dataOut. DataIn will have a name and the value type, dataOut will have a name,
 * the value type, the type of port specification and the list of ports.
 * 
 * @param <T>
 *            The type of the data value
 */
class DataImpl<T> implements Data<T> {

	protected String name;
	protected Class<T> clazz;

	public DataImpl(String name, Class<T> clazz) {
		if (name == null)
			throw new BIPException("Data name parameter for Data can not be null.");
		if (name.equals(""))
			throw new BIPException("Data name parameter for Data can not be an empty string.");
		if (clazz == null)
			throw new BIPException("Data type parameter for Data can not be null.");
		this.name = name;
		this.clazz = clazz;
	}

	public String name() {
		return this.name;
	}

	public Class<T> type() {
		return this.clazz;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Data=(");
		result.append("name = " + this.name);
		result.append(", type = " + this.clazz);
		result.append(")");

		return result.toString();
	}

}
