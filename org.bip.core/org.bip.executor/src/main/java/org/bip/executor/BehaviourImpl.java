/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bip.api.Data;
import org.bip.api.DataOut;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortType;
import org.bip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the Behaviour and ExecutableBehaviour interfaces, providing the behaviour of the component together with additional helper structures.
 * 
 */
class BehaviourImpl implements ExecutableBehaviour {

	// ASSUMPTIONS:
	// No two transitions of the same name from the same state
	// For each enforceable and spontaneous transition there is a port that has
	// the same name
	// There cannot be transitions of the same name but different types

	private String currentState;

	private String componentType;

	private ArrayList<Port> allPorts;
	private ArrayList<Port> enforceablePorts;	
	private Map<String, Port> spontaneousPorts;
	// for each port provides data it needs for guards
	private Hashtable<Port, Set<Data<?>>> portToDataInForGuard;
	// for each port provides data it needs for transitions
	private Hashtable<Port, Set<Data<?>>> portToDataInForTransition;

	// TODO after changing the interface to Set, change this to HashSet.
	private ArrayList<String> states;
	// maps state to its transitions
	private Hashtable<String, ArrayList<ExecutableTransition>> stateTransitions;
	// maps state to its enforceable ports
	private Hashtable<String, Set<Port>> stateToPorts;
	// gives a Transition instance from the key of (transition name + transition
	// source state)
	private Hashtable<String, ExecutableTransition> nameToTransition;
	
	private ArrayList<ExecutableTransition> allTransitions;	
	private ArrayList<ExecutableTransition> internalTransitions;
	private ArrayList<ExecutableTransition> spontaneousTransitions;
	private ArrayList<ExecutableTransition> enforceableTransitions;
	// for each enforceable transition get its port instance
	private Hashtable<ExecutableTransition, Port> transitionToPort;

	// the list of guards whose evaluation does not depend on data
	private ArrayList<Guard> guardsWithoutData;
	// the list of guards whose evaluation depends on data
	private ArrayList<Guard> guardsWithData;

	// the list of dataOut variables for this component
	private ArrayList<DataOutImpl<?>> dataOut;
	// the map between the name of the out variable and the method computing it
	private Hashtable<String, Method> dataOutName;
	private Object bipComponent;
	private Class<?> componentClass;


	private Logger logger = LoggerFactory.getLogger(BehaviourImpl.class);

	/**
	 * Creation of Behaviour without providing dataOut. However, there can be some dataIn hidden in guards and transitions
	 * 
	 * @param componentType
	 * @param currentState
	 * @param allTransitions
	 * @param allPorts
	 * @param states
	 * @param guards
	 * @param component
	 * @throws BIPException
	 */
	public BehaviourImpl(String componentType, String currentState, ArrayList<ExecutableTransition> allTransitions, 
						 ArrayList<Port> allPorts, HashSet<String> states, ArrayList<Guard> guards, 
						 Object component) throws BIPException {
		
		this.componentType = componentType;
		this.currentState = currentState;
		this.allTransitions = allTransitions;
		this.allPorts = allPorts;
		this.states = new ArrayList<String>(states);
		this.guardsWithoutData = new ArrayList<Guard>();
		this.guardsWithData = new ArrayList<Guard>();
		for (Guard guard : guards) {
			if (guard.hasData()) {
				this.guardsWithData.add(guard);
			} else {
				this.guardsWithoutData.add(guard);
			}
		}
		this.bipComponent = component;
		this.componentClass = bipComponent.getClass();
		
		portToDataInForGuard = new Hashtable<Port, Set<Data<?>>>();
		portToDataInForTransition = new Hashtable<Port, Set<Data<?>>>();
		enforceablePorts = new ArrayList<Port>();
		spontaneousPorts = new Hashtable<String, Port>();
		for (Port port : allPorts) {
			if (port.getType() == PortType.enforceable) {
				enforceablePorts.add(port);
				portToDataInForGuard.put(port, new HashSet<Data<?>>());
				portToDataInForTransition.put(port, new HashSet<Data<?>>());
			}
			if (port.getType() == PortType.spontaneous)
				spontaneousPorts.put(port.getId(), port);
		}

		stateToPorts = new Hashtable<String, Set<Port>>(states.size());
		stateTransitions = new Hashtable<String, ArrayList<ExecutableTransition>>();
		for (String state : states) {
			stateToPorts.put(state, new HashSet<Port>());
			stateTransitions.put(state, new ArrayList<ExecutableTransition>());
		}


		nameToTransition = new Hashtable<String, ExecutableTransition>();		
		this.internalTransitions = new ArrayList<ExecutableTransition>();
		this.spontaneousTransitions = new ArrayList<ExecutableTransition>();
		this.enforceableTransitions = new ArrayList<ExecutableTransition>();
		for (ExecutableTransition transition : allTransitions) {

			stateTransitions.get(transition.source()).add(transition);
			// BUG, what if we have two spontaneous transitions with an empty name then this key computation scheme is broken.
			nameToTransition.put(transition.name() + transition.source(), transition);

			switch (transition.getType()) {
			case enforceable:
				enforceableTransitions.add(transition);
				break;

			case internal:
				internalTransitions.add(transition);
				break;
				
			case spontaneous:
				spontaneousTransitions.add(transition);
				break;
				
			default:
				break;
			}
		}
			

		HashMap<String, Port> mapIdToPort = new HashMap<String, Port>( );
		for (Port port : allPorts)
			mapIdToPort.put(port.getId(), port);

		transitionToPort = new Hashtable<ExecutableTransition, Port>();
		for (ExecutableTransition transition : enforceableTransitions) {

			Port port = mapIdToPort.get(transition.name());
			
			transitionToPort.put(transition, port);
			
			if (transition.hasGuard()) {
				Set<Data<?>> portGuardData = portToDataInForGuard.get(port);
				for (Guard guard : transition.transitionGuards()) {
					portGuardData.addAll( guard.dataRequired() );
				}
			}

			Set<Data<?>> portTransitionData = portToDataInForTransition.get(port);
			for (Data<?> data : transition.dataRequired()) {
				portTransitionData.add(data);
			}

			Set<Port> stateports = stateToPorts.get(transition.source());
			if (stateports == null) {
				throw new BIPException("The source state " + transition.source() + 
									   " for transition " + transition.name() + 
									   " is not in the list of states of component " +
									   componentType);
			}
			stateports.add(port);

		}

	}

