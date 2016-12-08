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
package org.javabip.spec.seal;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.annotations.Transitions;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The sealable data can be set and sealed. The first time the data is read, it becomes sealed. Once it is sealed, it
 * cannot be changed anymore.
 * 
 */
@Ports({ @Port(name = "set", type = PortType.enforceable), @Port(name = "get", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableData")
public class SealableData<T> {

	Logger logger = LoggerFactory.getLogger(SealableData.class);

	private T data;
	private boolean sealed = false;

	public int noOfTransitions = 0;

	public SealableData(T data) {
		this.data = data;
	}

	public SealableData() {
	}

	@Transitions({ @Transition(name = "set", source = "set", target = "set", guard = "!isSealed & isSet"),
			@Transition(name = "set", source = "initial", target = "set", guard = "!isSet") })
	public void set(@Data(name = "input") T data) {

		logger.debug("Previous data {}, Current data: {}", this.data, data);
		this.data = data;
		noOfTransitions++;

	}

	@Data(name = "value", accessTypePort = AccessType.allowed, ports = { "get" })
	public T get() {
		return data;
	}

	@Transitions({ @Transition(name = "get", source = "initial", target = "sealed", guard = "isSet"),
			@Transition(name = "get", source = "set", target = "sealed", guard = "isSet"),
			@Transition(name = "get", source = "sealed", target = "sealed") })
	public void getTransition() {
		if (!sealed) {
			logger.debug("First get executed thus the data {} is being sealed.", data);
			sealed = true;
		}
	}

	@Transition(name = "", source = "initial", target = "set", guard = "isSet")
	public void afterPropertiesSet() {
		logger.debug("Data has been provided by the constructor so transitioning into set state internally");
	}

	@Guard(name = "isSealed")
	public boolean isSealed() {
		return sealed;
	}

	@Guard(name = "isSet")
	public boolean isSet() {
		return data != null;
	}

}
