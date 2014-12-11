/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.executor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bip.api.BIPActorAware;
import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.Behaviour;
import org.bip.api.ComponentProvider;
import org.bip.api.OrchestratedExecutor;
import org.bip.api.Port;
import org.bip.api.PortBase;
import org.bip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * It is not a multi-thread safe executor kernel therefore it should never be directly used.
 * It needs to proxied to protect it from multi-thread access by for example Akka actor approach. 
 */
public class ExecutorKernel extends SpecificationParser implements OrchestratedExecutor, ComponentProvider {

	String id;
	
	private BIPEngine engine;
	
	private ArrayList<String> notifiers  = new ArrayList<String>();

	private ArrayList<Map<String, Object>> notifiersData = new ArrayList< Map<String, Object> >();
	
	protected boolean registered = false;

	private Logger logger = LoggerFactory.getLogger(ExecutorKernel.class);
	
	private Map<String, Object> dataEvaluation = new Hashtable<String, Object>();
	
	boolean waitingForSpontaneous = false;
	
	protected OrchestratedExecutor proxy;
	
	/**
	 * By default, the Executor is created for a component with annotations. If
	 * you want to create the Executor for a component with behaviour, use
	 * another constructor
	 * 
	 * @param bipComponent
	 * @throws BIPException
	 */
	public ExecutorKernel(Object bipComponent, String id) throws BIPException {
		this(bipComponent, id, true);
	}

	public ExecutorKernel(Object bipComponent, String id, boolean useSpec)
			throws BIPException {
		super(bipComponent, useSpec);
		this.id = id;
	}
	
	// TODO, Proxy can be also obtained using this singleton 
	// proxy = TypedActor.<OrchestratedExecutor>self();
	// However, we tight ourselves to TypedActor singleton that can 
	// be disastrous in OSGi setup. 
	// However, at the same time any exception thrown from any function 
	// in this class will cause the TypedActor to die and a new one respawn
	// making this proxy obsolete thus not being able to progress any further.
	// Maybe, this is actually is not bad as we may want to have guarantees that
	// after exception is being thrown no more function calls follow to this object.
	public void setProxy(OrchestratedExecutor proxy) {
		this.proxy = proxy;
		if (bipComponent instanceof BIPActorAware) {
			((BIPActorAware) bipComponent).setBIPActor(proxy);
		}
	}

	/**
	 * It registers the engine within given ExecutorKernel. The function setProxy must be 
	 * called before with properly constructed proxy or an exception will occur.
	 */
	public void register(BIPEngine engine) {
		if (proxy == null) {
			throw new BIPException("Proxy to provide multi-thread safety was not provided.");
		}
		this.engine = engine;
		registered = true;
		waitingForSpontaneous = false;
		proxy.step();
	}

	public void deregister() {
		this.registered = false;
		this.waitingForSpontaneous = false;
		this.engine = null;
	}

	// Computed in guardToValue, used for checks in execute.
	Map<String, Boolean> guardToValue;

	/**
	 * If no engine is registered it will exit immediately.
	 * 
	 * @return true if the next step can be immediately executed, false if a spontaneous event must happen to have reason to execute next step again.
	 * @throws BIPException
	 */
	public void step() throws BIPException {
		
		// if the actor was deregistered then it no longer does any steps.
		if (!registered)
			return;
		
		dataEvaluation.clear();

		guardToValue = behaviour.computeGuardsWithoutData();

		// we have to compute this in order to be able to raise an exception
		boolean existInternalTransition = behaviour.existEnabledInternal(guardToValue);
		
		if (existInternalTransition) {
			logger.debug("About to execute internal transition for component {}", id);
			behaviour.executeInternal(guardToValue);
			logger.debug("Issuing next step message for component {}", id);
			// Scheduling the next execution step.
			proxy.step();
			logger.debug("Finishing current step that has executed an internal transition for component {}", id);
			return;
		};

		boolean existSpontaneousTransition = behaviour.existInCurrentStateAndEnabledSpontaneous(guardToValue);

		if (existSpontaneousTransition && !notifiers.isEmpty()) {

			for (int i = 0; i < notifiers.size(); i++) {
				
				String port = notifiers.get(i);

				if (behaviour.hasEnabledTransitionFromCurrentState(port, guardToValue)) {
					logger.debug("About to execute spontaneous transition {} for component {}", port, id);
					
					notifiers.remove(i);
					Map<String, ?> data = notifiersData.remove(i); 
					
					// Both notifiers and notifiersData should be LinkedList to perform efficient removal from the middle.
					if (data == null) {											
						behaviour.executePort(port);
					}
					else {						
						behaviour.execute(port, data);
					}
					
					logger.debug("Issuing next step message for component {}", id);
					// Scheduling the next execution step.					
					proxy.step();
					logger.debug("Finishing current step that has executed a spontaneous transition for component {}", id);
					return;
				}
			}

		}

		boolean existEnforceableTransition = behaviour.existInCurrentStateAndEnabledEnforceableWithoutData(guardToValue) ||
											 behaviour.existInCurrentStateAndEnforceableWithData();
		
		Set<Port> globallyDisabledPorts = behaviour.getGloballyDisabledEnforceablePortsWithoutDataTransfer(guardToValue);

		if (existEnforceableTransition) {
			logger.debug("About to execute engine inform for component {}", id);
			engine.inform(proxy, behaviour.getCurrentState(), globallyDisabledPorts);
			// Next step will be invoked upon finishing treatment of the message execute.
			return;
		} 
		
		// existSpontaneous transition exists but spontaneous event has not happened yet, thus a follow step should be postponned until
		// any spontaneous event is received.
		// TODO: Tell the engine that I am waiting (send all disabled ports in
		// the inform)
		if (existSpontaneousTransition) {
			logger.debug("Finishing current step for component {} doing nothing due no spontaneous events.", id);
			waitingForSpontaneous = true;
			// Next step will be invoked upon receiving a spontaneous event. 
			return;
		}
		
		// throw new BIPException("No transition of known type from state "
		// + behaviour.getCurrentState() + " in component "
		// + this.getId());

	}

