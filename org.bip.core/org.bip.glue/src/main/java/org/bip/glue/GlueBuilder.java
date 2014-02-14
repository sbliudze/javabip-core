package org.bip.glue;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.bip.api.BIPGlue;
import org.bip.api.Port;
import org.bip.impl.PortImpl;

public abstract class GlueBuilder {

	public GlueBuilder() {
		glue = new BIPGlueImpl();
	}

	BIPGlueImpl glue;

	Class<?> spec;
	String portId;

	Map<String, RequiresImpl> requiresMap = new HashMap<String, RequiresImpl>();

	Map<String, AcceptsImpl> acceptsMap = new HashMap<String, AcceptsImpl>();

	public BIPGlue build() {

		configure();

		return glue;
	}

	abstract public void configure();

	public GlueBuilder port(Class<?> spec, String portId) {

		if (portId == null)
			throw new IllegalArgumentException("Port name can not be null.");
		if (spec == null)
			throw new IllegalArgumentException("Spec type can not be null");
		if (portId.equals(""))
			throw new IllegalArgumentException(
					"PortId can not be an empty string.");

		this.spec = spec;
		this.portId = portId;

		return this;

	}

	public void requiresNothing() {

		if (spec == null)
			throw new IllegalStateException(
					"The port type for which requires nothing is called was not specified.");

		if (portId == null)
			throw new IllegalStateException(
					"The portId for which requires nothing is called was not specified.");

		// empty list of causes.
		ArrayList<Port> causes = new ArrayList<Port>();

		addRequire(spec, portId, causes);

		spec = null;
		portId = null;

	}

	private void addRequire(Class<?> spec, String portId, List<Port> causes) {

		RequiresImpl requires;

		String key = computeKey(spec, portId);

		if (requiresMap.containsKey(key)) {

			requires = requiresMap.get(key);

			requires.addCause(causes);
		} else {

			List<List<Port>> causesOptions = new ArrayList<List<Port>>();
			causesOptions.add(causes);
			requires = new RequiresImpl(new PortImpl(portId, spec),
					causesOptions);

			RequiresImpl req = glue.addRequire(requires);
			requiresMap.put(key, req);
		}
	}

	private void addAccept(Class<?> spec, String portId, Collection<Port> causes) {

		AcceptsImpl accepts;

		String key = computeKey(spec, portId);

		if (acceptsMap.containsKey(key)) {

			accepts = acceptsMap.get(key);

			accepts.addCauses(causes);

		} else {

			Set<Port> setOfCauses = new LinkedHashSet<Port>(causes);

			accepts = new AcceptsImpl(new PortImpl(portId, spec), setOfCauses);

			AcceptsImpl acc = glue.addAccept(accepts);
			acceptsMap.put(key, acc);

		}

	}

	public void requires(Object... args) {

		try {

			if (spec == null)
				throw new IllegalStateException(
						"The port type for which requires is called was not specified.");

			if (portId == null)
				throw new IllegalStateException(
						"The portId for which requires is called was not specified.");

			if (args.length == 0) {
				requiresNothing();
				return;
			}

			ArrayList<Port> causes = parseCauses(spec, args);

			addRequire(spec, portId, causes);

		} finally {

			spec = null;
			portId = null;

		}

	}

	public void acceptsNothing() {

		if (spec == null)
			throw new IllegalStateException(
					"The port type for which accepts nothing is called was not specified.");

		if (portId == null)
			throw new IllegalStateException(
					"The portId for which accepts nothing is called was not specified.");

		// empty list of causes.
		ArrayList<Port> causes = new ArrayList<Port>();

		addAccept(spec, portId, causes);

		spec = null;
		portId = null;

	}

	public void accepts(Object... args) {

		try {

			if (spec == null)
				throw new IllegalStateException(
						"The port type for which accepts is called was not specified.");

			if (portId == null)
				throw new IllegalStateException(
						"The portId for which accepts is called was not specified.");

			if (args.length == 0) {
				acceptsNothing();
				return;
			}

			ArrayList<Port> causes = parseCauses(spec, args);

			addAccept(spec, portId, causes);

		} finally {

			spec = null;
			portId = null;

		}

	}

	private ArrayList<Port> parseCauses(Class<?> currentSpec, Object... args) {

		ArrayList<Port> result = new ArrayList<Port>();

		for (Object o : args) {

			if (o instanceof Class) {
				currentSpec = (Class<?>) o;
				continue;
			}

			if (o instanceof String) {
				String effectPortId = (String) o;
				result.add(new PortImpl(effectPortId, currentSpec));
				continue;
			}

			throw new IllegalArgumentException(
					"Any argument within requires function must be either of type Class or String"
							+ o.getClass());

		}

		return result;
	}

	private String computeKey(Class<?> clazz, String portId) {
		return clazz.getCanonicalName() + "#" + portId;
	}

	public DataWireBuilder data(Class<?> spec, String dataId) {

		if (spec == null)
			throw new IllegalArgumentException("Spec type can not be null");
		if (dataId == null)
			throw new IllegalArgumentException("DataId can not be null.");
		if (dataId.equals(""))
			throw new IllegalArgumentException(
					"DataId can not be an empty string.");

		return new DataWireBuilder(new PortImpl(dataId, spec));

	}

	public class DataWireBuilder {

		Port from;

		public DataWireBuilder(Port from) {
			this.from = from;
		}

		public void to(Class<?> spec, String dataId) {

			if (dataId == null)
				throw new IllegalArgumentException("DataId can not be null.");
			if (spec == null)
				throw new IllegalArgumentException("Spec type can not be null");
			if (dataId.equals(""))
				throw new IllegalArgumentException(
						"DataId can not be an empty string.");

			glue.addDataWire(new DataWireImpl(from, new PortImpl(dataId, spec)));

		}

	}

	public static BIPGlue fromXML(InputStream inputStream) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(BIPGlueImpl.class);
			Unmarshaller um = context.createUnmarshaller();
			return (BIPGlueImpl) um
					.unmarshal(new InputStreamReader(inputStream));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
