package org.bip.spec.resources;

import org.bip.resources.BoundedResourceManager;

public class MemoryManager extends BoundedResourceManager {

	private boolean hasData;
	private String dataName;
	private DataMemoryManager dManager;
	
	public MemoryManager(String name, int capacity) {
		super(name, capacity);
	}

	public void setDataMemoryManager(DataMemoryManager dManager) {
		this.dManager = dManager;
	}
	
	public void notifyCreation(String storedDataName, int readCount) {
		this.hasData = true;
		this.dataName = storedDataName;
		dManager.create(storedDataName);
	}

	public void notifyDeletion(String data) {
		this.hasData = false;
		dManager.delete();
	}
	
	@Override
	public String cost() {
		if (hasData) {
			return (resourceName + "=0");
		}
		return super.cost();
	}

}
