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
package org.javabip.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.javabip.api.Guard;
import org.javabip.exceptions.BIPException;

// TODO DESIGN do we have to keep this class public?, make it private refactor guardparser into this package.
/**
 * Represents the tree of a guard expression for a transition.
 * 
 * @author Alina Zolotukhina
 * 
 */
public class GuardTreeNode {

	public ArrayList<GuardTreeNode> children = new ArrayList<GuardTreeNode>();

	public GuardTreeNode parent;
	public String data;
	public Guard guard;

	private Collection<Guard> guardList;

	public GuardTreeNode(String value, GuardTreeNode parent) {
		this.parent = parent;
		this.data = value;
		if (parent != null)
			parent.addChildren(this);
	}

	public GuardTreeNode(String value) {
		this.parent = null;
		this.data = value;
	}

	private Boolean addChildren(GuardTreeNode child) {
		this.children.add(child);
		return true;
	}

	public Boolean hasChildren() {
		return !this.children.isEmpty();
	}

	/**
	 * Attaches a child node to the parent one.
	 * 
	 * @param parent
	 *            the parent node
	 * @return the updated tree
	 */
	public Boolean attachToNode(GuardTreeNode parent) {
		if (parent != null) {
			if (parent.addChildren(this)) {
				this.parent = parent;
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter for a list of guards used in the guard expression of this transition.
	 * 
	 * @return the list of guards used in the guard expression
	 */
	public Collection<Guard> guardList() {
		return this.guardList;
	}

	/**
	 * Computes the guard expression value given the evaluation of guards.
	 * 
	 * @param nameToValue
	 *            a mapping between the guard name ant its value
	 * @return the guard expression value
	 * @throws BIPException
	 *             when there is a guard which does not have an evaluation
	 */
	public Boolean evaluate(Map<String, Boolean> nameToValue) throws BIPException {
		if (this.data.equals("&"))
			return this.children.get(0).evaluate(nameToValue) && this.children.get(1).evaluate(nameToValue);
		else if (this.data.equals("|"))
			return this.children.get(0).evaluate(nameToValue) || this.children.get(1).evaluate(nameToValue);
		else if (this.data.equals("!")) {
			return !this.children.get(0).evaluate(nameToValue);
		} else {
			if (!nameToValue.containsKey(this.data)) {
				throw new BIPException("Missing evaluation for the guard function " + this.data);
			}
		}
		return (nameToValue.get(this.data));
	}

	/**
	 * Given a list of all guards, build a list of guards used in this guard expression
	 * 
	 * @param guardList
	 *            a mapping between guard name and guard instance
	 * @return a collection of guards used in this particular transition
	 * @throws BIPException
	 */
	public Collection<Guard> createGuardList(Map<String, Guard> guardList) throws BIPException {
		this.guardList = createGuardTree(guardList);
		return this.guardList;
	}

	private Collection<Guard> createGuardTree(Map<String, Guard> guardList) throws BIPException {
		ArrayList<Guard> usedGuards = new ArrayList<Guard>();
		// if this is not a symbol (i.e. this is a guard), add it to the list of guards
		if (!(this.data.equals("&") || this.data.equals("|") || this.data.equals("!"))) {

			this.guard = guardList.get(this.data);

			if (this.guard != null) {
				usedGuards.add(guard);
			} else {
				throw new BIPException("Cannot find publicly accessible guard function " + this.data);
			}
		}
		for (GuardTreeNode child : this.children) {
			usedGuards.addAll(child.createGuardTree(guardList));
		}
		return usedGuards;
	}

}
