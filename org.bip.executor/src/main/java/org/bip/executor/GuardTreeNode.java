package org.bip.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bip.api.Guard;
import org.bip.exceptions.BIPException;

// TODO DESIGN do we have to keep this class public?, make it private refactor guardparser into this package.
// TODO DESCRIPTION missing, explain this class purpose and document functions.
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

	public Boolean attachToNode(GuardTreeNode parent) {
		if (parent != null) {
			if (parent.addChildren(this)) {
				this.parent = parent;
				return true;
			}
		}
		return false;
	}

	public Collection<Guard> guardList() {
		return this.guardList;
	}

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
	 * @return
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
			}
			else {
				throw new BIPException("Cannot find publicly accessible guard function " + this.data);
			}
		}
		for (GuardTreeNode child : this.children) {
			usedGuards.addAll(child.createGuardTree(guardList));
		}
		return usedGuards;
	}

}
