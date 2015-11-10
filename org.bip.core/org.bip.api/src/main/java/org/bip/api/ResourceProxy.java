package org.bip.api;

public interface ResourceProxy {
	
	public ResourceProxy get();
	public void release(ResourceProxy proxy);

}
