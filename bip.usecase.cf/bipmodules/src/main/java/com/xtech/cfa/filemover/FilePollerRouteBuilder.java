package com.xtech.cfa.filemover;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * FilePollerRouteBuilder contains the route which will move file from inFolder
 * to outFolder with an initial delay when route is established.
 * 
 * @author radoslaw.szymanek
 * 
 */
class FilePollerRouteBuilder extends RouteBuilder implements InitializingBean {

	/**
	 * Logger for logging information.
	 */
	Logger logger = LoggerFactory.getLogger(FilePollerRouteBuilder.class);

	/**
	 * Input folder from which files will be picked up by the route.
	 */
	String inFolder;

	/**
	 * Output folder to which files will be written to.
	 */
	String outFolder;

	String routeId;

	/**
	 * Configures file processing route.
	 */
	@Override
	public void configure() {
		from("file:" + inFolder + "?delete=true").process(new Processor() {

			public void process(Exchange exchange) throws Exception {

			}
		}).routeId(routeId).noAutoStartup().to("file:" + outFolder);
	}

	/**
	 * Called after the bean has been created and properties set.
	 */
	public void afterPropertiesSet() {

		if (inFolder == null)
			throw new IllegalStateException("InFolder is not setup properly");

		if (outFolder == null)
			throw new IllegalStateException("OutFolder is not setup properly");

		logger.info("Route from {} to {} has been established.", inFolder, outFolder);

	}

	/**
	 * Set the input folder from which the route will be picking up. It is used
	 * by Spring and does not change the route after it has been built.
	 * 
	 * @param inFolder
	 *            input folder
	 */
	public void setInFolder(String inFolder) {
		this.inFolder = inFolder;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	/**
	 * Set the output folder from which the route will be writing to. It is used
	 * by Spring and does not change the route after it has been built.
	 * 
	 * @param outFolder
	 *            output folder
	 */
	public void setOutFolder(String outFolder) {
		this.outFolder = outFolder;
	}

}
