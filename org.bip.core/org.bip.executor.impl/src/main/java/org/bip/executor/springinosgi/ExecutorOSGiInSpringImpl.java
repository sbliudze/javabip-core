package org.bip.executor.springinosgi;

import org.bip.exceptions.BIPException;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ExecutorOSGiInSpringImpl extends ExecutorOSGiImpl implements InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(ExecutorOSGiInSpringImpl.class);

	public ExecutorOSGiInSpringImpl(Object bipComponent, boolean useSpec) throws BIPException {
		super(bipComponent, useSpec);

	}

	public ExecutorOSGiInSpringImpl(Object bipComponent) throws BIPException {
		super(bipComponent, true);

	}

	Thread executorThread;
	private String id = "";

	public void destroy() throws Exception {
		continueLoop = false;
		Thread destroying = new Thread(new Runnable() {

			public void run() {
				while (!executorThread.getState().equals(Thread.State.TERMINATED))
					;
				unpublish();
				executorThread.interrupt();
			}
		});
		destroying.start();
	}

	public void afterPropertiesSet() throws Exception {

		publish();
		String serviceID = serviceRegistration.getReference().getProperty(Constants.SERVICE_ID).toString();
		this.id = this.behaviour.getComponentType() + serviceID;
		executorThread = new Thread(this, this.behaviour.getComponentType() + serviceID);
		logger.info("Executor thread created: " + this.behaviour.getComponentType() + serviceID);
		executorThread.start();
	}

	public String getId() {
		return this.id;
	}
		
}
