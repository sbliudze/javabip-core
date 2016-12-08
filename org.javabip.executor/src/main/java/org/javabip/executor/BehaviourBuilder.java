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
package org.javabip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.javabip.api.ComponentProvider;
import org.javabip.api.Data;
import org.javabip.api.ExecutableBehaviour;
import org.javabip.api.Guard;
import org.javabip.api.Port;
import org.javabip.api.PortType;
import org.javabip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO, EXTENSION autoboxing between for example int and Integer may be a good feature to help wire data from multiple components.

/**
 * Gathers all the information to build the behaviour for a BIP component. There are two ways to build it: with data and
 * without data.
 * 
 * @author Alina Zolotukhina
 * 
 */
public class BehaviourBuilder {

	private Logger logger = LoggerFactory.getLogger(BehaviourBuilder.class);

	public String componentType;
	public String currentState;
	private ArrayList<TransitionImpl> allTransitions;
	private Hashtable<String, Port> allPorts;
	private HashSet<String> states;
	private Hashtable<String, Guard> guards;
	private Object component;

	private Hashtable<String, MethodHandle> dataOutName;
	private ArrayList<DataOutImpl<?>> dataOut;

	public BehaviourBuilder(Object component) {
		this.component = component;
		allTransitions = new ArrayList<TransitionImpl>();
		allPorts = new Hashtable<String, Port>();
		states = new HashSet<String>();
		guards = new Hashtable<String, Guard>();
		dataOutName = new Hashtable<String, MethodHandle>();
		dataOut = new ArrayList<DataOutImpl<?>>();
	}

	/**
	 * Builds the behaviour basing on previously collected information.
	 * 
	 * @param provider
	 *            the component provided for the ports
	 * @return a new behaviour instance
	 * @throws BIPException
	 */
	public ExecutableBehaviour build(ComponentProvider provider) throws BIPException {

		if (componentType == null || componentType.isEmpty()) {
			throw new NullPointerException("Component type for object " + component + " cannot be null or empty.");
		}
		if (currentState == null || currentState.isEmpty()) {
			throw new NullPointerException("The initial state of the component of type " + componentType
					+ " cannot be null or empty.");
		}
		if (allTransitions == null || allTransitions.isEmpty()) {
			throw new BIPException("List of transitions in component of type " + componentType
					+ " cannot be null or empty.");
		}
		if (states == null || states.isEmpty()) {
			throw new BIPException("List of states in component of type " + componentType + " cannot be null or empty.");
		}
		if (allPorts == null || allPorts.isEmpty()) {
			throw new BIPException("List of states in component of type " + componentType + " cannot be null or empty.");
		}
		if (component == null) {
			throw new NullPointerException("The component object of type " + componentType + " cannot be null.");
		}

		ArrayList<Port> componentPorts = new ArrayList<Port>();
		// We need to create new ports here as there was no provider information available when the specification was
		// parsed.
		for (Port port : this.allPorts.values()) {
			componentPorts.add(new PortImpl(port.getId(), port.getType(), port.getSpecType(), provider));
		}

		Map<String, Port> allEnforceablePorts = new HashMap<String, Port>();
		for (Port port : componentPorts) {
			if (port.getType().equals(PortType.enforceable))
				allEnforceablePorts.put(port.getId(), port);
		}

		for (DataOutImpl<?> data : dataOut) {
			data.computeAllowedPort(allEnforceablePorts);
		}

		return new BehaviourImpl(componentType, currentState, transformIntoExecutableTransition(), componentPorts,
				states, guards.values(), dataOut, dataOutName, component);
	}

	private ArrayList<ExecutableTransition> transformIntoExecutableTransition() {

		HashMap<String, Port> mapIdToPort = new HashMap<String, Port>();
		for (Port port : allPorts.values())
			mapIdToPort.put(port.getId(), port);

		// Transform transitions into ExecutableTransitions.
		ArrayList<ExecutableTransition> transformedAllTransitions = new ArrayList<ExecutableTransition>();
		for (TransitionImpl transition : allTransitions) {

			// TODO DESIGN, what are exactly different ways of specifying that the port is internal. We need to be
			// specific about it in spec.
			if (transition.name().equals("")) {
				transformedAllTransitions.add(new ExecutableTransitionImpl(transition, PortType.internal, guards));
				continue;
			}

			PortType transitionPortType = mapIdToPort.get(transition.name()).getType();

			transformedAllTransitions.add(new ExecutableTransitionImpl(transition, transitionPortType, guards));
		}

		return transformedAllTransitions;

	}

	/**
	 * Sets the BIP component type
	 * 
	 * @param type
	 *            the type of the component
	 */
	public void setComponentType(String type) {
		this.componentType = type;
	}

	/**
	 * Sets the initial state of the component.
	 * 
	 * @param state
	 *            the initial state
	 */
	public void setInitialState(String state) {
		this.currentState = state;
		states.add(state);
	}

	/**
	 * Adds a new port.
	 * 
	 * @param id
	 *            the port name
	 * @param type
	 *            the port type (enforceable or spontaneous)
	 * @param specificationType
	 *            the component type to which the port belongs
	 */
	public void addPort(String id, PortType type, Class<?> specificationType) {
		Port port = new PortImpl(id, type, specificationType);
		// PortImpl constructor already protects against null id.
		if (allPorts.containsKey(id))
			throw new BIPException("Port with id " + id + " has been already defined.");
		allPorts.put(id, port);
	}

