/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.bip.api.Data;
import org.bip.api.Guard;
import org.bip.api.PortType;
import org.bip.api.Transition;

interface ExecutableTransition extends Transition {

	public Method method();

	public String guard();

	public Iterable<Data<?>> dataRequired();

	public boolean hasDataOnGuards();
	
	public boolean hasData();

	public boolean guardIsTrue(Map<String, Boolean> guardToValue);

	public PortType getType();

	public String name();

	public String source();

	public String target();

	public boolean hasGuard();

	public Collection<Guard> transitionGuards();
}
