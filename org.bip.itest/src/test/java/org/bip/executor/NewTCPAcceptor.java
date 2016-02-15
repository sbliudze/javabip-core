package org.bip.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.pubsub.typed.ClientProxy;
import org.bip.spec.pubsub.typed.ClientProxyInterface;
import org.bip.spec.pubsub.typed.CommandBuffer;
import org.bip.spec.pubsub.typed.CommandHandler;
import org.bip.spec.pubsub.typed.Counter;
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

public class NewTCPAcceptor {
	
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
	
	private static final String epfl = "epfl";
	private static final String concurrence = "concurrence";
	private static final String risd = "risd";
	private static final String programming = "programming";
	private static final String history = "history";
	private static final String hello = "hello";
	private static final String bonjour = "bonjour";
	private static final String salut = "salut";

	@Test
	public void main() {
		int BUFFER_SIZE = 10;
		
		ArrayList<String> topics=new ArrayList<String>();
		ArrayList<String> msgs=new ArrayList<String>();
		topics.add(epfl);
		topics.add(concurrence);
		topics.add(risd);
		topics.add(programming);
		topics.add(history);
		msgs.add(bonjour);
		msgs.add(hello);
		msgs.add(salut);

		try {
			ServerSocket tcpacceptor = new ServerSocket(7676);


		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

					 synchron(TCPReader.class, "giveCommandToBuffer").to(CommandBuffer.class,
					 "putCommand");
					synchron(CommandBuffer.class, "getCommand").to(CommandHandler.class, "handleCommand");

					 data(TCPReader.class, "readerInput").to(CommandBuffer.class, "input");
					data(CommandBuffer.class, "command").to(CommandHandler.class, "command");
			}

		}.build();

			BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		Counter counter = new Counter();
		
			CommandBuffer buffer = new CommandBuffer(BUFFER_SIZE);
			BIPActor actorBuffer = engine.register(buffer, "buffer", true);

			Topic topic1 = new Topic(epfl);
			TopicInterface proxyForTopic1 = (TopicInterface) engine.register(topic1, "topic1", true);

			Topic topic2 = new Topic(concurrence);
			TopicInterface proxyForTopic2 = (TopicInterface) engine.register(topic2, "topic2", true);

			Topic topic3 = new Topic(risd);
			TopicInterface proxyForTopic3 = (TopicInterface) engine.register(topic3, "topic3", true);

			Topic topic4 = new Topic(programming);
			TopicInterface proxyForTopic4 = (TopicInterface) engine.register(topic4, "topic4", true);
			
			Topic topic5 = new Topic(history);
			TopicInterface proxyForTopic5 = (TopicInterface) engine.register(topic5, "topic5", true);

			
			 HashMap<String, TopicInterface> topicsMap = new HashMap<String, TopicInterface>();
			 topicsMap.put(epfl, proxyForTopic1);
				topicsMap.put(concurrence, proxyForTopic2);
				topicsMap.put(risd, proxyForTopic3);
				topicsMap.put(programming, proxyForTopic4);
				topicsMap.put(history, proxyForTopic5);

			TopicManager top_manager = new TopicManager(topicsMap);
			TopicManagerInterface proxyForManager = (TopicManagerInterface) engine.register(top_manager,
					"topicManager", true);

			CommandHandler handler1 = new CommandHandler(proxyForManager);
			BIPActor commandHandler1 = engine.register(handler1, "commandHandler1", true);

			CommandHandler handler2 = new CommandHandler(proxyForManager);
			BIPActor commandHandler2 = engine.register(handler2, "commandHandler2", true);

			CommandHandler handler3 = new CommandHandler(proxyForManager);
			BIPActor commandHandler3 = engine.register(handler3, "commandHandler3", true);

//			CommandHandler handler4 = new CommandHandler(proxyForManager);
//			BIPActor commandHandler4 = engine.register(handler4, "commandHandler4", true);
//
//			CommandHandler handler5 = new CommandHandler(proxyForManager);
//			BIPActor commandHandler5 = engine.register(handler5, "commandHandler5", true);
//
//			CommandHandler handler6 = new CommandHandler(proxyForManager);
//			BIPActor commandHandler6 = engine.register(handler6, "commandHandler6", true);

