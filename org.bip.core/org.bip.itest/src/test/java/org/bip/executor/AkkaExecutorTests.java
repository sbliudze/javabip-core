package org.bip.executor;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.BIPCoordinator;
import org.bip.engine.api.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.spec.HanoiGlueBuilder;
import org.bip.spec.HanoiMonitor;
import org.bip.spec.LeftHanoiPeg;
import org.bip.spec.MiddleHanoiPeg;
import org.bip.spec.RightHanoiPeg;
import org.bip.spec.hanoi.HanoiOptimalMonitor;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaExecutorTests {

	@Test
	public void akkaExecutorSimpleTest() {

		HanoiMonitor hanoiMonitor = new HanoiMonitor(3);

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);

		// BIP engine.
		BIPCoordinator engine = new BIPCoordinatorImpl();

		Executor kernel = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		assertEquals("Actor Typed actor is not working properly", "hanoiMonitor", kernel.getId());

	}
	
	@Test

	public void bipHannoiWithDataTest() throws JAXBException, BIPException {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		int size = 8;

		BIPGlue bipGlue4Hanoi = new org.bip.spec.hanoi.HanoiOptimalGlueBuilder()
				.build();

		bipGlue4Hanoi.toXML(System.out);

		HanoiOptimalMonitor hanoiMonitor = new HanoiOptimalMonitor(size);
		Executor hanoiExecutor = factory.create(engine, hanoiMonitor, "hanoiMonitor", false);

		org.bip.spec.hanoi.LeftHanoiPeg leftHanoiPeg = new org.bip.spec.hanoi.LeftHanoiPeg(
				size);
		
		Executor lExecutor = factory.create(engine, leftHanoiPeg, "LeftHanoiPeg", false);

		org.bip.spec.hanoi.MiddleHanoiPeg middleHanoiPeg = new org.bip.spec.hanoi.MiddleHanoiPeg(
				size);
		Executor mExecutor = factory.create(engine, middleHanoiPeg, "MiddleHanoiPeg", false);


		org.bip.spec.hanoi.RightHanoiPeg rightHanoiPeg = new org.bip.spec.hanoi.RightHanoiPeg(
				size);
		Executor rExecutor = factory.create(engine, rightHanoiPeg, "RightHanoiPeg", false);

		engine.specifyGlue(bipGlue4Hanoi);
		engine.start();
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());
		engine.stop();
	}
	
	@Test
	public void akkaExecutorHannoiwithActorEngineTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine",
				new BIPCoordinatorImpl());


		int size = 3;

		BIPGlue bipGlue4Hanoi = new HanoiGlueBuilder(size).build();

		// bipGlue4Hanoi.toXML(System.out);

		// BIP engine.

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
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals((int) Math.pow(2, size) - 1,
				hanoiMonitor.getNumberOfMoves());

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);
		engine.stop();

	}	



	@Test
	public void akkaExecutorHannoiTest() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);


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

		boolean destroyed = factory.destroy(hanoiExecutor);
		destroyed &= factory.destroy(lExecutor);
		destroyed &= factory.destroy(mExecutor);
		destroyed &= factory.destroy(rExecutor);

		assertEquals("Some actors where not destroyed succesfully", true, destroyed);
		engine.stop();

	}	


}