package org.bip.spec.resources;

import java.util.ArrayList;
import java.util.Hashtable;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.DataOut.AccessType;

@Ports({ @Port(name = "askResource", type = PortType.enforceable),
		@Port(name = "getResource", type = PortType.enforceable),
		@Port(name = "generate", type = PortType.enforceable),
		@Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.resources.KalrayTask")
public class KalrayTask extends RConsumerComponent {

	private String dataToCreate;
	private String memoryId;
	private String name;

	public KalrayTask(String name, String utility, String dataCreated) {
		super(utility); // "p=1 & m=1 & Dxx=1"
		this.dataRelease.add("p");
		this.dataRelease.add("m"); // the other, additional resources are put in setRequiredData method
		this.dataToCreate = dataCreated;
		this.name = name;
		this.memoryId = "";
	}
	
	public void setRequiredData(String requiredDataName) {
		this.dataRelease.add(requiredDataName);
	}

	@Transition(name = "askResource", source = "0", target = "1", guard = "")
	public void askResource() {
		System.err.println("Task " + this.name + " has asked for resources with utility " + utility);
	}

	@Transition(name = "getResource", source = "1", target = "2", guard = "")
	public void getResource(@Data(name = "resourceArray") Hashtable<String, String> resources,
			@Data(name = "resourceAmounts") Hashtable<String, Integer> resourceAmounts, @Data(name = "allocID") int allocID) {
		System.err.println("Task " + this.name + " storing the resources: " + resourceAmounts);
		this.allocID = allocID;
		for (String resourceName: resourceAmounts.keySet()) {
			if (resourceName.contains("m") && resourceName.length()>1 && resourceAmounts.get(resourceName)>0) {
				this.memoryId = resources.get(resourceName);
				//return;
			}
		}
		if (this.memoryId==null)this.memoryId ="";
		
	}

	@Transition(name = "generate", source = "2", target = "3", guard = "")
	public void createData() {
		System.err.println("Task " + name + " genarating data " + dataToCreate);
		// this is synchronizing with KalrayMemory.create
	}

	@Transition(name = "release", source = "3", target = "0", guard = "")
	public void releaseResource() {
		System.err.println("Releasing the resources: " + dataRelease);
	}
	
	@Data(name = "dataArray", accessTypePort = AccessType.any)
	public ArrayList<String> dataArray() {
		ArrayList<String> ddd = new ArrayList<String>();
		ddd.add(utility);
		ddd.add(name);
		return ddd;
	}

	@Data(name = "componentID", accessTypePort = AccessType.any)
	public String componentID() {
		return name;
	}
	
	@Data(name = "memory", accessTypePort = AccessType.any)
	public String memoryID() {
		return memoryId;
	}

	@Data(name = "dataName", accessTypePort = AccessType.any)
	public String generatedDataName() {
		return dataToCreate;
	}

	@Data(name = "dataReadCount", accessTypePort = AccessType.any)
	public int dataReadCount() {
		return 1;
	}
}