	/**
	 * Adds a new port.
	 * 
	 * @param id
	 *            the port name
	 * @param type
	 *            the port type (enforceable or spontaneous)
	 * @param specType
	 *            the component type to which the port belongs
	 */
	public void addPort(String id, PortType type, String specType) {
		Port port = new PortImpl(id, type, specType);
		// PortImpl constructor already protects against null id.
		if (allPorts.containsKey(id))
			throw new BIPException("Port with id " + id + " has been already defined.");
		allPorts.put(id, port);
	}

	/**
	 * Adds a new state.
	 * 
	 * @param state
	 *            the state to add
	 */
	public void addState(String state) {
		states.add(state);
	}

	/**
	 * Adds a transition without data and its source and target states.
	 * 
	 * @param name
	 *            transition name
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @param guard
	 *            the guard expression
	 * @param method
	 *            the method representing the transition
	 */
	public void addTransitionAndStates(String name, String source, String target, String guard, Method method) {

		addTransitionAndStates(name, source, target, guard, method, ReflectionHelper.parseDataAnnotations(method));

	}

	/**
	 * Adds a transition requiring data and its source and target states.
	 * 
	 * @param name
	 *            transition name
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @param guard
	 *            the guard expression
	 * @param method
	 *            the method representing the transition
	 * @param data
	 *            the list of data required by the transition
	 */
	public void addTransitionAndStates(String name, String source, String target, String guard, Method method,
			List<Data<?>> data) {

		if (!allPorts.containsKey(name)) {
			if (name == null)
				throw new BIPException(
						"Transition name can not be null, use empty empty string for internal transitions");
			if (!name.isEmpty())
				throw new BIPException("In component " + this.componentType + " transition " + name
						+ " does not correspond to any port. Specify ports first and/or make sure the names match. ");
		}

		addState(source);
		addState(target);

		allTransitions.add(new TransitionImpl(name, source, target, guard, method, data));
	}

	/**
	 * Allows to add a new transition which does not require data.
	 * 
	 * @param name
	 *            transition name
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @param guard
	 *            the guard expression
	 * @param method
	 *            the method representing the transition
	 */
	public void addTransition(String name, String source, String target, String guard, Method method) {

		addTransition(name, source, target, guard, method, ReflectionHelper.parseDataAnnotations(method));
	}

	/**
	 * Allows to add a new transition which requires data.
	 * 
	 * @param name
	 *            transition name
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @param guard
	 *            the guard expression
	 * @param method
	 *            the method representing the transition
	 * @param data
	 *            the list of data required by the transition
	 */
	public void addTransition(String name, String source, String target, String guard, Method method, List<Data<?>> data) {

		if (!allPorts.containsKey(name)) {
			if (name == null)
				throw new BIPException(
						"Transition name can not be null, use empty empty string for internal transitions");
			if (!name.isEmpty())
				throw new BIPException("Transition " + name
						+ " does not correspond to any port. Specify ports first and/or make sure the names match. ");
		}

		if (!states.contains(source))
			throw new BIPException("Transition " + name + " is specifying source state " + source
					+ " that has not been explicitly stated before.");

		if (!states.contains(target))
			throw new BIPException("Transition " + name + " is specifying target state " + target
					+ " that has not been explicitly stated before.");

		allTransitions.add(new TransitionImpl(name, source, target, guard, method, data));
	}

	/**
	 * It adds a guard based on the provided method with the guard name equal to method name.
	 * 
	 * @param method
	 *            the method the guard represents
	 */
	public void addGuard(Method method) {
		addGuard(method.getName(), method);
	}

	/**
	 * It adds the guard by providing directly the method parameter. The method parameter needs to be annotated to
	 * convey information about the data required by the method.
	 * 
	 * This function is left for the user convenience so the annotations can still be used to specify data.
	 * 
	 * @param name
	 *            name of the guard
	 * @param method
	 *            the method that is invoked to compute given guard
	 */
	public void addGuard(String name, Method method) {
		addGuard(name, method, ReflectionHelper.parseDataAnnotations(method));
	}

	/**
	 * Allows to add a new guard
	 * 
	 * @param name
	 *            the name of the guard
	 * @param method
	 *            the method the guard represents
	 * @param data
	 *            the list of data required for the guard to be computed
	 */
	public void addGuard(String name, Method method, List<Data<?>> data) {
		guards.put(name, new GuardImpl(name, method, data));
	}

	/**
	 * Allows to add a new output data given the method providing it. This function can be called by a class which
	 * creates its own executable behaviour.
	 * 
	 * @param method
	 *            an annotated method providing the output data
	 */
	public void addDataOut(Method method) {

		DataOutImpl<?> data = ReflectionHelper.parseReturnDataAnnotation(method);
		dataOut.add(data);
		dataOutName.put(data.name(), getMethodHandleFromMethod(method));
	}

	/**
	 * Allows to add a new output data given the method providing it and its annotation.
	 * 
	 * @param method
	 *            the method providing the data
	 * @param annotation
	 *            the data annotation
	 */
	public void addDataOut(Method method, org.javabip.annotations.Data annotation) {

		DataOutImpl<?> data = ReflectionHelper.parseReturnDataAnnotation(method, annotation);
		dataOut.add(data);
		dataOutName.put(data.name(), getMethodHandleFromMethod(method));

	}

	private MethodHandle getMethodHandleFromMethod(Method method) {
		MethodType methodType;
		MethodHandle methodHandle = null;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
		try {
			methodHandle = lookup.findVirtual(component.getClass(), method.getName(), methodType);
		} catch (NoSuchMethodException e) {
			ExceptionHelper.printExceptionTrace(logger, e, "When building behaviour for component " + componentType);
		} catch (IllegalAccessException e) {
			ExceptionHelper.printExceptionTrace(logger, e, "When building behaviour for component " + componentType);
		}
		return methodHandle;
	}

}