	/**
	 * Executes a particular transition as told by the Engine
	 */
	public void execute(String portID) {

		if (portID != null) {

			if (dataEvaluation.isEmpty()) {

				if (behaviour.transitionNoDataGuardData(portID)) //TODO what about checking if is enabled?
				{
					behaviour.executePort(portID);
				}
				
				else if (!behaviour.existInCurrentStateAndEnabledEnforceableWithoutData(guardToValue))
					throw new BIPException("Port " + portID + " is not enabled in the current state");
				else {
					behaviour.executePort(portID);
				}
			}
			else {

				// Performing a check that all data provided make the transition enabled.
				List<Map<String, Object>> parameter = new ArrayList<Map<String, Object>>();
				parameter.add(dataEvaluation);

				try {
					if (!behaviour.checkEnabledness(portID, parameter).get(0)) {
						throw new BIPException("Port with " + portID
								+ " that requires data is not enabled for the received data");
					}
				} catch (Exception e) {
					throw new BIPException(e);
				}

				behaviour.execute(portID, dataEvaluation);
			}
		}

		logger.debug("Issuing next step message for component {}", id);
		proxy.step();
		
	}

	public void inform(String portID) {
		
		inform(portID, null);
		
	}
	
	@Override
	public void inform(String portID, Map<String, Object> data) {

		// TODO DESIGN DISCUSSION what if the port (spontaneous does not exist?). It should throw an exception or ignore it.
		if (portID == null || portID.isEmpty() || !behaviour.isSpontaneousPort(portID)) {
			return;
		}
		
		logger.info("{} was informed of a spontaneous transition {}", this.getId(), portID);

		notifiers.add(portID);
		notifiersData.add(data);
		
		if (waitingForSpontaneous) {
			logger.debug("Issuing next step message for component {}", id);
			waitingForSpontaneous = false;
			proxy.step();
		}
		
	}


	public <T> T getData(String name, Class<T> clazz) {

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					"The name of the required data variable from the component "
							+ bipComponent.getClass().getName()
							+ " cannot be null or empty.");
		}

		T result = null;

		try {
			logger.debug("Component {} getting data {}.",
					behaviour.getComponentType(), name);
			Object methodResult = behaviour.getDataOutMapping().get(name)
					.invoke(bipComponent);

			if (!methodResult.getClass().isAssignableFrom(clazz)) {
				result = getPrimitiveData(name, methodResult, clazz);
			} else
				result = clazz.cast(methodResult);

		} catch (IllegalAccessException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			ExceptionHelper.printExceptionTrace(logger, e);
			ExceptionHelper.printExceptionTrace(logger, e.getTargetException());
			e.printStackTrace();
		}
		return result;
	}

	Set<Class<?>> primitiveTypes = new HashSet<Class<?>>(
			Arrays.<Class<?>> asList(int.class, float.class, double.class,
					byte.class, long.class, short.class, boolean.class,
					char.class));

	<T> T getPrimitiveData(String name, Object methodResult, Class<T> clazz) {

		if (primitiveTypes.contains(clazz)) {

			// For primitive types, as specified in primitiveTypes set,
			// we use direct casting that will employ autoboxing
			// feature from Java. Therefore, we suppress unchecked
			@SuppressWarnings("unchecked")
			T result = (T) methodResult;

			return result;
		} else
			throw new IllegalArgumentException("The type "
					+ methodResult.getClass()
					+ " of the required data variable " + name
					+ " from the component "
					+ bipComponent.getClass().getName()
					+ " does not correspond to the specified return type "
					+ clazz);

	}

	public List<Boolean> checkEnabledness(PortBase port,
										  List<Map<String, Object>> data) {
		try {
			return behaviour.checkEnabledness(port.getId(), data);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (BIPException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BIPComponent component() {
		if (proxy == null) {
			throw new BIPException("Proxy to provide multi-thread safety was not provided.");
		}
		return proxy;
	}

	public void setData(String dataName, Object data) {
		this.dataEvaluation.put(dataName, data);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("BIPComponent=(");
		result.append("type = " + behaviour.getComponentType());
		result.append(", hashCode = " + this.hashCode());
		result.append(")");

		return result.toString();

	}

	public String getType() {
		return behaviour.getComponentType();
	}

	public Behaviour getBehavior() {
		return behaviour;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public BIPEngine engine() {
		return engine;
	}

}
