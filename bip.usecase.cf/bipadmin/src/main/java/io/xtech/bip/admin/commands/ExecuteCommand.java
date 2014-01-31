package io.xtech.bip.admin.commands;

/**
 * User: radoslaw szymanek
 * Date: 6/1/12
 * Copyright 2012 Crossing-Tech
 */

import java.io.InputStream;
import java.net.URL;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.glue.GlueBuilder;
import org.osgi.framework.ServiceReference;

@Command(scope = "bip", name = "execute", description = "Starts BIP Engine control of the registered components.")
public class ExecuteCommand extends OsgiCommandSupport {

	@Override
	protected Object doExecute() throws Exception {

		String bipGlueFilename = "META-INF/BIP/bipGlueWithData.xml";

		ServiceReference ref = getBundleContext().getServiceReference(BIPEngine.class.getName());

		if (ref != null) {

			try {

				BIPEngine bipEngine = (BIPEngine) getBundleContext().getService(ref);

				if (bipEngine != null) {

					try {

						URL configURL = getBundleContext().getBundle().getResource(bipGlueFilename);

						if (configURL == null) {
							System.out.println("I can not find " + bipGlueFilename + " file. ");
							return null;
						}

						InputStream inputStream = configURL.openConnection().getInputStream();

						BIPGlue bipGlue = GlueBuilder.fromXML(inputStream);

						bipEngine.specifyGlue(bipGlue);

					} catch (Exception ex) {
						System.out.println("Exception " + ex + "has occurred while trying to handle bip-glue.xml");
						System.out.println("Ignoring start command.");
						return null;
					}

					bipEngine.execute();

				} else {
					System.out.println("No BIPEngine service available. I do not know what BIP engine to start.");
				}

			} finally {
				getBundleContext().ungetService(ref);
			}

		} else {
			System.out.println("No BIPEngine service available. I do not know what BIP engine to start.");
		}

		return null;

	}

}