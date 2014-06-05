package org.bip.executor;

import static org.junit.Assert.assertEquals;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.spec.diningphilosophers.DiningPhilosophersGlueBuilder;
import org.bip.spec.diningphilosophers.Fork;
import org.bip.spec.diningphilosophers.Philosophers;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DiningPhilosopherTests {

	@Test
	public void akkaExecutorPhilosopherwithDataTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));


		BIPGlue bipGlue4Philosophers = new DiningPhilosophersGlueBuilder().build();

		Fork f1 = new Fork(1);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(3);
		
		Philosophers p1 = new Philosophers(1, 2, true);
		Philosophers p2 = new Philosophers(2, 3, true);
		Philosophers p3 = new Philosophers(3, 1, true);
		
		
		Executor f1E = factory.create(engine, f1, "f1E", true);
		Executor f2E = factory.create(engine, f2, "f2E", true);
		Executor f3E = factory.create(engine, f3, "f3E", true);
		
		Executor p1E = factory.create(engine, p1, "p1E", true);
		Executor p2E = factory.create(engine, p2, "p2E", true);
		Executor p3E = factory.create(engine, p3, "p3E", true);

		engine.specifyGlue(bipGlue4Philosophers);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int noOfTimesUsed = f1.noOfTimesUsed() + f2.noOfTimesUsed() + f3.noOfTimesUsed();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int noOfTimesUsed2 = f1.noOfTimesUsed() + f2.noOfTimesUsed() + f3.noOfTimesUsed();

		assertEquals("BIP engine could not progress the system.", true,
				noOfTimesUsed2 > noOfTimesUsed);

		boolean destroyed = factory.destroy(f1E);
		destroyed &= factory.destroy(f2E);
		destroyed &= factory.destroy(f3E);
		destroyed &= factory.destroy(p1E);
		destroyed &= factory.destroy(p2E);
		destroyed &= factory.destroy(p3E);

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);
		engine.stop();

	}	

}
