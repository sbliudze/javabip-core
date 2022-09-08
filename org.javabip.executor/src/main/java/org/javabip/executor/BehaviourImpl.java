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

import javafx.util.Pair;
import org.javabip.api.*;
import org.javabip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Implements the Behaviour and ExecutableBehaviour interfaces, providing the behaviour of the component together with
 * additional helper structures.
 *
 * @author Alina Zolotukhina
 */
class BehaviourImpl implements ExecutableBehaviour {

    private String currentState;

    private String componentType;

    private Set<String> states;
    private ArrayList<Port> allPorts;
    private ArrayList<Port> enforceablePorts;
    private Map<String, Port> spontaneousPorts;
    /**
     * For each port provides data it needs for guards
     */
    private Hashtable<Port, Set<Data<?>>> portToDataInForGuard;
    /**
     * For each port provides data it needs for transitions
     */
    private Hashtable<Port, Set<Data<?>>> portToDataInForTransition;

    /**
     * Maps state to its transitions
     */
    private Hashtable<String, ArrayList<ExecutableTransition>> stateTransitions;
    /**
     * Maps state to its enforceable ports
     */
    private Hashtable<String, Set<Port>> stateToPorts;
    // TODO DISCUSS since it's a hashtable, there cannot be several transitions with the same name
    // for different data variables (see theory?)
    /**
     * Gives a Transition instance from two keys - first key is currentState, second key is transition name.
     */
    private Hashtable<String, Hashtable<String, ExecutableTransition>> nameToTransition;

    private ArrayList<ExecutableTransition> allTransitions;
    private ArrayList<ExecutableTransition> internalTransitions;
    private ArrayList<ExecutableTransition> spontaneousTransitions;
    private ArrayList<ExecutableTransition> enforceableTransitions;
    /**
     * for each enforceable transition get its port instance
     */
    private Hashtable<ExecutableTransition, Port> transitionToPort;

    /**
     * The list of guards whose evaluation does not depend on data
     */
    private ArrayList<Guard> guardsWithoutData;
    /**
     * The list of guards whose evaluation depends on data
     */
    private ArrayList<Guard> guardsWithData;

    /**
     * The list of dataOut variables for this component
     */
    private ArrayList<DataOutImpl<?>> dataOut;

    /**
     * The map between the name of the out variable and the method computing it
     */
    private Hashtable<String, MethodHandle> dataOutName;

    private Map<String, List<Port>> dataFromTransitionToPorts;
    private Map<String, List<Port>> dataFromGuardsToPorts;
    private Map<String, List<Port>> portsNeedingData;

    private InvariantImpl invariant;
    private HashMap<TransitionImpl, InvariantImpl> transitionToPreConditionMap;
    private HashMap<TransitionImpl, InvariantImpl> transitionToPostConditionMap;
    private HashMap<String, InvariantImpl> stateToPredicateMap;

    private Object bipComponent;
    private Class<?> componentClass;

    private Logger logger = LoggerFactory.getLogger(BehaviourImpl.class);

    // ******************************** Constructors *********************************************

