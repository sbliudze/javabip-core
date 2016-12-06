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
 */
package org.bip.glue;

/**
 * Glue builder allowing to create synchronous connections between ports.
 * 
 */
public abstract class TwoSynchronGlueBuilder extends GlueBuilder {

	public TwoSynchronGlueBuilder() {
		super();
	}

	public SynchronBuilder synchron(Class<?> spec, String portId) {

		if (spec == null)
			throw new IllegalArgumentException("Spec type can not be null");
		if (portId == null)
			throw new IllegalArgumentException("PortId can not be null.");

		return new SynchronBuilder(portId, spec);

	}

	public class SynchronBuilder {

		private String fromPortId;
		private Class<?> fromSpec;

		public SynchronBuilder(String portId, Class<?> spec) {
			this.fromPortId = portId;
			this.fromSpec = spec;
		}

		/**
		 * Connects the port with another port with a synchron connector.
		 * 
		 * @param spec
		 *            the component type of the other port
		 * @param portId
		 *            the name of the other port
		 */
		public void to(Class<?> spec, String portId) {

			if (spec == null)
				throw new IllegalArgumentException("Spec type can not be null");
			if (portId == null)
				throw new IllegalArgumentException("DataId can not be null.");
			if (portId.equals(""))
				throw new IllegalArgumentException("PortId can not be an empty string.");

			port(fromSpec, fromPortId).requires(spec, portId);

			port(fromSpec, fromPortId).accepts(spec, portId);

			port(spec, portId).requires(fromSpec, fromPortId);

			port(spec, portId).accepts(fromSpec, fromPortId);

		}

	}

}
