/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */
package org.bip.executor;

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

import org.bip.api.ComponentProvider;
import org.bip.api.Data;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortType;
import org.bip.api.ResourceType;
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
	private Hashtable<String, MethodHandle> dataOutName2;
	private ArrayList<DataOutImpl<?>> dataOut;
	private ArrayList<ResourceReqImpl> resources;

	private Hashtable<TransitionImpl,  ArrayList<ResourceReqImpl>> transitionResources;
	private Hashtable<TransitionImpl, String> transitionRequest;
	//helper map in needed to construct resources to transition map
	private Hashtable<Method, ArrayList<ResourceReqImpl>> methodResources;
	//helper map in needed to construct transition to utility map
	private Hashtable<Method, String> methodUtility;
	//helper map to construct resource to transition map
	private Hashtable<Method, TransitionImpl> methodToTransition;
	

	public BehaviourBuilder(Object component) {
		this.component = component;
		allTransitions = new ArrayList<TransitionImpl>();
		allPorts = new Hashtable<String, Port>();
		states = new HashSet<String>();
		guards = new Hashtable<String, Guard>();
		dataOutName = new Hashtable<String, Method>();
		dataOutName2 = new Hashtable<String, MethodHandle>();
		dataOut = new ArrayList<DataOutImpl<?>>();
		resources = new ArrayList<ResourceReqImpl>();
		transitionResources = new Hashtable<TransitionImpl,  ArrayList<ResourceReqImpl>>();
		transitionRequest = new Hashtable<TransitionImpl, String>();
		methodResources = new Hashtable<Method, ArrayList<ResourceReqImpl>>();
		methodUtility = new Hashtable<Method, String>();
		methodToTransition = new Hashtable<Method, TransitionImpl>();
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
		
		for (Method method : methodResources.keySet()) {
			transitionResources.put(methodToTransition.get(method), methodResources.get(method));
			transitionRequest.put(methodToTransition.get(method), methodUtility.get(method));
		}
		if (methodResources.size() != methodUtility.size()) {
			throw new BIPException("There is a transition where either the required resources or the utility function is not specified");
		}
		
		if (!resources.isEmpty())
		{
			return new BehaviourImpl(componentType, currentState, transformIntoExecutableTransition(), 
					 componentPorts, states, guards.values(), dataOut, dataOutName, dataOutName2, component,
					 transitionResources, transitionRequest);
		}
		
		return new BehaviourImpl(componentType, currentState, transformIntoExecutableTransition(), 
								 componentPorts, states, guards.values(), dataOut, dataOutName, dataOutName2, component);
	}
	
//	public ExecutableBehaviour build2(ComponentProvider provider) throws BIPException {
//		
//		if (componentType == null || componentType.isEmpty()) {
//			throw new NullPointerException("Component type for object " + component + " cannot be null or empty.");
//		}	
//		if (currentState == null || currentState.isEmpty()) {
//			throw new NullPointerException("The initial state of the component of type " + componentType + " cannot be null or empty.");
//		}
//		if (allTransitions == null || allTransitions.isEmpty()) {
//			throw new BIPException("List of transitions in component of type " + componentType + " cannot be null or empty.");
//		}
//		if (states == null || states.isEmpty()) {
//			throw new BIPException("List of states in component of type " + componentType + " cannot be null or empty.");
//		}
//		if (allPorts == null || allPorts.isEmpty()) {
//			throw new BIPException("List of states in component of type " + componentType + " cannot be null or empty.");
//		}
//		if (component == null) {
//			throw new NullPointerException("The component object of type " + componentType + " cannot be null.");
//		}
//
//		ArrayList<Port> componentPorts = new ArrayList<Port>();
//		// We need to create new ports here as there was no provider information available when the specification was parsed.
//		for (Port port : this.allPorts.values()) {
//			componentPorts.add(new PortImpl(port.getId(), port.getType(), port.getSpecType(), provider));
//		}
//
//		Map<String, Port> allEnforceablePorts = new HashMap<String, Port>();
//		for (Port port : componentPorts) {
//			if (port.getType().equals(PortType.enforceable))
//				allEnforceablePorts.put(port.getId(), port);
//		}
//		
//		for (DataOutImpl<?> data : dataOut) {
//			data.computeAllowedPort(allEnforceablePorts);
//		}
//		
//		return new BehaviourImpl(componentType, currentState, transformIntoExecutableTransition(), transformIntoExecutableTransition2(), 
//								 componentPorts, states, guards.values(), dataOut, dataOutName, component);
//	}
	
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
	

	public void addPort(String id, PortType type, String specType) {
		Port port = new PortImpl(id, type, specType);
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
				throw new BIPException("In component " + this.componentType + " transition " + name + " does not correspond to any port. Specify ports first and/or make sure the names match. ");
		}

		addState(source);
		addState(target);

		TransitionImpl t = new TransitionImpl(name, source, target, guard, method, data) ;
		methodToTransition.put(method, t);
		allTransitions.add( t );
		//allTransitions2.add( new TransitionImpl2(name, source, target, guard, getMethodHandleForTransition(method), data) );
	}
	

	private MethodHandle getMethodHandleForTransition(Method method) {
		MethodType methodType;
		MethodHandle methodHandle = null;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());// clazz - type of data being returned, method has no arguments

		// lookup the method by its name - we do not need to do it here, we need to do it beforehand and store
		try {
			methodHandle = lookup.findVirtual(component.getClass(), method.getName(), methodType);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodHandle;

	}
	
	public void addTransition(String name, String source, 
	  		   				  String target, String guard, 
	  		   				  Method method) {			

		addTransition(name, source, target, guard, method, ReflectionHelper.parseDataAnnotations(method), ReflectionHelper.parseResourceAnnotations(method));
	}

	public void addTransition(String name, String source, 
			   				  String target, String guard, 
			   				  Method method, List<Data<?>> data, List<ResourceReqImpl> resourceReq) {			

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

		//TransitionImpl t = new TransitionImpl(name, source, target, guard, method, data) ;
		TransitionImpl t = new TransitionImpl(name, source, target, guard, method, data, resourceReq) ;
		methodToTransition.put(method, t);
		//TODO transition here or execulable transition?
		
		allTransitions.add(t);

		allTransitions.add(new TransitionImpl(name, source, target, guard, method, data));

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
		dataOutName2.put(data.name(), getMethodHandleFromMethod(method));
	}

	public void addDataOut(Method method, org.bip.annotations.Data annotation) {		
		
		DataOutImpl<?> data = ReflectionHelper.parseReturnDataAnnotation(method, annotation); 
		dataOut.add( data );
		dataOutName.put(data.name(), method);
		dataOutName2.put(data.name(), getMethodHandleFromMethod(method));
								
	}

	private MethodHandle getMethodHandleFromMethod(Method method) {
		MethodType methodType;
		MethodHandle methodHandle = null;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
		try {
			methodHandle = lookup.findVirtual(component.getClass(), method.getName(), methodType);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodHandle;
	}

	public void addResource(Method method, String label, ResourceType type, String utility) {
		// TODO R remove utility from here?
		ResourceReqImpl r = new ResourceReqImpl(label, type, utility);
		resources.add(r);
		ArrayList<ResourceReqImpl> methodReqResources = new ArrayList<ResourceReqImpl>();
		if (methodResources.containsKey(method)) {
			methodReqResources = methodResources.get(method);
		}
		methodReqResources.add(r);
		methodResources.put(method, methodReqResources);
	}

	public void addResourceUtility(Method method, String utility) {
		methodUtility.put(method, utility);
	}
	
}
