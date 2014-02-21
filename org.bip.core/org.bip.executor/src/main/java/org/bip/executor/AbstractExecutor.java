package org.bip.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bip.annotations.bipComponentType;
import org.bip.annotations.bipData;
import org.bip.annotations.bipExecutableBehaviour;
import org.bip.annotations.bipGuard;
import org.bip.annotations.bipPort;
import org.bip.annotations.bipPorts;
import org.bip.annotations.bipTransition;
import org.bip.annotations.bipTransitions;
import org.bip.api.BIPEngine;
import org.bip.api.ComponentProvider;
import org.bip.api.Data;
import org.bip.api.DataOut;
import org.bip.api.ExecutableBehaviour;
import org.bip.api.Executor;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortBase;
import org.bip.exceptions.BIPException;
import org.bip.impl.DataImpl;
import org.bip.impl.GuardImpl;
import org.bip.impl.PortImpl;
import org.bip.impl.TransitionImpl;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

/**
 * Creates a Behaviour for the future use of the Executor
 */
public abstract class AbstractExecutor implements Executor, ComponentProvider {

	protected Object bipComponent;
	protected String componentName;
	protected ExecutableBehaviour behaviour;
	protected Class<?> componentClass;

	// we use it for the ability to persist administration setup within
	// BIPengine
	// so where CF restarts the BIP coordination can automatically restart.
	// TODO find a way to get a unique name that is persistent across different
	// CF executions
	private String uniqueName;
	private boolean useSpec;

	public AbstractExecutor(Object bipComponent, boolean useSpec) throws BIPException {
		this.bipComponent = bipComponent;
		this.componentClass = bipComponent.getClass();
		this.useSpec = useSpec;
		this.initialize();
	}

	public void register(BIPEngine engine) {
		engine.register(this, behaviour);
	}

	// TODO: synchronize setId and getId
	public String getId() {
		return componentClass.getName();
	}

//	public void setId(String uniqueGlobalId) {
//		uniqueName = uniqueGlobalId;
//	}

	private void initialize() throws BIPException {
		BehaviourBuilder builder = new BehaviourBuilder();
		if (useSpec) {
			builder = parseAnnotations(builder);
		} else {
			builder = getExecutableBehaviour(builder);
		}

		this.behaviour = builder.buildWithData(this);
		componentName = behaviour.getComponentType();
	}

