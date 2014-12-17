package org.bip.executor;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.spec.telephonic.CalleeAgregation;
import org.bip.spec.telephonic.CallerAgregation;
import org.bip.spec.telephonic.Client;
import org.bip.spec.telephonic.DialWaitSync;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class TelephonicTest {

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
	public void test()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		int n=3; 
		Client client1 = new Client(1,n);
		Client client2 = new Client(2,n);
		Client client3 = new Client(3,n);
		CallerAgregation callerAgregation = new CallerAgregation(n);
		CalleeAgregation calleeAgregation = new CalleeAgregation(n); 
		DialWaitSync dialWait = new DialWaitSync(n);
		
		
		
		BIPActor actor1 = engine.register(client1, "client1", true);
		BIPActor actor2 = engine.register(client2, "client2", true);
		BIPActor actor3 = engine.register(client3, "client3", true);
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee);
		client2.setExecutorRefs(actorCaller, actorCallee);
		client3.setExecutorRefs(actorCaller, actorCallee);
		callerAgregation.setExecutorRefs(actorDialWait);
		calleeAgregation.setExecutorRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		callerAgregation.setClientRefs(actor1, 1);
		callerAgregation.setClientRefs(actor2, 2);
		callerAgregation.setClientRefs(actor3, 3);
		
		calleeAgregation.setClientRefs(actor1, 1);
		calleeAgregation.setClientRefs(actor2, 2);
		calleeAgregation.setClientRefs(actor3, 3);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
}
