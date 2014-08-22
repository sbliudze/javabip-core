package org.bip.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.spec.Peer;
import org.bip.spec.Tracker;
import org.junit.Test;

import akka.actor.ActorSystem;

public class AkkaTrackersAndPeersTests {

	@Test
	public void TrackerPeer5Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Peer peer1a = new Peer(11);
		Peer peer1b = new Peer(12);
		Peer peer2a = new Peer(21);
		Peer peer2b = new Peer(22);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor1a = factory.create(engine, peer1a, "11", true);
		final Executor executor1b = factory.create(engine, peer1b, "12", true);
		final Executor executor2a = factory.create(engine, peer2a, "21", true);
		final Executor executor2b = factory.create(engine, peer2b, "22", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TrackerPeer10Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer15Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer20Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer25Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer30Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor6 = factory.create(engine, tracker6, "6", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);
		final Executor executor31 = factory.create(engine, peer21, "31", true);
		final Executor executor32 = factory.create(engine, peer22, "32", true);
		final Executor executor33 = factory.create(engine, peer23, "33", true);
		final Executor executor34 = factory.create(engine, peer24, "34", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer35Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor6 = factory.create(engine, tracker6, "6", true);
		final Executor executor7 = factory.create(engine, tracker7, "7", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);
		final Executor executor31 = factory.create(engine, peer21, "31", true);
		final Executor executor32 = factory.create(engine, peer22, "32", true);
		final Executor executor33 = factory.create(engine, peer23, "33", true);
		final Executor executor34 = factory.create(engine, peer24, "34", true);
		final Executor executor35 = factory.create(engine, peer25, "35", true);
		final Executor executor36 = factory.create(engine, peer26, "36", true);
		final Executor executor37 = factory.create(engine, peer27, "37", true);
		final Executor executor38 = factory.create(engine, peer28, "38", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer40Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor6 = factory.create(engine, tracker6, "6", true);
		final Executor executor7 = factory.create(engine, tracker7, "7", true);
		final Executor executor8 = factory.create(engine, tracker8, "8", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);
		final Executor executor31 = factory.create(engine, peer21, "31", true);
		final Executor executor32 = factory.create(engine, peer22, "32", true);
		final Executor executor33 = factory.create(engine, peer23, "33", true);
		final Executor executor34 = factory.create(engine, peer24, "34", true);
		final Executor executor35 = factory.create(engine, peer25, "35", true);
		final Executor executor36 = factory.create(engine, peer26, "36", true);
		final Executor executor37 = factory.create(engine, peer27, "37", true);
		final Executor executor38 = factory.create(engine, peer28, "38", true);
		final Executor executor39 = factory.create(engine, peer29, "39", true);
		final Executor executor40 = factory.create(engine, peer30, "40", true);
		final Executor executor41 = factory.create(engine, peer31, "41", true);
		final Executor executor42 = factory.create(engine, peer32, "42", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer45Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Tracker tracker9 = new Tracker(9);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);
		Peer peer33 = new Peer(43);
		Peer peer34 = new Peer(44);
		Peer peer35 = new Peer(45);
		Peer peer36 = new Peer(46);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor6 = factory.create(engine, tracker6, "6", true);
		final Executor executor7 = factory.create(engine, tracker7, "7", true);
		final Executor executor8 = factory.create(engine, tracker8, "8", true);
		final Executor executor9 = factory.create(engine, tracker9, "9", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);
		final Executor executor31 = factory.create(engine, peer21, "31", true);
		final Executor executor32 = factory.create(engine, peer22, "32", true);
		final Executor executor33 = factory.create(engine, peer23, "33", true);
		final Executor executor34 = factory.create(engine, peer24, "34", true);
		final Executor executor35 = factory.create(engine, peer25, "35", true);
		final Executor executor36 = factory.create(engine, peer26, "36", true);
		final Executor executor37 = factory.create(engine, peer27, "37", true);
		final Executor executor38 = factory.create(engine, peer28, "38", true);
		final Executor executor39 = factory.create(engine, peer29, "39", true);
		final Executor executor40 = factory.create(engine, peer30, "40", true);
		final Executor executor41 = factory.create(engine, peer31, "41", true);
		final Executor executor42 = factory.create(engine, peer32, "42", true);
		final Executor executor43 = factory.create(engine, peer33, "43", true);
		final Executor executor44 = factory.create(engine, peer34, "44", true);
		final Executor executor45 = factory.create(engine, peer35, "45", true);
		final Executor executor46 = factory.create(engine, peer36, "46", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void TrackerPeer50Test() {

		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Tracker tracker9 = new Tracker(9);
		Tracker tracker10 = new Tracker(10);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);
		Peer peer33 = new Peer(43);
		Peer peer34 = new Peer(44);
		Peer peer35 = new Peer(45);
		Peer peer36 = new Peer(46);
		Peer peer37 = new Peer(47);
		Peer peer38 = new Peer(48);
		Peer peer39 = new Peer(49);
		Peer peer40 = new Peer(50);

		final Executor executor1 = factory.create(engine, tracker1, "1", true);
		final Executor executor2 = factory.create(engine, tracker2, "2", true);
		final Executor executor3 = factory.create(engine, tracker3, "3", true);
		final Executor executor4 = factory.create(engine, tracker4, "4", true);
		final Executor executor5 = factory.create(engine, tracker5, "5", true);
		final Executor executor6 = factory.create(engine, tracker6, "6", true);
		final Executor executor7 = factory.create(engine, tracker7, "7", true);
		final Executor executor8 = factory.create(engine, tracker8, "8", true);
		final Executor executor9 = factory.create(engine, tracker9, "9", true);
		final Executor executor10 = factory.create(engine, tracker10, "10", true);
		final Executor executor11 = factory.create(engine, peer1, "11", true);
		final Executor executor12 = factory.create(engine, peer2, "12", true);
		final Executor executor13 = factory.create(engine, peer3, "13", true);
		final Executor executor14 = factory.create(engine, peer4, "14", true);
		final Executor executor15 = factory.create(engine, peer5, "15", true);
		final Executor executor16 = factory.create(engine, peer6, "16", true);
		final Executor executor17 = factory.create(engine, peer7, "17", true);
		final Executor executor18 = factory.create(engine, peer8, "18", true);
		final Executor executor19 = factory.create(engine, peer9, "19", true);
		final Executor executor20 = factory.create(engine, peer10, "20", true);
		final Executor executor21 = factory.create(engine, peer11, "21", true);
		final Executor executor22 = factory.create(engine, peer12, "22", true);
		final Executor executor23 = factory.create(engine, peer13, "23", true);
		final Executor executor24 = factory.create(engine, peer14, "24", true);
		final Executor executor25 = factory.create(engine, peer15, "25", true);
		final Executor executor26 = factory.create(engine, peer16, "26", true);
		final Executor executor27 = factory.create(engine, peer17, "27", true);
		final Executor executor28 = factory.create(engine, peer18, "28", true);
		final Executor executor29 = factory.create(engine, peer19, "29", true);
		final Executor executor30 = factory.create(engine, peer20, "30", true);
		final Executor executor31 = factory.create(engine, peer21, "31", true);
		final Executor executor32 = factory.create(engine, peer22, "32", true);
		final Executor executor33 = factory.create(engine, peer23, "33", true);
		final Executor executor34 = factory.create(engine, peer24, "34", true);
		final Executor executor35 = factory.create(engine, peer25, "35", true);
		final Executor executor36 = factory.create(engine, peer26, "36", true);
		final Executor executor37 = factory.create(engine, peer27, "37", true);
		final Executor executor38 = factory.create(engine, peer28, "38", true);
		final Executor executor39 = factory.create(engine, peer29, "39", true);
		final Executor executor40 = factory.create(engine, peer30, "40", true);
		final Executor executor41 = factory.create(engine, peer31, "41", true);
		final Executor executor42 = factory.create(engine, peer32, "42", true);
		final Executor executor43 = factory.create(engine, peer33, "43", true);
		final Executor executor44 = factory.create(engine, peer34, "44", true);
		final Executor executor45 = factory.create(engine, peer35, "45", true);
		final Executor executor46 = factory.create(engine, peer36, "46", true);
		final Executor executor47 = factory.create(engine, peer37, "47", true);
		final Executor executor48 = factory.create(engine, peer38, "48", true);
		final Executor executor49 = factory.create(engine, peer39, "49", true);
		final Executor executor50 = factory.create(engine, peer40, "50", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	// Compute time in seconds for 1000 iterations
	public void computeTimeInSecsfor1000Iterations() throws IOException {
		int i;
		for (i = 5; i < 50; i = i + 5) {

			File file = new File("/home/mavridou/workspace/javaengineperformance/TrackersPeers/Time/" + "TP" + i
					+ ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count = 0;
			double sum = 0;
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (count == 1000)
					break;
				if (!line.equals("")) {
					sum += Integer.parseInt(line);
					count++;
				}
			}
			if (count == 0)
				return;
			System.out.println(i + " " + sum / 1000);
		}
	}

	@Test
	// Compute time in seconds for 1000 iterations
	public void computeAverage() throws IOException {
		int i;
		for (i = 5; i < 50; i = i + 5) {

			File file = new File("/home/mavridou/workspace/javaengineperformance/TrackersPeers/Time/" + "TP" + i
					+ ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count = 0;
			double sum = 0;
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.equals("")) {
					sum += Integer.parseInt(line);
					count++;
				}
			}
			if (count == 0)
				return;
			System.out.println(i + " " + sum / count + " for iterations: " + count);
		}
	}
}
