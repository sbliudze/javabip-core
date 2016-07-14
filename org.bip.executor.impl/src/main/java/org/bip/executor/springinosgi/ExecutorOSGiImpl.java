package org.bip.executor.springinosgi;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.Executor;
import org.bip.api.PortBase;
import org.bip.api.Publishable;
import org.bip.api.ResourceHandle;
import org.bip.exceptions.BIPException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

public class ExecutorOSGiImpl implements BundleContextAware, Publishable, Executor {

	private BundleContext bundleContext;
	protected ServiceRegistration serviceRegistration;
	protected Executor executor;
	
	private Logger logger = LoggerFactory.getLogger(ExecutorOSGiImpl.class);
	
	public ExecutorOSGiImpl(Executor executor) throws BIPException {
		this.executor = executor;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	public void publish() {

		logger.info("Publishing BIPComponent service in OSGi registry");

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("bip.spec", executor.getType());

		serviceRegistration = bundleContext.registerService(BIPComponent.class.getName(), this, props);
	}

	public void unpublish() {
		logger.info("Unpublishing BIPComponent service in OSGi registry");
		serviceRegistration.unregister();
	}

	@Override
	public String getId() {
		return executor.getId();
	}

	@Override
	public String getType() {
		return executor.getType();
	}

	@Override
	public void execute(String portID) {
		executor.execute(portID);
	}

	@Override
	public void inform(String portID) {
		executor.inform(portID);
	}

	@Override
	public <T> T getData(String name, Class<T> clazz) {
		return executor.getData(name, clazz);
	}

	@Override
	public List<Boolean> checkEnabledness(PortBase port,
			List<Map<String, Object>> data) {
		return executor.checkEnabledness(port, data);
	}

	@Override
	public void setData(String dataName, Object value) {
		executor.setData(dataName, value);
	}

	@Override
	public void register(BIPEngine bipEngine) {
		executor.register(bipEngine);
	}

	@Override
	public void deregister() {
		executor.deregister();
	}

	@Override
	public BIPEngine engine() {
		return executor.engine();
	}

	@Override
	public void inform(String portID, Map<String, Object> data) {
		executor.inform(portID, data);

	}

	@Override
	public void provideAllocation(String resourceName,
			ResourceHandle resourceHandle) {
		executor.provideAllocation(resourceName, resourceHandle);
	}

}
