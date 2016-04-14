/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.util.Map;
import java.util.Set;

/**
 * The Interface BIPEngine.
 */
public interface BIPEngine {

	/**
	 * It specify the BIP glue to be enforced by BIP engine when choosing interactions.
	 *
	 * @param glue the BIP glue
	 */
	void specifyGlue(BIPGlue glue);

	/**
	 * It register a given component with its behavior within the engine to be managed by the engine.
	 *
	 * @param component the component
	 * @param behaviour the behaviour
	 */
	BIPActor register(Object component, String id, boolean useSpec);

	// It only specifies the disabled ports with no data transfers, some ports with data
	// transfers may have disabled status but this has to be obtain by querying BIPexecutor with the help
	// of checkEnabledness. If there are no for certain ports that are disabled then this array list is empty.
	// A port that requires data transfer may still be disabled no matter what data transfer occurs that can
	// be already indicated here, so the ports specified within disabledPorts may require data transfers.
	/**
	 * It informs BIP engine about the state of the BIP component.
	 *
	 * @param component the component for which the information about the state is provided.
	 * @param currentState the current state of the component for which the information is provided.
	 * @param disabledPorts the disabled ports of the component for which the information is provided.
	 */
	void inform(BIPComponent component, String currentState, Set<Port> disabledPorts);

	/**
	 * InformSpecific is called multiple times for each component instance. It allows for the deciding 
	 * component to specify for each deciding port the disabled combinations for each potential collaborator 
	 * of deciding component. 
	 *
	 * @param decidingComponent the deciding component
	 * @param decidingPort the deciding port
	 * @param disabledCombinations the disabled combinations
	 */
	void informSpecific(BIPComponent decidingComponent, Port decidingPort, Map<BIPComponent, Set<Port>> disabledCombinations);

	/**
	 * It starts the BIP engine thread.
	 */
//	void start();

	/**
	 * It stops the BIP engine thread.
	 */
	void stop();

	/**
	 * It starts the execution of BIP engine cycles for the registered BIP components.
	 */
//	void execute();


	void initialize();

}