	private BehaviourBuilder getExecutableBehaviour(BehaviourBuilder builder) throws BIPException {
		// TODO: executable behaviour must be got not from the annotation but
		// from the method name? or return type?
		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof bipExecutableBehaviour) {
					Class<?> returnType = method.getReturnType();
					if (!BehaviourBuilder.class.isAssignableFrom(returnType)) {
						throw new BIPException("Method " + method.getName() + "annotated with @bipExecutableBehaviour should have a return type BehaviourBuilder");
					}
					try {
						if (method.getParameterTypes() != null && method.getParameterTypes().length != 0) {
							throw new BIPException("The method " + method.getName() + " for getting executable behaviour for component " + bipComponent.getClass().getName()
									+ "must have no arguments.");
						}
						builder = (BehaviourBuilder) method.invoke(bipComponent);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return builder;
	}

	// TODO create type for Transitions or make three classes for int, sp, enf
	private BehaviourBuilder parseAnnotations(BehaviourBuilder builder) throws BIPException {
		builder.setComponent(bipComponent);
		Annotation classAnnotation = componentClass.getAnnotation(bipComponentType.class);
		// get component name and type
		if (classAnnotation instanceof bipComponentType) {
			bipComponentType initialState = (bipComponentType) classAnnotation;
			String initial = initialState.initial();
			componentName = initialState.name();
			builder.setComponentType(componentName);
			builder.setInitialState(initial);
		} else {
			throw new BIPException("bipComponentType annotation is not specified.");
		}

		// get ports
		ArrayList<Port> componentPorts = new ArrayList<Port>();
		classAnnotation = componentClass.getAnnotation(bipPorts.class);
		if (classAnnotation instanceof bipPorts) {
			bipPorts ports = (bipPorts) classAnnotation;
			bipPort[] portArray = ports.value();
			for (bipPort port : portArray) {
				Port p = new PortImpl(port.name(), port.type(), componentClass.getCanonicalName(), this);
				builder.addPort(p);
				componentPorts.add(p);
			}
		} else {
			throw new BIPException("Port information for the BIP component is not specified.");
		}

		// get transitions & guards & data
		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof bipTransition) {
					Iterable<Data<?>> transitionData = extractParamAnnotations(method);
					addTransition(method, annotation, transitionData, builder);

				} else if (annotation instanceof bipTransitions) {
					bipTransitions transitionsAnnotation = (bipTransitions) annotation;
					Annotation[] transitionAnnotations = transitionsAnnotation.value();
					for (Annotation bipTransitionAnnotation : transitionAnnotations) {
						Iterable<Data<?>> transitionData = extractParamAnnotations(method);
						addTransition(method, bipTransitionAnnotation, transitionData, builder);
					}

				} else if (annotation instanceof bipGuard) {
					Guard guard = extractGuards(method, annotation);
					builder.addGuard(guard);

				} else if (annotation instanceof bipData) { // DATA OUT
					bipData dataAnnotation = (bipData) annotation;
					String name = dataAnnotation.name();
					String type = dataAnnotation.accessTypePort();
					String[] ports = dataAnnotation.ports();
					DataOut<?> data = createData(name, method.getReturnType(), type, ports);
					builder.addDataOut(data, method);

				} else if (annotation instanceof bipPorts) {
					bipPorts portsAnnotation = (bipPorts) annotation;
					Annotation[] portAnnotations = portsAnnotation.value();
					for (Annotation bipPortAnnotation : portAnnotations) {
						bipPort portAnnotation = (bipPort) bipPortAnnotation;
						Port port = new PortImpl(portAnnotation.name(), portAnnotation.type(), componentClass.getCanonicalName(), this);
						builder.addPort(port);
					}

				} else if (annotation instanceof bipPort) {
					bipPort portAnnotation = (bipPort) annotation;
					Port port = new PortImpl(portAnnotation.name(), portAnnotation.type(), componentClass.getCanonicalName(), this);
					builder.addPort(port);
				}

			}
			// extractParamAnnotations(method);
		}
		return builder;
	}

	private List<Data<?>> extractParamAnnotations(Method method) {
		// deal with method parameters: there might be a dataIn
		ArrayList<Data<?>> dataIn = new ArrayList<Data<?>>();
		Class<?> paramTypes[] = method.getParameterTypes();
		Annotation[][] paramsAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < paramsAnnotations.length; i++) {
			// for (Annotation[] paramAnnotations : paramsAnnotations) {
			for (Annotation annotation : paramsAnnotations[i]) {
				if (annotation instanceof bipData) {
					bipData dataAnnotation = (bipData) annotation;
					Data<?> data = createData(dataAnnotation.name(), paramTypes[i]);
					dataIn.add(data);
				}
			}
		}
		return dataIn;
	}
	
	//TODO find a way not to copy this method among classes
	<T> Data<T> createData(String dataName, Class<T> type) {
		Data<T> toReturn = new DataImpl<T>(dataName, type);
		return toReturn;
	}
	
	<T> DataOut<T> createData(String dataName, Class<T> type, String accessType, String[] ports) throws BIPException {
		DataOut<T> toReturn = new DataImpl<T>(dataName, type, accessType, ports);
		return toReturn;
	}

	private Guard extractGuards(Method method, Annotation annotation) throws BIPException {
		Class<?> returnType = method.getReturnType();
		if (!Boolean.class.isAssignableFrom(returnType) && !boolean.class.isAssignableFrom(returnType)) {
			throw new BIPException("Guard method " + method.getName() + " should be a boolean function");
		}
		bipGuard guardAnnotation = (bipGuard) annotation;

		return new GuardImpl(guardAnnotation.name(), method);
	}

	private void addTransition(Method method, Annotation annotation, Iterable<Data<?>> transitionData, BehaviourBuilder builder) {
		bipTransition transitionAnnotation = (bipTransition) annotation;
		// We store method so that there are no problems if there
		// are the same method names with different parameters
		TransitionImpl transition = new TransitionImpl(transitionAnnotation.name(), transitionAnnotation.source(), transitionAnnotation.target(), transitionAnnotation.guard(), method,
				transitionData);
		builder.addTransition(transition);
	}
}
