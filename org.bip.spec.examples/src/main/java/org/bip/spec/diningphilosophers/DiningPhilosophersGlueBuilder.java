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
package org.bip.spec.diningphilosophers;

import org.bip.glue.TwoSynchronGlueBuilder;

/**
 * Basic glue builder that may not prevent Philosophers starvation alone.
 * 
 * @author radoslaw
 * 
 */
public class DiningPhilosophersGlueBuilder extends TwoSynchronGlueBuilder {

	@Override
	public void configure() {

		synchron(Philosophers.class, "pickupFork").to(Fork.class, "hold");
		synchron(Philosophers.class, "putdownFork").to(Fork.class, "free");

		data(Fork.class, "forkId").to(Philosophers.class, "forkId");

	}

}
