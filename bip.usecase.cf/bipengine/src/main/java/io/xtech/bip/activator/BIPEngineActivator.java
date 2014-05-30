package io.xtech.bip.activator;

/**
 * User: radoslaw szymanek
 * Date: 5/31/12
 * Copyright 2012 Crossing-Tech
 */

import org.bip.api.BIPEngine;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorImpl;
import org.bip.engine.api.EngineFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import akka.actor.ActorSystem;

public class BIPEngineActivator implements BundleActivator {

	volatile BIPEngine engine;

	public void start(BundleContext ctx) throws Exception {
		
//		ActorSystem system = ActorSystem.create("MySystem");
//		EngineFactory engineFactory = new EngineFactory(system);
//		BIPEngine engine = engineFactory.create("myEngine",
//				new DataCoordinatorImpl(new BIPCoordinatorImpl()));
//
//		try {
//			ctx.registerService(BIPEngine.class.getName(), engine, null);
//		} catch (Exception ex) {
//			System.out.println(ex);
//			throw ex;
//		}
//
//		engine.start();
//		System.out.println("Bundle providing BIP Engine for Simple usecase is started.");
		
	}

	public void stop(BundleContext ctx) {
//		engine.stop();
//		System.out.println("Bundle providing BIP Engine for Simple usecase is stopped.");

	}

}