/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */
package org.bip.executor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import org.bip.api.ComponentProvider;
import org.bip.api.DataOut;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.exceptions.BIPException;
import org.bip.impl.PortImpl;
import org.bip.impl.TransitionImpl;

// TODO, all classes should have a header and description of its purpose for nice looking JavaDoc document.
// For example check BIPSpecification for an example.

/**
 * Gathers all the information to build a behaviour of a component. There are two ways to build it: with data and without data.
 */
public class BehaviourBuilder {

	// private Logger logger = LoggerFactory.getLogger(BehaviourBuilder.class);

	public String componentType;
	public String currentState;
	private ArrayList<TransitionImpl> allTransitions;
	private ArrayList<Port> allPorts;
	private ArrayList<String> states;
	private ArrayList<Guard> guards;
	private Object component;

	private Hashtable<String, Method> dataOutName;
	private ArrayList<DataOut<?>> dataOut;

	public BehaviourBuilder(String type, String currentState, ArrayList<TransitionImpl> allTransitions, ArrayList<Port> allPorts, ArrayList<String> states, ArrayList<Guard> guards,
			Object component) {
		this.componentType = type;
		this.currentState = currentState;
		this.allTransitions = allTransitions;
		this.allPorts = allPorts;
		this.states = states;
		this.guards = guards;
		this.component = component;
	}

	public BehaviourBuilder(String type, String currentState, ArrayList<TransitionImpl> allTransitions, ArrayList<Port> allPorts, ArrayList<String> states, ArrayList<Guard> guards,
			Hashtable<String, Method> dataOutName, ArrayList<DataOut<?>> dataOut, Object component) {
		this(type, currentState, allTransitions, allPorts, states, guards, component);
		this.dataOut = dataOut;
		this.dataOutName = dataOutName;
	}

	public BehaviourBuilder() {
		allTransitions = new ArrayList<TransitionImpl>();
		allPorts = new ArrayList<Port>();
		states = new ArrayList<String>();
		guards = new ArrayList<Guard>();
		dataOutName = new Hashtable<String, Method>();
		dataOut = new ArrayList<DataOut<?>>();
	}

	public ExecutableBehaviour buildWithoutData(ComponentProvider provider) throws BIPException {
		ArrayList<Port> componentPorts = new ArrayList<Port>();
		for (Port port : this.allPorts) {
			componentPorts.add(new PortImpl(port.getId(), port.getType().toString(), port.getSpecType(), provider));
		}
		return new BehaviourImpl(componentType, currentState, allTransitions, componentPorts, states, guards, component);
	}

	public ExecutableBehaviour buildWithData(ComponentProvider provider) throws BIPException {
		ArrayList<Port> componentPorts = new ArrayList<Port>();
		for (Port port : this.allPorts) {
			componentPorts.add(new PortImpl(port.getId(), port.getType().toString(), port.getSpecType(), provider));
		}
		return new BehaviourImpl(componentType, currentState, allTransitions, componentPorts, states, guards, dataOut, dataOutName, component);
	}

	public void setComponentType(String type) {
		this.componentType = type;
	}

	public void setComponent(Object component) {
		this.component = component;
	}

	public void setInitialState(String state) {
		this.currentState = state;
	}

	public void addTransition(TransitionImpl transition) {
		allTransitions.add(transition);

		// update the states list
		if (!states.contains(transition.source())) {
			states.add(transition.source());
		}
		if (!states.contains(transition.target())) {
			states.add(transition.target());
		}
	}

	public void addPort(Port port) {
		allPorts.add(port);
	}

	public void addGuard(Guard guard) {
		guards.add(guard);
	}

	public void addDataOut(DataOut<?> data, Method method) {
		dataOutName.put(data.name(), method);
		dataOut.add(data);
	}
}
