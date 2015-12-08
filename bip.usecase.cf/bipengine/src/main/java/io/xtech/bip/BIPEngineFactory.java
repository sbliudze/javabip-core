package io.xtech.bip;

import akka.actor.ActorSystem;
import com.typesafe.config.*;
import org.bip.api.*;
import org.bip.engine.factory.EngineFactory;
import org.osgi.framework.*;

import java.util.*;

public class BIPEngineFactory implements BundleActivator {

	// volatile Thread greetingThread;

	ActorSystem actorSystem;

	HashMap<String, BIPEngine> engines = new HashMap<String, BIPEngine>();

	BIPServiceListener bipServiceListener;

	@Override
	public void start(final BundleContext context) throws Exception {

		System.out.println("BIP Engine factory bundle listening to BIPGlue services is starting.");

				EngineFactory engineFactory = null;

				// Get ActorSystem to create EngineFactory.
				while (actorSystem == null)	{

						ServiceReference ref = context.getServiceReference(ActorSystem.class.getName());

						if (ref != null) {

							actorSystem = (ActorSystem) context.getService(ref); // can still be null.

						}

						if (actorSystem == null) {

							ClassLoader actorSystemClassLoader = ActorSystem.class.getClassLoader();
							Config config = ConfigFactory.defaultReference(actorSystemClassLoader);

							actorSystem = ActorSystem.create("BIPActorSystem",  config, actorSystemClassLoader);

						}
						else
							System.out.println("Using Actorsystem provided as a service.");

						engineFactory = new EngineFactory(actorSystem);
						// context.ungetService(ref);

				}

				bipServiceListener = new BIPServiceListener(engineFactory, context, actorSystem);

				synchronized (bipServiceListener) {

					String filter = "(" + Constants.OBJECTCLASS + "="
									+ BIPGlue.class.getName() + ")";

					try {
						context.addServiceListener(bipServiceListener, filter);
						ServiceReference[] refs = context.getServiceReferences(null, filter);

						if (refs != null) {
							for (ServiceReference r : refs) {
								bipServiceListener.serviceChanged(
									new ServiceEvent(ServiceEvent.REGISTERED, r));
							}
						}

					} catch (InvalidSyntaxException e) {
						e.printStackTrace();
						return;
					}

				}

		synchronized (bipServiceListener) {

			String filter = "(" + Constants.OBJECTCLASS + "="
					+ BIPSpec.class.getName() + ")";

			try {
				context.addServiceListener(bipServiceListener, filter);
				ServiceReference[] refs = context.getServiceReferences(null, filter);

				if (refs != null) {
					for (ServiceReference r : refs) {
						bipServiceListener.serviceChanged(
								new ServiceEvent(ServiceEvent.REGISTERED, r));
					}
				}

			} catch (InvalidSyntaxException e) {
				e.printStackTrace();
				return;
			}

		}



	}

	@Override
	public void stop(BundleContext context) throws Exception {

		synchronized (bipServiceListener) {

			bipServiceListener.close();

		}
	}


	public class BIPServiceListener implements ServiceListener {

		private final ActorSystem actorSystem;
		EngineFactory engineFactory;

		BundleContext context;

		public BIPServiceListener(EngineFactory engineFactory, BundleContext context, ActorSystem actorSystem) {
			this.engineFactory = engineFactory;
			this.context = context;
			this.actorSystem = actorSystem;
		}

		HashMap<ServiceReference, ServiceRegistration> map = new HashMap<ServiceReference, ServiceRegistration>();
		HashMap<String, BIPEngine> mapDomainNameEngine = new HashMap<String, BIPEngine>();
		HashMap<String, HashSet<ServiceReference>> mapEngineSpec = new HashMap<String, HashSet<ServiceReference>>();
		HashMap<String, Integer> mapEngineRequiredComponents = new HashMap<String, Integer>();
		HashMap<String, ArrayList<ServiceRegistration>> mapBIPActors = new HashMap<String, ArrayList<ServiceRegistration>>();

		public void close() {

			for (ServiceRegistration serviceRegistration : map.values() ) {
				BIPEngine engine = (BIPEngine) context.getService( serviceRegistration.getReference() );
				serviceRegistration.unregister();
				engine.stop();
				engineFactory.destroy(engine);
			}

		}

		public void registerComponent(BIPEngine bipEngine,
									  BIPSpec spec,
									  String domainName,
									  String componentName,
									  Boolean useAnnotationsBasedSpec) {

			BIPActor bipActor = bipEngine.register(spec, componentName, useAnnotationsBasedSpec);

			Hashtable<String, Object> properties = new Hashtable<String, Object>();
			properties.put("domain.name", domainName);
			properties.put("component.name", componentName);

			ServiceRegistration serviceRegistration =
					context.registerService(BIPActor.class.getName(), bipActor, properties);

			ArrayList<ServiceRegistration> actors = mapBIPActors.get(domainName);

			if (actors == null) {
				actors = new ArrayList<ServiceRegistration>();
				mapBIPActors.put(domainName, actors);
			}

			actors.add(serviceRegistration);

		}

