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

import javafx.util.Pair;
import org.javabip.api.PortBase;

import java.util.ArrayList;
//import java.util.stream.Collectors;

/**
 * Glue builder allowing to create synchronous connections between ports.
 * 
 */
public abstract class ManySynchronGlueBuilder extends GlueBuilder {

	public ManySynchronGlueBuilder() {
		super();
	}

	public void synchrons(Object... args) {

		ArrayList<Pair<Class<?>, String>> allPorts = new ArrayList<Pair<Class<?>, String>>();
		ArrayList<PortBase> allCauses = new ArrayList<PortBase>();

		Class<?> currentSpec = null;
		for (Object o : args) {

			if (o instanceof Class) {
				currentSpec = (Class<?>) o;
				continue;
			}

			if (o instanceof String) {
				String effectPortId = (String) o;
				allPorts.add(new Pair(currentSpec, effectPortId));
				continue;
			}

			throw new IllegalArgumentException(
					"Any argument within requires function must be either of type Class or String" + o.getClass());

		}

		for (Pair<Class<?>, String> onePort : allPorts) {
			allCauses.add(new PortBaseImpl(onePort.getValue(), getComponentType(onePort.getKey())));
		}

		for (Pair<Class<?>, String> effect : allPorts) {

			ArrayList<PortBase> causes = (ArrayList<PortBase>) allCauses.clone();
			causes.remove(new PortBaseImpl(effect.getValue(), getComponentType(effect.getKey())));

			addRequire (effect.getKey(), effect.getValue(), causes);
			addAccept (effect.getKey(), effect.getValue(), causes);
		}

	}
}
