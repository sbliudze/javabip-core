package org.bip.executor;

import org.bip.api.ResourceType;

public class ResourceReqImpl {

	private ResourceType type;
	private String name;
	private String utility;

	public ResourceReqImpl(String name, ResourceType type) {
		this.name = name;
		this.type = type;
	}

	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Resource name=" + name);
		result.append(", type =" + type);
		return result.toString();
	}
	
	public String name() {
		return name;
	}
	
	public ResourceType type() {
		return type;
	}
}
