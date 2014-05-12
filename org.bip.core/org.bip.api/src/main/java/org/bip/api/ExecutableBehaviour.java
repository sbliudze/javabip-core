/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void execute(String portId);

	/**
	 * Execute the enforceable transition which needs data that is provided as map parameter.
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
	 *
	 * @param portID the port id
	 * @return true, if successful
	 */
	public boolean hasEnabledTransitionFromCurrentState(String portID, Map<String, Boolean> guardToValue);

	/**
	 * Gets the data out mapping.
	 *
	 * @return the data out mapping
	 */
	public Map<String, Method> getDataOutMapping();

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
	// TODO, Disccussion point, what if guard expression (e.g. !f && g ) requires some data, will it work? Add test for this case.
	public List<Boolean> checkEnabledness(String port, List<Map<String, Object>> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	/**
	 * Gets the globally disabled ports within the current state the behaviour is in.
	 *
	 * @param guardToValue the guard to value
	 * @return the globally disabled ports
	 */
	public Set<Port> getGloballyDisabledPorts(Map<String, Boolean> guardToValue);

	/**
	 * Exist enabled.
	 *
	 * @param transitionType the transition type
	 * @param guardToValue the guard to value
	 * @return true, if successful
	 */
	public boolean existEnabled(PortType transitionType, Map<String, Boolean> guardToValue);

	// TODO, Change Hashtable into an interface, map.
	/**
	 * Compute guards.
	 *
	 * @return the hashtable
	 */
	public Hashtable<String, Boolean> computeGuardsWithoutData();
}
