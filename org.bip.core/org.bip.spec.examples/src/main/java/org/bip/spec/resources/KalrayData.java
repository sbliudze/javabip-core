package org.bip.spec.resources;

import org.bip.resources.SingleResourceManager;

public class KalrayData extends SingleResourceManager  {

	private boolean created;
	
	public KalrayData(String name) {
		super(name);
		this.created = false;
	}
	
	public void create() {
		logger.info("The resource data " + this.resourceName + " has been created.");
		created = true;
	}
}
