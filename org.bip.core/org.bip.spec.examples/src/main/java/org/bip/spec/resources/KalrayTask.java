package org.bip.spec.resources;

import java.util.Hashtable;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.PortType;

@Ports({ 
	@Port(name = "askResource", type = PortType.enforceable),
	@Port(name = "getResource", type = PortType.enforceable),
	@Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.KalrayTask")
public class KalrayTask extends RConsumerComponent {

	private String dataToCreate;

	public KalrayTask(String utility, String dataCreated) {
		super(utility); // "p=1 & m=1 & Dxx=1"
		this.dataRelease.add("???");
		this.dataToCreate = dataCreated;
	}

	@Transition(name = "askResource", source = "0", target = "1", guard = "")
	public void askResource() {
		System.err.println("Asking for p, m, maybe D");
	}

	@Transition(name = "getResource", source = "1", target = "2", guard = "")
	public void getResource(@Data(name="resourceArray") Hashtable<String, String> resources, @Data(name="allocID") int allocID) {
		System.err.println("Storing the resources: "+ resources);
		this.allocID = allocID;
	}

	@Transition(name = "release", source = "2", target = "0", guard = "")
	public void releaseResource() {
		System.err.println("Releasing the resources: "+ dataRelease);
		//TODO create data  now
	}
	
}
