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

import org.javabip.annotations.*;
import org.javabip.api.PortType;

@Ports({ @Port(name = "add", type = PortType.enforceable), @Port(name = "rm", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.RouteOnOffMonitor")
public class RouteOnOffMonitor {

	final private int routeLimit;

	private int routeOnCounter = 0;

	public RouteOnOffMonitor(int routeLimit) {
		this.routeLimit = routeLimit;
	}

	@Transitions({ @Transition(name = "add", source = "0", target = "1", guard = "hasCapacity"),
			@Transition(name = "add", source = "1", target = "2", guard = "hasCapacity") })
	public void addRoute() {
		routeOnCounter++;
	}

	@Transitions({ @Transition(name = "rm", source = "2", target = "1", guard = "hasRouteRunning"),
			@Transition(name = "rm", source = "1", target = "0", guard = "hasRouteRunning") })
	public void removeRoute() {
		routeOnCounter--;
	}

	@Guard(name = "hasCapacity")
	public boolean hasCapacity() {
		return routeOnCounter < routeLimit;
	}

	@Guard(name = "hasRouteRunning")
	public boolean hasRouteRunning() {
		return routeOnCounter > 0;
	}
}
