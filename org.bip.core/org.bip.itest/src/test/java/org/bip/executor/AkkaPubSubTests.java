package org.bip.executor;


import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
import lsr.concurrence.provided.tests.InputChecker;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.pubsub.typed.CommandBuffer;
import org.bip.spec.pubsub.typed.CommandHandler;
import org.bip.spec.pubsub.typed.TCPReader;
import org.bip.spec.pubsub.typed.TopicManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;


public class AkkaPubSubTests {

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
		public void pubsubTypedTest() {
			
		int BUFFER_SIZE = 10;

		ServerSocket tcpacceptor = null;
		try {
			tcpacceptor = new ServerSocket(7676);
		} catch (IOException e1) {
			System.err.println("Fail to listen on port 7676");
			System.exit(-1);
		}

		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(TCPReader.class, "giveCommandToBuffer").to(CommandBuffer.class, "putCommand");
				synchron(CommandBuffer.class, "getCommand").to(CommandHandler.class, "handleCommand");

				data(TCPReader.class, "command").to(CommandBuffer.class, "input");
				data(CommandBuffer.class, "command").to(CommandBuffer.class, "command");
			}

		}.build();

			CommandBuffer buffer = new CommandBuffer(BUFFER_SIZE);
			BIPActor actorBuffer = engine.register(buffer, "buffer", true);

			TopicManager top_manager = new TopicManager();
			BIPActor actorTManager = engine.register(top_manager, "topicManager", true);

			CommandHandler handler1 = new CommandHandler(top_manager);
			BIPActor commandHandler1 = engine.register(handler1, "commandHandler1", true);

			CommandHandler handler2 = new CommandHandler(top_manager);
			BIPActor commandHandler2 = engine.register(handler2, "commandHandler2", true);

			CommandHandler handler3 = new CommandHandler(top_manager);
			BIPActor commandHandler3 = engine.register(handler3, "commandHandler3", true);

			CommandHandler handler4 = new CommandHandler(top_manager);
			BIPActor commandHandler4 = engine.register(handler4, "commandHandler4", true);

			CommandHandler handler5 = new CommandHandler(top_manager);
			BIPActor commandHandler5 = engine.register(handler5, "commandHandler5", true);



		TCPReader reader1;
		try {
			reader1 = new TCPReader(tcpacceptor.accept(), 1, buffer);
			BIPActor actorReader1 = engine.register(reader1, "tcpReader1", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
			TCPReader reader2;
		try {
			reader2 = new TCPReader(tcpacceptor.accept(), 2, buffer);
			BIPActor actorReader2 = engine.register(reader2, "tcpReader2", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
					engine.specifyGlue(bipGlue);
					engine.start();


					engine.execute();

					try {
			Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					engine.stop();
					engineFactory.destroy(engine);

	}

	@Test
	public void pubsubTypedSecondTest() {

		String host = "localhost";
		int port = 7676;
		System.out.println("Test with server " + host + ":" + port);

		ArrayList<String> topics = new ArrayList<String>();
		ArrayList<String> msgs = new ArrayList<String>();
		topics.add("epfl");
		topics.add("concurrence");
		msgs.add("bonjour");
		msgs.add("hello");
		int error = 0;

		try {

			Socket connection = new Socket(host, port);

			ClientOutputWriter output = new ClientOutputWriter(connection.getOutputStream());
			InputChecker inputCheck = new InputChecker(new ClientInputReader(connection.getInputStream()));

			if (inputCheck.checkConnected() != 0) {
				System.out.println("Error in connection");
				connection.close();
				return;
			}

			// A simple test
			// sequence: subscribe - publish - unsubscribe
			System.out.println("**** TEST 1 ***");
			error = 0;

			output.subscribeTo(topics.get(0));

			error += inputCheck.checkSubscribe(topics.get(0));
			output.publish(topics.get(0), msgs.get(0));
			error += inputCheck.checkPublish(topics.get(0), msgs.get(0));
			output.unsubscribeTo(topics.get(0));
			error += inputCheck.checkUnsubscribe(topics.get(0));

			assertTrue("No errors", error == 0);
			System.out.println("passed\n");
		} catch (IOException e) {
			System.err.println("Fail to accept client connection");
			System.exit(-1);
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
