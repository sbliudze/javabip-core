package org.bip.executor;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.spec.diningphilosophers.DiningPhilosophersGlueBuilder;
import org.bip.spec.diningphilosophers.Fork;
import org.bip.spec.diningphilosophers.Philosophers;
import org.bip.spec.hanoi.HanoiOptimalMonitor;
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
	public void akkaExecutorPhilosopherwithDataTest() {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue4Philosophers = new DiningPhilosophersGlueBuilder().build();

		Fork f1 = new Fork(1);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(3);

		Philosophers p1 = new Philosophers(1, 2, true);
		Philosophers p2 = new Philosophers(2, 3, true);
		Philosophers p3 = new Philosophers(3, 1, true);

		engine.register(f1, "f1E", true);
		engine.register(f2, "f2E", true);
		engine.register(f3, "f3E", true);

		engine.register(p1, "p1E", true);
		engine.register(p2, "p2E", true);
		engine.register(p3, "p3E", true);

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

		engine.stop();
		engineFactory.destroy(engine);

		assertEquals("BIP engine could not progress the system.", true, noOfTimesUsed2 > noOfTimesUsed);

	}

	@Test
	public void bipHannoiWithDataTestSize3() throws JAXBException, BIPException {

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		int size = 3;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder().build();

		HanoiOptimalMonitor hanoiMonitor = new HanoiOptimalMonitor(size);
		engine.register(hanoiMonitor, "hanoiMonitor", false);

		org.bip.spec.hanoi.LeftHanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.LeftHanoiPeg(size);
		engine.register(leftHanoiPeg, "LeftHanoiPeg", false);

		org.bip.spec.hanoi.MiddleHanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.MiddleHanoiPeg(size);
		engine.register(middleHanoiPeg, "MiddleHanoiPeg", false);

		org.bip.spec.hanoi.RightHanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.RightHanoiPeg(size);
		engine.register(rightHanoiPeg, "RightHanoiPeg", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Finished test, number of transitions " + hanoiMonitor.getNumberOfMoves());
		System.out.flush();


		engine.stop();
		engineFactory.destroy(engine);

		assertEquals("Hanoi tower has not reached its final state ", (int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());

	}

}
