package io.xtech.bip.admin.commands;

/**
 * User: radoslaw szymanek
 * Date: 6/1/12
 * Copyright 2012 Crossing-Tech
 */

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.glue.GlueBuilder;
import org.osgi.framework.ServiceReference;

@Command(scope = "bip", name = "execute", description = "Starts BIP Engine control of the registered components.")
public class ExecuteCommand extends OsgiCommandSupport {

	@Argument(index = 0, name = "domain", description = "the domainName of the BIP domain", required = true, multiValued = false)
    String domainName = null;
	
	@Override
	protected Object doExecute() throws Exception {

		String filter = "(domain=" + domainName + ")";
		
		ServiceReference[] ref = getBundleContext().getServiceReferences(BIPEngine.class.getName(), filter);

		if (ref != null) {

			ServiceReference firstServiceReference = ref[0];
			
			try {

				BIPEngine bipEngine = (BIPEngine) getBundleContext().getService(firstServiceReference);

				if (bipEngine != null) {

					try {

						String filename = "META-INF/BIP/" + domainName + ".xml";

						URL configURL = getBundleContext().getBundle().getResource(filename);

						if (configURL == null) {
							System.out.println("I can not find glue file " + filename + " for the domain " + domainName);
							return null;
						}

						InputStream inputStream = configURL.openConnection().getInputStream();

						BIPGlue bipGlue = GlueBuilder.fromXML(inputStream);

						bipEngine.specifyGlue(bipGlue);

					} catch (Exception ex) {
						System.out.println("Exception " + ex + "has occurred while trying to handle bipglue" );
						System.out.println("Ignoring start command.");
						return null;
					}

					bipEngine.execute();

				} else {
					System.out.println("No BIPEngine service for domain " + domainName + " available. I do not know what BIP engine to start.");
				}

			} finally {
				getBundleContext().ungetService(firstServiceReference);
			}

		} else {
			System.out.println("No BIPEngine service for domain " + domainName + " available. I do not know what BIP engine to start.");
		}

		return null;

	}

}