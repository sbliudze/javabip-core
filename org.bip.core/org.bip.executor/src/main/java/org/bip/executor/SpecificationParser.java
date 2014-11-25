/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Ports;
import org.bip.annotations.Transitions;
import org.bip.api.Behaviour;
import org.bip.api.ComponentProvider;
import org.bip.api.ExecutableBehaviour;
import org.bip.exceptions.BIPException;

/**
 * Creates a Behaviour for the future use of the Executor
 */
public abstract class SpecificationParser implements ComponentProvider {

	protected Object bipComponent;
	protected ExecutableBehaviour behaviour;
	protected Class<?> componentClass;

	public SpecificationParser(Object bipComponent, boolean useAnnotationSpec) throws BIPException {
		this.bipComponent = bipComponent;
		this.componentClass = bipComponent.getClass();
		
		if (useAnnotationSpec) {
			this.behaviour = parseAnnotations( bipComponent.getClass() ).build(this);
		} else {
			this.behaviour = getExecutableBehaviour( bipComponent.getClass() ).build(this);
		}

	}

	public Behaviour getBehaviour() {
		return behaviour;
	}

	private BehaviourBuilder getExecutableBehaviour( Class<?> componentClass ) throws BIPException {

		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof org.bip.annotations.ExecutableBehaviour) {
					Class<?> returnType = method.getReturnType();
					if (!BehaviourBuilder.class.isAssignableFrom(returnType)) {
						throw new BIPException("Method " + method.getName() + "annotated with @ExecutableBehaviour should have a return type BehaviourBuilder");
					}
					try {
						if (method.getParameterTypes() != null && method.getParameterTypes().length != 0) {
							throw new BIPException("The method " + method.getName() + " for getting executable behaviour for component " + bipComponent.getClass().getName()
									+ "must have no arguments.");
						}
						return (BehaviourBuilder) method.invoke(bipComponent);
					} catch (Exception e) {
						throw new BIPException("Method annotated with ExecutableBehavior annotation threw exception upon execution", e);
					}
				}
			}
		}
		
		throw new BIPException("No annotation ExecutableBehaviour found in class " + componentClass.getCanonicalName());

	}

	private BehaviourBuilder parseAnnotations( Class<?> componentClass ) throws BIPException {
		BehaviourBuilder builder = new BehaviourBuilder();
		
		builder.setComponent(bipComponent);
		
		String specType = "";
		// TODO: Add simple test that forgets the componentType annotation to see whether the
		// Exception (else part) is thrown.
		Annotation classAnnotation = componentClass.getAnnotation(ComponentType.class);
		// get component name and type
		if (classAnnotation instanceof ComponentType) {
			ComponentType componentTypeAnnotation = (ComponentType) classAnnotation;
			builder.setComponentType( componentTypeAnnotation.name() );
			specType = componentTypeAnnotation.name();
			builder.setInitialState( componentTypeAnnotation.initial() );
		} else {
			throw new BIPException("ComponentType annotation is not specified.");
		}

		// get ports
		classAnnotation = componentClass.getAnnotation(Ports.class);
		if (classAnnotation instanceof Ports) {
			Ports ports = (Ports) classAnnotation;
			org.bip.annotations.Port[] portArray = ports.value();
			for (org.bip.annotations.Port bipPortAnnotation : portArray) {
				
				if (bipPortAnnotation instanceof org.bip.annotations.Port)
					addPort((org.bip.annotations.Port) bipPortAnnotation, specType, builder);

			}
		} else {
			throw new BIPException("Port information for the BIP component is not specified.");
		}

		// get transitions & guards & data
		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof org.bip.annotations.Transition) {
					
					addTransitionAndStates(method, (org.bip.annotations.Transition) annotation, builder);

				} else if (annotation instanceof Transitions) {
					Transitions transitionsAnnotation = (Transitions) annotation;
					Annotation[] transitionAnnotations = transitionsAnnotation.value();
					for (Annotation bipTransitionAnnotation : transitionAnnotations) {
						
						addTransitionAndStates(method, (org.bip.annotations.Transition) bipTransitionAnnotation, builder);
					}

				} else if (annotation instanceof org.bip.annotations.Guard) {
					
						addGuard(method, (org.bip.annotations.Guard) annotation, builder);						

				} else if (annotation instanceof Data) { // DATA OUT
	
					addData(method, (Data)annotation, builder);
					
				// TODO DESIGN Do we really make it possible to specify Port(s) directly within the function?
				} else if (annotation instanceof Ports) {
					Ports portsAnnotation = (Ports) annotation;
					Annotation[] portAnnotations = portsAnnotation.value();
					for (Annotation bipPortAnnotation : portAnnotations) {
						
						if (bipPortAnnotation instanceof org.bip.annotations.Port)
							addPort((org.bip.annotations.Port) bipPortAnnotation, componentClass, builder);
						
					}

				} else if (annotation instanceof org.bip.annotations.Port) {
				
					addPort((org.bip.annotations.Port) annotation, componentClass, builder);
					
				}

			}

		}
		return builder;
	}

	private void addGuard(Method method, 
						  org.bip.annotations.Guard annotation,
						  BehaviourBuilder builder) throws BIPException {
		
		Class<?> returnType = method.getReturnType();
		if (!Boolean.class.isAssignableFrom(returnType) 
			&& !boolean.class.isAssignableFrom(returnType)) {
			throw new BIPException("Guard method " + method.getName() + " should be a boolean function");
		}

		builder.addGuard(annotation.name(), method, ReflectionHelper.parseDataAnnotations(method));
		
	}
	
	private void addData(Method method, Data annotation, BehaviourBuilder builder) {

		builder.addDataOut(method, annotation);
		
	}

	private void addTransitionAndStates(Method method, org.bip.annotations.Transition transitionAnnotation, BehaviourBuilder builder) {
		
		builder.addTransitionAndStates(transitionAnnotation.name(), transitionAnnotation.source(), transitionAnnotation.target(), transitionAnnotation.guard(), method);

	}

	private void addPort(org.bip.annotations.Port portAnnotation, Class<?> componentClass, BehaviourBuilder builder) {
		
		builder.addPort( portAnnotation.name(), portAnnotation.type(), componentClass );

	}
	
	private void addPort(org.bip.annotations.Port portAnnotation, String specType,	BehaviourBuilder builder) {
		builder.addPort( portAnnotation.name(), portAnnotation.type(), specType );
		
	}
	
}