	/**
	 * Creation of Behaviour with dataOut.
	 * 
	 * @param type
	 * @param currentState
	 * @param allTransitions
	 * @param allPorts
	 * @param states
	 * @param guards
	 * @param dataOut
	 * @param component
	 * @throws BIPException
	 */
	public BehaviourImpl(String type, String currentState, ArrayList<ExecutableTransition> allTransitions, 
						 ArrayList<Port> allPorts, HashSet<String> states, ArrayList<Guard> guards,
						 ArrayList<DataOutImpl<?>> dataOut, Hashtable<String, Method> dataOutName, Object component) throws BIPException {

		this(type, currentState, allTransitions, allPorts, states, guards, component);

		this.dataOut = dataOut;
		this.dataOutName = dataOutName;

	}

	// TODO create a test for disallowed ports

	public String getCurrentState() {
		return currentState;
	}

	public ExecutableTransition getTransition(String transitionName) {
		return nameToTransition.get(transitionName);

	}

	public List<String> getStates() {
		return states;
	}

	public Iterable<ExecutableTransition> getStateTransitions(String state) {
		return stateTransitions.get(state);
	}

	public Map<String, Set<Port>> getStateToPorts() {
		return stateToPorts;
	}

	public Iterable<Port> getAllPorts() {
		return this.allPorts;
	}

	public List<Port> getEnforceablePorts() {
		return enforceablePorts;
	}

	public String getComponentType() {
		return this.componentType;
	}

	public Iterable<Guard> getGuardsWithoutData() {
		return this.guardsWithoutData;
	}

	public Map<String, Method> getDataOutMapping() {
		return dataOutName;
	}

	// TODO, change to PortBase first
	public Iterable<Data<?>> portToDataInForTransition(Port port) {
		// TODO with new equals of ports, remove the second part
		// the method is so complex due to wrong equality of ports
		// if we have the same instance of port in the map, return the result
		if (portToDataInForTransition.contains(port)) {
			return this.portToDataInForTransition.get(port);
		}
		// else check if there is a port with the same id and spec type
		else {
			for (Port keyPort : portToDataInForTransition.keySet()) {
				if (keyPort.getId().equals(port.getId()) && keyPort.getSpecType().equals(port.getSpecType())) {
					return this.portToDataInForTransition.get(keyPort);
				}
			}
		}
		return null;
	}

	public Map<Port, Set<Data<?>>> portToDataInForGuard() {
		return this.portToDataInForGuard;
	}

