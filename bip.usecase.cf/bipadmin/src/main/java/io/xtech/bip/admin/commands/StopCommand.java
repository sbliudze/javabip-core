package io.xtech.bip.admin.commands;

/**
 * User: radoslaw szymanek
 * Date: 6/1/12
 * Copyright 2012 Crossing-Tech
 */

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.bip.api.BIPEngine;
import org.osgi.framework.ServiceReference;

@Command(scope = "bip", name = "stop", description = "Stops the engine.")
public class StopCommand extends OsgiCommandSupport {

	@Override
	protected Object doExecute() throws Exception {

		ServiceReference ref = getBundleContext().getServiceReference(BIPEngine.class.getName());

		if (ref != null) {

			try {

				BIPEngine bipEngine = (BIPEngine) getBundleContext().getService(ref);

				if (bipEngine != null) {

					bipEngine.stop();

				} else {
					System.out.println("No BIPEngine service available. I do not know what to stop.");
				}

			} finally {
				getBundleContext().ungetService(ref);
			}

		} else {
			System.out.println("No BIP Engine service available. I do not know what BIP engine to stop.");
		}

		return null;

	}
}