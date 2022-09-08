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

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.RoutePolicy;
import org.javabip.annotations.*;
import org.javabip.api.Executor;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

@Ports({
		@Port(name = "end", type = PortType.spontaneous),
		@Port(name = "on", type = PortType.enforceable),
		@Port(name = "off", type = PortType.enforceable),
		@Port(name = "finished", type = PortType.enforceable)
})
@ComponentType(initial = "off", name = "org.bip.spec.SwitchableRoute1")
public class SwitchableRoute1 extends SwitchableRoute {

	public SwitchableRoute1 (String routeId) {
		super (routeId);
	}

	public SwitchableRoute1 (String routeId, CamelContext camelContext) {
		super (routeId, camelContext);
	}
}


