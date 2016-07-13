/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.api;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bip.exceptions.BIPException;

/**
 * It provides an executable behavior that makes it possible to execute transitions within the behavior.
 */
public interface ExecutableBehaviour extends Behaviour {
	// It is put in the API and made public in order for developers to be able to use it while doing their own executors.
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public String getCurrentState();

	/**
	 * Execute the enforceable transition or spontaneous event based transition as specified by the port.
	 *
	 * @param portId the port id
	 */
	public void executePort(String portId);

	/**
	 * Execute the enforceable or spontaneous event based transition which needs data that is provided as map parameter.
	 *
	 * @param portID the port id
	 * @param data the data for the enforceable transition.
	 */
	public void execute(String portID, Map<String, ?> data);

	/**
	 * It attempts to execute an internal transition if any is enabled. 
	 *
	 * @param guardToValue the guard to value
	 */
	public void executeInternal(Map<String, Boolean> guardToValue);

	/**
	 * Checks for transition from current state.
	 * Enforceable transition with guards with data is treated as enabled, as the guards value is not known and 
	 * depends on the value provided.
	 *
	 * @param portID the port id
	 * @return true, if successful
	 */
	public boolean hasEnabledTransitionFromCurrentState(String portID, Map<String, Boolean> guardToValue);

	/**
	 * Gets the method name to java method mapping for data out.
	 *
	 * @return the data out mapping
	 */
	public Map<String, MethodHandle> getDataOutMapping();

	/**
	 * Check enabledness.
	 *
	 * @param port the port
	 * @param data the data
	 * @return the list
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	// TODO TEST Discussion point, what if guard expression (e.g. !f && g ) requires some data, will it work? Add test for this case.
	public List<Boolean> checkEnabledness(String port, List<Map<String, Object>> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	/**
	 * Gets the globally disabled ports within the current state the behaviour is in.
	 *
	 * @param guardToValue the guard to value
	 * @return the globally disabled ports
	 */
	public Set<Port> getGloballyDisabledEnforceablePortsWithoutDataTransfer(Map<String, Boolean> guardToValue);

	/**
	 * It returns true if there is an enforceable transition that does not need data.
	 * @param guardToValue values of guards (without data)
	 * @return
	 * @throws BIPException
	 */
	public boolean existInCurrentStateAndEnabledEnforceableWithoutData(Map<String, Boolean> guardToValue) throws BIPException;
	
	/**
	 * Return true if a transition corresponding to the particular port in the current state has guards on data but does not have data itself.
	 * @param port name of the port (transition)
	 * @return
	 * @throws BIPException
	 */
	// Big TODO: Check implementation
	public boolean transitionNoDataGuardData(String port) throws BIPException;
	
	/**
	 * It returns true if there is an enabled spontaneous transition.
	 * @param guardToValue
	 * @return
	 * @throws BIPException
	 */
	public boolean existInCurrentStateAndEnabledSpontaneous(Map<String, Boolean> guardToValue) throws BIPException;
	
	/**
	 * It returns true if there is an enabled internal transition.
	 * @param guardToValue
	 * @return
	 * @throws BIPException
	 */
	public boolean existEnabledInternal(Map<String, Boolean> guardToValue) throws BIPException;
	
	public boolean existInCurrentStateAndEnforceableWithData();
	
	/**
	 * Compute guards.
	 * 
	 * @param currentState
	 * 
	 * @return the hashtable
	 */
	public Map<String, Boolean> computeGuardsWithoutData(String currentState);
}
