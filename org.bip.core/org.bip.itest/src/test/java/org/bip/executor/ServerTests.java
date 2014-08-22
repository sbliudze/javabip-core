package org.bip.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.Executor;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.spec.InitialServer;
import org.bip.spec.Server;
import org.junit.Test;

import akka.actor.ActorSystem;

public class ServerTests {

	@Test
	public void Servers3Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		InitialServer server1 = new InitialServer(1);

		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor1 = factory.create(engine, server1, "1", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers6Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);

		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers9Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		InitialServer server13 = new InitialServer(13);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);

		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers12Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers15Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server1 = new InitialServer(1);
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers18Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers21Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers24Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers27Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);		
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers30Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers33Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		Server server31 = new Server(31);
		Server server32 = new Server(32);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		InitialServer server33 = new InitialServer(33);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		final Executor executor31 = factory.create(engine, server31, "31", true);
		final Executor executor32 = factory.create(engine, server32, "32", true);
		final Executor executor33 = factory.create(engine, server33, "33", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers36Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		Server server31 = new Server(31);
		Server server32 = new Server(32);
		Server server34 = new Server(34);
		Server server35 = new Server(35);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		InitialServer server33 = new InitialServer(33);	
		InitialServer server36 = new InitialServer(36);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		final Executor executor31 = factory.create(engine, server31, "31", true);
		final Executor executor32 = factory.create(engine, server32, "32", true);
		final Executor executor33 = factory.create(engine, server33, "33", true);
		final Executor executor34 = factory.create(engine, server34, "34", true);
		final Executor executor35 = factory.create(engine, server35, "35", true);
		final Executor executor36 = factory.create(engine, server36, "36", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers39Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		Server server31 = new Server(31);
		Server server32 = new Server(32);
		Server server34 = new Server(34);
		Server server35 = new Server(35);
		Server server37 = new Server(37);
		Server server38 = new Server(38);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		InitialServer server33 = new InitialServer(33);	
		InitialServer server36 = new InitialServer(36);	
		InitialServer server39 = new InitialServer(39);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		final Executor executor31 = factory.create(engine, server31, "31", true);
		final Executor executor32 = factory.create(engine, server32, "32", true);
		final Executor executor33 = factory.create(engine, server33, "33", true);
		final Executor executor34 = factory.create(engine, server34, "34", true);
		final Executor executor35 = factory.create(engine, server35, "35", true);
		final Executor executor36 = factory.create(engine, server36, "36", true);
		final Executor executor37 = factory.create(engine, server37, "37", true);
		final Executor executor38 = factory.create(engine, server38, "38", true);
		final Executor executor39 = factory.create(engine, server39, "39", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers42Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		Server server31 = new Server(31);
		Server server32 = new Server(32);
		Server server34 = new Server(34);
		Server server35 = new Server(35);
		Server server37 = new Server(37);
		Server server38 = new Server(38);
		Server server40 = new Server(40);
		Server server41 = new Server(41);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		InitialServer server33 = new InitialServer(33);	
		InitialServer server36 = new InitialServer(36);	
		InitialServer server39 = new InitialServer(39);	
		InitialServer server42 = new InitialServer(42);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		final Executor executor31 = factory.create(engine, server31, "31", true);
		final Executor executor32 = factory.create(engine, server32, "32", true);
		final Executor executor33 = factory.create(engine, server33, "33", true);
		final Executor executor34 = factory.create(engine, server34, "34", true);
		final Executor executor35 = factory.create(engine, server35, "35", true);
		final Executor executor36 = factory.create(engine, server36, "36", true);
		final Executor executor37 = factory.create(engine, server37, "37", true);
		final Executor executor38 = factory.create(engine, server38, "38", true);
		final Executor executor39 = factory.create(engine, server39, "39", true);
		final Executor executor40 = factory.create(engine, server40, "40", true);
		final Executor executor41 = factory.create(engine, server41, "41", true);
		final Executor executor42 = factory.create(engine, server42, "42", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Servers45Test() {
		ActorSystem system = ActorSystem.create("MySystem");
		OrchestratedExecutorFactory factory = new OrchestratedExecutorFactory(system);
		EngineFactory engineFactory = new EngineFactory(system);
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl()));

		BIPGlue bipGlue = createGlue("src/test/resources/bipGlueServers.xml");


		Server server1 = new Server(1);
		Server server2 = new Server(2);
		Server server3 = new Server(3);
		Server server4 = new Server(4);
		Server server5 = new Server(5);
		Server server6 = new Server(6);
		Server server7 = new Server(7);
		Server server8 = new Server(8);
		Server server9 = new Server(9);
		Server server10 = new Server(10);
		Server server15 = new Server(15);
		Server server18 = new Server(18);
		Server server19 = new Server(19);
		Server server20 = new Server(20);
		Server server22 = new Server(22);
		Server server23 = new Server(23);
		Server server25 = new Server(25);
		Server server26 = new Server(26);
		Server server28 = new Server(28);
		Server server29 = new Server(29);
		Server server31 = new Server(31);
		Server server32 = new Server(32);
		Server server34 = new Server(34);
		Server server35 = new Server(35);
		Server server37 = new Server(37);
		Server server38 = new Server(38);
		Server server40 = new Server(40);
		Server server41 = new Server(41);
		Server server43 = new Server(43);
		Server server44 = new Server(44);
		
		InitialServer server11 = new InitialServer(11);
		InitialServer server12 = new InitialServer(12);
		InitialServer server13 = new InitialServer(13);
		InitialServer server14 = new InitialServer(14);
		InitialServer server16 = new InitialServer(16);
		InitialServer server17 = new InitialServer(17);
		InitialServer server21 = new InitialServer(21);
		InitialServer server24 = new InitialServer(24);
		InitialServer server27 = new InitialServer(27);	
		InitialServer server30 = new InitialServer(30);	
		InitialServer server33 = new InitialServer(33);	
		InitialServer server36 = new InitialServer(36);	
		InitialServer server39 = new InitialServer(39);	
		InitialServer server42 = new InitialServer(42);	
		InitialServer server45 = new InitialServer(45);	
		
		final Executor executor1 = factory.create(engine, server1, "1", true);
		final Executor executor2 = factory.create(engine, server2, "2", true);
		final Executor executor3 = factory.create(engine, server3, "3", true);
		final Executor executor4 = factory.create(engine, server4, "4", true);
		final Executor executor5 = factory.create(engine, server5, "5", true);
		final Executor executor6 = factory.create(engine, server6, "6", true);
		final Executor executor7 = factory.create(engine, server7, "7", true);
		final Executor executor8 = factory.create(engine, server8, "8", true);
		final Executor executor9 = factory.create(engine, server9, "9", true);
		final Executor executor10 = factory.create(engine, server10, "10", true);
		final Executor executor11 = factory.create(engine, server11, "11", true);
		final Executor executor12 = factory.create(engine, server12, "12", true);
		final Executor executor13 = factory.create(engine, server13, "13", true);
		final Executor executor14 = factory.create(engine, server14, "14", true);
		final Executor executor15 = factory.create(engine, server15, "15", true);
		final Executor executor16 = factory.create(engine, server16, "16", true);
		final Executor executor17 = factory.create(engine, server17, "17", true);
		final Executor executor18 = factory.create(engine, server18, "18", true);
		final Executor executor19 = factory.create(engine, server19, "19", true);
		final Executor executor20 = factory.create(engine, server20, "20", true);
		final Executor executor21 = factory.create(engine, server21, "21", true);
		final Executor executor22 = factory.create(engine, server22, "22", true);
		final Executor executor23 = factory.create(engine, server23, "23", true);
		final Executor executor24 = factory.create(engine, server24, "24", true);
		final Executor executor25 = factory.create(engine, server25, "25", true);
		final Executor executor26 = factory.create(engine, server26, "26", true);
		final Executor executor27 = factory.create(engine, server27, "27", true);
		final Executor executor28 = factory.create(engine, server28, "28", true);
		final Executor executor29 = factory.create(engine, server29, "29", true);
		final Executor executor30 = factory.create(engine, server30, "30", true);
		final Executor executor31 = factory.create(engine, server31, "31", true);
		final Executor executor32 = factory.create(engine, server32, "32", true);
		final Executor executor33 = factory.create(engine, server33, "33", true);
		final Executor executor34 = factory.create(engine, server34, "34", true);
		final Executor executor35 = factory.create(engine, server35, "35", true);
		final Executor executor36 = factory.create(engine, server36, "36", true);
		final Executor executor37 = factory.create(engine, server37, "37", true);
		final Executor executor38 = factory.create(engine, server38, "38", true);
		final Executor executor39 = factory.create(engine, server39, "39", true);
		final Executor executor40 = factory.create(engine, server40, "40", true);
		final Executor executor41 = factory.create(engine, server41, "41", true);
		final Executor executor42 = factory.create(engine, server42, "42", true);
		final Executor executor43 = factory.create(engine, server40, "43", true);
		final Executor executor44 = factory.create(engine, server41, "44", true);
		final Executor executor45 = factory.create(engine, server42, "45", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
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
}
