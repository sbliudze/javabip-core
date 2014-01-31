package io.xtech.bip.admin.commands;

/**
 * User: radoslaw szymanek
 * Date: 6/1/12
 * Copyright 2012 Crossing-Tech
 */

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.Executor;
import org.bip.jmx.ExecutorBean;
import org.osgi.framework.*;

@Command(scope = "bip", name = "register", description = "Registers a component.")
public class RegisterBIPComponentCommand extends OsgiCommandSupport {

	@Argument(index = 0, name = "service.id", description = "the service.id number of the registered BIP component", required = true, multiValued = false)
	String name = null;

	@Override
	protected Object doExecute() throws Exception {

		ServiceReference ref = getBundleContext().getServiceReference(BIPEngine.class.getName());

		if (ref != null) {

			try {

				BIPEngine bipEngine = (BIPEngine) getBundleContext().getService(ref);

				if (bipEngine != null) {

					doRegister(bipEngine, name);

				} else {
					System.out.println("No BIPEngine service available. I do not know how to control BIP components.");
				}

			} finally {
				getBundleContext().ungetService(ref);
			}

		} else {
			System.out.println("No BIPEngine service available. I do not know how to control BIP components.");
		}

		return null;

	}

	private void doRegister(BIPEngine bipEngine, String name) {

		final ServiceReference ref;

		String filterString = "(&(" + Constants.OBJECTCLASS + "=" + BIPComponent.class.getName() + ")(service.id=" + name + "))";

		try {
			// this is used to check that the filterString is correct
			getBundleContext().createFilter(filterString);
		} catch (InvalidSyntaxException e) {
			System.out.println("Provided service.id " + name + " caused an exception " + e);
			return;
		}

		try {

			ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(null, filterString);

			if (serviceReferences == null) {
				System.out.println("Executor service with a given service.id=" + name + " has not been found.");
				return;
			} else
				ref = serviceReferences[0];

		} catch (InvalidSyntaxException e) {
			throw new IllegalStateException("The exception " + e.getClass().getName() + " should happen earlier in filter creation. ", e);
		}

		if (ref != null) {

			try {

				Executor executor = (Executor) getBundleContext().getService(ref);

				if (executor != null) {

					executor.register(bipEngine);

					// ******************JMX****************
					ExecutorBean mbean = new ExecutorBean(executor);

					MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
					ObjectName beanName;
					try {
						beanName = new ObjectName("bip.org.mbeans:type=" + executor.getId());
						if (!mbs.isRegistered(beanName))
							mbs.registerMBean(mbean, beanName);
					} catch (MalformedObjectNameException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (InstanceAlreadyExistsException e) {
						e.printStackTrace();
					} catch (MBeanRegistrationException e) {
						e.printStackTrace();
					} catch (NotCompliantMBeanException e) {
						e.printStackTrace();
					}

					// **********************************

				} else {
					System.out.println("No BIPEngine service available. I do not know how to control BIP components.");
				}

			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw ex;
			} finally {
				getBundleContext().ungetService(ref);
			}

		} else {
			System.out.println("No BIPEngine service available. I do not know how to control BIP components.");
		}

	}

}