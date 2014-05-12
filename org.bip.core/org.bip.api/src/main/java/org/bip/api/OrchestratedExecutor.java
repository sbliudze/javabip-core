/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

/**
 * 
 * It specifies the executor that is given step instructions to perform its role.
 *
 */
public interface OrchestratedExecutor extends Executor {
	
	void step();

}
