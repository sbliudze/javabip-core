package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.createRoutePolicy;

import org.apache.camel.CamelContext;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.spec.A;
import org.bip.spec.B;
import org.bip.spec.C;
import org.bip.spec.ExampleA;
import org.bip.spec.ExampleB;
import org.bip.spec.ExampleC;
import org.bip.spec.ExampleE;
import org.bip.spec.RouteOnOffMonitor;
import org.bip.spec.SwitchableRouteExecutableBehavior;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DynamicityTests {

	private static ActorSystem system;
	private static EngineFactory engineFactory;

	@BeforeClass
	public static void initialize() {
		system = ActorSystem.create("MySystem");
		engineFactory = new EngineFactory(system);
	}

	@AfterClass
	public static void cleanup() {
		system.shutdown();
	}

	@Ignore
	@Test
	public void validDependenciesSystemTest() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueDependenciesSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine", glue);

		final int nbBCComponents = 5;

		A a = new A();
		B[] bComponents = new B[nbBCComponents];
		C[] cComponents = new C[nbBCComponents];

		// final BIPActor actorA =
		engine.register(a, "a", true);

		for (int i = 0; i < 5; ++i) {
			bComponents[i] = new B(String.format("%d", i));
			cComponents[i] = new C(String.format("%d", i));
			engine.register(bComponents[i], String.format("b%d", i), true);
			engine.register(cComponents[i], String.format("c%d", i), true);
		}

		sleep(2);

		engine.stop();
		sleep(1);
		// System.out.println(String.format("We have %d accept constraints",
		// glue.getAcceptConstraints().size()));
		// System.out.println(String.format("We have %d require constraints",
		// glue.getRequiresConstraints().size()));
		// System.out.println(bComponents[0].getResult());
		// System.out.println(cComponents[0].getResult());
		// System.out.println(a.getResult());
		engineFactory.destroy(engine);
	}

	@Test
	public void testEngineStartsAutomatically() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine0", glue);

		engine.register(new ExampleA(), "a0", true);
		engine.register(new ExampleA(), "a1", true);
		engine.register(new ExampleA(), "a2", true);
		engine.register(new ExampleB(), "b0", true);
		engine.register(new ExampleB(), "b1", true);
		engine.register(new ExampleC(), "c0", true);

		sleep(2);

		killEngine(engine);
	}

	@Test
	public void testEngineStartsAutomaticallyWithMicroSystem() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine1", glue);

		engine.register(new ExampleE(), "e", true);

		sleep(2);

		killEngine(engine);
	}

	@Test
	public void testCamelRoutesEngineStartsAutomatically() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("myEngine2", glue);

		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		CamelContext context = new DefaultCamelContext();
		r1.setCamelContext(context);

		setupCamelContext(context, new int[]{1});

		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(10), "monitor", true);

		sleep(2);

		killEngine(engine);
	}

	@Test
	public void testCamelRoutesAddRouteOnTheFly() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("myEngine3", glue);

		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		CamelContext context = new DefaultCamelContext();
		r1.setCamelContext(context);
		r2.setCamelContext(context);

		setupCamelContext(context, new int[]{1, 2});

		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(2), "monitor", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(5);

		killEngine(engine);
	}

	@Test
	public void testCamelRoutesAddMultipleRoutesOnTheFly() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("myEngine4", glue);

		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		SwitchableRouteExecutableBehavior r3 = new SwitchableRouteExecutableBehavior("r3");
		CamelContext context = new DefaultCamelContext();
		r1.setCamelContext(context);
		r2.setCamelContext(context);
		r3.setCamelContext(context);

		setupCamelContext(context, new int[]{1, 2, 3});
		
		engine.register(r1, "r1", false);
		engine.register(new RouteOnOffMonitor(3), "monitor", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(4);

		engine.register(r3, "r3", false);

		sleep(5);

		killEngine(engine);
	}
	
	@Test
	public void testCamelRoutesAddRoutesAndMonitors() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("myEngine5", glue);

		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		SwitchableRouteExecutableBehavior r2 = new SwitchableRouteExecutableBehavior("r2");
		SwitchableRouteExecutableBehavior r3 = new SwitchableRouteExecutableBehavior("r3");
		RouteOnOffMonitor m1 = new RouteOnOffMonitor(3);
		RouteOnOffMonitor m2 = new RouteOnOffMonitor(3);
		CamelContext context = new DefaultCamelContext();
		r1.setCamelContext(context);
		r2.setCamelContext(context);
		r3.setCamelContext(context);

		setupCamelContext(context, new int[]{1, 2, 3});

		engine.register(r1, "r1", false);
		engine.register(m1, "m1", true);

		sleep(3);

		engine.register(r2, "r2", false);

		sleep(4);

		engine.register(r3, "r3", false);

		sleep(5);
		
		engine.register(m2, "m2", true);
		
		sleep(4);

		killEngine(engine);
	}

	private void sleep(int s) {
		try {
			Thread.sleep(s * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void killEngine(BIPEngine engine) {
		engine.stop();
		sleep(1);
		engineFactory.destroy(engine);
	}
	
	private void setupCamelContext(CamelContext context, final int[] ids) {

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				for( int i = 0; i < ids.length ; ++i) {
					from("file:inputfolder"+i+"?delete=true").routeId(Integer.toString(i)).routePolicy(createRoutePolicy()).to("file:outputfolder"+i);
				}
			}
		};

		context.setAutoStartup(false);
		try {
			if(context.getStatus() != ServiceStatus.Started)
				context.addRoutes(builder);
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
