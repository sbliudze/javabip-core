package org.bip.api;

import java.util.List;

public interface BIPBuilderBehaviour extends Behaviour {

	public List<Transition> getAllTransitions();

	public String getCurrentState();

	public Iterable<Port> getAllPorts();

	// public List<Guard> getGuards();
}
