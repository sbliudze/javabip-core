package org.bip.dynamicity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.executor.ExecutorKernel;
import org.bip.glue.GlueBuilder;

public final class HelperFunctions {
	static BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return bipGlue;
	}

	static <T> BIPComponent createComponent(T instance, String id, boolean useSpec) {
		return new ExecutorKernel(instance, id, useSpec);
	}

	static RoutePolicy createRoutePolicy() {
		return new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {

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

	static void sleep(int s) {
		try {
			Thread.sleep(s * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static void killEngine(EngineFactory factory, BIPEngine engine) {
		engine.stop();
		sleep(1);
		factory.destroy(engine);
	}

	static void setupCamelContext(CamelContext context, final int[] ids) {
		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				for (int i = 0; i < ids.length; ++i) {
					from("file:inputfolder" + i + "?delete=true").routeId(Integer.toString(i))
							.routePolicy(createRoutePolicy()).to("file:outputfolder" + i);
				}
			}
		};

		context.setAutoStartup(false);
		try {
			if (context.getStatus() != ServiceStatus.Started)
				context.addRoutes(builder);
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
