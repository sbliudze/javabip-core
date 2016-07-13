package org.bip.spec.resources;

import org.bip.resources.BoundedResourceManager;

public class MemoryManager extends BoundedResourceManager {

	private boolean hasData;
	private DataMemoryManager dManager;
	
	private int dataCount;
	private int dataDeletionCount;
	
	public MemoryManager(String name, int capacity) {
		super(name, capacity);
	}

	public void setDataMemoryManager(DataMemoryManager dManager) {
		this.dManager = dManager;
	}
	
	public void notifyCreation(String storedDataName, int readCount) {
		this.hasData = true;
		dManager.create(storedDataName);
		dataCount++;
	}

	public void notifyDeletion(String data) {
		this.hasData = false;
		dManager.delete();
		dataDeletionCount++;
	}
	
	@Override
	public String constraint() {
		// in case when memory stores data, its cost changes:
		// the part where memory is stored is not available
		// (in our case it is the whole memory)
		if (hasData) {
			return (resourceName + "=0");
		}
		return super.constraint();
	}
	
	@Override
	public String cost() {
		// in case when memory stores data, its cost changes:
		// the part where memory is stored is not available
		// (in our case it is the whole memory)
		if (hasData) {
			return ("0, " + resourceName + "=0 ;");
		}
		return super.cost();
	}
	
	public int dataCreationCount() {
		return dataCount;
	}
	
	public int dataDeletionCount() {
		return dataDeletionCount;
	}

}