    /**
     * Creation of Behaviour without providing dataOut. However, there can be some dataIn hidden in guards and
     * transitions
     *
     * @param componentType
     * @param currentState
     * @param allTransitions
     * @param allPorts
     * @param states
     * @param guards
     * @param component
     * @param invariant
     * @param transitionToPreConditionMap
     * @param transitionToPostConditionMap
     * @param stateToPredicateMap
     * @throws BIPException
     */
    public BehaviourImpl(String componentType, String currentState, ArrayList<ExecutableTransition> allTransitions,
                         ArrayList<Port> allPorts, HashSet<String> states, Collection<Guard> guards, Object component, InvariantImpl invariant, HashMap<TransitionImpl, InvariantImpl> transitionToPreConditionMap, HashMap<TransitionImpl, InvariantImpl> transitionToPostConditionMap, HashMap<String, InvariantImpl> stateToPredicateMap)
            throws BIPException {

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
        dataFromTransitionToPorts = new HashMap<String, List<Port>>();
        dataFromGuardsToPorts = new HashMap<String, List<Port>>();
        portsNeedingData = new HashMap<String, List<Port>>();
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

        HashMap<String, Port> mapIdToPort = new HashMap<String, Port>();
        for (Port port : allPorts)
            mapIdToPort.put(port.getId(), port);

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

        transitionToPort = new Hashtable<ExecutableTransition, Port>();
        for (ExecutableTransition transition : enforceableTransitions) {

            Port port = mapIdToPort.get(transition.name());

            transitionToPort.put(transition, port);

            if (transition.hasGuard()) {
                Set<Data<?>> portGuardData = portToDataInForGuard.get(port);
                for (Guard guard : transition.transitionGuards()) {
                    portGuardData.addAll(guard.dataRequired());
                    for (Data<?> data : guard.dataRequired()) {
                        // for each data in guard data, add it to guard collection
                        if (!dataFromGuardsToPorts.containsKey(data.name())) {
                            ArrayList<Port> dataPorts = new ArrayList<Port>();
                            dataPorts.add(port);
                            dataFromGuardsToPorts.put(data.name(), dataPorts);
                        } else {
                            if (!dataFromGuardsToPorts.get(data.name()).contains(port)) {
                                dataFromGuardsToPorts.get(data.name()).add(port);
                            }
                        }
                        // for each data in guard data, add it to general collection (if not there)
                        updatePortsNeedingData(port, data);
                    }
                }
            }

            // (foreach is in enforceable but not in all)
            Set<Data<?>> portTransitionData = portToDataInForTransition.get(port);
            for (Data<?> data : transition.dataRequired()) {
                // for each data in transitions data, add it to transition collection
                if (!dataFromTransitionToPorts.containsKey(data.name())) {
                    ArrayList<Port> dataPorts = new ArrayList<Port>();
                    dataPorts.add(port);
                    dataFromTransitionToPorts.put(data.name(), dataPorts);
                } else {
                    if (!dataFromTransitionToPorts.get(data.name()).contains(port)) {
                        dataFromTransitionToPorts.get(data.name()).add(port);
                    }
                }
                // for each data in transition data, add it to general collection (if not there)
                updatePortsNeedingData(port, data);
                portTransitionData.add(data);
            }

            Set<Port> stateports = stateToPorts.get(transition.source());
            if (stateports == null) {
                throw new BIPException("The source state " + transition.source() + " for transition "
                        + transition.name() + " is not in the list of states of component " + componentType);
            }
            stateports.add(port);

        }

        this.invariant = invariant;
        this.transitionToPreConditionMap = transitionToPreConditionMap;
        this.transitionToPostConditionMap = transitionToPostConditionMap;
        this.stateToPredicateMap = stateToPredicateMap;
    }

