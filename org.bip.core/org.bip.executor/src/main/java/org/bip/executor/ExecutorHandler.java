package org.bip.executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.bip.api.Executor;

public class ExecutorHandler implements InvocationHandler {

	private Executor internalExecutor;
	private Object bipSpec;
	
	public ExecutorHandler(Executor executor, Object bipSpec) {
		this.internalExecutor = executor;
		this.bipSpec = bipSpec;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Class<?> declaredClass = method.getDeclaringClass();
		
		if (Executor.class.isAssignableFrom(declaredClass)) {
		    return method.invoke(internalExecutor, args);
		}
		else 
			return method.invoke(bipSpec, args);
		
	}

	public static Object newProxyInstance(ClassLoader classLoader, Executor executor, Object bipSpec) {
		
		ExecutorHandler handler = new ExecutorHandler(executor, bipSpec);
				
		Class<?>[] interfaces = bipSpec.getClass().getInterfaces();
		
		if (interfaces.length == 0) 
				throw new IllegalArgumentException("BIP Spec object does not implement any interface thus no proxy can be created.");
		
		return Proxy.newProxyInstance(classLoader, interfaces, handler);
                     
	}
	
}