	// TODO, change to PortBase first
	public Set<Data<?>> portToDataInForGuard(Port port) {
		// TODO with new equals of ports, remove the second part
		// the method is so complex due to wrong equality of ports
		// if we have the same instance of port in the map, return the result
		if (portToDataInForGuard.contains(port)) {
			return this.portToDataInForGuard.get(port);
		}
		// TODO do we really need to check spectype?
		// Anyway, do it differently: check once against this behaviour but not against every port
		// else check if there is a port with the same id and spec type
		else {
			for (Port keyPort : portToDataInForGuard.keySet()) {
				if (keyPort.getId().equals(port.getId()) && keyPort.getSpecType().equals(port.getSpecType())) {
					return this.portToDataInForGuard.get(keyPort);
				}
			}
		}
		return null;
	}

	public boolean existEnabled(PortType transitionType, Map<String, Boolean> guardToValue) throws BIPException {
		
		switch (transitionType) {
		
			case enforceable: {
				for (ExecutableTransition transition : enforceableTransitions) {
					if (isEnabled(transition, guardToValue) || transition.hasDataOnGuards()) {
						return true;
					}
				}
				return false;
			}
			
			case spontaneous: {
				for (ExecutableTransition transition : spontaneousTransitions) {
					if (isEnabled(transition, guardToValue)) {
						return true;
					}
				}
				return false;
			}
			
			case internal: {
				boolean internalEnabled = false;
				for (ExecutableTransition transition : internalTransitions) {
					if (isEnabled(transition, guardToValue)) {
						if (internalEnabled) {
							throw new BIPException("Cannot have two internal transitions in the state " + this.currentState + " in component " + this.componentType);
						} else {
							internalEnabled = true;
						}
					}
				}
				return internalEnabled;
			}
			
			default: {
				logger.error("Unsupported transition type {} in component {}.", transitionType, componentType);
				throw new BIPException( "Unsupported transition type " + transitionType + " in component " + componentType + ".");
			}
		}
		
	}

	private boolean isEnabled(ExecutableTransition transition, Map<String, Boolean> guardToValue) throws BIPException {
		// TODO, Why does this has a side effect and is checking also against the current state? 
		// It should throw an exception if this function is used in such a way.
		if (!transition.source().equals(currentState)) {
			return false;
		}
		if (!transition.hasGuard()) {
			return true;
		}
		// TODO, Why does it return false, when it is actually not known if it is enabled or not?
		// Again the exception should have been thrown.
		if (transition.hasDataOnGuards()) {
			return false;
		}
		return transition.guardIsTrue(guardToValue);
	}

	public boolean hasEnabledTransitionFromCurrentState(String portID, Map<String, Boolean> guardToValue) {

		ArrayList<ExecutableTransition> transitions = stateTransitions.get(currentState);
		for (ExecutableTransition transition : transitions) {
			if (transition.name().equals(portID)) { 
				return isEnabled(transition, guardToValue);
			}
		}
		return false;
	}

	public Set<Port> getGloballyDisabledPorts(Map<String, Boolean> guardToValue) {
		HashSet<Port> result = new HashSet<Port>();
		for (ExecutableTransition transition : getStateTransitions(currentState)) {
			if (!transition.getType().equals(PortType.enforceable)) {
				continue;
			}			
			if (!transition.hasGuard() || transition.hasDataOnGuards()) {
				continue;
			}
			if (!transition.guardIsTrue(guardToValue)) {
				result.add(transitionToPort.get(transition));
			}
		}
		return result;
	}

