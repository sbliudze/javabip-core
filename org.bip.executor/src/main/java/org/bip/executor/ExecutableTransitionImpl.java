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
package org.bip.executor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bip.api.Data;
import org.bip.api.Guard;
import org.bip.api.PortType;
import org.bip.exceptions.BIPException;
import org.bip.executor.guardparser.boolLexer;
import org.bip.executor.guardparser.boolParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends Transition and provides information about a transition relevant to an executor. It has additional guard
 * information.
 * 
 * @author Alina Zolotukhina
 * 
 */
class ExecutableTransitionImpl extends TransitionImpl implements ExecutableTransition {

	private PortType portType;
	private GuardTreeNode guardTree;

	private Logger logger = LoggerFactory.getLogger(ExecutableTransitionImpl.class);

	public ExecutableTransitionImpl(TransitionImpl transition, PortType portType, Map<String, Guard> guards) {
		super(transition);
		this.portType = portType;
		if (hasGuard()) {
			this.guardTree = parseANTLR(guard);
			if (this.guardTree == null)
				throw new BIPException("Guard expression " + guard + " does not have proper syntax.");
			this.guardTree.createGuardList(guards);
		}
	}

	public PortType getType() {
		return this.portType;
	}

	/*
	 * This method is used in BehaviourImpl in order to check that the method is called on the object it belongs to and
	 * also to get the number of parameters. Possible improvement: remove method() and add two other methods for actions
	 * described above.
	 */
	public Method method() {
		return this.method;
	}

	public MethodHandle methodHandle() {
		return this.methodHandle;
	}

	public String guard() {
		return this.guard;
	}

	public GuardTreeNode guardTree() {
		return this.guardTree;
	}

	public Iterable<Data<?>> dataRequired() {
		return dataRequired;
	}

	public Collection<Guard> transitionGuards() {
		return this.guardTree.guardList();
	}

	private GuardTreeNode parseANTLR(String input) {
		boolLexer lexer = new boolLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		boolParser p = new boolParser(tokens);
		p.setBuildParseTree(true);
		// p.addParseListener(new boolListener());
		p.formula();
		if (!p.stack.empty()) {
			return p.stack.pop();
		}
		return null;
	}

	public boolean hasDataOnGuards() {
		if (!hasGuard()) {
			return false;
		}
		for (Guard guard : this.guardTree.guardList()) {
			if (guard.hasData()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasGuard() {
		return !guard.isEmpty();
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("ExecutorTransition=(");
		result.append("name = " + name());
		result.append(", source = " + source());
		result.append(" -> target = " + target());
		result.append(", guard = " + guard());
		result.append(", method = " + method());
		result.append(")");
		return result.toString();
	}

	public boolean guardIsTrue(Map<String, Boolean> guardToValue) throws BIPException {
		if (!hasGuard()) {
			return true;
		}
		if (this.guardTree.evaluate(guardToValue)) {
			logger.debug("Transition {} is enabled.", this.name());
			return true;

		} else {
			logger.debug("Transition {} is disabled.", this.name());
			return false;
		}
	}

	@Override
	public boolean hasData() {
		return dataRequired.iterator() == null;
	}
}
