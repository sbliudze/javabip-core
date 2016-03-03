/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.spec.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.RoutePolicy;
import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.BIPActor;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.bip.api.ResourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Ports({ @Port(name = "end", type = PortType.spontaneous), @Port(name = "on", type = PortType.enforceable), 
		 @Port(name = "off", type = PortType.enforceable), @Port(name = "finished", type = PortType.enforceable),
		 @Port(name = "init", type = PortType.enforceable), @Port(name = "delete", type = PortType.enforceable)  })
@ComponentType(initial = "i", name = "org.bip.spec.resources.RouteResource")
public class RouteResource implements CamelContextAware, InitializingBean, DisposableBean, ResourceProxy {

	public int noOfEnforcedTransitions;
	
	public ModelCamelContext camelContext;

	public String routeId;

	Logger logger = LoggerFactory.getLogger(RouteResource.class);

	private BIPActor executor;
	private RoutePolicy notifier;

	private int deltaMemory = 100;
	
	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = (ModelCamelContext) camelContext;
	}

	public void setExecutor(BIPActor executor) {
		this.executor = executor;
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public RouteResource(String routeId, CamelContext camelContext) {
		this.routeId = routeId;
		this.camelContext = (ModelCamelContext) camelContext;
	}
	
	private String routeSource;

	@Transition(name = "init", source = "i", target = "off", guard = "idOK")
	public void initRoute(@Data(name="routeIn")String inPath, @Data(name="routeOut")String outPath) throws Exception {
		String id = routeId+ "1";
		routeSource = inPath;
		System.err.println("Creating new route from " + inPath + " to " + outPath + " with id " + id);
		newRoute(inPath, outPath, id);
		routeId = id;
		noOfEnforcedTransitions++;
		//System.err.println(camelContext.getRoutes());
	}
	
	private void newRoute(String inPath, String outPath, String id) {
		final String in = inPath;
		final String out = outPath;
		final String idd = id;
		RouteBuilder builder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:" + in + "?delete=true").routeId(idd)
				// onCompletion is processed every time an exchange of one file is done.
						.routePolicy(createRoutePolicy()).
//						onCompletion().process(new org.apache.camel.Processor() {
//		                    Thread stop;
//		                    
//		                    @Override
//		                    public void process(final Exchange exchange) throws Exception {
//		                        // stop this route using a thread that will stop
//		                        // this route gracefully while we are still running
//		                        if (stop == null) {
//		                            stop = new Thread() {
//		                                @Override
//		                                public void run() {
//		                                    try {
//		                                    	System.err.println("RUNNING SHUTDOWN");
//		                                    	//TODO this method does not work if there are no files to process
//		                                    	// HACK creating another thread to stop the route...
//		                                        exchange.getContext().stopRoute(routeId);
//		                                    } catch (Exception e) {
//		                                        // ignore
//		                                    }
//		                                }
//		                            };
//		                        }
//		 
//		                        // start the thread that stops this route
//		                        stop.start();
//		                    }
//		                }).end().
		                to("file:" + out);
			}
		};
		try {
			camelContext.addRoutes(builder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RoutePolicy createRoutePolicy() {
		return new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				System.err.print("exchange done");
				executor.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			@Override
			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};
	}

	@Transition(name = "delete", source = "off", target = "i", guard = "")
	public void deleteRoute() throws Exception {
		logger.debug("Stop transition handler for {} is being executed.", routeId);
		camelContext.stopRoute(routeId);
		camelContext.removeRoute(routeId);
		noOfEnforcedTransitions++;
	}
	
	@Transition(name = "on", source = "off", target = "on", guard = "")
	public void startRoute() throws Exception {
		logger.debug("Start transition handler for {} is being executed.", routeId);
		camelContext.resumeRoute(routeId);
		System.out.println("Starting route " + routeId);
		noOfEnforcedTransitions++;
	}
	
	//we assume that new files do not appear in the folder, therefore once it's empty, we can stop the route.
	//@Transition(name = "", source = "on", target = "off", guard = "folderIsEmpty")
	public void stopEmptyRoute() throws Exception {
		logger.debug("Stop due to no data transition handler for {} is being executed.", routeId);
		camelContext.stopRoute(routeId);
		System.out.println("Stopping route " + routeId + " as there is no transfer to be done");
		noOfEnforcedTransitions++;
	}

	@Transition(name = "end", source = "on", target = "off", guard = "")
	public void spontaneousEnd() throws Exception {
		logger.debug("Received end notification for the route {}.", routeId);
		camelContext.suspendRoute(routeId);
	}

	@Guard(name = "folderIsEmpty")
	public boolean isDirEmpty() throws IOException {
		File file = new File(routeSource);
		return file.list().length==0;
	}

	@Guard(name = "isFinished")
	public boolean isFinished() {
		if (camelContext == null)
			throw new IllegalStateException( "Camel Context has not been properly setup for route " + routeId);
		return camelContext.getInflightRepository().size(routeId) == 0;
	}

	
	@Guard(name = "idOK")
	public boolean interactionAllowed(@Data(name="id")String id) {
		return this.routeId==id;
	}

	@Data(name = "deltaMemoryOnTransition", accessTypePort = AccessType.allowed, ports = { "on", "finished" })
	public int deltaMemoryOnTransition() {
		return deltaMemory;
	}

	public void afterPropertiesSet() throws Exception {
		RouteDefinition routeDefinition = camelContext.getRouteDefinition(routeId);

		if (routeDefinition == null)
			throw new IllegalStateException("The route with a given id " + routeId + " can not be found in the CamelContext.");

		if (executor == null)
			throw new IllegalStateException("BIP Executor for handling this bip spec has not been injected thus no spontaneous even notification can be established.");

		List<RoutePolicy> routePolicyList = routeDefinition.getRoutePolicies();

		if (routePolicyList == null) {
			routePolicyList = new ArrayList<RoutePolicy>();
		}
		final BIPActor finalExecutor = executor;
		notifier = new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				finalExecutor.inform("end");
				System.err.println("exchange done!!!");
			}

			@Override
			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};

		routePolicyList.add(notifier);
		routeDefinition.setRoutePolicies(routePolicyList);

	}

	public void destroy() throws Exception {

		RouteDefinition routeDefinition = camelContext.getRouteDefinition(routeId);

		if (routeDefinition != null) {

			List<RoutePolicy> routePolicyList = routeDefinition.getRoutePolicies();

			routePolicyList.remove(notifier);
			routeDefinition.setRoutePolicies(routePolicyList);

		}

	}
	
	private final String commonResourceID = "route"; 
	
	@Override
	public String resourceID() {
		//return commonResourceID + routeId;
		return routeId;
	}

}
