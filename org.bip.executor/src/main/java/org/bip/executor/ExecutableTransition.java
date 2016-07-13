/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package org.bip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Map;

import org.bip.api.Data;
import org.bip.api.PortType;
import org.bip.api.Transition;

interface ExecutableTransition extends Transition {

	public Method method();
	
	public MethodHandle methodHandle();

	public Iterable<Data<?>> dataRequired();

	public boolean hasDataOnGuards();
	
	public boolean hasData();

	public boolean guardIsTrue(Map<String, Boolean> guardToValue);

	public PortType getType();

}
