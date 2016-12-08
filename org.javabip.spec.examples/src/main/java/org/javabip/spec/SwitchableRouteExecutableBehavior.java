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
import org.javabip.annotations.ExecutableBehaviour;
import org.javabip.api.Executor;
import org.javabip.api.PortType;
import org.javabip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SwitchableRouteExecutableBehavior implements CamelContextAware, InitializingBean, DisposableBean {

	@ExecutableBehaviour
	public BehaviourBuilder getExecutableBehavior() throws NoSuchMethodException {

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

		String currentState = "off";

		behaviourBuilder.setInitialState(currentState);

		behaviourBuilder.addPort("end", PortType.spontaneous, this.getClass());
		behaviourBuilder.addPort("on", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("off", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("finished", PortType.enforceable, this.getClass());

		behaviourBuilder.addTransitionAndStates("on", "off", "on", "",
				SwitchableRouteExecutableBehavior.class.getMethod("startRoute"));
		behaviourBuilder.addTransitionAndStates("off", "on", "wait", "",
				SwitchableRouteExecutableBehavior.class.getMethod("stopRoute"));
		behaviourBuilder.addTransitionAndStates("end", "wait", "done", "!isFinished",
				SwitchableRouteExecutableBehavior.class.getMethod("spontaneousEnd"));
		behaviourBuilder.addTransitionAndStates("", "wait", "done", "isFinished",
				SwitchableRouteExecutableBehavior.class.getMethod("internalEnd"));
		behaviourBuilder.addTransitionAndStates("finished", "done", "off", "",
				SwitchableRouteExecutableBehavior.class.getMethod("finishedTransition"));

		// [off, on, wait, done]

		behaviourBuilder.addState("off");
		behaviourBuilder.addState("on");
		behaviourBuilder.addState("wait");
		behaviourBuilder.addState("done");

		behaviourBuilder.addGuard("isFinished", this.getClass().getMethod("isFinished"));

		return behaviourBuilder;
	}

	public ModelCamelContext camelContext;

	public String routeId;

	Logger logger = LoggerFactory.getLogger(SwitchableRouteExecutableBehavior.class);

	private Executor executor;
	private RoutePolicy notifier;

	public int noOfEnforcedTransitions;

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = (ModelCamelContext) camelContext;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public SwitchableRouteExecutableBehavior(String routeId) {
		this.routeId = routeId;
	}

	/**
	 * In some cases you may want to execute
	 */
	public void workDone() {
		logger.debug("Port handler for end port is executing.");
	}

	/*
	 * Check what are the conditions for throwing the exception.
	 */
	public void stopRoute() throws Exception {
		logger.debug("Stop transition handler for {} is being executed.", routeId);
		camelContext.suspendRoute(routeId);
		noOfEnforcedTransitions++;
	}

	public void spontaneousEnd() throws Exception {
		logger.info("Received end notification for the route {}.", routeId);
	}

	public void internalEnd() throws Exception {
		logger.info("Transitioning to done state directly since work within {} is already finished.", routeId);
	}

	public void finishedTransition() throws Exception {
		logger.debug("Transitioning to off state from done for {}.", routeId);
		noOfEnforcedTransitions++;
	}

	public void startRoute() throws Exception {
		noOfEnforcedTransitions++;
		logger.debug("Start transition handler for {} is being executed.", routeId);
		camelContext.resumeRoute(routeId);
	}

	public boolean isFinished() {
		return camelContext.getInflightRepository().size(routeId) == 0;
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

	public void destroy() throws Exception {

		RouteDefinition routeDefinition = camelContext.getRouteDefinition(routeId);

		if (routeDefinition != null) {

			List<RoutePolicy> routePolicyList = routeDefinition.getRoutePolicies();

			routePolicyList.remove(notifier);
			routeDefinition.setRoutePolicies(routePolicyList);

		}

	}

}
