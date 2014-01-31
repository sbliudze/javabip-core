package org.bip.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


public interface Guard {
	public String name();

	public Method method();

	public Collection<Data<?>> dataRequired();

	public Boolean hasData();

	public boolean evaluateGuard(Object component) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	public boolean evaluateGuard(Object component, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}