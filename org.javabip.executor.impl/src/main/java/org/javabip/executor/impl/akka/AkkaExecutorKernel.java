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
package org.javabip.executor.impl.akka;

import org.javabip.exceptions.BIPException;
import org.javabip.executor.ExecutorKernel;

import akka.actor.TypedActor;

public class AkkaExecutorKernel extends ExecutorKernel implements TypedActor.PostStop {

	public AkkaExecutorKernel(Object bipComponent, String id, boolean useSpec) throws BIPException {
		super(bipComponent, id, useSpec);
	}

	public AkkaExecutorKernel(Object bipComponent, String id) throws BIPException {
		super(bipComponent, id);
	}

	// TODO DISCUSSION there is also PostRestart lifecycle, can typedActor be restarted on its own? Investigate a bit to
	// make sure no changes to the design are needed.
	@Override
	public void postStop() {
		proxy = null;
	}

}
