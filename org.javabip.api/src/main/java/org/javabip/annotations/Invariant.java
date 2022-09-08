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

package org.javabip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An invariant annotation, which specifies the invariant of a component
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Invariant {

	/**
	 * expr is a string containing plain Java expressions
	 * it must hold after construction of the component, can be assumed to hold before a transition, and must hold after a transition
	 * @return expr
	 */
	String expr();
}
