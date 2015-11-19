package io.xtech.bip;

import org.bip.api.BIPGlue;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

public class BIPGlueFactory implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");

				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off")	.requiresNothing();

				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class, "memoryUsage");

			}

		}.build();

		try {
			java.util.Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("domain.name", "switchable.route");
			context.registerService(BIPGlue.class.getName(), bipGlue, properties);
		}
		catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}



}
