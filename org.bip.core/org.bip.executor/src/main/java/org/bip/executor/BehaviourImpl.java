/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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
import org.bip.api.Transition;
import org.bip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the Behaviour and ExecutableBehaviour interfaces, providing the behaviour of the component together with additional helper structures.
 * 
 */
class BehaviourImpl implements ExecutableBehaviour {

	private String currentState;

	private String componentType;

	private ArrayList<Port> allPorts;
	private ArrayList<Port> enforceablePorts;	
	private Map<String, Port> spontaneousPorts;
	// for each port provides data it needs for guards
	private Hashtable<Port, Set<Data<?>>> portToDataInForGuard;
	// for each port provides data it needs for transitions
	private Hashtable<Port, Set<Data<?>>> portToDataInForTransition;

	private Set<String> states;
	// maps state to its transitions
	private Hashtable<String, ArrayList<ExecutableTransition>> stateTransitions;
	// maps state to its enforceable ports
	private Hashtable<String, Set<Port>> stateToPorts;
	// gives a Transition instance from the key of (transition name + transition
	// source state)
//	private Hashtable<String, ExecutableTransition> nameToTransition;
	// gives a Transition instance from two keys - first key is currentState, second key is transition name.
	private Hashtable<String, Hashtable<String, ExecutableTransition>> nameToTransition;
	
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
	private Hashtable<String, MethodHandle> dataOutName2;
	
