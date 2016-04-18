package org.bip.spec.resources;

import org.bip.resources.BoundedResourceManager;

public class DataMemoryManager extends BoundedResourceManager {

	private String dataName;
	private int data;
	private boolean isCreated = false;

	public DataMemoryManager(String name, int capacity) {
		super(name, capacity);
	}

	@Override
	public String cost() {
		if (!isCreated) {
			return (resourceName + "=0");
		}
		return resourceName + "=0 |" + resourceName + "=" + data;
	}

	public void create(String dataName) {
		isCreated = true;
		this.dataName = dataName;
		this.data = Integer.parseInt(dataName.substring(1));
		System.err.println("Data " + data + " created in dMemory " + name());
	}

	public void delete() {
		isCreated = false;
	}

//	@Override
//	public String providedResourceID() {
//		return "m" + resourceName.charAt(resourceName.length()-1);
//	}
	
}