	public Hashtable<String, Boolean> computeGuardsWithoutData() {
		
		Hashtable<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
		for (Guard guard : this.guardsWithoutData) {
			try {
				guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return guardToValue;
	}

	public void execute(String portID) throws BIPException {
		// this component does not take part in the interaction
		if (portID == null) {
			return;
		}
		ExecutableTransition transition = getTransition(portID + currentState);
		if (transition == null) { // this shouldn't normally happen
			throw new BIPException("The spontaneous transition cannot be null after inform");
		}
		// TODO, executeInternal is protected against misuse, this execute is not protected.
		// What if the port to be specified to be executed is actually not enabled?
		invokeMethod(transition);
	}

	public void executeInternal(Map<String, Boolean> guardToValue) throws BIPException {
		
		for (ExecutableTransition transition : getStateTransitions(currentState)) {
			if (transition.getType().equals(PortType.internal) && 
				transition.guardIsTrue(guardToValue)) {
					invokeMethod(transition);
					return;
			}
		}
		
		throw new BIPException("There is no enabled internal transition in state " + this.currentState + 
							   " for component " + this.componentType + 
							   ". This exception is internal to the implementation.");
		
	}

	private void invokeMethod(ExecutableTransition transition) {
		Method componentMethod;
		try {
			componentMethod = transition.method();
			if (!componentMethod.getDeclaringClass().isAssignableFrom(componentClass)) {
				throw new IllegalArgumentException("The method " + componentMethod.getName() + 
						" belongs to the class " + componentMethod.getDeclaringClass().getName() +
						" but not  to the class of the component " + componentClass.getName());
			}
			componentMethod.invoke(bipComponent);

			performTransition(transition);

		} catch (SecurityException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (IllegalAccessException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (IllegalArgumentException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (InvocationTargetException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
			ExceptionHelper.printExceptionTrace(logger, e.getCause());
		} catch (BIPException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		}
	}

	private void performTransition(ExecutableTransition transition) throws BIPException {
		if (!currentState.equals(transition.source())) {
			throw new BIPException("Could not perform transition " + transition.name() + 
								   " of component " + componentType + 
								   " because the component is in the wrong state " + currentState +
								   " instead of state " + transition.source());
		}
		currentState = transition.target();
	}

	public List<Boolean> checkEnabledness(String port, List<Map<String, Object>> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, BIPException {

		ArrayList<Boolean> result = new ArrayList<Boolean>();
		ExecutableTransition transition = getTransition(port + this.currentState);
		// TODO find out why this can happen
		// if the guard is not there, it does not need data, any data is good, no need to do check Enabledness?
		if (!transition.hasGuard()) {
			for (int i = data.size(); i > 0; i--) {
				result.add(true);
				// TODO, BUG, missing return? as the for loop below will also add data.size() number of booleans.
			}
		}
		// for each different row of the data evaluation table
		for (Map<String, Object> dataRow : data) {
			Map<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
			// for each Guard of this transition
			for (Guard guard : transition.transitionGuards()) {
				if (!guard.hasData()) {
					guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent));
				} else {
					// if it has data,
					// then for each data it needs add the corresponding value
					// to the array of its arguments
					ArrayList<Object> args = new ArrayList<Object>();
					for (Data<?> guardData : guard.dataRequired()) {
						Object value = dataRow.get(guardData.name());
						args.add(value);
					}
					guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent, args.toArray()));
				}
			}
			result.add(transition.guardIsTrue(guardToValue));
		}
		return result;
	}

	public List<Port> portsNeedingData(String dataName) {
		ArrayList<Port> result = new ArrayList<Port>();
		for (ExecutableTransition transition : this.allTransitions) {
			Iterable<Data<?>> data = transition.dataRequired();
			for (Data<?> d : data) {
				if (d.name().equals(dataName)) {
					result.add(this.transitionToPort.get(transition));
					break;
				}
			}
		}
		return result;
	}

	public void execute(String portID, Map<String, ?> data) {
		// this component does not take part in the interaction

		if (portID == null) {
			return;
		}
		ExecutableTransition transition = getTransition(portID + currentState);
		invokeMethod(transition, data);
	}

	private void invokeMethod(ExecutableTransition transition, Map<String, ?> data) {
		Method componentMethod;
		try {
			componentMethod = transition.method();
			if (!componentMethod.getDeclaringClass().isAssignableFrom(componentClass)) {
				throw new IllegalArgumentException("The method " + componentMethod.getName() + " belongs to the class " + componentMethod.getDeclaringClass().getName()
						+ " but not  to the class of the component " + componentClass.getName());
			}

			Object[] args = new Object[componentMethod.getParameterTypes().length];
			int i = 0;
			for (Data<?> trData : transition.dataRequired()) {
				// name parameter can not be null as it is enforced by the constructor.
				Object value = data.get(trData.name());
				// TODO, value can be null if the map is not properly constructed.
				args[i] = value;
			}
			logger.info("Invocation: " + transition.name() + " with args " + data);
			componentMethod.invoke(bipComponent, args);
			performTransition(transition);
		} catch (SecurityException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (IllegalAccessException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (IllegalArgumentException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		} catch (InvocationTargetException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
			ExceptionHelper.printExceptionTrace(logger, e.getTargetException());
		} catch (BIPException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
		}

	}

	public Set<Port> getDataProvidingPorts(String dataName) {
		if (dataName == null || dataName.isEmpty()) {
			return new HashSet<Port>();
		}
		for (DataOut<?> data : dataOut) {
			if (data.name().equals(dataName)) {
				return data.allowedPorts();
			}
		}
		return new HashSet<Port>();
	}

	@Override
	public boolean isSpontaneousPort(String port) {
		if (port == null || port.isEmpty()) {
			throw new IllegalArgumentException(
					"The name of the required port for the component "
							+ bipComponent.getClass().getName()
							+ " cannot be null or empty.");
		}
		return spontaneousPorts.containsKey(port);
	}
	
}
