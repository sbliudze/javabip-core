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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bip.api.ComponentProvider;
import org.bip.api.Data;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortType;
import org.bip.exceptions.BIPException;

// TODO DESCRIPTION all classes should have a header and description of its purpose for nice looking JavaDoc document.
// For example check BIPSpecification for an example.

// TODO, EXTENSION autoboxing between for example int and Integer may be a good feature to help wire data from multiple components.
/**
 * Gathers all the information to build a behaviour of a component. There are two ways to build it: with data and without data.
 */
public class BehaviourBuilder {

	// private Logger logger = LoggerFactory.getLogger(BehaviourBuilder.class);

	public String componentType;
	public String currentState;
	private ArrayList<TransitionImpl> allTransitions;
	private Hashtable<String, Port> allPorts;
	private HashSet<String> states;
	private Hashtable<String, Guard> guards;
	private Object component;

	private Hashtable<String, Method> dataOutName;
	private ArrayList<DataOutImpl<?>> dataOut;

	public BehaviourBuilder() {
		allTransitions = new ArrayList<TransitionImpl>();
		allPorts = new Hashtable<String, Port>();
		states = new HashSet<String>();
		guards = new Hashtable<String, Guard>();
		dataOutName = new Hashtable<String, Method>();
		dataOut = new ArrayList<DataOutImpl<?>>();
	}

	public ExecutableBehaviour build(ComponentProvider provider) throws BIPException {
		
		if (componentType == null || componentType.isEmpty()) {
			throw new NullPointerException("Component type for object " + component + " cannot be null or empty.");
		}	
		if (currentState == null || currentState.isEmpty()) {
			throw new NullPointerException("The initial state of the component of type " + componentType + " cannot be null or empty.");
		}
		if (allTransitions == null || allTransitions.isEmpty()) {
			throw new BIPException("List of transitions in component of type " + componentType + " cannot be null or empty.");
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
		// We need to create new ports here as there was no provider information available when the specification was parsed.
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
		
		return new BehaviourImpl(componentType, currentState, transformIntoExecutableTransition(), 
								 componentPorts, states, guards.values(), dataOut, dataOutName, component);
	}
	
	private ArrayList<ExecutableTransition> transformIntoExecutableTransition() {

		HashMap<String, Port> mapIdToPort = new HashMap<String, Port>( );
		for (Port port : allPorts.values())
			mapIdToPort.put(port.getId(), port);
		
		// Transform transitions into ExecutableTransitions.
		ArrayList<ExecutableTransition> transformedAllTransitions = new ArrayList<ExecutableTransition>();
		for (TransitionImpl transition : allTransitions) {
			
			// TODO DESIGN, what are exactly different ways of specifying that the port is internal. We need to be specific about it in spec.
			if (transition.name().equals("") ) {
				transformedAllTransitions.add( new ExecutableTransitionImpl(transition, PortType.internal, guards) );
				continue;
			}
			
			PortType transitionPortType = mapIdToPort.get(transition.name()).getType();
			
			transformedAllTransitions.add( new ExecutableTransitionImpl(transition, transitionPortType, guards) );
		}
		
		return transformedAllTransitions;
		
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

	public void addPort(String id, PortType type, Class<?> specificationType) {
		Port port = new PortImpl(id, type, specificationType);
		// PortImpl constructor already protects against null id.
		if (allPorts.containsKey(id))
			throw new BIPException("Port with id " + id + " has been already defined.");
		allPorts.put(id, port);
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

		if (!allPorts.containsKey(name)) {
			if (name == null)
				throw new BIPException("Transition name can not be null, use empty empty string for internal transitions");
			if (!name.isEmpty())
				throw new BIPException("Transition " + name + " does not correspond to any port. Specify ports first and/or make sure the names match. ");
		}

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

		if (!allPorts.containsKey(name)) {
			if (name == null)
				throw new BIPException("Transition name can not be null, use empty empty string for internal transitions");
			if (!name.isEmpty())
				throw new BIPException("Transition " + name + " does not correspond to any port. Specify ports first and/or make sure the names match. ");
		}
		
		if (!states.contains(source))
			throw new BIPException("Transition " + name + " is specifying source state " + source + " that has not been explicitly stated before.");

		if (!states.contains(target))
			throw new BIPException("Transition " + name + " is specifying target state " + target + " that has not been explicitly stated before.");

		allTransitions.add( new TransitionImpl(name, source, target, guard, method, data) );

	}	

	/**
	 * It add a guard based on the provided method with the guard name equal to method name.
	 * @param method
	 */
	public void addGuard(Method method) {
		addGuard(method.getName(), method);
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
		guards.put(name, new GuardImpl(name, method, data));		
	}

	public void addDataOut(Method method) {		
		
		DataOutImpl<?> data = ReflectionHelper.parseReturnDataAnnotation(method); 
		dataOut.add( data );
		dataOutName.put(data.name(), method);
								
	}

	public void addDataOut(Method method, org.bip.annotations.Data annotation) {		
		
		DataOutImpl<?> data = ReflectionHelper.parseReturnDataAnnotation(method, annotation); 
		dataOut.add( data );
		dataOutName.put(data.name(), method);
								
	}
	
	
}
