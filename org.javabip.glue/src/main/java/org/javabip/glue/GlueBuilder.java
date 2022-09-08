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
package org.javabip.glue;

import org.javabip.annotations.ComponentType;
import org.javabip.api.BIPGlue;
import org.javabip.api.PortBase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Provides functionality to build BIP Glue.
 * 
 */
public abstract class GlueBuilder {

	public GlueBuilder() {
		glue = new BIPGlueImpl();
	}

	BIPGlueImpl glue;

	Class<?> spec;
	String portId;

	Map<String, RequireImpl> requiresMap = new HashMap<String, RequireImpl>();

	Map<String, AcceptImpl> acceptsMap = new HashMap<String, AcceptImpl>();

	/**
	 * Builds the glue using the abstract function configure.
	 * 
	 * @return the BIP glue
	 */
	public BIPGlue build() {

		configure();

		/*
		 * TODO Here some housekeeping can be executed, for example do not allow mutability of Accepts and Requires,
		 * simply keep collecting causes within GlueBuilder and here you can build complete Accepts/Requires with the
		 * help of just constructor.
		 */

		return glue;
	}

	abstract public void configure();

	/**
	 * Creates a port glue item upon which the glue can be built.
	 * 
	 * @param spec
	 *            the component type of the port
	 * @param portId
	 *            the port name
	 * @return the glue builder
	 */
	public GlueBuilder port(Class<?> spec, String portId) {

		if (portId == null)
			throw new IllegalArgumentException("Port name can not be null.");
		if (spec == null)
			throw new IllegalArgumentException("Spec type can not be null");
		if (portId.equals(""))
			throw new IllegalArgumentException("PortId can not be an empty string.");

		this.spec = spec;
		this.portId = portId;

		return this;

	}

	/**
	 * Specifies that the port does not require any other ports in the interaction.
	 */
	public void requiresNothing() {

		if (spec == null)
			throw new IllegalStateException("The port type for which requires nothing is called was not specified.");

		if (portId == null)
			throw new IllegalStateException("The portId for which requires nothing is called was not specified.");

		// empty list of causes.
		ArrayList<PortBase> causes = new ArrayList<PortBase>();

		addRequire(spec, portId, causes);

		spec = null;
		portId = null;

	}

	protected void addRequire(Class<?> spec, String portId, List<PortBase> causes) {

		RequireImpl requires;

		String key = computeKey(spec, portId);

		if (requiresMap.containsKey(key)) {

			requires = requiresMap.get(key);

			requires.addCause(causes);
		} else {

			List<List<PortBase>> causesOptions = new ArrayList<List<PortBase>>();
			causesOptions.add(causes);
			requires = new RequireImpl(new PortBaseImpl(portId, getComponentType(spec)), causesOptions);

			RequireImpl req = glue.addRequire(requires);
			requiresMap.put(key, req);
		}
	}

	protected String getComponentType(Class<?> spec) {

		Annotation classAnnotation = spec.getAnnotation(ComponentType.class);
		// get component type
		if (classAnnotation instanceof ComponentType) {
			ComponentType componentTypeAnnotation = (ComponentType) classAnnotation;
			return componentTypeAnnotation.name();
		}

		return spec.getCanonicalName();

	}

	protected void addAccept(Class<?> spec, String portId, Collection<PortBase> causes) {

		AcceptImpl accepts;

		String key = computeKey(spec, portId);

		if (acceptsMap.containsKey(key)) {

			accepts = acceptsMap.get(key);

			accepts.addCauses(causes);

		} else {

			Set<PortBase> setOfCauses = new LinkedHashSet<PortBase>(causes);

			accepts = new AcceptImpl(new PortBaseImpl(portId, getComponentType(spec)), setOfCauses);

			AcceptImpl acc = glue.addAccept(accepts);
			acceptsMap.put(key, acc);

		}

	}

	/**
	 * Specifies which are the other ports required by the current one.
	 * 
	 * @param args
	 *            the required ports.
	 */
	public void requires(Object... args) {

		try {

			if (spec == null)
				throw new IllegalStateException("The port type for which requires is called was not specified.");

			if (portId == null)
				throw new IllegalStateException("The portId for which requires is called was not specified.");

			if (args.length == 0) {
				requiresNothing();
				return;
			}

			ArrayList<PortBase> causes = parseCauses(spec, args);

			addRequire(spec, portId, causes);

		} finally {

			spec = null;
			portId = null;

		}

	}

