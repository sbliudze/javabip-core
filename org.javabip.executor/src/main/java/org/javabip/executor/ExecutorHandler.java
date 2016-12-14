/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.javabip.executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPComponent;
import org.javabip.api.Executor;
import org.javabip.api.Identifiable;
import org.javabip.api.OrchestratedExecutor;

/**
 * It allows to propagate interfaces that specify asynchronous/spontaneous events from BIP Spec to Executor. This makes
 * it possible to interact with executor in a typed manner without using inform function to send the events.
 * 
 * TODO: Used only in tests and might be replaced by TunellingExecutorHandler.
 * 
 */
public class ExecutorHandler implements InvocationHandler {

	private OrchestratedExecutor internalExecutor;
	private Object bipSpec;

	public ExecutorHandler(OrchestratedExecutor executor, Object bipSpec) {
		this.internalExecutor = executor;
		this.bipSpec = bipSpec;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Class<?> declaredClass = method.getDeclaringClass();

		if (OrchestratedExecutor.class.isAssignableFrom(declaredClass)
				|| Executor.class.isAssignableFrom(declaredClass) || BIPComponent.class.isAssignableFrom(declaredClass)
				|| Identifiable.class.isAssignableFrom(declaredClass) || BIPActor.class.isAssignableFrom(declaredClass)) {
			return method.invoke(internalExecutor, args);
		} else
			return method.invoke(bipSpec, args);

	}

	public static Object newProxyInstance(ClassLoader classLoader, OrchestratedExecutor executor, Object bipSpec) {

		ExecutorHandler handler = new ExecutorHandler(executor, bipSpec);

		Class<?>[] interfaces = bipSpec.getClass().getInterfaces();

		if (interfaces.length == 0)
			throw new IllegalArgumentException(
					"BIP Spec object does not implement any interface thus no proxy can be created.");

		Class<?>[] completeInterfaces = new Class<?>[interfaces.length + 1];
		System.arraycopy(interfaces, 0, completeInterfaces, 0, interfaces.length);
		completeInterfaces[interfaces.length] = OrchestratedExecutor.class;

		return Proxy.newProxyInstance(classLoader, completeInterfaces, handler);

	}

}