	private ArrayList<ResourceReqImpl> resources;// do we need a method? actually no, but we need a transition name along...
	
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
						 ArrayList<Port> allPorts, HashSet<String> states, Collection<Guard> guards, 
						 Object component) throws BIPException {
		
		this.componentType = componentType;
		this.currentState = currentState;
		this.allTransitions = allTransitions;
		this.allPorts = allPorts;
		this.states = new HashSet<String>(states);
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
		nameToTransition = new Hashtable<String, Hashtable<String, ExecutableTransition>>();
		for (String state : states) {
			stateToPorts.put(state, new HashSet<Port>());
			stateTransitions.put(state, new ArrayList<ExecutableTransition>());
			nameToTransition.put(state, new Hashtable<String, ExecutableTransition>());
		}


		
		this.internalTransitions = new ArrayList<ExecutableTransition>();
		this.spontaneousTransitions = new ArrayList<ExecutableTransition>();
		this.enforceableTransitions = new ArrayList<ExecutableTransition>();
		for (ExecutableTransition transition : allTransitions) {

			stateTransitions.get(transition.source()).add(transition);
			nameToTransition.get(transition.source()).put(transition.name(), transition);

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
						 ArrayList<Port> allPorts, HashSet<String> states, Collection<Guard> guards,
						 ArrayList<DataOutImpl<?>> dataOut, Hashtable<String, Method> dataOutName, Hashtable<String, MethodHandle> dataOutName2, Object component) throws BIPException {

		this(type, currentState, allTransitions, allPorts, states, guards, component);

		this.dataOut = dataOut;
		this.dataOutName = dataOutName;
		this.dataOutName2 = dataOutName2;
	}

	
	public String getCurrentState() {
		return currentState;
	}

	private ExecutableTransition getTransition(String state, String transitionName) {
		return nameToTransition.get(state).get(transitionName);
	}

	public Set<String> getStates() {
		return states;
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
	
	public Map<String, MethodHandle> getDataOutMapping2() {
		return dataOutName2;
	}
	
	public Iterable<Data<?>> portToDataInForTransition(Port port) {
			return this.portToDataInForTransition.get(port);
	}

	public Map<Port, Set<Data<?>>> portToDataInForGuard() {
		return this.portToDataInForGuard;
	}

	public Set<Data<?>> portToDataInForGuard(Port port) {
			return this.portToDataInForGuard.get(port);
	}

	@Override
	public boolean existInCurrentStateAndEnforceableWithData() {
		
		for (ExecutableTransition transition : stateTransitions.get(currentState)) {
			if (transition.getType().equals(PortType.enforceable) && transition.hasDataOnGuards()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean existInCurrentStateAndEnabledEnforceableWithoutData(Map<String, Boolean> guardToValue) throws BIPException {

		for (ExecutableTransition transition : enforceableTransitions) {
			if (!transition.hasDataOnGuards() && isInCurrentStateAndEnabled(transition, guardToValue)) {
				return true;
			}
		}
		return false;

	}
	
	// TODO: The check that throws exception should be done by the Behaviour builder and not here
	public boolean transitionNoDataGuardData(String port) throws BIPException {
		ExecutableTransition transition = this.nameToTransition.get(currentState).get(port);
		if (transition == null) {
			throw new BIPException("No transition " + port + " from state " + currentState + " in component "
					+ componentType);
		}
		return transition.hasDataOnGuards() && !transition.hasData();
	}
	
	@Override
	public boolean existInCurrentStateAndEnabledSpontaneous(Map<String, Boolean> guardToValue) throws BIPException {
	
		for (ExecutableTransition transition : spontaneousTransitions) {
			if (isInCurrentStateAndEnabled(transition, guardToValue)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean existEnabledInternal(Map<String, Boolean> guardToValue) throws BIPException {

		boolean internalEnabled = false;
		for (ExecutableTransition transition : internalTransitions) {
			if (isInCurrentStateAndEnabled(transition, guardToValue)) {
				if (internalEnabled) {
					throw new BIPException("Cannot have two enabled internal transitions in the state " + this.currentState + " in component " + this.componentType);
				} else {
					internalEnabled = true;
				}
			}
		}
		return internalEnabled;
		
	}

	private boolean isInCurrentStateAndEnabled(ExecutableTransition transition, Map<String, Boolean> guardToValue) throws BIPException {

		if (!transition.source().equals(currentState)) {
			return false;
		}
		
		if (!transition.hasGuard()) {
			return true;
		}
		
		return transition.guardIsTrue(guardToValue);
	}

	public boolean hasEnabledTransitionFromCurrentState(String portID, Map<String, Boolean> guardToValue) {

		ArrayList<ExecutableTransition> transitions = stateTransitions.get(currentState);
		for (ExecutableTransition transition : transitions) {
			if (transition.name().equals(portID)) { 
				return isInCurrentStateAndEnabled(transition, guardToValue);
			}
		}
		return false;
	}

	public Set<Port> getGloballyDisabledEnforceablePortsWithoutDataTransfer(Map<String, Boolean> guardToValue) {
		HashSet<Port> result = new HashSet<Port>();
		for (ExecutableTransition transition : stateTransitions.get(currentState)) {
			// Check only enforceable transitions
			if (transition.getType().equals(PortType.enforceable)) {
				
				// Enforceable transition must have a guard without data and this guard has to evaluate to false.
				if (transition.hasGuard() &&
					!transition.hasDataOnGuards() &&
					!transition.guardIsTrue(guardToValue)) {
					result.add(transitionToPort.get(transition));
				}						
				
			}
		}
		return result;
	}

	public Map<String, Boolean> computeGuardsWithoutData(String currentState) {
		return computeGuardsWithoutData2(currentState);
		
		// TODO BUG DESIGN compute only guards needed for this current state, as other 
		// guards not guaranteed to compute properly if executed in the wrong state.

//		Hashtable<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
//		ArrayList<ExecutableTransition> transitionsFromState = stateTransitions.get(currentState);
//		for (ExecutableTransition transition : transitionsFromState) {
//			if (transition.hasGuard()) {
//				for (Guard guard : transition.transitionGuards()) {
//					if (this.guardsWithoutData.contains(guard)) {
//						try {
//							guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent));
//						} catch (IllegalAccessException e) {
//							e.printStackTrace();
//						} catch (IllegalArgumentException e) {
//							e.printStackTrace();
//						} catch (InvocationTargetException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//		return guardToValue;
	}
	
	public Map<String, Boolean> computeGuardsWithoutData2(String currentState) {
		
		// TODO BUG DESIGN compute only guards needed for this current state, as other 
		// guards not guaranteed to compute properly if executed in the wrong state.

		Hashtable<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
		ArrayList<ExecutableTransition> transitionsFromState = stateTransitions.get(currentState);
		for (ExecutableTransition transition : transitionsFromState) {
			if (transition.hasGuard()) {
				for (Guard guard : transition.transitionGuards()) {
					if (this.guardsWithoutData.contains(guard)) {
						try {
							Object[] args = new Object[1];
							args[0] = bipComponent;
							guardToValue.put(guard.name(), guard.evaluateGuard2(args));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return guardToValue;
	}

	// ExecutorKernel, the owner of BehaviourImpl is checking the correctness of the execution.
	public void executePort(String portID) throws BIPException {
		// this component does not take part in the interaction
		if (portID == null) {
			return;
		}
		ExecutableTransition transition = getTransition(currentState, portID);
		if (transition == null) { // this shouldn't normally happen
			throw new BIPException("The spontaneous transition for port " + portID + " cannot be null after inform");
		}
		invokeMethod2(transition);
	}

	public void executeInternal(Map<String, Boolean> guardToValue) throws BIPException {
		
		for (ExecutableTransition transition : stateTransitions.get(currentState)) {
			if (transition.getType().equals(PortType.internal) && 
				transition.guardIsTrue(guardToValue)) {
					invokeMethod2(transition);
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
			logger.info("Invocation: " + transition.name() );
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
	
	private void invokeMethod2(ExecutableTransition transition) {
		MethodHandle methodHandle;
		try {
			logger.info("Invocation: " + transition.name() );
			methodHandle = transition.methodHandle();
			if (!transition.method().getDeclaringClass().isAssignableFrom(componentClass)) {
				throw new IllegalArgumentException("The method " + transition.method().getName() + 
						" belongs to the class " + transition.method().getDeclaringClass().getName() +
						" but not  to the class of the component " + componentClass.getName());
			}
			Object[] args = new Object[1];
			args[0] = bipComponent;
			methodHandle.invokeWithArguments(args);

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
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

//		ArrayList<Boolean> result = new ArrayList<Boolean>();
//		ExecutableTransition transition = getTransition(currentState, port);
//		// TODO DESIGN, find out why this can happen if the guard is not there, 
//		// it does not need data, any data is good, no need to do check Enabledness?
//		if (!transition.hasGuard()) {
//			for (int i = data.size(); i > 0; i--) {
//				result.add(true);
//			}
//			return result;
//		}
//		// for each different row of the data evaluation table
//		for (Map<String, Object> dataRow : data) {
//			Map<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
//			// for each Guard of this transition
//			for (Guard guard : transition.transitionGuards()) {
//				if (!guard.hasData()) {
//					guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent));
//				} else {
//					// if it has data,
//					// then for each data it needs add the corresponding value
//					// to the array of its arguments
//					ArrayList<Object> args = new ArrayList<Object>();
//					for (Data<?> guardData : guard.dataRequired()) {
//						Object value = dataRow.get(guardData.name());
//						args.add(value);
//					}
//					guardToValue.put(guard.name(), guard.evaluateGuard(bipComponent, args.toArray()));
//				}
//			}
//			result.add(transition.guardIsTrue(guardToValue));
//		}
//		return result;
		
		return checkEnabledness2(port, data);
	}
	
	public List<Boolean> checkEnabledness2(String port, List<Map<String, Object>> data) throws BIPException {

		ArrayList<Boolean> result = new ArrayList<Boolean>();
		ExecutableTransition transition = getTransition(currentState, port);
		// TODO DESIGN, find out why this can happen if the guard is not there, 
		// it does not need data, any data is good, no need to do check Enabledness?
		if (!transition.hasGuard()) {
			for (int i = data.size(); i > 0; i--) {
				result.add(true);
			}
			return result;
		}
		// for each different row of the data evaluation table
		for (Map<String, Object> dataRow : data) {
			Map<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
			// for each Guard of this transition
			for (Guard guard : transition.transitionGuards()) {
				if (!guard.hasData()) {
					Object[] args = new Object[1];
					args[0] = bipComponent;
					guardToValue.put(guard.name(), guard.evaluateGuard2(args));
				} else {
					// if it has data,
					// then for each data it needs add the corresponding value
					// to the array of its arguments
					ArrayList<Object> args = new ArrayList<Object>();
					args.add(bipComponent);
					for (Data<?> guardData : guard.dataRequired()) {
						Object value = dataRow.get(guardData.name());
						args.add(value);
					}
					guardToValue.put(guard.name(), guard.evaluateGuard2(args.toArray()));
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

	// TODO, now it also executes spontaneous transitions with data, does getTransition properly works?
	public void execute(String portID, Map<String, ?> data) {
		// this component does not take part in the interaction

		if (portID == null) {
			return;
		}
		ExecutableTransition transition = getTransition(currentState, portID);
		invokeMethod2(transition, data);
	}

	// TODO, now it also executes spontaneous transitions with data, does transition.dataRequired() works properly?
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
				// TODO, CHECK, value can be null if the map is not properly constructed. Throw more informative exception.
				args[i] = value;
				i++;
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
	
	// TODO, now it also executes spontaneous transitions with data, does transition.dataRequired() works properly?
	private void invokeMethod2(ExecutableTransition transition, Map<String, ?> data) {
		Method componentMethod;
		MethodHandle methodHandle;
		try {
			componentMethod = transition.method();
			methodHandle = transition.methodHandle();
			if (!componentMethod.getDeclaringClass().isAssignableFrom(componentClass)) {
				throw new IllegalArgumentException("The method " + componentMethod.getName() + " belongs to the class " + componentMethod.getDeclaringClass().getName()
						+ " but not  to the class of the component " + componentClass.getName());
			}

			Object[] args = new Object[componentMethod.getParameterTypes().length+1];
			args[0] = bipComponent;
			int i = 1;
			for (Data<?> trData : transition.dataRequired()) {
				// name parameter can not be null as it is enforced by the constructor.
				Object value = data.get(trData.name());
				// TODO, CHECK, value can be null if the map is not properly constructed. Throw more informative exception.
				args[i] = value;
				i++;
			}
			logger.info("Invocation: " + transition.name() + " with args " + data);
			//if the method is not static, the first param to invokeExact should be treated as instance where to look for the method
			 methodHandle.invokeWithArguments(args);
			
			//componentMethod.invoke(bipComponent, args);
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
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public List<Transition> getAllTransitions() {
		List<Transition> result = new ArrayList<Transition>( allTransitions );
		return result;
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