    private void updatePortsNeedingData(Port port, Data<?> data) {
        if (!portsNeedingData.containsKey(data.name())) {
            ArrayList<Port> dataPorts = new ArrayList<Port>();
            dataPorts.add(port);
            portsNeedingData.put(data.name(), dataPorts);
        } else {
            if (!portsNeedingData.get(data.name()).contains(port)) {
                portsNeedingData.get(data.name()).add(port);
            }
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
     * @param invariant
     * @param transitionToPreConditionMap
     * @param transitionToPostConditionMap
     * @param stateToPredicateMap
     * @throws BIPException
     */
    public BehaviourImpl(String type, String currentState, ArrayList<ExecutableTransition> allTransitions,
                         ArrayList<Port> allPorts, HashSet<String> states, Collection<Guard> guards,
                         ArrayList<DataOutImpl<?>> dataOut, Hashtable<String, MethodHandle> dataOutName, Object component, InvariantImpl invariant, HashMap<TransitionImpl, InvariantImpl> transitionToPreConditionMap, HashMap<TransitionImpl, InvariantImpl> transitionToPostConditionMap, HashMap<String, InvariantImpl> stateToPredicateMap)
            throws BIPException {

        this(type, currentState, allTransitions, allPorts, states, guards, component, invariant, transitionToPreConditionMap, transitionToPostConditionMap, stateToPredicateMap);

        this.dataOut = dataOut;
        this.dataOutName = dataOutName;

    }

    // *************************** End of Constructors *******************************************

    // ******************************* Getter functions ******************************************
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

    public Map<String, MethodHandle> getDataOutMapping() {
        return dataOutName;
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

    public List<Port> portsNeedingData(String dataName) {
        return portsNeedingData.get(dataName);
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
        List<Transition> result = new ArrayList<Transition>(allTransitions);
        return result;
    }

    // ************************** End of Getter functions ****************************************

    // ***************************** Transition existence ****************************************

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
    public boolean existInCurrentStateAndEnabledEnforceableWithoutData(Map<String, Boolean> guardToValue)
            throws BIPException {

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
    public boolean existInCurrentStateAndEnabledSpontaneous(Map<String, Boolean> guardToValue) {

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
                    throw new BIPException("Cannot have two enabled internal transitions in the state "
                            + this.currentState + " in component " + this.componentType);
                } else {
                    internalEnabled = true;
                }
            }
        }
        return internalEnabled;

    }

    @Override
    public boolean isSpontaneousPort(String port) {
        if (port == null || port.isEmpty()) {
            throw new IllegalArgumentException("The name of the required port for the component "
                    + bipComponent.getClass().getName() + " cannot be null or empty.");
        }
        return spontaneousPorts.containsKey(port);
    }

    // ************************** End of Transition existence ************************************

    // ************************************** Enabledness ****************************************

    private boolean isInCurrentStateAndEnabled(ExecutableTransition transition, Map<String, Boolean> guardToValue) {

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
                if (transition.hasGuard() && !transition.hasDataOnGuards() && !transition.guardIsTrue(guardToValue)) {
                    result.add(transitionToPort.get(transition));
                }

            }
        }
        return result;
    }

    public Map<String, Boolean> computeGuardsWithoutData(String currentState) {
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
                            guardToValue.put(guard.name(), guard.evaluateGuard(args));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return guardToValue;
    }

    public List<Boolean> checkEnabledness(String port, List<Map<String, Object>> data) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, BIPException {

        ArrayList<Boolean> result = new ArrayList<Boolean>();
        ExecutableTransition transition = getTransition(currentState, port);
        // TODO DESIGN, find out why this can happen if the guard is not there,
        // it does not need data, any data is good, no need to do check Enabledness?
        // -- I think it does not happen, but it's here just in case
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
                    guardToValue.put(guard.name(), guard.evaluateGuard(args));
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
                    guardToValue.put(guard.name(), guard.evaluateGuard(args.toArray()));
                }
            }
            result.add(transition.guardIsTrue(guardToValue));
        }
        return result;
    }

    // ******************************* End of enabledness ****************************************
    // ************************************ Execution ********************************************

    public void execute(String portID, Map<String, ?> data) throws BIPException {
        // this component does not take part in the interaction

        if (portID == null) {
            return;
        }

        //check state predicate;
        checkStatePredicate(currentState);

        // getTransition works correctly with spontaneous as well, as it addresses the list of all transitions
        ExecutableTransition transition = getTransition(currentState, portID);

        //checkTransitionPreCondition(transition);
        checkTransitionCondition(transition, true);

        invokeMethod(transition, data);

        //checkTransitionPostcondition(transition);
        checkTransitionCondition(transition, false);
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

        //checkTransitionPreCondition(transition);
        checkTransitionCondition(transition, true);

        invokeMethod(transition);

        //checkTransitionPostcondition(transition);
        checkTransitionCondition(transition, false);
    }

    public void executeInternal(Map<String, Boolean> guardToValue) throws BIPException {

        for (ExecutableTransition transition : stateTransitions.get(currentState)) {
            if (transition.getType().equals(PortType.internal) && transition.guardIsTrue(guardToValue)) {
                invokeMethod(transition);
                return;
            }
        }

        throw new BIPException("There is no enabled internal transition in state " + this.currentState
                + " for component " + this.componentType + ". This exception is internal to the implementation.");

    }

    private void invokeMethod(ExecutableTransition transition) {
        MethodHandle methodHandle;
        String errorMessage = "The following exception while executing " + transition.name() + " in component "
                + this.componentType;
        try {
            logger.info("Invocation: " + transition.name());
            methodHandle = transition.methodHandle();
            if (!transition.method().getDeclaringClass().isAssignableFrom(componentClass)) {
                throw new IllegalArgumentException("The method " + transition.method().getName()
                        + " belongs to the class " + transition.method().getDeclaringClass().getName()
                        + " but not  to the class of the component " + componentClass.getName());
            }
            Object[] args = new Object[1];
            args[0] = bipComponent;
            methodHandle.invokeWithArguments(args);

            performTransition(transition);

        } catch (SecurityException e) {
            ExceptionHelper.printExceptionTrace(logger, e, errorMessage);
        } catch (IllegalAccessException e) {
            ExceptionHelper.printExceptionTrace(logger, e, errorMessage);
        } catch (IllegalArgumentException e) {
            ExceptionHelper.printExceptionTrace(logger, e, errorMessage);
        } catch (InvocationTargetException e) {
            ExceptionHelper.printExceptionTrace(logger, e, errorMessage);
            ExceptionHelper.printExceptionTrace(logger, e.getCause());
        } catch (BIPException e) {
            ExceptionHelper.printExceptionTrace(logger, e, errorMessage);
        } catch (Throwable e) {
            ExceptionHelper.printExceptionTrace(logger, e);
        }
    }

    private void invokeMethod(ExecutableTransition transition, Map<String, ?> data) {
        java.lang.reflect.Method componentMethod;
        MethodHandle methodHandle;
        try {
            componentMethod = transition.method();
            methodHandle = transition.methodHandle();
            if (!componentMethod.getDeclaringClass().isAssignableFrom(componentClass)) {
                throw new IllegalArgumentException("The method " + componentMethod.getName() + " belongs to the class "
                        + componentMethod.getDeclaringClass().getName() + " but not  to the class of the component "
                        + componentClass.getName());
            }

            Object[] args = new Object[componentMethod.getParameterTypes().length + 1];
            args[0] = bipComponent;
            int i = 1;
            /*
             * NOTE dataRequired works in the same manner for enforceable and spontaneous transitions, since it is
             * processed by the builder who does not differentiate between them
             */
            for (Data<?> trData : transition.dataRequired()) {
                // name parameter can not be null as it is enforced by the constructor.
                Object value = data.get(trData.name());
                // TODO, CHECK, value can be null if the map is not properly constructed. Throw more informative
                // exception.
                args[i] = value;
                i++;
            }
            logger.debug("In component " + this.componentType + " INVOCATION of " + transition.name() + " with args "
                    + data);
            // if the method is not static, the first param to invokeExact should be treated as instance where to look
            // for the method
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
            ExceptionHelper.printExceptionTrace(logger, e.getTargetException());
        } catch (BIPException e) {
            ExceptionHelper.printExceptionTrace(logger, e);
        } catch (Throwable e) {
            ExceptionHelper.printExceptionTrace(logger, e);
        }

    }

    private void performTransition(ExecutableTransition transition) throws BIPException {
        if (!currentState.equals(transition.source())) {
            throw new BIPException("Could not perform transition " + transition.name() + " of component "
                    + componentType + " because the component is in the wrong state " + currentState
                    + " instead of state " + transition.source());
        }
        currentState = transition.target();
    }

    // ****************************** End of Execution *******************************************

    // ****************************** Invariants *******************************************

    @Override
    public Pair<Boolean, String> checkInvariant() {
        if (invariant != null)
            synchronized (this) {
                try {
                    //return invariant.evaluateInvariant(componentClass, bipComponent);
                    return new Pair(invariant.evaluateInvariant(componentClass, bipComponent), invariant.expression());
                } catch (Exception e) { //TODO throw custom exception
                    e.printStackTrace();
                    throw e;
                }
            }
        //if there is no precondition we just ignore this execution
        return new Pair(true, "");
    }

    @Override
    public Pair<Boolean, String> checkTransitionCondition(Object transition, Boolean pre) {
        InvariantImpl condition;
        String message;
        if (pre) {
            condition = transitionToPreConditionMap.get((TransitionImpl) transition);
            message = "PRE-CONDITION VIOLATION: ";
        } else {
            condition = transitionToPostConditionMap.get((TransitionImpl) transition);
            message = "POST-CONDITION VIOLATION: ";
        }

        if (condition != null) {
            synchronized (this) {
                try {
                    Pair<Boolean, String> result = new Pair(condition.evaluateInvariant(componentClass, bipComponent), condition.expression());
                    if (!result.getKey()) {
                        System.out.println(message + result.getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return new Pair(true, "");
    }

    @Override
    public Pair<Boolean, String> checkStatePredicate(String currentState) {
        InvariantImpl statePredicate = stateToPredicateMap.get(currentState);
        if (statePredicate != null) {
            synchronized (this) {
                try {
                    //return invariant.evaluateInvariant(componentClass, bipComponent);
                    Pair<Boolean, String> result = new Pair(statePredicate.evaluateInvariant(componentClass, bipComponent), statePredicate.expression());
                    if (!result.getKey()) {
                        System.out.println("STATE PREDICATE VIOLATION: " + result.getValue() + ", STATE: " + currentState);
                    }
                } catch (Exception e) { //TODO throw custom exception
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        //if there is no precondition we just ignore this execution
        return new Pair(true, "");
    }


    // ****************************** End of Invariant *******************************************

}
