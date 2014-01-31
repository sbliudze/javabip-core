/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */
package org.bip.api;

//This interface is used for the port to be able to have information about the component instance it belongs to.
//We cannot provide the component right away in the case of executable behaviour, because the ports are created without the knowledge of BIPComponent, i.e. the Executor..
public interface ComponentProvider {

	public BIPComponent getComponent();

}
