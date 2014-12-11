package org.bip.executor;

import java.io.IOException;
import java.net.ServerSocket;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.pubsub.typed.CommandBuffer;
import org.bip.spec.pubsub.typed.CommandHandler;
import org.bip.spec.pubsub.typed.TCPReader;
import org.bip.spec.pubsub.typed.TopicManager;
import org.bip.spec.pubsub.typed.TopicManagerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class TCPAcceptor {
	
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
	public void main() {
		int BUFFER_SIZE = 10;

		try {
			ServerSocket tcpacceptor = new ServerSocket(7676);


			Thread tr = new Thread(new TestPubSub());
			tr.start();

			Thread tr2 = new Thread(new TestPubSub());
			tr2.start();

			Thread tr3 = new Thread(new TestPubSub());
			tr3.start();
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(TCPReader.class, "giveCommandToBuffer").to(CommandBuffer.class, "putCommand");
					synchron(CommandBuffer.class, "getCommand").to(CommandHandler.class, "handleCommand");

					data(TCPReader.class, "readerInput").to(CommandBuffer.class, "input");
					data(CommandBuffer.class, "command").to(CommandHandler.class, "command");
					data(CommandHandler.class, "command").to(TopicManager.class, "value");
			}

		}.build();

			bipGlue.toXML(System.out);

			CommandBuffer buffer = new CommandBuffer(BUFFER_SIZE);
			BIPActor actorBuffer = engine.register(buffer, "buffer", true);

			TopicManager top_manager = new TopicManager();
			TopicManagerInterface proxy1 = (TopicManagerInterface) engine.register(top_manager, "topicManager", true);

			CommandHandler handler1 = new CommandHandler(top_manager);
			BIPActor commandHandler1 = engine.register(handler1, "commandHandler1", true);

			CommandHandler handler2 = new CommandHandler(top_manager);
			BIPActor commandHandler2 = engine.register(handler2, "commandHandler2", true);

			CommandHandler handler3 = new CommandHandler(top_manager);
			BIPActor commandHandler3 = engine.register(handler3, "commandHandler3", true);

			TCPReader reader1;
			try {
				reader1 = new TCPReader(tcpacceptor.accept(), 1, buffer);
				BIPActor actorReader1 = engine.register(reader1, "tcpReader1", true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			TCPReader reader2;
			try {
				reader2 = new TCPReader(tcpacceptor.accept(), 2, buffer);
				BIPActor actorReader2 = engine.register(reader2, "tcpReader2", true);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			TCPReader reader3;
			try {
				reader3 = new TCPReader(tcpacceptor.accept(), 3, buffer);
				BIPActor actorReader3 = engine.register(reader3, "tcpReader3", true);
			} catch (IOException e3) {
				e3.printStackTrace();
			}


			System.out.println("Before specify glue..");
		engine.specifyGlue(bipGlue);
			System.out.println("Before starting the engine..");
		engine.start();


		engine.execute();

		try {
				Thread.sleep(5000);
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
		} catch (IOException e11) {
			System.err.println("Fail to listen on port 7676");
			System.exit(-1);
		}

	}

}
