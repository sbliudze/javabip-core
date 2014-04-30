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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.bip.api.ComponentProvider;
import org.bip.api.Data;
import org.bip.api.DataOut;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.exceptions.BIPException;

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
	private HashSet<String> states;
	private ArrayList<Guard> guards;
	private Object component;

	private Hashtable<String, Method> dataOutName;
	private ArrayList<DataOut<?>> dataOut;

	public BehaviourBuilder() {
		allTransitions = new ArrayList<TransitionImpl>();
		allPorts = new ArrayList<Port>();
		states = new HashSet<String>();
		guards = new ArrayList<Guard>();
		dataOutName = new Hashtable<String, Method>();
		dataOut = new ArrayList<DataOut<?>>();
	}

	public ExecutableBehaviour build(ComponentProvider provider) throws BIPException {
		ArrayList<Port> componentPorts = new ArrayList<Port>();
		// TODO, Why do we need to recreate ports here? Because there was no provider information within the port.
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
		states.add(state);
	}

	public void addPort(String id, String type, Class<?> specificationType) {
		allPorts.add(new PortImpl(id, type, specificationType));
	}
	
	public void addState(String state) {		
		states.add(state);		
	}
		
	public void addTransitionAndStates(String name, String source, 
			  				  		   String target, String guard, 
			  				  		   Method method) {			
	
			addTransitionAndStates(name, source, target, guard, method, ReflectionHelper.parseDataAnnotations(method));
					
	}
	
	public void addTransitionAndStates(String name, String source, 
									   String target, String guard, 
									   Method method, List<Data<?>> data) {			

		addState(source);
		addState(target);

		allTransitions.add( new TransitionImpl(name, source, target, guard, method, data) );


	}

	public void addTransition(String name, String source, 
	  		   				  String target, String guard, 
	  		   				  Method method) {			

		addTransition(name, source, target, guard, method, ReflectionHelper.parseDataAnnotations(method));

	}

	public void addTransition(String name, String source, 
			   				  String target, String guard, 
			   				  Method method, List<Data<?>> data) {			

		if (!states.contains(source))
			throw new BIPException("Transition " + name + " is specifying source state " + source + " that has not been explicitly stated before.");

		if (!states.contains(target))
			throw new BIPException("Transition " + name + " is specifying target state " + target + " that has not been explicitly stated before.");

		addState(source);
		addState(target);

		allTransitions.add( new TransitionImpl(name, source, target, guard, method, data) );

	}	

	/**
	 * It adds the guard by providing directly the method parameter. The method 
	 * parameter needs to be annotated to convey information about the data required by the method.
	 * 
	 * This function is left for the user convenience so the annotations can still be used to specify data.
	 *  
	 * @param name name of the guard
	 * @param method the method that is invoked to compute given guard.
	 */
	public void addGuard(String name, Method method) {		
		addGuard(name, method, ReflectionHelper.parseDataAnnotations(method));		
	}
	
	public void addGuard(String name, Method method, List<Data<?>> data) {		
		guards.add(new GuardImpl(name, method, data));		
	}

	public void addDataOut(Method method) {		
		addDataOut(ReflectionHelper.parseReturnDataAnnotation(method), method);						
	}

	public void addDataOut(DataOut<?> data, Method method) {
		dataOut.add(data);
		dataOutName.put(data.name(), method);		
	}
	
}
