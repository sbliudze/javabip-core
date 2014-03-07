/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * It specifies the behaviour of any BIP component.
 */
// TODO, List and Set interface allows for mutation, use Iterable interface instead.
public interface Behaviour {

	/**
	 * Gets the state to ports.
	 *
	 * @return the state to ports
	 */
	public Map<String, Set<Port>> getStateToPorts();

	/**
	 * Gets the component type for this behavior.
	 *
	 * @return the component type described by this behavior.
	 */
	public String getComponentType();

	/**
	 * Gets the enforceable ports within this behavior.
	 *
	 * @return the enforceable ports
	 */
	public List<Port> getEnforceablePorts();

	/**
	 * Gets the states the component described by this behavior can be in.
	 *
	 * @return the states
	 */
	public List<String> getStates();

	/**
	 * Port to data in for guard.
	 *
	 * @return the map
	 */
	// TODO, do we really need to return the whole Map structure? Maybe simple function to query for Iterable
	// of Data for a given port is sufficient? We avoid mutability and avoid brining the whole part of behavior outside of it.
	// The function below just exactly that so maybe build on 
	public Map<Port, Set<Data<?>>> portToDataInForGuard();

	// TODO, what if a given port is associated with different transitions within behavior and these transitions 
	// have different guards associated to them? This comment applies to both functions of portToDataInForGuard.
	/**
	 * Port to data in for guard.
	 *
	 * @param port the port for which its required data is returned.
	 * @return the sets of data required by the guard to the given transition associated with a given port.
	 */
	public Set<Data<?>> portToDataInForGuard(Port port);

	/**
	 * Port to data in for transition.
	 *
	 * @param port the port
	 * @return the iterable
	 */
	public Iterable<Data<?>> portToDataInForTransition(Port port);

	/**
	 * Ports needing data.
	 *
	 * @param dataName the data name
	 * @return the list
	 */
	public List<Port> portsNeedingData(String dataName);

	/**
	 * Gets the data providing ports.
	 *
	 * @param dataName name of the data out variable.
	 * @return set of ports for which the data out is provided.
	 */
	public Set<Port> getDataProvidingPorts(String dataName);

}