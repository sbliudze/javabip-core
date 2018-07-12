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
 * Date: 15/10/12
 */

package org.javabip.spec;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.RoutePolicy;
import org.javabip.annotations.*;
import org.javabip.api.DataOut;
import org.javabip.api.Executor;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Ports({
		@Port(name = "end", type = PortType.spontaneous),
		@Port(name = "on", type = PortType.enforceable),
		@Port(name = "off", type = PortType.enforceable),
		@Port(name = "finished", type = PortType.enforceable)
})
@ComponentType(initial = "off", name = "org.bip.spec.SwitchableRoute")
public class SwitchableRoute implements CamelContextAware, InitializingBean, DisposableBean {

	public ModelCamelContext camelContext;

	public String routeId;
	public int noOfEnforcedTransitions;

	Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);

	private Executor executor;
	private RoutePolicy notifier;

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = (ModelCamelContext) camelContext;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public SwitchableRoute(String routeId) {
		this.routeId = routeId;
	}

	public SwitchableRoute(String routeId, CamelContext camelContext) {
		this.routeId = routeId;
		this.camelContext = (ModelCamelContext) camelContext;
	}

	/*
	 * TODO: Check what are the conditions for throwing the exception.
	 */
	@Transition(name = "off", source = "on", target = "wait", guard = "")
	public void stopRoute() throws Exception {
//		logger.debug("Off transition handler for route {} is being executed.", routeId);
		System.out.printf("'Off' transition handler for %s is being executed.%n", routeId);
		camelContext.suspendRoute(routeId);
		noOfEnforcedTransitions++;
	}

	@Transition(name = "end", source = "wait", target = "done", guard = "!isFinished")
	public void spontaneousEnd() throws Exception {
//		logger.info("Received end notification for the route {}.", routeId);
		System.out.printf("Received 'end' notification for the route %s.%n", routeId);
	}

	@Transition(name = "", source = "wait", target = "done", guard = "isFinished")
	public void internalEnd() throws Exception {
//		logger.info("Transitioning to done state directly since work within the route {} is already finished.", routeId);
		System.out.printf("Transitioning to done state directly (internal) since work within %s is already finished.%n", routeId);
	}

	@Transition(name = "finished", source = "done", target = "off", guard = "")
	public void finishedTransition() throws Exception {
		noOfEnforcedTransitions++;
//		logger.debug("Transitioning to off state from done for {}.", routeId);
		System.out.printf("'Finished' transition handler is being executed " +
				"(transitioning directly to off state from done) for route %s.%n", routeId);
	}

	@Transition(name = "on", source = "off", target = "on", guard = "")
	public void startRoute() throws Exception {
//		logger.debug("Start transition handler for {} is being executed.", routeId);
		System.out.printf("'On' transition handler for route %s is being executed.%n", routeId);
		camelContext.resumeRoute(routeId);
		noOfEnforcedTransitions++;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Guard(name = "isFinished")
	public boolean isFinished() {
		return camelContext.getInflightRepository().size(routeId) == 0;
	}

	// It is inferred that this is Data Out as the annotation is provided within a function that returns smth.
	// The type of the DataOut is the type of the return.
	@Data(name = "id", accessTypePort = DataOut.AccessType.any)
	public String getRouteId() {
		return routeId;
	}

	public void afterPropertiesSet() throws Exception {
		RouteDefinition routeDefinition = camelContext.getRouteDefinition(routeId);

		if (routeDefinition == null)
			throw new IllegalStateException("The route with a given id " + routeId
					+ " can not be found in the CamelContext.");

		if (executor == null)
			throw new IllegalStateException(
					"BIP Executor for handling this bip spec has not been injected thus no spontaneous even notification can be established.");

		List<RoutePolicy> routePolicyList = routeDefinition.getRoutePolicies();

		if (routePolicyList == null) {
			routePolicyList = new ArrayList<RoutePolicy>();
		}
		final Executor finalExecutor = executor;
		notifier = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				finalExecutor.inform("end");
			}

			@Override
			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};

		routePolicyList.add(notifier);
		routeDefinition.setRoutePolicies(routePolicyList);

	}

	public void destroy() {

		RouteDefinition routeDefinition = camelContext.getRouteDefinition(routeId);

		if (routeDefinition != null) {

			List<RoutePolicy> routePolicyList = routeDefinition.getRoutePolicies();

			routePolicyList.remove(notifier);
			routeDefinition.setRoutePolicies(routePolicyList);

		}

	}

}


