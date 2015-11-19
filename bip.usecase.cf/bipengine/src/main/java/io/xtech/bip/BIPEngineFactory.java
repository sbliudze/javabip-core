package io.xtech.bip;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;
import akka.osgi.ActorSystemActivator;
import akka.osgi.OsgiActorSystemFactory;
import com.typesafe.config.*;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.OrchestratedExecutor;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.ExecutorKernel;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.osgi.framework.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BIPEngineFactory implements BundleActivator {

	// volatile Thread greetingThread;

	@Override
	public void start(final BundleContext context) throws Exception {

		System.out.println("Client bundle with robust greeting is starting.");

//		Runnable runnable = new Runnable() {
//			public void run() {

				ClassLoader actorSystemClassLoader = ActorSystem.class.getClassLoader();
				Config config = ConfigFactory.defaultReference(actorSystemClassLoader);

				ActorSystem actorSystem = ActorSystem.create("BIPActorSystem",  config, actorSystemClassLoader);
				EngineFactory engineFactory = null;
				HashMap<String, BIPEngine> engines = new HashMap<String, BIPEngine>();



/*
				// Get ActorSystem to create EngineFactory.
				while (actorSystem == null)	{

						ServiceReference ref = context.getServiceReference(ActorSystem.class.getName());

						if (ref != null) {

							try {

								actorSystem = (ActorSystem) context.getService(ref);

								if (actorSystem != null) {
									engineFactory = new EngineFactory(actorSystem);
								}

								if (actorSystem == null)
									Thread.sleep(5000);

							} catch (InterruptedException ex) {
								System.out.println("Intialization thread interrupted without a chance to complete.");
								return;
							}

							context.ungetService(ref);

						}
					}
*/

						/////////////

						ServiceListener bipGlueListener = new BIPGlueListener(engineFactory, context, actorSystem);

						synchronized (bipGlueListener) {

							String filter = "(" + Constants.OBJECTCLASS + "="
									+ BIPGlue.class.getName() + ")";

							try {
								context.addServiceListener(bipGlueListener, filter);
								ServiceReference[] refs = context.getServiceReferences(null, filter);

								if (refs != null) {
									for (ServiceReference r : refs) {
										bipGlueListener.serviceChanged(
												new ServiceEvent(ServiceEvent.REGISTERED, r));
									}
								}


							} catch (InvalidSyntaxException e) {
								e.printStackTrace();
								return;
							}

						}

//			}
//		};

//		greetingThread = new Thread(runnable);
//		greetingThread.start();

	}

	@Override
	public void stop(BundleContext context) throws Exception {

	//	if (greetingThread != null)
	//		greetingThread.interrupt();

	}


	public class BIPGlueListener implements ServiceListener {

		private final ActorSystem actorSystem;
		EngineFactory engineFactory;

		BundleContext context;

		public BIPGlueListener(EngineFactory engineFactory, BundleContext context, ActorSystem actorSystem) {
			this.engineFactory = engineFactory;
			this.context = context;
			this.actorSystem = actorSystem;
		}

		HashMap<ServiceReference, ServiceRegistration> map = new HashMap<ServiceReference, ServiceRegistration>();


		public synchronized void serviceChanged(ServiceEvent event) {

			switch (event.getType()) {

				case ServiceEvent.REGISTERED:

					BIPGlue glue = (BIPGlue) context.getService(event.getServiceReference());

					if (glue != null) {
						BIPEngine bipEngine = create(glue, actorSystem);

						Object domainName = event.getServiceReference().getProperty("domain.name");
						Hashtable<String, Object> properties = new Hashtable<String, Object>();
						properties.put("domain.name", domainName);

						try {
							ServiceRegistration serviceRegistration = context.registerService(BIPEngine.class.getName(), bipEngine, properties);
							map.put(event.getServiceReference(), serviceRegistration);
						}
						catch (Exception ex) {
							map.remove(event.getServiceReference());
						}

						// test //

						CamelContext camelContext = new DefaultCamelContext();
						camelContext.setAutoStartup(false);

						SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
						SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
						SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);

						System.out.println("Trying to create a Typed actor");
						System.out.flush();

						final ExecutorKernel executor = new ExecutorKernel(route1, "1", true);
						OrchestratedExecutor executorActor;

						executorActor = TypedActor.get(actorSystem).typedActorOf(
								new TypedProps<OrchestratedExecutor>(OrchestratedExecutor.class,
										new Creator<OrchestratedExecutor>() {
											public ExecutorKernel create() {
												return executor;
											}
										}), executor.getId());


						executor.setProxy(executorActor);

						System.out.println("Typed actor created successfully " + executorActor);
						System.out.flush();

						executorActor.register(bipEngine);



						// test //
					}


					break;

				case ServiceEvent.MODIFIED:
					break;

				case ServiceEvent.UNREGISTERING:

					ServiceRegistration serviceRegistration = map.remove(event.getServiceReference());
					if (serviceRegistration != null) {
						BIPEngine engine = (BIPEngine) context.getService( serviceRegistration.getReference() );
						serviceRegistration.unregister();
						engineFactory.destroy(engine);
					}
					break;

				default:
					break;
			}
		}

	}


	public BIPEngine create(BIPGlue glue, ActorSystem actorSystem) {

		BIPEngine bipEngine;

		if (glue.getDataWires().size() == 0) {
			bipEngine = new BIPCoordinatorImpl(actorSystem);
		}
		else {
			bipEngine = new DataCoordinatorKernel(new BIPCoordinatorImpl(actorSystem));
		}

		// bipEngine.specifyGlue(glue);

		return bipEngine;
	}

}
