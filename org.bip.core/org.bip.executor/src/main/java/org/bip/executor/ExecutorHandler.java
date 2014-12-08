package org.bip.executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.bip.api.BIPActor;
import org.bip.api.BIPComponent;
import org.bip.api.Executor;
import org.bip.api.Identifiable;
import org.bip.api.OrchestratedExecutor;

public class ExecutorHandler implements InvocationHandler {

	private OrchestratedExecutor internalExecutor;
	private Object bipSpec;
	
	public ExecutorHandler(OrchestratedExecutor executor, Object bipSpec) {
		this.internalExecutor = executor;
		this.bipSpec = bipSpec;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Class<?> declaredClass = method.getDeclaringClass();
		
		if (OrchestratedExecutor.class.isAssignableFrom(declaredClass)
				|| Executor.class.isAssignableFrom(declaredClass) || BIPComponent.class.isAssignableFrom(declaredClass)
				|| Identifiable.class.isAssignableFrom(declaredClass) || BIPActor.class.isAssignableFrom(declaredClass)) {
		    return method.invoke(internalExecutor, args);
		}
		else 
			return method.invoke(bipSpec, args);
		
	}

	public static Object newProxyInstance(ClassLoader classLoader, OrchestratedExecutor executor, Object bipSpec) {
		
		ExecutorHandler handler = new ExecutorHandler(executor, bipSpec);
				
		Class<?>[] interfaces = bipSpec.getClass().getInterfaces();
		
		if (interfaces.length == 0) 
				throw new IllegalArgumentException("BIP Spec object does not implement any interface thus no proxy can be created.");
		
		Class<?>[] completeInterfaces = new Class<?>[interfaces.length + 1];
		System.arraycopy(interfaces, 0, completeInterfaces, 0, interfaces.length);
		completeInterfaces[interfaces.length] = OrchestratedExecutor.class;

		return Proxy.newProxyInstance(classLoader, completeInterfaces, handler);
                     
	}
	
}
