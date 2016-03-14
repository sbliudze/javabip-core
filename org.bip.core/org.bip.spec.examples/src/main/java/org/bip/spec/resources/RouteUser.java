package org.bip.spec.resources;

import java.util.ArrayList;
import java.util.Hashtable;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.ResourceRequired;
import org.bip.annotations.ResourceUtility;
import org.bip.annotations.Transition;
import org.bip.api.PortType;
import org.bip.api.ResourceType;
import org.bip.api.DataOut.AccessType;

@Ports({ 
		@Port(name = "askRoute", type = PortType.enforceable),
		@Port(name = "getRoute", type = PortType.enforceable),
		@Port(name = "initRoute", type = PortType.enforceable),
		@Port(name = "transfer", type = PortType.enforceable),
		@Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.RouteUser")
public class RouteUser {

	private final String utility;// = "p=1 & m=128";
	private String routeID="";
	private int allocID;
	
	public RouteUser() {
		this.utility = "r=1";
	}
	
	@Transition(name = "askRoute", source = "0", target = "1", guard = "")
	@ResourceRequired(label = "r1", type = ResourceType.route, utility = "routeUtility")
	@ResourceUtility(utility = "r=1")
	public void askRoute() {
		System.err.println("Asking for a ROUTE");
	}

	@Transition(name = "getRoute", source = "1", target = "1.25", guard = "")
	public void getRoute(@Data(name="resourceArray") Hashtable<String, String> resources, @Data(name="allocID") int allocID) {
		// here we must be using a resource
		System.err.println("Storing the route resource: "+ resources);
		routeID = resources.get("r");
		this.allocID = allocID;
	}
	
	@Transition(name = "initRoute", source = "1.25", target = "1.5", guard = "")
	public void initRoute() {
		// here we create a route, giving it the endpoints we need, but in fact we do not need to perform anything ourselves
		// TODO create transitions which can synchronize with an "empty" transition?
		System.err.println("creating route");

	}
	
	@Transition(name = "transfer", source = "1.5", target = "2", guard = "")
	public void transfer() {
		// here we must be using a resource
		System.err.println("using route");

	}
	
	@Transition(name = "release", source = "2", target = "0", guard = "")
	public void release() {
		System.err.println("Releasing resources");
		routeID = "";
	}

	@Data(name = "utility", accessTypePort = AccessType.any)
	public String utility() {
		return utility;
	}

	@Data(name = "route", accessTypePort = AccessType.any)
	public String routeID() {
		return routeID;
	}

	private String inPath = "files/infolder/";
	private String outPath =  "files/outfolder/";
	
	@Data(name = "inPath", accessTypePort = AccessType.any)
	public String inPath() {
		return inPath;
	}
	
	@Data(name = "outPath", accessTypePort = AccessType.any)
	public String outPath() {
		return outPath;
	}
	
	@Data(name = "allocID", accessTypePort = AccessType.any)
	public int allocID() {
		return allocID;
	}

	
	@Data(name = "resourceUnit", accessTypePort = AccessType.any)
	public ArrayList<String> releasedResources() {
		ArrayList<String> dataRelease = new ArrayList<String>();
		dataRelease.add("r");
		return dataRelease;
	}
	
}
