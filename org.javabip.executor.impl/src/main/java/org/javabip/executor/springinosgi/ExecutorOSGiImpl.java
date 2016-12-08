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

package org.javabip.executor.springinosgi;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.javabip.api.BIPComponent;
import org.javabip.api.BIPEngine;
import org.javabip.api.Executor;
import org.javabip.api.PortBase;
import org.javabip.api.Publishable;
import org.javabip.exceptions.BIPException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

/**
 * An executor implementation which can work within OSGi.
 * 
 */
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
	public List<Boolean> checkEnabledness(PortBase port, List<Map<String, Object>> data) {
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

}