			// CommandHandler handler7 = new CommandHandler(proxyForManager);
			// BIPActor commandHandler7 = engine.register(handler7, "commandHandler7", true);

			// CommandHandler handler8 = new CommandHandler(proxyForManager);
			// BIPActor commandHandler8 = engine.register(handler8, "commandHandler8", true);

			// CommandHandler handler9 = new CommandHandler(proxyForManager);
			// BIPActor commandHandler9 = engine.register(handler9, "commandHandler9", true);

			Thread tr0=new Thread(new PubSub5topics(topics, msgs));
			tr0.start();
			Thread tr1=new Thread(new PubSub5topics(topics, msgs));
			tr1.start();
			Thread tr2=new Thread(new PubSub5topics1(topics, msgs));
			tr2.start();
			Thread tr3=new Thread(new PubSub5topics1(topics, msgs));
			tr3.start();
			Thread tr4=new Thread(new PubSub5topics(topics, msgs));
			tr4.start();

//			Thread tr6 = new Thread(new TestPubSub(true));
//			tr6.start();
//
//			Thread tr7 = new Thread(new TestPubSub(true));
//			tr7.start();
//
//			Thread tr8 = new Thread(new TestPubSub(true));
//			tr8.start();
//
//			Thread tr9 = new Thread(new TestPubSub(true));
//			tr9.start();
//
//			Thread tr10 = new Thread(new TestPubSub(true));
//			tr10.start();
//
//			Thread tr11 = new Thread(new TestPubSub(true));
//			tr11.start();
//
//			Thread tr12 = new Thread(new TestPubSub(true));
//			tr12.start();

			// Thread tr13 = new Thread(new TestPubSub(true));
			// tr13.start();
			//
			// Thread tr14 = new Thread(new TestPubSub(true));
			// tr14.start();
			//
			// Thread tr15 = new Thread(new TestPubSub(true));
			// tr15.start();
			//
			// Thread tr16 = new Thread(new TestPubSub(true));
			// tr16.start();

			// Thread tr17 = new Thread(new TestPubSub(true));
			// tr17.start();
			//
			// Thread tr18 = new Thread(new TestPubSub(true));
			// tr18.start();

			ClientProxy client1 = new ClientProxy(1, tcpacceptor, counter);
			ClientProxyInterface proxyForClient1 = (ClientProxyInterface) engine.register(client1, "client1", true);

			ClientProxy client2 = new ClientProxy(2, tcpacceptor, counter);
			ClientProxyInterface proxyForClient2 = (ClientProxyInterface) engine.register(client2, "client2", true);

			ClientProxy client3 = new ClientProxy(3, tcpacceptor, counter);
			ClientProxyInterface proxyForClient3 = (ClientProxyInterface) engine.register(client3, "client3", true);

			ClientProxy client4 = new ClientProxy(4, tcpacceptor, counter);
			ClientProxyInterface proxyForClient4 = (ClientProxyInterface) engine.register(client4, "client4", true);

			ClientProxy client5 = new ClientProxy(5, tcpacceptor, counter);
			ClientProxyInterface proxyForClient5 = (ClientProxyInterface) engine.register(client5, "client5", true);

//			ClientProxy client6 = new ClientProxy(6, tcpacceptor);
//			ClientProxyInterface proxyForClient6 = (ClientProxyInterface) engine.register(client6, "client6", true);
//
//			ClientProxy client7 = new ClientProxy(7, tcpacceptor);
//			ClientProxyInterface proxyForClient7 = (ClientProxyInterface) engine.register(client7, "client7", true);
//
//			ClientProxy client8 = new ClientProxy(8, tcpacceptor);
//			ClientProxyInterface proxyForClient8 = (ClientProxyInterface) engine.register(client8, "client8", true);
//
//			ClientProxy client9 = new ClientProxy(9, tcpacceptor);
//			ClientProxyInterface proxyForClient9 = (ClientProxyInterface) engine.register(client9, "client9", true);
//
//			ClientProxy client10 = new ClientProxy(10, tcpacceptor);
//			ClientProxyInterface proxyForClient10 = (ClientProxyInterface) engine.register(client10, "client10", true);
//
//			ClientProxy client11 = new ClientProxy(11, tcpacceptor);
//			ClientProxyInterface proxyForClient11 = (ClientProxyInterface) engine.register(client11, "client11", true);
//
//			ClientProxy client12 = new ClientProxy(12, tcpacceptor);
//			ClientProxyInterface proxyForClient12 = (ClientProxyInterface) engine.register(client12, "client12", true);

