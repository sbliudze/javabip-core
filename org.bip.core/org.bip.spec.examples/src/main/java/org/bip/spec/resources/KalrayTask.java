package org.bip.spec.resources;

import java.util.ArrayList;
import java.util.Hashtable;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.DataOut.AccessType;

@Ports({ @Port(name = "askResource", type = PortType.enforceable),
		@Port(name = "getResource", type = PortType.enforceable),
		@Port(name = "readData", type = PortType.enforceable),
		@Port(name = "generate", type = PortType.enforceable),
		@Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "init", name = "org.bip.spec.resources.KalrayTask")
public class KalrayTask extends RConsumerComponent {

	private String dataToCreate;
	private String memoryId;
	private String name;
	private boolean asked;
	private boolean needsData;
	private ArrayList<String> dataMemories;

	public KalrayTask(String name, String utility, String dataCreated) {
		super(utility); // "p=1 & m=1 & Dxx=1"
		this.dataRelease.add("p");
		this.dataRelease.add("m"); // the other, additional resources are put in setRequiredData method
		this.dataToCreate = dataCreated;
		this.name = name;
		this.memoryId = "";
		this.asked = false;
		this.needsData = false;
		dataMemories = new ArrayList<String>();  
	}
	
	public void setRequiredData(String requiredDataName) {
		this.dataRelease.add(requiredDataName);
		if (requiredDataName.startsWith("D")) {
			this.needsData = true;
		}
	}

	@Transition(name = "askResource", source = "init", target = "asked", guard = "firstTime")
	public void askResource() {
		dataRelease.remove(dataRelease.size()-1);
		dataRelease.add("-1");
		System.err.println("Task " + this.name + " has asked for resources with utility " + utility);
		asked = true;
	}

	@Transition(name = "getResource", source = "asked", target = "got", guard = "")
	public void getResource(@Data(name = "resourceArray") Hashtable<String, String> resources,
			@Data(name = "resourceAmounts") Hashtable<String, Integer> resourceAmounts, @Data(name = "allocID") int allocID) {
		System.err.println("Task " + this.name + " storing the resources: " + resourceAmounts);
	
		this.allocID = allocID;
		Integer ii = allocID;
		dataRelease.remove(dataRelease.size()-1);
		dataRelease.add(ii.toString());
		System.err.println("Task " + this.name + ": resources to release after alloc " + allocID + " are " + dataRelease);
		for (String resourceName: resourceAmounts.keySet()) {
			if (resourceName.startsWith("m") && resourceName.length()>1 && resourceAmounts.get(resourceName)>0) {
				this.memoryId = resources.get(resourceName);
				//System.err.println("Storing memory id " + memoryId);
			}
			else if (resourceName.startsWith("dm") && resourceAmounts.get(resourceName)>0) {
				dataMemories.add(resources.get(resourceName));
				//System.err.println("Storing datamemory id " + dataMemories.get(dataMemories.size() -1));
			}
		}
		if (this.memoryId==null)this.memoryId ="";
		
	}
	
	@Transition(name = "", source = "got", target = "3", guard = "!needsData & noData")
	public void noDataToGenerate() {
		System.err.println("Task " + name + " does not generate data " + dataToCreate);
	}
	
	@Transition(name = "readData", source = "got", target = "got", guard = "needsData")
	public void readExternalData() {
		System.err.println("Task " + name + " reading data " + dataMemories.remove(0));
		//dataMemories.remove(0);
		// this is synchronizing with KalrayMemory.read
	}


	@Transition(name = "generate", source = "got", target = "3", guard = "!needsData & !noData")
	public void createData() {
		System.err.println("Task " + name + " genarating data " + dataToCreate);
		// this is synchronizing with KalrayMemory.create
	}

	@Transition(name = "release", source = "3", target = "init", guard = "")
	public void releaseResource() {
		System.err.println("Task " + name + " releasing the resources: " + dataRelease);
	}
	
	@Transition(name = "", source = "-1", target = "init", guard = "")
	public void cleanUp() {
		dataRelease.remove(dataRelease.size()-1);
		dataRelease.add("-1");
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
	
	@Data(name = "dataToRead", accessTypePort = AccessType.allowed, ports={"readData"})
	public String dataToRead() {
		if (dataMemories.size() > 0) {
			//System.out.println(" Task " + this.name + " asks for mem " + dataMemories.get(0));
			return dataMemories.get(0);
		}
		return "";
	}

	@Data(name = "dataReadCount", accessTypePort = AccessType.any)
	public int dataReadCount() {
		return 1;
	}
	
	@Guard(name="firstTime")
	public boolean hasAsked() {
		return !asked;
	}
	
	@Guard(name="needsData")
	public boolean needsData() {
		//System.out.println(needsData + " Task " + this.name + " needing mem " + dataMemories.get(0));
		return needsData && !dataMemories.isEmpty();
	}
	
	@Guard(name="noData")
	public boolean noData() {
		return dataToCreate=="";
	}
}
