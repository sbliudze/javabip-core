package org.bip.api;

import java.util.ArrayList;
import java.util.Hashtable;

public interface ResourceConsumer {

	public ArrayList<String> releasedResources();
	public int allocID();
	public String utility();
	public void askResource();
	public void getResource(Hashtable<String, String> resources, int allocID);
	public void releaseResource();
}
