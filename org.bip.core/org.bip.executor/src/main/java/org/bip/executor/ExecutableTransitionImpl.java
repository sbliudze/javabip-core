package org.bip.executor;


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bip.api.Data;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.exceptions.BIPException;
import org.bip.executor.guardparser.boolLexer;
import org.bip.executor.guardparser.boolParser;
import org.bip.impl.TransitionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends Transition and provides information about a transition relevant to an executor. It has additional guard information.
 * 
 */
class ExecutableTransitionImpl extends TransitionImpl implements ExecutableTransition {

	private Port.Type type;
	private GuardTreeNode guardTree;

	private Logger logger = LoggerFactory.getLogger(ExecutableTransitionImpl.class);

	public ExecutableTransitionImpl(TransitionImpl transition, Port.Type type, List<Guard> guards) {
		super(transition);
		this.type = type;
		if (hasGuard()) {
			this.guardTree = parseANTLR(guard);
			this.guardTree.createGuardList(guards);
		}
	}

	public Port.Type getType() {
		return this.type;
	}

	public String name() {
		return this.name;
	}

	public String source() {
		return this.source;
	}

	public String target() {
		return this.target;
	}

	public Method method() {
		return this.method;
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

	public Collection<Guard> transitionGuards()
	{
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
		return guard != null && !guard.isEmpty();
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
}