			// ClientProxy client13 = new ClientProxy(13, tcpacceptor);
			// ClientProxyInterface proxyForClient13 = (ClientProxyInterface)
			// engine.register(client13, "client13", true);
			//
			// ClientProxy client14 = new ClientProxy(14, tcpacceptor);
			// ClientProxyInterface proxyForClient14 = (ClientProxyInterface)
			// engine.register(client14, "client14", true);
			//
			// ClientProxy client15 = new ClientProxy(15, tcpacceptor);
			// ClientProxyInterface proxyForClient15 = (ClientProxyInterface)
			// engine.register(client15, "client15", true);
			//
			// ClientProxy client16 = new ClientProxy(16, tcpacceptor);
			// ClientProxyInterface proxyForClient16 = (ClientProxyInterface)
			// engine.register(client16, "client16", true);

			// ClientProxy client17 = new ClientProxy(17, tcpacceptor);
			// ClientProxyInterface proxyForClient17 = (ClientProxyInterface)
			// engine.register(client17, "client17", true);
			//
			// ClientProxy client18 = new ClientProxy(18, tcpacceptor);
			// ClientProxyInterface proxyForClient18 = (ClientProxyInterface)
			// engine.register(client18, "client18", true);

			TCPReader reader1;
			try {
				reader1 = new TCPReader(client1.getSocket(), 1, buffer, proxyForClient1);
				BIPActor actorReader1 = engine.register(reader1, "tcpReader1", true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			TCPReader reader2;
			try {
				reader2 = new TCPReader(client2.getSocket(), 2, buffer, proxyForClient2);
				BIPActor actorReader2 = engine.register(reader2, "tcpReader2", true);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			TCPReader reader3;
			try {
				reader3 = new TCPReader(client3.getSocket(), 3, buffer, proxyForClient3);
				BIPActor actorReader3 = engine.register(reader3, "tcpReader3", true);
			} catch (IOException e3) {
				e3.printStackTrace();
			}

			TCPReader reader4;
			try {
				reader4 = new TCPReader(client4.getSocket(), 4, buffer, proxyForClient4);
				BIPActor actorReader4 = engine.register(reader4, "tcpReader4", true);
			} catch (IOException e4) {
				e4.printStackTrace();
			}

			TCPReader reader5;
			try {
				reader5 = new TCPReader(client5.getSocket(), 5, buffer, proxyForClient5);
				BIPActor actorReader5 = engine.register(reader5, "tcpReader5", true);
			} catch (IOException e5) {
				e5.printStackTrace();
			}

//			TCPReader reader6;
//			try {
//				reader6 = new TCPReader(client6.getSocket(), 6, buffer, proxyForClient6);
//				BIPActor actorReader6 = engine.register(reader6, "tcpReader6", true);
//			} catch (IOException e6) {
//				e6.printStackTrace();
//			}
//
//			TCPReader reader7;
//			try {
//				reader7 = new TCPReader(client7.getSocket(), 7, buffer, proxyForClient7);
//				BIPActor actorReader7 = engine.register(reader7, "tcpReader7", true);
//			} catch (IOException e7) {
//				e7.printStackTrace();
//			}
//
//			TCPReader reader8;
//			try {
//				reader8 = new TCPReader(client8.getSocket(), 8, buffer, proxyForClient8);
//				BIPActor actorReader8 = engine.register(reader8, "tcpReader8", true);
//			} catch (IOException e8) {
//				e8.printStackTrace();
//			}
//
//			TCPReader reader9;
//			try {
//				reader9 = new TCPReader(client9.getSocket(), 9, buffer, proxyForClient9);
//				BIPActor actorReader9 = engine.register(reader9, "tcpReader9", true);
//			} catch (IOException e9) {
//				e9.printStackTrace();
//			}
//
//			TCPReader reader10;
//			try {
//				reader10 = new TCPReader(client10.getSocket(), 10, buffer, proxyForClient10);
//				BIPActor actorReader10 = engine.register(reader10, "tcpReader10", true);
//			} catch (IOException e10) {
//				e10.printStackTrace();
//			}
//
//			TCPReader reader11;
//			try {
//				reader11 = new TCPReader(client11.getSocket(), 11, buffer, proxyForClient11);
//				BIPActor actorReader11 = engine.register(reader11, "tcpReader11", true);
//			} catch (IOException e11) {
//				e11.printStackTrace();
//			}
//
//			TCPReader reader12;
//			try {
//				reader12 = new TCPReader(client12.getSocket(), 12, buffer, proxyForClient12);
//				BIPActor actorReader12 = engine.register(reader12, "tcpReader12", true);
//			} catch (IOException e12) {
//				e12.printStackTrace();
//			}

			// TCPReader reader13;
			// try {
			// reader13 = new TCPReader(client13.getSocket(), 13, buffer, proxyForClient13);
			// BIPActor actorReader13 = engine.register(reader13, "tcpReader13", true);
			// } catch (IOException e13) {
			// e13.printStackTrace();
			// }
			//
			// TCPReader reader14;
			// try {
			// reader14 = new TCPReader(client14.getSocket(), 14, buffer, proxyForClient14);
			// BIPActor actorReader14 = engine.register(reader14, "tcpReader14", true);
			// } catch (IOException e14) {
			// e14.printStackTrace();
			// }
			//
			// TCPReader reader15;
			// try {
			// reader15 = new TCPReader(client15.getSocket(), 15, buffer, proxyForClient15);
			// BIPActor actorReader15 = engine.register(reader15, "tcpReader15", true);
			// } catch (IOException e15) {
			// e15.printStackTrace();
			// }
			//
			// TCPReader reader16;
			// try {
			// reader16 = new TCPReader(client16.getSocket(), 16, buffer, proxyForClient16);
			// BIPActor actorReader16 = engine.register(reader16, "tcpReader16", true);
			// } catch (IOException e16) {
			// e16.printStackTrace();
			// }

			// TCPReader reader17;
			// try {
			// reader17 = new TCPReader(client17.getSocket(), 17, buffer, proxyForClient17);
			// BIPActor actorReader17 = engine.register(reader17, "tcpReader17", true);
			// } catch (IOException e17) {
			// e17.printStackTrace();
			// }
			//
			// TCPReader reader18;
			// try {
			// reader18 = new TCPReader(client18.getSocket(), 18, buffer, proxyForClient18);
			// BIPActor actorReader18 = engine.register(reader18, "tcpReader18", true);
			// } catch (IOException e18) {
			// e18.printStackTrace();
			// }
		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
				Thread.sleep(500000);
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}

			// int transitions = client1.noOfTransitions + client2.noOfTransitions +
			// client3.noOfTransitions;
			// // System.out.println("Number of transitions: " + transitions);
			// assertTrue("Correct number of transitions for client proxys", client1.noOfTransitions
			// == 12);

		engine.stop();
		engineFactory.destroy(engine);
		} catch (IOException e11) {
			System.err.println("Fail to listen on port 7676");
			System.exit(-1);
		}

	}

	@Test
	// Compute time in seconds for 1000 iterations
	public void computeTimeInSecsfor1000Iterations() throws IOException {
		int i;
		for (i = 5; i < 55; i = i + 5) {

			File file = new File("/home/mavridou/Documents/javaengineperformance/PubSub/PS" + i);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count = 0;
			double sum = 0;
			bufferedReader.readLine();
			bufferedReader.readLine();
			bufferedReader.readLine();
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (count == 1500) {
					System.out.println("1000 lines");
					break;
				}
				if (!line.equals("")) {
					sum += Integer.parseInt(line);
					count++;
				}
			}
			if (count == 0)
				return;
			System.out.println(i + " " + sum / 1500);
		}
	}

}
