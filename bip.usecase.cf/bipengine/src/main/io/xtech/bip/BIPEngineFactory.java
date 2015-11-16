package io.xtech.bip;

import akka.actor.ActorSystem;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;

public class BIPEngineFactory implements BundleActivator {

	volatile Thread greetingThread;

	@Override
	public void start(final BundleContext context) throws Exception {

		System.out.println("Client bundle with robust greeting is starting.");

		Runnable runnable = new Runnable() {
			public void run() {

				ActorSystem actorSystem = null;
				EngineFactory engineFactory = null;
				HashMap<String, BIPEngine> engines = new HashMap<String, BIPEngine>();

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


						/////////////

						ServiceListener bipGlueListener = new BIPGlueListener(engineFactory, context);

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

			}
		};

		greetingThread = new Thread(runnable);
		greetingThread.start();

	}

	@Override
	public void stop(BundleContext context) throws Exception {

		if (greetingThread != null)
			greetingThread.interrupt();

	}


	public class BIPGlueListener implements ServiceListener {

		EngineFactory engineFactory;

		BundleContext context;

		public BIPGlueListener(EngineFactory engineFactory, BundleContext context) {
			this.engineFactory = engineFactory;
			this.context = context;
		}

		HashMap<ServiceReference, ServiceRegistration> map = new HashMap<ServiceReference, ServiceRegistration>();


		public synchronized void serviceChanged(ServiceEvent event) {

			switch (event.getType()) {

				case ServiceEvent.REGISTERED:

					BIPGlue glue = (BIPGlue) context.getService(event.getServiceReference());

					if (glue != null) {
						BIPEngine bipEngine = create(glue);

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


	public BIPEngine create(BIPGlue glue) {

		BIPEngine bipEngine;

		if (glue.getDataWires().size() == 0) {
			bipEngine = new BIPCoordinationImpl(actorSystem);
		}
		else {
			bipEngine = new DataCoordinatorKernel(new BIPCoordinationImpl(actorSystem));
		}

		bipEngine.specifyGlue(glue);

		return bipEngine;
	}

}
