package org.bip.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.RecognitionException;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.ResourceProvider;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.resources.AllocatorImpl;
import org.bip.resources.DNetException;
import org.bip.spec.resources.Bus;
import org.bip.spec.resources.ComponentNeedingResource;
import org.bip.spec.resources.Memory;
import org.bip.spec.resources.Processor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class ResourceTest {

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
	
	@Test
	public void test() throws RecognitionException, IOException, DNetException
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		//BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				 synchron(ComponentNeedingResource.class, "getResource").to(AllocatorImpl.class,
						 "request");
				 synchron(ComponentNeedingResource.class, "release").to(AllocatorImpl.class,
						 "release");
				 
				//	port(ComponentNeedingResource.class, "release").acceptsNothing();
				//	port(ComponentNeedingResource.class, "release")	.requiresNothing();
				 
				 data(ComponentNeedingResource.class, "utility").to(AllocatorImpl.class, "request");
				 data(ComponentNeedingResource.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
			}

		}.build();

		String dnetSpec = "src/test/resources/dnet.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec); 
		
		
		ComponentNeedingResource aComp = new ComponentNeedingResource(128);
		ComponentNeedingResource bComp = new ComponentNeedingResource(100);
		
		BIPActor actor1 = engine.register(aComp, "resourceNeeder1", true); 
		BIPActor actor2 = engine.register(bComp, "resourceNeeder2", true); 
		BIPActor allocatorActor = engine.register(alloc, "allocator", true); 
		aComp.setAllocator(allocatorActor);
		bComp.setAllocator(allocatorActor);
		ResourceProvider memory = new Memory(256);
		ResourceProvider processor = new Processor();
		ResourceProvider bus = new Bus(128);
		
		alloc.addResource(memory);
		alloc.addResource(processor);
		alloc.addResource(bus);

		engine.specifyGlue(bipGlue);
		
		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		engine.stop();
		engineFactory.destroy(engine);
	}
}
