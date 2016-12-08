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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transitions;
import org.javabip.api.Behaviour;
import org.javabip.api.ComponentProvider;
import org.javabip.api.ExecutableBehaviour;
import org.javabip.exceptions.BIPException;

/**
 * Parses the component specification to create a behaviour instance. The behaviour is specified either via annotations
 * of programmatically.
 * 
 * @author Alina Zolotukhina
 */
public abstract class SpecificationParser implements ComponentProvider {

	protected Object bipComponent;
	protected ExecutableBehaviour behaviour;
	protected Class<?> componentClass;

	/**
	 * Creates an instance of SpecificationParserю
	 * 
	 * @param bipComponent
	 *            the BIP component specification to parse
	 * @param useAnnotationSpec
	 *            true, if the annotations are used; false, if the behaviour is specified programmatically
	 * @throws BIPException
	 */
	public SpecificationParser(Object bipComponent, boolean useAnnotationSpec) throws BIPException {
		this.bipComponent = bipComponent;
		this.componentClass = bipComponent.getClass();

		if (useAnnotationSpec) {
			this.behaviour = parseAnnotations(bipComponent.getClass()).build(this);
		} else {
			this.behaviour = getExecutableBehaviour(bipComponent.getClass()).build(this);
		}

	}

	public Behaviour getBehaviour() {
		return behaviour;
	}

	private BehaviourBuilder getExecutableBehaviour(Class<?> componentClass) throws BIPException {

		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof org.javabip.annotations.ExecutableBehaviour) {
					Class<?> returnType = method.getReturnType();
					if (!BehaviourBuilder.class.isAssignableFrom(returnType)) {
						throw new BIPException("Method " + method.getName()
								+ "annotated with @ExecutableBehaviour should have a return type BehaviourBuilder");
					}
					try {
						if (method.getParameterTypes() != null && method.getParameterTypes().length != 0) {
							throw new BIPException("The method " + method.getName()
									+ " for getting executable behaviour for component "
									+ bipComponent.getClass().getName() + "must have no arguments.");
						}
						return (BehaviourBuilder) method.invoke(bipComponent);
					} catch (Exception e) {
						throw new BIPException(
								"Method annotated with ExecutableBehavior annotation threw exception upon execution", e);
					}
				}
			}
		}

		throw new BIPException("No annotation ExecutableBehaviour found in class " + componentClass.getCanonicalName());

	}

	private BehaviourBuilder parseAnnotations(Class<?> componentClass) throws BIPException {
		BehaviourBuilder builder = new BehaviourBuilder(bipComponent);

		String specType = "";
		// TODO: Add simple test that forgets the componentType annotation to see whether the
		// Exception (else part) is thrown.
		Annotation classAnnotation = componentClass.getAnnotation(ComponentType.class);
		// get component name and type
		if (classAnnotation instanceof ComponentType) {
			ComponentType componentTypeAnnotation = (ComponentType) classAnnotation;
			builder.setComponentType(componentTypeAnnotation.name());
			specType = componentTypeAnnotation.name();
			builder.setInitialState(componentTypeAnnotation.initial());
		} else {
			throw new BIPException("ComponentType annotation is not specified.");
		}

		// get ports
		classAnnotation = componentClass.getAnnotation(Ports.class);
		if (classAnnotation instanceof Ports) {
			Ports ports = (Ports) classAnnotation;
			org.javabip.annotations.Port[] portArray = ports.value();
			for (org.javabip.annotations.Port bipPortAnnotation : portArray) {

				if (bipPortAnnotation instanceof org.javabip.annotations.Port)
					addPort((org.javabip.annotations.Port) bipPortAnnotation, specType, builder);

			}
		} else {
			throw new BIPException("Port information for the BIP component is not specified.");
		}

		// get transitions & guards & data
		Method[] componentMethods = componentClass.getMethods();
		for (Method method : componentMethods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof org.javabip.annotations.Transition) {

					addTransitionAndStates(method, (org.javabip.annotations.Transition) annotation, builder);

				} else if (annotation instanceof Transitions) {
					Transitions transitionsAnnotation = (Transitions) annotation;
					Annotation[] transitionAnnotations = transitionsAnnotation.value();
					for (Annotation bipTransitionAnnotation : transitionAnnotations) {

						addTransitionAndStates(method, (org.javabip.annotations.Transition) bipTransitionAnnotation,
								builder);
					}

				} else if (annotation instanceof org.javabip.annotations.Guard) {

					addGuard(method, (org.javabip.annotations.Guard) annotation, builder);

				} else if (annotation instanceof Data) { // DATA OUT

					addData(method, (Data) annotation, builder);

					// TODO DESIGN Do we really make it possible to specify Port(s) directly within the function?
				} else if (annotation instanceof Ports) {
					Ports portsAnnotation = (Ports) annotation;
					Annotation[] portAnnotations = portsAnnotation.value();
					for (Annotation bipPortAnnotation : portAnnotations) {

						if (bipPortAnnotation instanceof org.javabip.annotations.Port)
							addPort((org.javabip.annotations.Port) bipPortAnnotation, componentClass, builder);

					}

				} else if (annotation instanceof org.javabip.annotations.Port) {

					addPort((org.javabip.annotations.Port) annotation, componentClass, builder);

				}

			}

		}
		return builder;
	}

	private void addGuard(Method method, org.javabip.annotations.Guard annotation, BehaviourBuilder builder)
			throws BIPException {

		Class<?> returnType = method.getReturnType();
		if (!Boolean.class.isAssignableFrom(returnType) && !boolean.class.isAssignableFrom(returnType)) {
			throw new BIPException("Guard method " + method.getName() + " should be a boolean function");
		}

		builder.addGuard(annotation.name(), method, ReflectionHelper.parseDataAnnotations(method));

	}

	private void addData(Method method, Data annotation, BehaviourBuilder builder) {

		builder.addDataOut(method, annotation);

	}

	private void addTransitionAndStates(Method method, org.javabip.annotations.Transition transitionAnnotation,
			BehaviourBuilder builder) {

		builder.addTransitionAndStates(transitionAnnotation.name(), transitionAnnotation.source(),
				transitionAnnotation.target(), transitionAnnotation.guard(), method);

	}

	private void addPort(org.javabip.annotations.Port portAnnotation, Class<?> componentClass, BehaviourBuilder builder) {

		builder.addPort(portAnnotation.name(), portAnnotation.type(), componentClass);

	}

	private void addPort(org.javabip.annotations.Port portAnnotation, String specType, BehaviourBuilder builder) {
		builder.addPort(portAnnotation.name(), portAnnotation.type(), specType);

	}

}