	/**
	 * Specifies that the port does not accept any other ports in the interaction.
	 */
	public void acceptsNothing() {

		if (spec == null)
			throw new IllegalStateException("The port type for which accepts nothing is called was not specified.");

		if (portId == null)
			throw new IllegalStateException("The portId for which accepts nothing is called was not specified.");

		// empty list of causes.
		ArrayList<PortBase> causes = new ArrayList<PortBase>();

		addAccept(spec, portId, causes);

		spec = null;
		portId = null;

	}

	/**
	 * Specifies which are the other ports accepted by the current one.
	 * 
	 * @param args
	 *            the accepted ports.
	 */
	public void accepts(Object... args) {

		try {

			if (spec == null)
				throw new IllegalStateException("The port type for which accepts is called was not specified.");

			if (portId == null)
				throw new IllegalStateException("The portId for which accepts is called was not specified.");

			if (args.length == 0) {
				acceptsNothing();
				return;
			}

			ArrayList<PortBase> causes = parseCauses(spec, args);

			addAccept(spec, portId, causes);

		} finally {

			spec = null;
			portId = null;

		}

	}

	protected ArrayList<PortBase> parseCauses(Class<?> currentSpec, Object... args) {

		ArrayList<PortBase> result = new ArrayList<PortBase>();

		for (Object o : args) {

			if (o instanceof Class) {
				currentSpec = (Class<?>) o;
				continue;
			}

			if (o instanceof String) {
				String effectPortId = (String) o;
				result.add(new PortBaseImpl(effectPortId, getComponentType(currentSpec)));
				continue;
			}

			throw new IllegalArgumentException(
					"Any argument within requires function must be either of type Class or String" + o.getClass());

		}

		return result;
	}

	private String computeKey(Class<?> clazz, String portId) {
		return getComponentType(clazz) + "#" + portId;
	}

	/**
	 * Creates a data glue item upon which the glue can be built.
	 * 
	 * @param spec
	 *            the component class
	 * @param dataId
	 *            the data name
	 * @return the data glue builder
	 */
	public DataWireBuilder data(Class<?> spec, String dataId) {

		if (spec == null)
			throw new IllegalArgumentException("Spec type can not be null");
		if (dataId == null)
			throw new IllegalArgumentException("DataId can not be null.");
		if (dataId.equals(""))
			throw new IllegalArgumentException("DataId can not be an empty string.");

		return new DataWireBuilder(new PortBaseImpl(dataId, getComponentType(spec)));

	}

	public class DataWireBuilder {

		PortBase from;

		public DataWireBuilder(PortBase from) {
			this.from = from;
		}

		public void to(Class<?> spec, String dataId) {

			if (dataId == null)
				throw new IllegalArgumentException("DataId can not be null.");
			if (spec == null)
				throw new IllegalArgumentException("Spec type can not be null");
			if (dataId.equals(""))
				throw new IllegalArgumentException("DataId can not be an empty string.");

			glue.addDataWire(new DataWireImpl(from, new PortBaseImpl(dataId, getComponentType(spec)), false));

		}

		public void copyTo(Class<?> spec, String dataId) {

			if (dataId == null)
				throw new IllegalArgumentException("DataId can not be null.");
			if (spec == null)
				throw new IllegalArgumentException("Spec type can not be null");
			if (dataId.equals(""))
				throw new IllegalArgumentException("DataId can not be an empty string.");

			glue.addDataWire(new DataWireImpl(from, new PortBaseImpl(dataId, getComponentType(spec)), true));

		}

	}

	/**
	 * Extracts the BIP Glue from an xml specification file.
	 * 
	 * @param inputStream
	 *            the stream of the xml specification
	 * @return the BIP Glue
	 */
	public static BIPGlue fromXML(InputStream inputStream) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(BIPGlueImpl.class);
			Unmarshaller um = context.createUnmarshaller();
			return (BIPGlueImpl) um.unmarshal(new InputStreamReader(inputStream));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
