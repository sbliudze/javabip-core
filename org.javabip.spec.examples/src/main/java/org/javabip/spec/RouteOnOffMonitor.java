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

@Ports({
		@Port(name = "add", type = PortType.enforceable),
		@Port(name = "rm", type = PortType.enforceable),
		@Port(name = "switch", type = PortType.spontaneous)
})
@ComponentType(initial = "0", name = "org.bip.spec.RouteOnOffMonitor")
public class RouteOnOffMonitor {

	final private int routeLimit;
	private boolean workAllowed = true;

	private int routeOnCounter = 0;

	public RouteOnOffMonitor(int routeLimit) {
		this.routeLimit = routeLimit;
	}

	public RouteOnOffMonitor (int routeLimit, boolean workAllowed) {
		this.routeLimit = routeLimit;
		this.workAllowed = workAllowed;
	}

	@Transitions({
			@Transition(name = "add", source = "0", target = "1", guard = "hasCapacity&canWork"),
			@Transition(name = "add", source = "1", target = "2", guard = "hasCapacity&canWork")
	})
	public void addRoute() {

		routeOnCounter++;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.printf(" routeOnCounter = %d,%n workAllowed = %b%n", routeOnCounter, workAllowed);
	}

	@Transitions({
			@Transition(name = "rm", source = "2", target = "1", guard = "hasRouteRunning"),
			@Transition(name = "rm", source = "1", target = "0", guard = "hasRouteRunning")
	})
	public void removeRoute() {

		routeOnCounter--;
        System.out.printf(" routeOnCounter = %d,%n workAllowed = %b%n", routeOnCounter, workAllowed);
	}

	@Transitions({
			@Transition(name = "switch", source = "0", target = "0", guard = ""),
			@Transition(name = "switch", source = "1", target = "1", guard = ""),
			@Transition(name = "switch", source = "2", target = "2", guard = "")
	})
	public void switchWorkAllowed() {

		workAllowed = !workAllowed;
        System.out.printf("Switch transition handler for Route Monitor is being executed.%n workAllowed = %b%n", workAllowed);
	}

	@Guard(name = "canWork")
	public boolean canWork() {
		return workAllowed;
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

