package org.bip.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.annotations.Transitions;
import org.bip.api.BIPActor;
import org.bip.api.BIPComponent;
import org.bip.api.Data;
import org.bip.api.Executor;
import org.bip.api.Identifiable;
import org.bip.api.OrchestratedExecutor;
import org.bip.api.PortType;
import org.bip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TunellingExecutorHandler implements InvocationHandler {

	private Logger logger = LoggerFactory.getLogger(TunellingExecutorHandler.class);
	
	private MethodWithDifferentEquals globalWrapper = new MethodWithDifferentEquals(null);
	
	class MethodWithDifferentEquals {
	
		public Method method;
		
		public MethodWithDifferentEquals(Method method) {
			this.method = method;
		}
	    
		public boolean equals(Object obj) {
	    	if (obj != null && obj instanceof MethodWithDifferentEquals) {
	    	    Method other = ((MethodWithDifferentEquals)obj).method;
	    	    if (method.getName().equals( other.getName())) {
	    		if (!method.getReturnType().equals(other.getReturnType()))
	    		    return false;
	    		/* Avoid unnecessary cloning */
	    		Class[] params1 = method.getParameterTypes();
	    		Class[] params2 = other.getParameterTypes();
	    		if (params1.length == params2.length) {
	    		    for (int i = 0; i < params1.length; i++) {
	    			if (params1[i] != params2[i])
	    			    return false;
	    		    }
	    		    return true;
	    		}
	    	    }
	    	}
	    	return false;
	    }
	    
	    public int hashCode() {
	    	  return method.getName().hashCode();
	    }
		
	}
	
	private OrchestratedExecutor internalExecutor;
	private Object bipSpec;	
	
	private HashMap<MethodWithDifferentEquals, String> tunellingPortName;
	private HashMap<MethodWithDifferentEquals, ArrayList<String>> tunellingArgsNames;
	
	private Method informNoParameters;
	private Method informWithData;
	
	public TunellingExecutorHandler(OrchestratedExecutor executor, Object bipSpec) {
		this.internalExecutor = executor;
		this.bipSpec = bipSpec;
		this.tunellingPortName = new HashMap<MethodWithDifferentEquals, String>();
		this.tunellingArgsNames = new HashMap<MethodWithDifferentEquals, ArrayList<String>>();
		
		try {
			this.informNoParameters = BIPActor.class.getDeclaredMethod("inform", String.class);
		} catch (SecurityException e) {
			throw new BIPException(e);
		} catch (NoSuchMethodException e) {
			throw new BIPException(e);
		}
		
		try {
			this.informWithData = BIPActor.class.getDeclaredMethod("inform", String.class, Map.class);
		} catch (SecurityException e) {
			throw new BIPException(e);
		} catch (NoSuchMethodException e) {
			throw new BIPException(e);
		}
		
		Class<?> componentClass = bipSpec.getClass();
		
		ArrayList<String> namesOfSpontaneousPorts = new ArrayList<String>();
		
		Annotation classAnnotation = componentClass.getAnnotation(Ports.class);
		
		if (classAnnotation instanceof Ports) {
			Ports ports = (Ports) classAnnotation;
			org.bip.annotations.Port[] portArray = ports.value();
			for (org.bip.annotations.Port annotation : portArray) {
				
				if (annotation instanceof org.bip.annotations.Port) {
					Port portAnnotation = (org.bip.annotations.Port) annotation;
							 
					if (portAnnotation.type().equals(PortType.spontaneous)) {
						namesOfSpontaneousPorts.add(portAnnotation.name());
					}
						
				}

			}
		} else {
			throw new BIPException("Port information for the BIP component is not specified.");
		}
		
		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof org.bip.annotations.Transition) {
					
					parseTransition(method, (org.bip.annotations.Transition) annotation, namesOfSpontaneousPorts);

				} else if (annotation instanceof Transitions) {
					Transitions transitionsAnnotation = (Transitions) annotation;
					Annotation[] transitionAnnotations = transitionsAnnotation.value();
					for (Annotation bipTransitionAnnotation : transitionAnnotations) {
						
						parseTransition(method, (org.bip.annotations.Transition) bipTransitionAnnotation, namesOfSpontaneousPorts);
					}
				}
			}
		}
		
		logger.info("Tunneling executor handler for executor {} and bipSpec {} has been created.", executor, bipSpec);
		logger.debug("TunellingPortNames {}", tunellingPortName);
		logger.debug("TunnelingArgsNames {}", tunellingArgsNames);
		
	}
	
	private void parseTransition(Method method,	Transition bipTransitionAnnotation, ArrayList<String> namesOfSpontaneousPorts) {
		
		if (!namesOfSpontaneousPorts.contains(bipTransitionAnnotation.name()))
			// Transition is not spontaneous.
			return;
		
		MethodWithDifferentEquals wrapper = new MethodWithDifferentEquals(method);
		
		tunellingPortName.put(wrapper, bipTransitionAnnotation.name());
		
		ArrayList<String> dataNames = new ArrayList<String>();
		tunellingArgsNames.put(wrapper, dataNames);
		for ( Data<?> data : ReflectionHelper.parseDataAnnotations(method)) {
			dataNames.add(data.name());
		}		
		
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
		else {
			
			globalWrapper.method = method;
			
			logger.debug("Calling method {}", method);
			
			if (tunellingPortName.containsKey(globalWrapper)) {
				
				if (args == null || args.length == 0) {
					// inform without data
					Object[] tunneledArguments = new Object[1];
					tunneledArguments[0] = tunellingPortName.get(globalWrapper);
					logger.debug("Tunneled call no parameters.");
					return informNoParameters.invoke(internalExecutor, tunneledArguments);
				}
				else {
					Object[] tunneledArguments = new Object[2];
					tunneledArguments[0] = tunellingPortName.get(globalWrapper);
					HashMap<String, Object> map = new HashMap<String, Object>();
					ArrayList<String> argsNames = tunellingArgsNames.get(globalWrapper);
										
					if (args.length != argsNames.size())
						throw new BIPException("Internal error. Number of arguments does not match. ");
					
					for (int i = 0; i < args.length; i++) {
						map.put(argsNames.get(i), args[i]);
					}
					
					tunneledArguments[1] = map;			
					logger.debug("Tunnneled call to port {} with map {} ", tunneledArguments[0], tunneledArguments[1]);
					return informWithData.invoke(internalExecutor, tunneledArguments);
				}
				
			}
			else {
				logger.debug("Not tunneled call to method {}", method);
				return method.invoke(bipSpec, args);
			}
			
		}
		
	}
	
	public static Object newProxyInstance(ClassLoader classLoader, OrchestratedExecutor executor, Object bipSpec) {
		
		TunellingExecutorHandler handler = new TunellingExecutorHandler(executor, bipSpec);
				
		Class<?>[] interfaces = bipSpec.getClass().getInterfaces();
		
		if (interfaces.length == 0) 
				throw new IllegalArgumentException("BIP Spec object does not implement any interface thus no proxy can be created.");
		
		Class<?>[] completeInterfaces = new Class<?>[interfaces.length + 1];
		System.arraycopy(interfaces, 0, completeInterfaces, 0, interfaces.length);
		completeInterfaces[interfaces.length] = OrchestratedExecutor.class;

		return Proxy.newProxyInstance(classLoader, completeInterfaces, handler);
                     
	}
	
}
