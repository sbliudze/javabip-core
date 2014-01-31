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

public interface BIPEngine {

	void specifyGlue(BIPGlue glue);

	void register(BIPComponent component, Behaviour behaviour);

	// It only specifies the disabled ports with no data transfers, some ports with data
	// transfers may have disabled status but this has to be obtain by querying BIPexecutor with the help
	// of checkEnabledness. If there are no for certain ports that are disabled then this array list is empty.
	// A port that requires data transfer may still be disabled no matter what data transfer occurs that can
	// be already indicated here, so the ports specified within disabledPorts may require data transfers.
	void inform(BIPComponent component, String currentState, Set<Port> disabledPorts);

	/**
	 * InformSpecific is called multiple times for each component instance. In particular, it is called for each row of the data related table of a
	 * particular component of the DataCoordinator.
	 */
	void informSpecific(BIPComponent decidingComponent, Port decidingPort, Map<BIPComponent, Set<Port>> disabledCombinations);

	/**
	 * It starts the procedure of control of the registered BIP components with the help of this BIP engine.
	 */
	void start();

	void stop();

	void execute();
}
