package org.bip.dynamicity;

import static org.bip.dynamicity.HelperFunctions.createGlue;
import static org.bip.dynamicity.HelperFunctions.createRoutePolicy;

import org.apache.camel.CamelContext;
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
		//
		// engine.start();
		//
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// }
		//
		// engine.execute();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		engine.stop();
		// System.out.println(String.format("We have %d accept constraints",
		// glue.getAcceptConstraints().size()));
		// System.out.println(String.format("We have %d require constraints",
		// glue.getRequiresConstraints().size()));
		// System.out.println(bComponents[0].getResult());
		// System.out.println(cComponents[0].getResult());
		// System.out.println(a.getResult());
		engineFactory.destroy(engine);
	}

	// @Ignore
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

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		engine.stop();
		engineFactory.destroy(engine);
	}

	@Test
	public void testEngineStartsAutomaticallyWithMicroSystem() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExampleSystem.xml");
		BIPEngine engine = engineFactory.create("myEngine1", glue);

		engine.register(new ExampleE(), "e", true);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void testCamelRoutesEngineStartsAutomatically() {
		BIPGlue glue = createGlue("src/test/resources/bipGlueExecutableBehaviour.xml");
		BIPEngine engine = engineFactory.create("myEngine2", glue);

		SwitchableRouteExecutableBehavior r1 = new SwitchableRouteExecutableBehavior("r1");
		CamelContext context = new DefaultCamelContext();
		r1.setCamelContext(context);

		engine.register(r1, "r1", false);
		final RoutePolicy route1Policy = createRoutePolicy();

		engine.register(new RouteOnOffMonitor(10), "monitor", true);

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(route1Policy).to("file:outputfolder1");
			}

		};

		context.setAutoStartup(false);
		try {
			context.addRoutes(builder);
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.stop();
		engineFactory.destroy(engine);
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

		engine.register(r1, "r1", false);
		final RoutePolicy route1Policy = createRoutePolicy();
		final RoutePolicy route2Policy = createRoutePolicy();

		engine.register(new RouteOnOffMonitor(10), "monitor", true);

		RouteBuilder builder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(route1Policy).to("file:outputfolder1");
				from("file:inputfolder2?delete=true").routeId("2").routePolicy(route2Policy).to("file:outputfolder2");
			}

		};

		context.setAutoStartup(false);
		try {
			context.addRoutes(builder);
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.register(r2, "r2", false);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
}
