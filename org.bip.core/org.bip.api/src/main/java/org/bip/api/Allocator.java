package org.bip.api;

public interface Allocator {

	public void request();
	public void release();
	public void addResource(ResourceProvider resource);
}
