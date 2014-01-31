package org.bip.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Behaviour {

	public Map<String, Set<Port>> getStateToPorts();

	public String getComponentType();

	public List<Port> getEnforceablePorts();

	public List<String> getStates();

	public Map<Port, Set<Data<?>>> portToDataInForGuard();

	public Set<Data<?>> portToDataInForGuard(Port port);

	public Iterable<Data<?>> portToDataInForTransition(Port port);

	public List<Port> portsNeedingData(String dataName);

	/**
	 * 
	 * @param dataName
	 *            Name of the data out variable
	 * @return List of Ports upon which the data out is provided. The order is not important, it can be a set, but it is dependent on other structures
	 *         which are lists.
	 */
	public Set<Port> getDataProvidingPorts(String dataName);
}