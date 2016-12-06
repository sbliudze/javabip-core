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
package org.bip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Map;

import org.bip.api.Data;
import org.bip.api.PortType;
import org.bip.api.Transition;

/**
 * This interface specifies a transition which can be executed.
 * 
 * @author Alina Zolotukhina
 * 
 */
interface ExecutableTransition extends Transition {

	/**
	 * Return the method associated to transition.
	 * 
	 * @return the method associated to transition.
	 */
	public Method method();

	/**
	 * Returns the method handle associated to transition.
	 * 
	 * @return the method handle associated to transition.
	 */
	public MethodHandle methodHandle();

	/**
	 * Provides a list of parameters in the transition method signature (required data).
	 * 
	 * @return a list of data items that are required by the transition
	 */
	public Iterable<Data<?>> dataRequired();

	/**
	 * Defines if the transition requires data for guard computation.
	 * 
	 * @return true, if the transition requires data for the guards
	 */
	public boolean hasDataOnGuards();

	/**
	 * Defines if the transition requires data for the execution.
	 * 
	 * @return true, if the transition requires data for the execution
	 */
	public boolean hasData();

	/**
	 * Defines if the transition guard is true provided the guard valuations.
	 * 
	 * @param guardToValue
	 *            the mapping between the guard name and its boolean value
	 * @return true, if the guard is true
	 */
	public boolean guardIsTrue(Map<String, Boolean> guardToValue);

	/**
	 * Gets the type of the port this transition is associated to.
	 * 
	 * @return the port type
	 */
	public PortType getType();

}
