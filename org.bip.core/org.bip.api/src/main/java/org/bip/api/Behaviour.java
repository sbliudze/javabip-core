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
 * It specifies the behaviour of any BIP component. Instances of those class are meant to be immutable
 * even if using data structures that provide a possibility for mutation. Do not mutate. 
 */
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
	 * Returns true is the port is spontaneous.
	 * @param port the name of the port, can not be null or empty.
	 * @return true if the port is spontaneous, otherwise fault.
	 */
	public boolean isSpontaneousPort(String port);

	/**
	 * Gets the states the component described by this behavior can be in.
	 *
	 * @return the states
	 */
	public Set<String> getStates();

	/**
	 * Port to data in for guard.
	 *
	 * @return the map
	 */
	public Map<Port, Set<Data<?>>> portToDataInForGuard();

	// TODO DESIGN BUG what if a given port is associated with different transitions within behavior and these transitions 
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