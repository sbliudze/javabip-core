package org.bip.executor;

import java.util.Hashtable;

import org.bip.api.BIPComponent;
import org.bip.api.Publishable;
import org.bip.exceptions.BIPException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

public class ExecutorOSGiImpl extends ExecutorImpl implements BundleContextAware, Publishable {

	private BundleContext bundleContext;
	protected ServiceRegistration serviceRegistration;
	
	private Logger logger = LoggerFactory.getLogger(ExecutorOSGiImpl.class);
	
	public ExecutorOSGiImpl(Object bipComponent) throws BIPException {
		super(bipComponent);
	}

	public ExecutorOSGiImpl(Object bipComponent, boolean useSpec) {
		super(bipComponent, useSpec);
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	public void publish() {

		logger.info("Publishing BIPComponent service in OSGi registry");

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("bip.spec", bipComponent.getClass().getName());

		serviceRegistration = bundleContext.registerService(BIPComponent.class.getName(), this, props);
	}

	public void unpublish() {
		logger.info("Unpublishing BIPComponent service in OSGi registry");
		serviceRegistration.unregister();
	}
}
