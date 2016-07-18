package org.bip.spec.resources;

import java.util.ArrayList;

import org.bip.annotations.Data;
import org.bip.api.ResourceConsumer;
import org.bip.api.DataOut.AccessType;

public abstract class RConsumerComponent implements ResourceConsumer {

	protected String utility;
	protected int allocID;
	protected ArrayList<String> dataRelease = new ArrayList<String>();
	
	public RConsumerComponent(String utility) {
		this.utility = utility;
	}
	
	@Data(name = "allocID", accessTypePort = AccessType.any)
	public int allocID() {
		return allocID;
	}

	@Data(name = "resourceUnit", accessTypePort = AccessType.any)
	public ArrayList<String> releasedResources() {
		return dataRelease;
	}

	@Data(name = "utility", accessTypePort = AccessType.any)
	public String utility() {
		return utility;
	}

}
