package org.bip.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.RecognitionException;
import org.bip.api.Allocator;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.ResourceProvider;
import org.bip.engine.BIPCoordinatorImpl;
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
import org.bip.spec.telephonic.DummyComponent;
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
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		//BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				port(ComponentNeedingResource.class, "a").acceptsNothing();
				port(ComponentNeedingResource.class, "a")	.requiresNothing();
				
			}

		}.build();
		engine.specifyGlue(bipGlue);

		String dnetSpec = "src/test/resources/dnet.txt";
		Allocator alloc = new AllocatorImpl(dnetSpec); 
		
		
		ComponentNeedingResource aComp = new ComponentNeedingResource();
		BIPActor actor = engine.register(aComp, "resourceNeeder", true); 
		ResourceProvider memory = new Memory(256);
		ResourceProvider processor = new Processor();
		ResourceProvider bus = new Bus(128);
		
		alloc.addResource(memory);
		alloc.addResource(processor);
		alloc.addResource(bus);

		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		
		engine.stop();
		engineFactory.destroy(engine);
	}
}
