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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.javabip.api.DataOut;
import org.javabip.api.Port;
import org.javabip.exceptions.BIPException;

/**
 * Provides data, specified as dataIn or dataOut. DataIn will have a name and the value type, dataOut will have a name,
 * the value type, the type of port specification and the list of ports.
 * 
 * @param <T>
 *            The type of the data value
 */
class DataOutImpl<T> extends DataImpl<T> implements DataOut<T> {

	private AccessType portAccessType;
	private Set<Port> allowedPorts = new HashSet<Port>();
	private String[] stringPorts;

	public DataOutImpl(String name, Class<T> clazz, AccessType portAccessType, String[] ports) throws BIPException {

		super(name, clazz);
		if (portAccessType == null)
			throw new BIPException("AccessType parameter for Data can not be null.");

		if (portAccessType.equals(AccessType.any) || portAccessType.equals(AccessType.witness)) {

			if (ports != null && ports.length != 0)
				throw new BIPException("With the type " + this.portAccessType
						+ " the list of ports should not be specified.");

		} else {
			this.stringPorts = ports;
		}

		this.portAccessType = portAccessType;

	}

	public Set<Port> allowedPorts() {
		if (allowedPorts.isEmpty())
			throw new BIPException(
					"DataOut is being used while it has not been constructed properly, annotation is not properly specified and/or missing call to computeAllowedPort function.");
		return this.allowedPorts;
	}

	public AccessType portAccessType() {
		return this.portAccessType;
	}

	/*
	 * It needs to be called in order to finish the construction of Data, so it is wired properly to Port instances and
	 * not just having string based information about ports.
	 */
	/**
	 * This function is to be called in order to finish the construction of Data, so it is wired properly to Port
	 * instances instead of just having string based information about ports.
	 * 
	 * @param allEnforceablePorts
	 *            a mapping between port names and port instances
	 */
	public void computeAllowedPort(Map<String, Port> allEnforceablePorts) {

		if (portAccessType.equals(AccessType.any))
			allowedPorts.addAll(allEnforceablePorts.values());

		if (portAccessType.equals(AccessType.allowed)) {
			for (String portName : stringPorts) {
				if (!allEnforceablePorts.containsKey(portName))
					throw new BIPException("There is no port instance specified in Ports for the port " + portName
							+ " mentioned in data " + name);
				allowedPorts.add(allEnforceablePorts.get(portName));
			}
		}

		if (portAccessType.equals(AccessType.unallowed)) {

			allowedPorts.addAll(allEnforceablePorts.values());

			for (String portName : stringPorts) {
				if (!allEnforceablePorts.containsKey(portName))
					throw new BIPException("There is no port instance specified in Ports for the port " + portName
							+ " mentioned in data " + name);
				allowedPorts.remove(allEnforceablePorts.get(portName));
			}
		}

	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Data=(");
		result.append("name = " + this.name);
		result.append(", type = " + this.clazz);
		result.append(", alowed ports = " + this.allowedPorts);
		result.append(")");

		return result.toString();
	}

}
