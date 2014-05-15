package org.bip.executor.impl.akka;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import akka.actor.ActorSystem;
import akka.osgi.ActorSystemActivator;

public class ExecutorActorSystemActivator extends ActorSystemActivator implements BundleActivator {

	@Override
	public void configure(BundleContext context, ActorSystem system) {
		registerService(context, system);
	}

}
