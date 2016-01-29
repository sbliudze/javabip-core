package org.bip.spec.resources;

import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.ResourceProvider;
import org.bip.api.ResourceType;

public class RouteManager implements ResourceProvider {

	private ArrayList<RouteResource> routes;
	private String cost = "";
	private int capacity;
	private int currentCapacity;
	
	public RouteManager() {
		routes = new ArrayList<RouteResource>();
		
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);
		
		RouteBuilder builder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						//.routePolicy(routePolicy1)
						.to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2")
						//.routePolicy(routePolicy2)
				.to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						//.routePolicy(routePolicy3)
						.to("file:outputfolder3");
			}
			
		};
		
		
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void augmentCost(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String cost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decreaseCost(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceType type() {
		return ResourceType.route;
	}

	@Override
	public String providedResourceID() {
		return "route";
	}
	
}
