package org.bip.executor;

import static org.junit.Assert.assertEquals;

import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.api.BIPCoordinator;
import org.bip.spec.HanoiGlueBuilder;
import org.bip.spec.HanoiMonitor;
import org.bip.spec.LeftHanoiPeg;
import org.bip.spec.MiddleHanoiPeg;
import org.bip.spec.RightHanoiPeg;
import org.junit.Ignore;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaExecutorTests {

	@Test
	public void akkaExecutorSimpleTest() {

		HanoiMonitor hanoiMonitor = new HanoiMonitor(3);

		ActorSystem system = ActorSystem.create("MySystem");
		ExecutorFactory factory = new ExecutorFactory(system);

		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl();

		Executor kernel = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		assertEquals("Actor Typed actor is not working properly", "hanoiMonitor", kernel.getId());

	}


	@Test
	@Ignore
	public void akkaExecutorHannoiTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		ExecutorFactory factory = new ExecutorFactory(system);


		int size = 3;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		// bipGlue4Hanoi.toXML(System.out);

		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl();

		HanoiMonitor hanoiMonitor = new HanoiMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		LeftHanoiPeg leftHanoiPeg = new LeftHanoiPeg(size);
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "leftHanoi", false); 

		MiddleHanoiPeg middleHanoiPeg = new MiddleHanoiPeg(size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "middleHanoi", false);

		RightHanoiPeg rightHanoiPeg = new RightHanoiPeg(size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "rightHanoi", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());

		factory.destroy(hanoiExecutor);
		factory.destroy(lExecutor);
		factory.destroy(mExecutor);
		factory.destroy(rExecutor);

		engine.stop();

	}	


}