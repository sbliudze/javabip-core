package org.bip.executor;

import static org.junit.Assert.assertEquals;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.seal.SealableData;
import org.bip.spec.seal.SealableDataReader;
import org.bip.spec.seal.SealableDataWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

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

}
