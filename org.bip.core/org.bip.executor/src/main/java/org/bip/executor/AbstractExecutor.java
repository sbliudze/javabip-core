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
import java.util.concurrent.Semaphore;

import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.ComponentProvider;
import org.bip.api.Executor;
import org.bip.api.Port;
import org.bip.api.PortBase;
import org.bip.exceptions.BIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO BUG, Fix it or deprecate and remove later on. 
// Possibly rewrite in a similar manner (POJO and proxy) and provide orchestration with the help
// of ExecutorService.
public abstract class AbstractExecutor extends SpecificationParser implements Runnable, Executor, ComponentProvider {

	private BIPEngine engine;
	private ArrayList<String> notifiers;

	protected volatile boolean continueLoop = true;
	// defines whether this component has already been registered
	protected boolean registered = false;

	// Used to ensure that we do not go into the next cycle before we finish the
	// previous
	private Semaphore semaphore;

	private Logger logger = LoggerFactory.getLogger(AbstractExecutor.class);

	// defines whether the executor is idle waiting for a spontaneous event
	// if it is, and the spontaneous event is received, the semaphore should be
	// released
	private boolean waitingSpontaneous = false;

	private Map<String, Object> dataEvaluation = new Hashtable<String, Object>();

	/**
	 * By default, the Executor is created for a component with annotations. If
	 * you want to create the Executor for a component with behaviour, use
	 * another constructor
	 * 
	 * @param bipComponent
	 * @throws BIPException
	 */
	public AbstractExecutor(Object bipComponent) throws BIPException {
		this(bipComponent, true);
	}

	public AbstractExecutor(Object bipComponent, boolean useSpec)
			throws BIPException {
		super(bipComponent, useSpec);
		this.notifiers = new ArrayList<String>();
		semaphore = new Semaphore(1);
	}

	public AbstractExecutor(Object bipComponent, boolean useSpec, BIPEngine engine)
			throws BIPException {
		super(bipComponent, useSpec);
		this.engine = engine;
		this.notifiers = new ArrayList<String>();
		semaphore = new Semaphore(1);
	}

	public void register(BIPEngine engine) {
		engine.register(this, behaviour);
		this.setEngine(engine);
		synchronized (this) {
			registered = true;
			notifyAll();
		}
	}

	public void deregister() {
		this.registered = false;
	}

	public void setEngine(BIPEngine engine) {
		this.engine = engine;
	}

	public String getCurrentState() {
		return behaviour.getCurrentState();
	}

	public void run() {
		
		try {
		logger.debug("Executor thread started: " + Thread.currentThread().getName());
		
		synchronized (this) {
			while (!registered) {
				try {
					wait();
				} catch (InterruptedException e) {
					logger.debug("Executor thread interrupted: " + Thread.currentThread().getName());
					return;
				}
			}
		}
		loop();
		logger.debug("Executor thread terminated: "	+ Thread.currentThread().getName());
		}
		catch(Error error) {
			ExceptionHelper.printExceptionTrace(logger, error);
			throw error;
		}
	}

	public void loop() {
		// TODO OR DEPRECATED do we need to set continueLoop to false in some cases?
		// add a stop function?
		// TODO OR DEPRECATED add todo to engine: remove while true in run, add a variable,
		// interrupt -> run-false
		while (continueLoop) {
			try {
				semaphore.acquire();
				nextStep();
			} catch (InterruptedException e) {
				ExceptionHelper.printExceptionTrace(logger, e);
				return;
			} catch (BIPException e) {
				ExceptionHelper.printExceptionTrace(logger, e);
				return;
			}

		}
	}

	public void nextStep() throws BIPException {
		waitingSpontaneous = false;
		dataEvaluation.clear();

		Hashtable<String, Boolean> guardToValue = behaviour.computeGuardsWithoutData();

		// we have to compute this in order to be able to raise an exception
		boolean existInternal = behaviour.existEnabledInternal(guardToValue);
		boolean existSpontaneous = behaviour.existInCurrentStateAndEnabledSpontaneous(guardToValue);
		boolean existEnforceable = behaviour.existInCurrentStateAndEnabledEnforceableWithoutData(guardToValue) || 
								   behaviour.existInCurrentStateAndEnforceableWithData();

		Set<Port> globallyDisabledPorts = behaviour.getGloballyDisabledEnforceablePortsWithoutDataTransfer(guardToValue);
		
		if (existInternal) {
			behaviour.executeInternal(guardToValue);
			semaphore.release();
			return;
		} else if (existSpontaneous) {
			// TODO OR DEPRECATED if disabled transition informs, it will be executed
			logger.debug("There will be a spontaneous transition.");
			// TODO OR DEPRECATED get rid of this thread sleep - check test conditions, they
			// do not work without
			try {
				Thread.sleep(1000); // TestEnforceable2 was set to 10.000
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitingSpontaneous = true;
			boolean portFound = false;
			String portToExecute = null;
			synchronized (notifiers) {
				if (!notifiers.isEmpty()) {
					for (String port : notifiers) {
						if (behaviour.hasEnabledTransitionFromCurrentState(port, guardToValue)) {
							logger.debug("There is a notifier already.");
							this.executeSpontaneous(port);
							portFound = true;
							portToExecute = port;
							break;
						}
					}
					if (portFound) {
						notifiers.remove(portToExecute);
						return;
					} // else continue
				}
			}
		}
		if (existEnforceable) {
			engine.inform(this, behaviour.getCurrentState(), globallyDisabledPorts);
		} else if (!existSpontaneous) {
			throw new BIPException("No transition of known type from state "
					+ behaviour.getCurrentState() + " in component "
					+ this.getId());
		}
	}

	private void executeSpontaneous(String portID) throws BIPException {
		if (portID == null || portID.isEmpty()) {
			return;
		}
		// execute spontaneous
		logger.info("Executing spontaneous transition {}.", portID);
		behaviour.execute(portID);
		semaphore.release();

	}

	/**
	 * Executes a particular transition as told by the Engine
	 */
	public void execute(String portID) {
		// execute the particular transition
		// TODO OR DEPRECATED : need to check that port is enforceable, do not allow
		// spontaneous executions here.
		// TODO OR DEPRECATED : maybe we can then change the interface from String port to Port
		// port?
		if (dataEvaluation == null || dataEvaluation.isEmpty()) {
			try {
				behaviour.execute(portID);
			} catch (BIPException e) {
				// TODO OR DEPRECATED Discuss how to handle exceptions and apply it in the code.
				e.printStackTrace();
			}
			semaphore.release();
			return;
		}

		behaviour.execute(portID, dataEvaluation);
		semaphore.release();

	}

	public synchronized void inform(String portID) {
		if (portID == null || portID.isEmpty()) {
			return;
		}
		logger.info("{} was informed of a spontaneous transition {}",
				this.getId(), portID);

		synchronized (notifiers) {
			notifiers.add(portID);
		}
		if (waitingSpontaneous) {
			semaphore.release();
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
		return this;
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
	
	public BIPEngine engine() {
		return engine;
	}

}
