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
 * Author: Simon Bliudze, Anastasia Mavridou, Larisa Safina, Radoslaw Szymanek and Alina Zolotukhina
 * Date: 23.02.22
 */

package org.javabip.executor;

import org.javabip.api.Invariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class InvariantImpl implements Invariant {

	private Logger logger = LoggerFactory.getLogger(InvariantImpl.class);

	private String expr;

	public InvariantImpl(String expr) {
		this.expr = expr;
	}

	public String expr() {
		return expr;
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Invariant=(");
		result.append("expr = " + expr());
		result.append(")");

		return result.toString();
	}

	public boolean evaluateInvariant() {
		throw new UnsupportedOperationException();
	}
}
