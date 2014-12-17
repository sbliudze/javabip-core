package org.bip.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.pubsub.typed.ClientProxy;
import org.bip.spec.pubsub.typed.ClientProxyInterface;
import org.bip.spec.pubsub.typed.CommandBuffer;
import org.bip.spec.pubsub.typed.CommandHandler;
import org.bip.spec.pubsub.typed.TCPReader;
import org.bip.spec.pubsub.typed.Topic;
import org.bip.spec.pubsub.typed.TopicInterface;
import org.bip.spec.pubsub.typed.TopicManager;
import org.bip.spec.pubsub.typed.TopicManagerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

import com.typesafe.config.ConfigFactory;

public class TCPAcceptor {
	
	ActorSystem system;
	OrchestratedExecutorFactory factory;
	EngineFactory engineFactory;

	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem", ConfigFactory.load());
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


			Thread tr = new Thread(new TestPubSub(false));
			tr.start();

			Thread tr2 = new Thread(new TestPubSub(false));
			tr2.start();

			Thread tr3 = new Thread(new TestPubSub(true));
			tr3.start();
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(TCPReader.class, "giveCommandToBuffer").to(CommandBuffer.class, "putCommand");
					synchron(CommandBuffer.class, "getCommand").to(CommandHandler.class, "handleCommand");

					data(TCPReader.class, "readerInput").to(CommandBuffer.class, "input");
					data(CommandBuffer.class, "command").to(CommandHandler.class, "command");
			}

		}.build();

			bipGlue.toXML(System.out);
			System.exit(-1);

			CommandBuffer buffer = new CommandBuffer(BUFFER_SIZE);
			BIPActor actorBuffer = engine.register(buffer, "buffer", true);

			Topic topic1 = new Topic("epfl");
			TopicInterface proxyForTopic1 = (TopicInterface) engine.register(topic1, "topic1", true);

			Topic topic2 = new Topic("concurrence");
			TopicInterface proxyForTopic2 = (TopicInterface) engine.register(topic2, "topic2", true);

			TopicManager top_manager = new TopicManager(proxyForTopic1, proxyForTopic2);
			TopicManagerInterface proxyForManager = (TopicManagerInterface) engine.register(top_manager,
					"topicManager", true);

			CommandHandler handler1 = new CommandHandler(proxyForManager);
			BIPActor commandHandler1 = engine.register(handler1, "commandHandler1", true);

			CommandHandler handler2 = new CommandHandler(proxyForManager);
			BIPActor commandHandler2 = engine.register(handler2, "commandHandler2", true);

			CommandHandler handler3 = new CommandHandler(proxyForManager);
			BIPActor commandHandler3 = engine.register(handler3, "commandHandler3", true);

			CommandHandler handler4 = new CommandHandler(proxyForManager);
			BIPActor commandHandler4 = engine.register(handler4, "commandHandler4", true);

			CommandHandler handler5 = new CommandHandler(proxyForManager);
			BIPActor commandHandler5 = engine.register(handler5, "commandHandler5", true);

			Socket socket1 = tcpacceptor.accept();
			Socket socket2 = tcpacceptor.accept();
			Socket socket3 = tcpacceptor.accept();



			ClientProxy client1 = new ClientProxy(1, socket1.getOutputStream());
			ClientProxyInterface proxyForClient1 = (ClientProxyInterface) engine.register(client1, "client1", true);

			ClientProxy client2 = new ClientProxy(2, socket2.getOutputStream());
			ClientProxyInterface proxyForClient2 = (ClientProxyInterface) engine.register(client2, "client2", true);

			ClientProxy client3 = new ClientProxy(3, socket3.getOutputStream());
			ClientProxyInterface proxyForClient3 = (ClientProxyInterface) engine.register(client3, "client3", true);

			TCPReader reader1;
			try {
				reader1 = new TCPReader(socket1, 1, buffer, proxyForClient1);
				BIPActor actorReader1 = engine.register(reader1, "tcpReader1", true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			TCPReader reader2;
			try {
				reader2 = new TCPReader(socket2, 2, buffer, proxyForClient2);
				BIPActor actorReader2 = engine.register(reader2, "tcpReader2", true);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			TCPReader reader3;
			try {
				reader3 = new TCPReader(socket3, 3, buffer, proxyForClient3);
				BIPActor actorReader3 = engine.register(reader3, "tcpReader3", true);
			} catch (IOException e3) {
				e3.printStackTrace();
			}


			System.out.println("Before specify glue..");
		engine.specifyGlue(bipGlue);
			System.out.println("Before starting the engine..");
		engine.start();


		engine.execute();
			// assertEquals("Spontaneous wait on one component has blocked all the components",
			// bComponent.counterA > 0, true);

		try {
				Thread.sleep(3000);
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
