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

	private ArrayList<ExecutableTransition> allTransitions;

	private ArrayList<Port> allPorts;
	private ArrayList<Port> enforceablePorts;
	// maps state to its transitions
	private Hashtable<String, ArrayList<ExecutableTransition>> stateTransitions;
	// maps state to its enforceable ports
	private Hashtable<String, Set<Port>> stateToPorts;
	// gives a Transition instance from the key of (transition name + transition
	// source state)
	private Hashtable<String, ExecutableTransition> nameToTransition;
	// for each enforceable transition get its port instance
	private Hashtable<ExecutableTransition, Port> transitionToPort;
	// for each port provides data it needs for guards
	private Hashtable<Port, Set<Data<?>>> portToDataInForGuard;
	// for each port provides data it needs for transitions
	private Hashtable<Port, Set<Data<?>>> portToDataInForTransition;
	// TODO after changing the interface to Set, change this to HashSet.
	private ArrayList<String> states;
	private ArrayList<Guard> guards;
	// the list of guards whose evaluation does not depend on data
	private ArrayList<Guard> guardsWithoutData;
	// the list of guards whose evaluation depends on data
	private ArrayList<Guard> guardsWithData;
	// the list of dataOut variables for this component
	private ArrayList<DataImpl<?>> dataOut;
	// the map between the name of the out variable and the method computing it
	private Hashtable<String, Method> dataOutName;
	private Object bipComponent;
	private Class<?> componentClass;

	private ArrayList<ExecutableTransition> internalTransitions;
	private ArrayList<ExecutableTransition> spontaneousTransitions;
	private ArrayList<ExecutableTransition> enforceableTransitions;

	private Logger logger = LoggerFactory.getLogger(BehaviourImpl.class);

	/**
	 * Creation of Behaviour without providing dataOut. However, there can be some dataIn hidden in guards and transitions
	 * 
	 * @param type
	 * @param currentState
	 * @param allTransitions
	 * @param allPorts
	 * @param states
	 * @param guards
	 * @param component
	 * @throws BIPException
	 */
	public BehaviourImpl(String type, String currentState, ArrayList<TransitionImpl> allTransitions, ArrayList<Port> allPorts, HashSet<String> states, ArrayList<Guard> guards, Object component)
			throws BIPException {
		if (type == null || type.isEmpty()) {
			throw new NullPointerException("Component type for object " + component + " cannot be null or empty.");
		}
		if (currentState == null || currentState.isEmpty()) {
			throw new NullPointerException("The initial state of the component of type " + type + " cannot be null or empty.");
		}
		if (allTransitions == null || allTransitions.isEmpty()) {
			throw new BIPException("List of transitions in component of type " + type + " cannot be null or empty.");
		}
		if (states == null || states.isEmpty()) {
			throw new BIPException("List of states in component of type " + type + " cannot be null or empty.");
		}
		if (allPorts == null || allPorts.isEmpty()) {
			throw new BIPException("List of states in component of type " + type + " cannot be null or empty.");
		}
		if (component == null) {
			throw new NullPointerException("The component object of type " + type + " cannot be null.");
		}
		this.componentType = type;
		this.currentState = currentState;
		// this.allTransitions = allTransitions;
		this.allPorts = allPorts;
		this.states = new ArrayList<String>(states);
		this.guards = guards;
		this.guardsWithoutData = new ArrayList<Guard>();
		this.guardsWithData = new ArrayList<Guard>();
		for (Guard guard : guards) {
			if (guard.hasData()) {
				this.guardsWithData.add(guard);
			} else {
				this.guardsWithoutData.add(guard);
			}
		}

		this.internalTransitions = new ArrayList<ExecutableTransition>();
		this.spontaneousTransitions = new ArrayList<ExecutableTransition>();
		this.enforceableTransitions = new ArrayList<ExecutableTransition>();

		this.bipComponent = component;
		this.componentClass = bipComponent.getClass();
		setUpBehaviourData(allTransitions);
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
	public BehaviourImpl(String type, String currentState, ArrayList<TransitionImpl> allTransitions, ArrayList<Port> allPorts, HashSet<String> states, ArrayList<Guard> guards,
			ArrayList<DataOut<?>> dataOut, Hashtable<String, Method> dataOutName, Object component) throws BIPException {
		// setUpBehaviourData is called inside. we can do so since dataOut and
		// dataOutName are not used in the method setUpBehaviourData
		this(type, currentState, allTransitions, allPorts, states, guards, component);

		this.dataOut = new ArrayList<DataImpl<?>>();
		this.dataOutName = dataOutName;

		if (dataOut == null) {
			return;
		}
		for (DataOut<?> data : dataOut) {

			if (data.portSpecificationType().equals(DataOut.Type.any)) {
				this.dataOut.add(createData(data.name(), data.type(), new HashSet<Port>(this.enforceablePorts)));
				// data.addPorts(this.enforceablePorts);
			} else if (data.portSpecificationType().equals(DataOut.Type.allowed)) {
				HashSet<Port> allowedPorts = new HashSet<Port>();
				boolean portFound = false;
				for (String port : data.stringPorts()) {
					portFound = false;
					for (Port componentPort : this.enforceablePorts) {
						if (port.equals(componentPort.getId())) {
							allowedPorts.add(componentPort);
							portFound = true;
							break;
						}
					}
					if (!portFound) {
						throw new BIPException("There is no port instance specified in Ports for the port " + port + " mentioned in data " + data.name());
					}
				}

				// type casting from Object
				// ArrayList<Port> allowedPorts = (ArrayList<Port>) this.enforceablePorts.clone();
				// allowedPorts.removeAll(data.unallowedPorts());
				// data.addPorts(allowedPorts);
				this.dataOut.add(createData(data.name(), data.type(), allowedPorts));
			} else if (data.portSpecificationType().equals(DataOut.Type.unallowed)) {
				HashSet<Port> allowedPorts = new HashSet<Port>();
				boolean portFound = false;
				for (Port componentPort : this.enforceablePorts) {
					portFound = false;
					for (String port : data.stringPorts()) {
						if (port.equals(componentPort.getId())) {
							portFound = true;
							break;
						}
					}
					if (!portFound) {
						allowedPorts.add(componentPort);
					}
				}

				// type casting from Object
				// ArrayList<Port> allowedPorts = (ArrayList<Port>) this.enforceablePorts.clone();
				// allowedPorts.removeAll(data.unallowedPorts());
				// data.addPorts(allowedPorts);
				this.dataOut.add(createData(data.name(), data.type(), allowedPorts));
			}
		}
	}

	// TODO create a test for disallowed ports

	/**
	 * Sets up the helper data structures. Specifies the types of transitions, creates the stateToPorts and nameToTransition maps, as well as the list
	 * of enforceable ports
	 * 
	 * @throws BIPException
	 */
	private void setUpBehaviourData(ArrayList<TransitionImpl> transitions) throws BIPException {
		stateTransitions = new Hashtable<String, ArrayList<ExecutableTransition>>();
		nameToTransition = new Hashtable<String, ExecutableTransition>();
		stateToPorts = new Hashtable<String, Set<Port>>(states.size());
		transitionToPort = new Hashtable<ExecutableTransition, Port>();
		portToDataInForGuard = new Hashtable<Port, Set<Data<?>>>();
		portToDataInForTransition = new Hashtable<Port, Set<Data<?>>>();
		enforceablePorts = new ArrayList<Port>();
		this.allTransitions = new ArrayList<ExecutableTransition>();

		// create list of enforceable ports
		for (Port port : allPorts) {
			if (port.getType() == PortType.enforceable) {
				enforceablePorts.add(port);
				portToDataInForGuard.put(port, new HashSet<Data<?>>());
				portToDataInForTransition.put(port, new HashSet<Data<?>>());
			}
		}

		for (String state : states) {
			stateToPorts.put(state, new HashSet<Port>());
		}
		for (TransitionImpl transition : transitions) {
			ExecutableTransition typedTransition = null;

			// set type for the transitions
			if (transition.name().equals("")) {
				typedTransition = new ExecutableTransitionImpl(transition, PortType.internal, guards);
				this.allTransitions.add(typedTransition);
				internalTransitions.add(typedTransition);
			} else {
				for (Port port : allPorts) {
					if (transition.name().equals(port.getId())) {
						typedTransition = new ExecutableTransitionImpl(transition, port.getType(), guards);
						this.allTransitions.add(typedTransition);
						// if port is enforceable, add it to the list of state
						// ports
						if (port.getType().equals(PortType.enforceable)) {
							enforceableTransitions.add(typedTransition);
							transitionToPort.put(typedTransition, port);
							InjectDataInfo(port, typedTransition);

							Set<Port> stateports = stateToPorts.get(typedTransition.source());
							if (stateports == null) {
								throw new BIPException("The source state " + typedTransition.source() + " for transition " + typedTransition.name() + " is not in the list of states of component "
										+ componentType);
							}
							stateports.add(port);

						} else if (port.getType().equals(PortType.spontaneous)) {
							spontaneousTransitions.add(typedTransition);
						}
						break;
					}
				}
			}
			nameToTransition.put(typedTransition.name() + typedTransition.source(), typedTransition);

		}

		// create map state to transitions from this state
		for (String state : states) {
			ArrayList<ExecutableTransition> result = new ArrayList<ExecutableTransition>();
			for (ExecutableTransition transition : allTransitions) {
				if (transition.source().equals(state)) {
					result.add(transition);
				}
			}
			stateTransitions.put(state, result);
		}

	}

	private void InjectDataInfo(Port port, ExecutableTransition transition) {
		// GUARD
		ArrayList<Data<?>> guardData = new ArrayList<Data<?>>();
		// for every Guard function needed for checking if this transition is
		// enabled
		if (transition.hasGuard()) {
			for (Guard guard : transition.transitionGuards()) {
				guardData.addAll(guard.dataRequired());
			}
			HashSet<Data<?>> portGuardData = (HashSet<Data<?>>) portToDataInForGuard.get(port);
			portGuardData.addAll(guardData);
			portToDataInForGuard.put(port, portGuardData);
		}

		// TRANSITION
		HashSet<Data<?>> portTransitionData = (HashSet<Data<?>>) portToDataInForTransition.get(port);

		for (Data<?> data : transition.dataRequired()) {
			portTransitionData.add(data);
		}

		portToDataInForTransition.put(port, portTransitionData);
	}

	// SET UP FINISHED. GETTERS METHODS

	public Port getPort(ExecutableTransition transition) {
		return transitionToPort.get(transition);
	}

	public String getCurrentState() {
		return currentState;
	}

	public ExecutableTransition getTransition(String transitionName) {
		return nameToTransition.get(transitionName);

	}

	public Iterable<ExecutableTransition> getAllTransitions() {
		return allTransitions;
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

	// GETTERS FINISHED. INTERFACE METHODS

	// GET ENABLED TRANSITIONS, TYPED TRANSITIONS

	public boolean existEnabled(PortType transitionType, Map<String, Boolean> guardToValue) throws BIPException {
		switch (transitionType) {
		case enforceable: {
			for (ExecutableTransition transition : enforceableTransitions) {
				if (isEnabled(transition, guardToValue) || transition.hasDataOnGuards()) {
					return true;
				}
			}
		}
		case spontaneous: {
			for (ExecutableTransition transition : spontaneousTransitions) {
				if (isEnabled(transition, guardToValue)) {
					return true;
				}
			}
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
		default:
			logger.error("Unsupported transition type {} in component {}.", transitionType, componentType);
		}
		return false;
	}

	private boolean isEnabled(ExecutableTransition transition, Map<String, Boolean> guardToValue) throws BIPException {
		if (!transition.source().equals(currentState)) {
			return false;
		}
		if (transition.guard() == null || transition.guard().isEmpty()) {
			return true;
		}
		if (transition.hasDataOnGuards()) {
			return false;
		}
		return transition.guardIsTrue(guardToValue);
	}

	public boolean hasTransitionFromCurrentState(String portID) {

		ArrayList<ExecutableTransition> transitions = stateTransitions.get(currentState);
		for (ExecutableTransition transition : transitions) {
			if (transition.name().equals(portID)) {
				return true;
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
			String guardExpression = transition.guard();
			if (guardExpression == null || guardExpression.isEmpty() || transition.hasDataOnGuards()) {
				continue;
			}
			try {
				if (!transition.guardIsTrue(guardToValue)) {
					result.add(transitionToPort.get(transition));
				}
			} catch (BIPException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// EXECUTION

	public Hashtable<String, Boolean> computeGuards() {
		Iterable<Guard> guards = this.guardsWithoutData;
		Hashtable<String, Boolean> guardToValue = new Hashtable<String, Boolean>();
		for (Guard guard : guards) {
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
		invokeMethod(transition);
	}

	public void executeInternal(Map<String, Boolean> guardToValue) throws BIPException {
		ExecutableTransition transition = null;
		for (ExecutableTransition tr : getStateTransitions(currentState)) {
			if (tr.getType().equals(PortType.internal) && tr.guardIsTrue(guardToValue)) {
				transition = tr;
				break;
			}
		}
		if (transition == null) {
			throw new BIPException("There is no enabled internal transition in state " + this.currentState + " for component " + this.componentType
					+ ". This exception is internal to the implementation.");
		}
		invokeMethod(transition);
	}

	private void invokeMethod(ExecutableTransition transition) {
		Method componentMethod;
		try {
			componentMethod = transition.method();
			if (!componentMethod.getDeclaringClass().isAssignableFrom(componentClass)) {
				throw new IllegalArgumentException("The method " + componentMethod.getName() + " belongs to the class " + componentMethod.getDeclaringClass().getName()
						+ " but not  to the class of the component " + componentClass.getName());
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

	private boolean performTransition(ExecutableTransition transition) throws BIPException {
		if (!currentState.equals(transition.source())) {
			throw new BIPException("Could not perform transition " + transition.name() + " of component " + componentType + " because the component is in the wrong state " + currentState
					+ " instead of state " + transition.source());
		}
		currentState = transition.target();
		return true;
	}

	// DATA FUNCTIONS

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
				Object value = data.get(trData.name());
				args[i] = value;
				// TODO what if value is null?
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
		for (DataImpl<?> data : dataOut) {
			if (data.name().equals(dataName)) {
				return data.allowedPorts();
			}
		}
		return new HashSet<Port>();
	}
	
	// TODO find a way not to copy this method among classes
		<T> DataImpl<T> createData(String dataName, Class<T> type, Set<Port> ports) {
			DataImpl<T> toReturn = new DataImpl<T>(dataName, type, ports);
			return toReturn;
		}

}
