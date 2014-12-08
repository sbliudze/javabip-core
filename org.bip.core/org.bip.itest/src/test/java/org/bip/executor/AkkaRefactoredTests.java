package org.bip.executor;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.OrchestratedExecutor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.ComponentAWithEnvData;
import org.bip.spec.ComponentAWithEnvDataInterface;
import org.bip.spec.ComponentB;
import org.bip.spec.ComponentC;
import org.bip.spec.ProperComponentAWithEnvData;
import org.bip.spec.seal.SealableData;
import org.bip.spec.seal.SealableDataReader;
import org.bip.spec.seal.SealableDataWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;

public class AkkaRefactoredTests {

	ActorSystem system;
	OrchestratedExecutorFactory factory;
	EngineFactory engineFactory;

	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem");
		factory = new OrchestratedExecutorFactory(system);
		engineFactory = new EngineFactory(system);

	}

	@After
	public void cleanup() {

		system.shutdown();

	}

	@Test
	@Ignore
	// TODO, analyse why this test is failing, what is wrong with this spec that it causes a NullPointerException in DataCoordinatorKernel.
	public void akkaSealableDataTest() {

		BIPGlue bipGlue4SealableData = new TwoSynchronGlueBuilder() {
			
			@Override
			public void configure() {
				
				synchron(SealableData.class, "get").to(SealableDataReader.class, "read");
				synchron(SealableData.class, "set").to(SealableDataWriter.class, "write");
				
				data(SealableDataWriter.class, "value").to(SealableData.class, "input");
				data(SealableData.class, "value").to(SealableDataReader.class, "input");
				
			}
		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		// Two not initialized and one initialized data
		SealableData<Integer> data1 = new SealableData<Integer>();
		BIPActor actor1 = engine.register(data1, "data1", true);
		
		SealableData<Integer> data2 = new SealableData<Integer>();
		BIPActor actor2 = engine.register(data2, "data2", true);

		SealableData<Integer> data3 = new SealableData<Integer>(5);
		BIPActor actor3 = engine.register(data3, "data3", true);

		// Two writers. 
		SealableDataWriter<Integer> writer1 = new SealableDataWriter<Integer>(4);
		BIPActor actor4 = engine.register(writer1, "writer1", true);

		SealableDataWriter<Integer> writer2 = new SealableDataWriter<Integer>(3);
		BIPActor actor5 = engine.register(writer2, "writer2", true);

		// One reader.
		SealableDataReader<Integer> reader = new SealableDataReader<Integer>();
		BIPActor actor6 = engine.register(reader, "reader", true);
		
		engine.specifyGlue(bipGlue4SealableData);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
		
		assertEquals("All SealableData are sealed", true, data1.isSealed() & data2.isSealed() & data3.isSealed());
		
	}	

	@Test
	public void bipDataAvailabilityTestWithEnvDataSpontaneous() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueDataAvailability.xml");

		ComponentAWithEnvData componentA = new ComponentAWithEnvData(250);
		BIPActor actor1 = engine.register(componentA, "compA", true);

		ComponentB componentB = new ComponentB();
		BIPActor actor2 = engine.register(componentB, "compB", true);

		ComponentC componentC = new ComponentC();
		BIPActor actor3 = engine.register(componentC, "compC", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("memoryLimit", new Integer(500));

		actor1.inform("b", data);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);


		assertEquals("New environment based memory limit is not set", componentA.memoryLimit, 500);

	}

	public static Object create(ActorSystem actorSystem, ClassLoader classLoader, OrchestratedExecutor executor, Object bipSpec) {
		
		final Object proxy = ExecutorHandler.newProxyInstance(classLoader, executor, bipSpec);
		
		Object executorActor = TypedActor.get(actorSystem).typedActorOf(
                new TypedProps<Object>((Class<? super Object>) proxy.getClass(), new Creator<Object>() {
                    public Object create() {
                        return proxy;
                    }
                }), executor.getId());
		
		return executorActor;

	}
	
	@Test
	public void simpleProxyTest() {
		
		ComponentAWithEnvData componentA = new ComponentAWithEnvData(250);
		
		ExecutorKernel executor = new ExecutorKernel(componentA, "compA", true);
		
		ComponentAWithEnvDataInterface proxy1 = (ComponentAWithEnvDataInterface)AkkaRefactoredTests.create(system, OrchestratedExecutor.class.getClassLoader(), executor, componentA);
		
		proxy1.spontaneousOfA(500);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals("New environment based memory limit is not set", componentA.memoryLimit, 500);
		
	}
	
	@Test
	public void bipProxyTest() throws BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueDataAvailability.xml");

		ProperComponentAWithEnvData componentA = new ProperComponentAWithEnvData(250);
		
		ComponentAWithEnvDataInterface proxy1 = (ComponentAWithEnvDataInterface) engine.register(componentA, "compA",
				true);

		ComponentB componentB = new ComponentB();
		BIPActor actor2 = engine.register(componentB, "compB", true);

		ComponentC componentC = new ComponentC();
		BIPActor actor3 = engine.register(componentC, "compC", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		proxy1.spontaneousOfA(500);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);


		assertEquals("New environment based memory limit is not set", componentA.memoryLimit, 500);

	}

	
	private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;
	}

}
