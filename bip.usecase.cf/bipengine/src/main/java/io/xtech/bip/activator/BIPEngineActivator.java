package io.xtech.bip.activator;

/**
 * User: radoslaw szymanek
 * Date: 5/31/12
 * Copyright 2012 Crossing-Tech
 */

import java.util.Map;
import java.util.Set;

import org.bip.api.BIPComponent;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Behaviour;
import org.bip.api.Port;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BIPEngineActivator implements BundleActivator {

	volatile BIPEngine engine;

	public void start(BundleContext ctx) throws Exception {
		engine = new DataCoordinatorImpl(new BIPCoordinatorImpl());

		try {
			ctx.registerService(BIPEngine.class.getName(), new BIPEngine() {
				public void specifyGlue(BIPGlue glue) {
					System.out.println("Glue specification has been provided ");
					engine.specifyGlue(glue);
				}

				public void register(BIPComponent component, Behaviour behaviour) {
					engine.register(component, behaviour);
				}

				public void start() {
					engine.start();
					System.out.println("BIP Engine was told to start");
				}

				public void stop() {
					engine.stop();
					System.out.println("BIP Engine was told to stop");
				}

				public void execute() {
					engine.execute();
					System.out.println("BIP Engine was told to execute");

				}


				public void inform(BIPComponent component, String currentState,
						Set<Port> disabledPorts) {
					engine.inform(component, currentState, disabledPorts);
					
				}

				public void informSpecific(BIPComponent decidingComponent,
						Port decidingPort,
						Map<BIPComponent, Set<Port>> disabledCombinations) {
					engine.informSpecific(decidingComponent, decidingPort, disabledCombinations);
					
				}
			}, null);
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}

		engine.start();
		System.out.println("Bundle providing BIP Engine for Simple usecase is started.");
	}

	public void stop(BundleContext ctx) {
		engine.stop();
		System.out.println("Bundle providing BIP Engine for Simple usecase is stopped.");

	}

}