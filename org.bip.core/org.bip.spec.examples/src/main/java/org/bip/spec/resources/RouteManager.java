package org.bip.spec.resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteManager implements ResourceProvider {

	private Logger logger = LoggerFactory.getLogger(RouteManager.class);
	
	private final String name = "r";
	private ArrayList<RouteResource> avRoutes;
	private ArrayList<RouteResource> navRoutes;
	private String cost = "";
	private int currentCapacity;
	private CamelContext camelContext;
	private HashMap<RouteResource, String> routeIds;
	
	public RouteManager(CamelContext camelContext, ArrayList<RouteResource> routes) throws Exception {
		this.avRoutes = routes;
		this.camelContext = camelContext;
		currentCapacity = routes.size();
		this.cost =  costString();
		navRoutes = new ArrayList<RouteResource>();
		routeIds = new HashMap<RouteResource, String>();
		camelContext.stopRoute("1");
		camelContext.removeRoute("1");
		for (RouteResource routeResource : routes) {
			routeIds.put(routeResource, routeResource.routeId);
		}
	}
	
	//TODO augmentCost and decreaseCost are similar in different classes, make a superclass
	@Override
	public void augmentCost(String deltaCost) {
		logger.debug("Cost of " + name + " increased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity += taken;
		this.cost = costString();
		avRoutes.add(navRoutes.get(0));
		navRoutes.remove(0);
		//TODO this simple adding-removing above works fine only when there is one route
		//(otherwise it will add-remove an arbitrary route but not a particular one.
		System.err.println("cost is now (+) " + cost);
	}

	@Override
	public String cost() {
		return this.cost;
	}

	@Override
	public void decreaseCost(String deltaCost) {
		logger.debug("Cost of " + name + " decreased by " + deltaCost);
		int taken = Integer.parseInt(deltaCost);
		this.currentCapacity -= taken;
		this.cost = costString();
		navRoutes.add(avRoutes.get(0));
		avRoutes.remove(0);
		System.err.println("cost is now (-) " + cost);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ResourceType type() {
		return ResourceType.route;
	}

	@Override
	public String providedResourceID() {
		//TODO unstable: depends on the order of call with decreaseCost
		return navRoutes.get(0).resourceID();
	}
	
	private String costString() {
		return "r>=0 & r<=" + Integer.toString(currentCapacity);
	}
	
}
