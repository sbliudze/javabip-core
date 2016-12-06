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
package org.bip.spec.seal;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.annotations.Transitions;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The sealable data writer can change the sealable data (until it is sealed).
 * 
 */
@Ports({ @Port(name = "write", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableDataWriter")
public class SealableDataWriter<T> {

	Logger logger = LoggerFactory.getLogger(SealableDataWriter.class);

	private T data;
	public int noOfTransitions = 0;

	public SealableDataWriter(T data) {
		this.data = data;
	}

	@Data(name = "value")
	public T getData() {
		return data;
	}

	// TODO: change to only one transition when dynamicity is added. And remove component when it
	// reaches its final state.
	@Transitions({ @Transition(name = "write", source = "written", target = "written"),
			@Transition(name = "write", source = "initial", target = "written") })
	public void write() {
		logger.debug("Writing data {}", this.data);
		noOfTransitions++;
	}

}