		public synchronized void serviceChanged(ServiceEvent event) {

			Object service = context.getService(event.getServiceReference());

			switch (event.getType()) {

				case ServiceEvent.REGISTERED:

					if (service instanceof BIPGlue) {
						BIPGlue glue = (BIPGlue) service;

					if (glue != null) {

						String domainName;
						Integer noOfComponents;

						try {
							domainName = event.getServiceReference().getProperty("domain.name").toString();
							noOfComponents = Integer.valueOf( event.getServiceReference().getProperty("noOfComponents").toString() );
						}
						catch (Exception e) {
							return;
						}

						Hashtable<String, Object> properties = new Hashtable<String, Object>();
						properties.put("domain.name", domainName);
						properties.put("noOfComponents", noOfComponents);

						BIPEngine bipEngine = engineFactory.create((String) domainName, glue);

						try {
							ServiceRegistration serviceRegistration = context.registerService(BIPEngine.class.getName(), bipEngine, properties);
							map.put(event.getServiceReference(), serviceRegistration);
							mapDomainNameEngine.put(domainName, bipEngine);
							mapEngineRequiredComponents.put(domainName, noOfComponents);
						} catch (Exception ex) {
							map.remove(event.getServiceReference());
							mapDomainNameEngine.remove(domainName);
							mapEngineRequiredComponents.remove(domainName);
							engineFactory.destroy(bipEngine);
						}

					}

					}

					if (service instanceof BIPSpec) {
						BIPSpec spec = (BIPSpec) service;

						if (spec != null) {

							if (event.getServiceReference().getProperty("domain.name") == null ||
								event.getServiceReference().getProperty("component.name") == null ||
								event.getServiceReference().getProperty("annotations") == null)
								return;

							String domainName = event.getServiceReference().getProperty("domain.name").toString();

							if (mapEngineRequiredComponents.get(domainName) == -1)
								return;

							HashSet<ServiceReference> specs = mapEngineSpec.get(domainName);
							if (specs == null) {
								specs = new HashSet<ServiceReference>();
								mapEngineSpec.put(domainName, specs);
							}
							specs.add(event.getServiceReference());



							if (mapEngineRequiredComponents.get(domainName) != null &&
								specs.size() == mapEngineRequiredComponents.get(domainName)) {

								BIPEngine bipEngine = mapDomainNameEngine.get(domainName);

								for (ServiceReference ref : specs) {

									registerComponent(bipEngine,
											(BIPSpec) context.getService(ref),
											ref.getProperty("domain.name").toString(),
											ref.getProperty("component.name").toString(),
											Boolean.valueOf( ref.getProperty("annotations").toString() ));

								}

								mapEngineRequiredComponents.put(domainName, -1);
								bipEngine.start();
								bipEngine.execute();

							}
						}
					}



					break;

				case ServiceEvent.MODIFIED:
					break;

				case ServiceEvent.UNREGISTERING:

					if (service instanceof BIPGlue) {

						String domainName;
						Integer noOfComponents;

						try {
							domainName = event.getServiceReference().getProperty("domain.name").toString();
							noOfComponents = Integer.valueOf( event.getServiceReference().getProperty("noOfComponents").toString() );
						}
						catch (Exception e) {
							return;
						}

						ServiceRegistration serviceRegistration = map.remove(event.getServiceReference());
						BIPEngine engine = (BIPEngine) context.getService( serviceRegistration.getReference() );

						mapDomainNameEngine.remove(domainName);
						mapEngineSpec.remove(domainName);
						mapEngineRequiredComponents.remove(domainName);

						for (ServiceRegistration registration : mapBIPActors.get(domainName)) {
							registration.unregister();
						}

						serviceRegistration.unregister();
						engine.stop();
						engineFactory.destroy(engine);

					}

					if (service instanceof BIPSpec) {
						BIPSpec spec = (BIPSpec) service;

						if (spec != null) {

							if (event.getServiceReference().getProperty("domain.name") == null ||
									event.getServiceReference().getProperty("component.name") == null ||
									event.getServiceReference().getProperty("annotations") == null)
								return;

							String domainName = event.getServiceReference().getProperty("domain.name").toString();

							if (mapEngineRequiredComponents.get(domainName) == -1)
								return;

							HashSet<ServiceReference> specs = mapEngineSpec.get(domainName);
							if (specs != null) {
								specs.remove(event.getServiceReference());
							}

						}
					}

					break;

				default:
					break;
			}
		}

	}

}
