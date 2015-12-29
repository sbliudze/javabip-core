package org.bip.executor;

import org.bip.api.ResourceType;

public class ResourceReqImpl {

	private ResourceType type;
	private String name;
	private String utility;

	public ResourceReqImpl(String name, ResourceType type, String utility) {
		this.name = name;
		this.type = type;
		this.utility = utility;
	}

	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Resource name=" + name);
		result.append(", type =" + type);
		result.append(", utility=" + utility);
		return result.toString();
	}
}
