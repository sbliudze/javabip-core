package org.bip.executor;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.bip.spec.telephonic.AgregatorClient;
import org.bip.spec.telephonic.CalleeAgregation;
import org.bip.spec.telephonic.CallerAgregation;
import org.bip.spec.telephonic.Checker;
import org.bip.spec.telephonic.Client;
import org.bip.spec.telephonic.ClientCaller;
import org.bip.spec.telephonic.DialWaitSync;
import org.bip.spec.telephonic.DiscAgregation;
import org.bip.spec.telephonic.DiscSync;
import org.bip.spec.telephonic.DummyComponent;
import org.bip.spec.telephonic.VoiceAgregation;
import org.bip.spec.telephonic.VoiceSync;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class TelephonicTest {

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
	public void test5()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		//BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				port(DummyComponent.class, "aPort").acceptsNothing();
				port(DummyComponent.class, "aPort")	.requiresNothing();
				
			}

		}.build();
		engine.specifyGlue(bipGlue);

		int n=5; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		

		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation1 = new VoiceAgregation(n);
		
		ClientCaller discAgregation1 = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true); clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation1, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation1, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1,actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1,  actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1,  actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1,  actorDisc1,  actor5, checkerActor);
		
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation1.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation1.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation1 =  setClientRefs(clients, voiceAgregation1);
		discAgregation1 =  setClientRefs(clients, discAgregation1);
		
		client1 =  setClientRefs(clients, client1);
		client2 =  setClientRefs(clients, client2);
		client3 =  setClientRefs(clients, client3);
		client4 =  setClientRefs(clients, client4);
		client5 =  setClientRefs(clients, client5);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());
		
		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test10()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=10; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);	
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");


		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test15()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=15; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);

		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test20()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=20; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);

		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test25()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=25; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test30()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=30; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		AgregatorClient client26 = new Client(26,n);
		AgregatorClient client27 = new Client(27,n);
		AgregatorClient client28 = new Client(28,n);
		AgregatorClient client29 = new Client(29,n);
		AgregatorClient client30 = new Client(30,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);
		BIPActor actor26 = engine.register(client26, "client26", true);clients.add(actor26);
		BIPActor actor27 = engine.register(client27, "client27", true);clients.add(actor27);
		BIPActor actor28 = engine.register(client28, "client28", true);clients.add(actor28);
		BIPActor actor29 = engine.register(client29, "client29", true);clients.add(actor29);
		BIPActor actor30 = engine.register(client30, "client30", true);clients.add(actor30);

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, checkerActor);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, checkerActor);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, checkerActor);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, checkerActor);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, checkerActor);

		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		client26 = setClientRefs(clients, client26);
		client27 = setClientRefs(clients, client27);
		client28 = setClientRefs(clients, client28);
		client29 = setClientRefs(clients, client29);
		client30 = setClientRefs(clients, client30);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");
		actor26.inform("notify");
		actor27.inform("notify");
		actor28.inform("notify");
		actor29.inform("notify");
		actor30.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test35()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=35; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		AgregatorClient client26 = new Client(26,n);
		AgregatorClient client27 = new Client(27,n);
		AgregatorClient client28 = new Client(28,n);
		AgregatorClient client29 = new Client(29,n);
		AgregatorClient client30 = new Client(30,n);
		AgregatorClient client31 = new Client(31,n);
		AgregatorClient client32 = new Client(32,n);
		AgregatorClient client33 = new Client(33,n);
		AgregatorClient client34 = new Client(34,n);
		AgregatorClient client35 = new Client(35,n);

		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);
		BIPActor actor26 = engine.register(client26, "client26", true);clients.add(actor26);
		BIPActor actor27 = engine.register(client27, "client27", true);clients.add(actor27);
		BIPActor actor28 = engine.register(client28, "client28", true);clients.add(actor28);
		BIPActor actor29 = engine.register(client29, "client29", true);clients.add(actor29);
		BIPActor actor30 = engine.register(client30, "client30", true);clients.add(actor30);
		BIPActor actor31 = engine.register(client31, "client31", true);clients.add(actor31);
		BIPActor actor32 = engine.register(client32, "client32", true);clients.add(actor32);
		BIPActor actor33 = engine.register(client33, "client33", true);clients.add(actor33);
		BIPActor actor34 = engine.register(client34, "client34", true);clients.add(actor34);
		BIPActor actor35 = engine.register(client35, "client35", true);clients.add(actor35);

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, checkerActor);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, checkerActor);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, checkerActor);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, checkerActor);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, checkerActor);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, checkerActor);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, checkerActor);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, checkerActor);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, checkerActor);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, checkerActor);

		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		client26 = setClientRefs(clients, client26);
		client27 = setClientRefs(clients, client27);
		client28 = setClientRefs(clients, client28);
		client29 = setClientRefs(clients, client29);
		client30 = setClientRefs(clients, client30);
		client31 = setClientRefs(clients, client31);
		client32 = setClientRefs(clients, client32);
		client33 = setClientRefs(clients, client33);
		client34 = setClientRefs(clients, client34);
		client35 = setClientRefs(clients, client35);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");
		actor26.inform("notify");
		actor27.inform("notify");
		actor28.inform("notify");
		actor29.inform("notify");
		actor30.inform("notify");
		actor31.inform("notify");
		actor32.inform("notify");
		actor33.inform("notify");
		actor34.inform("notify");
		actor35.inform("notify");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test40()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=40; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		AgregatorClient client26 = new Client(26,n);
		AgregatorClient client27 = new Client(27,n);
		AgregatorClient client28 = new Client(28,n);
		AgregatorClient client29 = new Client(29,n);
		AgregatorClient client30 = new Client(30,n);
		AgregatorClient client31 = new Client(31,n);
		AgregatorClient client32 = new Client(32,n);
		AgregatorClient client33 = new Client(33,n);
		AgregatorClient client34 = new Client(34,n);
		AgregatorClient client35 = new Client(35,n);
		AgregatorClient client36 = new Client(36,n);
		AgregatorClient client37 = new Client(37,n);
		AgregatorClient client38 = new Client(38,n);
		AgregatorClient client39 = new Client(39,n);
		AgregatorClient client40 = new Client(40,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);
		BIPActor actor26 = engine.register(client26, "client26", true);clients.add(actor26);
		BIPActor actor27 = engine.register(client27, "client27", true);clients.add(actor27);
		BIPActor actor28 = engine.register(client28, "client28", true);clients.add(actor28);
		BIPActor actor29 = engine.register(client29, "client29", true);clients.add(actor29);
		BIPActor actor30 = engine.register(client30, "client30", true);clients.add(actor30);
		BIPActor actor31 = engine.register(client31, "client31", true);clients.add(actor31);
		BIPActor actor32 = engine.register(client32, "client32", true);clients.add(actor32);
		BIPActor actor33 = engine.register(client33, "client33", true);clients.add(actor33);
		BIPActor actor34 = engine.register(client34, "client34", true);clients.add(actor34);
		BIPActor actor35 = engine.register(client35, "client35", true);clients.add(actor35);
		BIPActor actor36 = engine.register(client36, "client36", true);clients.add(actor36);
		BIPActor actor37 = engine.register(client37, "client37", true);clients.add(actor37);
		BIPActor actor38 = engine.register(client38, "client38", true);clients.add(actor38);
		BIPActor actor39 = engine.register(client39, "client39", true);clients.add(actor39);
		BIPActor actor40 = engine.register(client40, "client40", true);clients.add(actor40);
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, checkerActor);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, checkerActor);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, checkerActor);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, checkerActor);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, checkerActor);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, checkerActor);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, checkerActor);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, checkerActor);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, checkerActor);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, checkerActor);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor36, checkerActor);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor37, checkerActor);
		client38.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor38, checkerActor);
		client39.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor39, checkerActor);
		client40.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor40, checkerActor);
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		client26 = setClientRefs(clients, client26);
		client27 = setClientRefs(clients, client27);
		client28 = setClientRefs(clients, client28);
		client29 = setClientRefs(clients, client29);
		client30 = setClientRefs(clients, client30);
		client31 = setClientRefs(clients, client31);
		client32 = setClientRefs(clients, client32);
		client33 = setClientRefs(clients, client33);
		client34 = setClientRefs(clients, client34);
		client35 = setClientRefs(clients, client35);
		client36 = setClientRefs(clients, client36);
		client37 = setClientRefs(clients, client37);
		client38 = setClientRefs(clients, client38);
		client39 = setClientRefs(clients, client39);
		client40 = setClientRefs(clients, client40);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");
		actor26.inform("notify");
		actor27.inform("notify");
		actor28.inform("notify");
		actor29.inform("notify");
		actor30.inform("notify");
		actor31.inform("notify");
		actor32.inform("notify");
		actor33.inform("notify");
		actor34.inform("notify");
		actor35.inform("notify");
		actor36.inform("notify");
		actor37.inform("notify");
		actor38.inform("notify");
		actor39.inform("notify");
		actor40.inform("notify");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test45()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n = 45;
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		AgregatorClient client26 = new Client(26,n);
		AgregatorClient client27 = new Client(27,n);
		AgregatorClient client28 = new Client(28,n);
		AgregatorClient client29 = new Client(29,n);
		AgregatorClient client30 = new Client(30,n);
		AgregatorClient client31 = new Client(31,n);
		AgregatorClient client32 = new Client(32,n);
		AgregatorClient client33 = new Client(33,n);
		AgregatorClient client34 = new Client(34,n);
		AgregatorClient client35 = new Client(35,n);
		AgregatorClient client36 = new Client(36,n);
		AgregatorClient client37 = new Client(37,n);
		AgregatorClient client38 = new Client(38,n);
		AgregatorClient client39 = new Client(39,n);
		AgregatorClient client40 = new Client(40,n);
		AgregatorClient client41 = new Client(41,n);
		AgregatorClient client42 = new Client(42,n);
		AgregatorClient client43 = new Client(43,n);
		AgregatorClient client44 = new Client(44,n);
		AgregatorClient client45 = new Client(45,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);
		BIPActor actor26 = engine.register(client26, "client26", true);clients.add(actor26);
		BIPActor actor27 = engine.register(client27, "client27", true);clients.add(actor27);
		BIPActor actor28 = engine.register(client28, "client28", true);clients.add(actor28);
		BIPActor actor29 = engine.register(client29, "client29", true);clients.add(actor29);
		BIPActor actor30 = engine.register(client30, "client30", true);clients.add(actor30);
		BIPActor actor31 = engine.register(client31, "client31", true);clients.add(actor31);
		BIPActor actor32 = engine.register(client32, "client32", true);clients.add(actor32);
		BIPActor actor33 = engine.register(client33, "client33", true);clients.add(actor33);
		BIPActor actor34 = engine.register(client34, "client34", true);clients.add(actor34);
		BIPActor actor35 = engine.register(client35, "client35", true);clients.add(actor35);
		BIPActor actor36 = engine.register(client36, "client36", true);clients.add(actor36);
		BIPActor actor37 = engine.register(client37, "client37", true);clients.add(actor37);
		BIPActor actor38 = engine.register(client38, "client38", true);clients.add(actor38);
		BIPActor actor39 = engine.register(client39, "client39", true);clients.add(actor39);
		BIPActor actor40 = engine.register(client40, "client40", true);clients.add(actor40);
		BIPActor actor41 = engine.register(client41, "client41", true);clients.add(actor41);
		BIPActor actor42 = engine.register(client42, "client42", true);clients.add(actor42);
		BIPActor actor43 = engine.register(client43, "client43", true);clients.add(actor43);
		BIPActor actor44 = engine.register(client44, "client44", true);clients.add(actor44);
		BIPActor actor45 = engine.register(client45, "client45", true);clients.add(actor45);
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, checkerActor);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, checkerActor);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, checkerActor);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, checkerActor);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, checkerActor);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, checkerActor);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, checkerActor);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, checkerActor);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, checkerActor);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, checkerActor);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor36, checkerActor);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor37, checkerActor);
		client38.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor38, checkerActor);
		client39.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor39, checkerActor);
		client40.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor40, checkerActor);
		client41.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor41, checkerActor);
		client42.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor42, checkerActor);
		client43.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor43, checkerActor);
		client44.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor44, checkerActor);
		client45.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor45, checkerActor);

		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		client26 = setClientRefs(clients, client26);
		client27 = setClientRefs(clients, client27);
		client28 = setClientRefs(clients, client28);
		client29 = setClientRefs(clients, client29);
		client30 = setClientRefs(clients, client30);
		client31 = setClientRefs(clients, client31);
		client32 = setClientRefs(clients, client32);
		client33 = setClientRefs(clients, client33);
		client34 = setClientRefs(clients, client34);
		client35 = setClientRefs(clients, client35);
		client36 = setClientRefs(clients, client36);
		client37 = setClientRefs(clients, client37);
		client38 = setClientRefs(clients, client38);
		client39 = setClientRefs(clients, client39);
		client40 = setClientRefs(clients, client40);
		client41 = setClientRefs(clients, client41);
		client42 = setClientRefs(clients, client42);
		client43 = setClientRefs(clients, client34);
		client44 = setClientRefs(clients, client44);
		client45 = setClientRefs(clients, client45);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");
		actor26.inform("notify");
		actor27.inform("notify");
		actor28.inform("notify");
		actor29.inform("notify");
		actor30.inform("notify");
		actor31.inform("notify");
		actor32.inform("notify");
		actor33.inform("notify");
		actor34.inform("notify");
		actor35.inform("notify");
		actor36.inform("notify");
		actor37.inform("notify");
		actor38.inform("notify");
		actor39.inform("notify");
		actor40.inform("notify");
		actor41.inform("notify");
		actor42.inform("notify");
		actor43.inform("notify");
		actor44.inform("notify");
		actor45.inform("notify");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	
	@Test
	public void test50()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=50; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		BIPActor checkerActor = engine.register(checker, "checker", true); 
		
		AgregatorClient client1 = new Client(1,n);
		AgregatorClient client2 = new Client(2,n);
		AgregatorClient client3 = new Client(3,n);
		AgregatorClient client4 = new Client(4,n);
		AgregatorClient client5 = new Client(5,n);
		AgregatorClient client6 = new Client(6,n);
		AgregatorClient client7 = new Client(7,n);
		AgregatorClient client8 = new Client(8,n);
		AgregatorClient client9 = new Client(9,n);
		AgregatorClient client10 = new Client(10,n);
		AgregatorClient client11 = new Client(11,n);
		AgregatorClient client12 = new Client(12,n);
		AgregatorClient client13 = new Client(13,n);
		AgregatorClient client14 = new Client(14,n);
		AgregatorClient client15 = new Client(15,n);
		AgregatorClient client16 = new Client(16,n);
		AgregatorClient client17 = new Client(17,n);
		AgregatorClient client18 = new Client(18,n);
		AgregatorClient client19 = new Client(19,n);
		AgregatorClient client20 = new Client(20,n);		
		AgregatorClient client21 = new Client(21,n);
		AgregatorClient client22 = new Client(22,n);
		AgregatorClient client23 = new Client(23,n);
		AgregatorClient client24 = new Client(24,n);
		AgregatorClient client25 = new Client(25,n);
		AgregatorClient client26 = new Client(26,n);
		AgregatorClient client27 = new Client(27,n);
		AgregatorClient client28 = new Client(28,n);
		AgregatorClient client29 = new Client(29,n);
		AgregatorClient client30 = new Client(30,n);
		AgregatorClient client31 = new Client(31,n);
		AgregatorClient client32 = new Client(32,n);
		AgregatorClient client33 = new Client(33,n);
		AgregatorClient client34 = new Client(34,n);
		AgregatorClient client35 = new Client(35,n);
		AgregatorClient client36 = new Client(36,n);
		AgregatorClient client37 = new Client(37,n);
		AgregatorClient client38 = new Client(38,n);
		AgregatorClient client39 = new Client(39,n);
		AgregatorClient client40 = new Client(40,n);
		AgregatorClient client41 = new Client(41,n);
		AgregatorClient client42 = new Client(42,n);
		AgregatorClient client43 = new Client(43,n);
		AgregatorClient client44 = new Client(44,n);
		AgregatorClient client45 = new Client(45,n);
		AgregatorClient client46 = new Client(46,n);
		AgregatorClient client47 = new Client(47,n);
		AgregatorClient client48 = new Client(48,n);
		AgregatorClient client49 = new Client(49,n);
		AgregatorClient client50 = new Client(50,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation = new VoiceAgregation(n);
		
		ClientCaller discAgregation = new DiscAgregation(n);
		
		DialWaitSync dialWait = new DialWaitSync(n);
		VoiceSync voice = new VoiceSync(n);
		DiscSync disc = new DiscSync(n);
		
		ArrayList<BIPActor> clients = new ArrayList<BIPActor>(n);
		
		BIPActor actor1 = engine.register(client1, "client1", true);clients.add(actor1);
		BIPActor actor2 = engine.register(client2, "client2", true);clients.add(actor2);
		BIPActor actor3 = engine.register(client3, "client3", true);clients.add(actor3);
		BIPActor actor4 = engine.register(client4, "client4", true);clients.add(actor4);
		BIPActor actor5 = engine.register(client5, "client5", true);clients.add(actor5);
		BIPActor actor6 = engine.register(client6, "client6", true);clients.add(actor6);
		BIPActor actor7 = engine.register(client7, "client7", true);clients.add(actor7);
		BIPActor actor8 = engine.register(client8, "client8", true);clients.add(actor8);
		BIPActor actor9 = engine.register(client9, "client9", true);clients.add(actor9);
		BIPActor actor10 = engine.register(client10, "client10", true);clients.add(actor10);
		BIPActor actor11 = engine.register(client11, "client11", true); clients.add(actor11);
		BIPActor actor12 = engine.register(client12, "client12", true);clients.add(actor12);
		BIPActor actor13 = engine.register(client13, "client13", true);clients.add(actor13);
		BIPActor actor14 = engine.register(client14, "client14", true);clients.add(actor14);
		BIPActor actor15 = engine.register(client15, "client15", true);clients.add(actor15);
		BIPActor actor16 = engine.register(client16, "client16", true); clients.add(actor16);
		BIPActor actor17 = engine.register(client17, "client17", true);clients.add(actor17);
		BIPActor actor18 = engine.register(client18, "client18", true);clients.add(actor18);
		BIPActor actor19 = engine.register(client19, "client19", true);clients.add(actor19);
		BIPActor actor20 = engine.register(client20, "client20", true);clients.add(actor20);
		BIPActor actor21 = engine.register(client21, "client21", true); clients.add(actor21);
		BIPActor actor22 = engine.register(client22, "client22", true);clients.add(actor22);
		BIPActor actor23 = engine.register(client23, "client23", true);clients.add(actor23);
		BIPActor actor24 = engine.register(client24, "client24", true);clients.add(actor24);
		BIPActor actor25 = engine.register(client25, "client25", true);clients.add(actor25);
		BIPActor actor26 = engine.register(client26, "client26", true);clients.add(actor26);
		BIPActor actor27 = engine.register(client27, "client27", true);clients.add(actor27);
		BIPActor actor28 = engine.register(client28, "client28", true);clients.add(actor28);
		BIPActor actor29 = engine.register(client29, "client29", true);clients.add(actor29);
		BIPActor actor30 = engine.register(client30, "client30", true);clients.add(actor30);
		BIPActor actor31 = engine.register(client31, "client31", true);clients.add(actor31);
		BIPActor actor32 = engine.register(client32, "client32", true);clients.add(actor32);
		BIPActor actor33 = engine.register(client33, "client33", true);clients.add(actor33);
		BIPActor actor34 = engine.register(client34, "client34", true);clients.add(actor34);
		BIPActor actor35 = engine.register(client35, "client35", true);clients.add(actor35);
		BIPActor actor36 = engine.register(client36, "client36", true);clients.add(actor36);
		BIPActor actor37 = engine.register(client37, "client37", true);clients.add(actor37);
		BIPActor actor38 = engine.register(client38, "client38", true);clients.add(actor38);
		BIPActor actor39 = engine.register(client39, "client39", true);clients.add(actor39);
		BIPActor actor40 = engine.register(client40, "client40", true);clients.add(actor40);
		BIPActor actor41 = engine.register(client41, "client41", true);clients.add(actor41);
		BIPActor actor42 = engine.register(client42, "client42", true);clients.add(actor42);
		BIPActor actor43 = engine.register(client43, "client43", true);clients.add(actor43);
		BIPActor actor44 = engine.register(client44, "client44", true);clients.add(actor44);
		BIPActor actor45 = engine.register(client45, "client45", true);clients.add(actor45);
		BIPActor actor46 = engine.register(client46, "client46", true);clients.add(actor46);
		BIPActor actor47 = engine.register(client47, "client47", true);clients.add(actor47);
		BIPActor actor48 = engine.register(client48, "client48", true);clients.add(actor48);
		BIPActor actor49 = engine.register(client49, "client49", true);clients.add(actor49);
		BIPActor actor50 = engine.register(client50, "client50", true);clients.add(actor50);
		
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, checkerActor);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, checkerActor);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, checkerActor);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, checkerActor);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, checkerActor);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, checkerActor);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, checkerActor);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, checkerActor);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, checkerActor);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, checkerActor);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, checkerActor);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, checkerActor);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, checkerActor);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, checkerActor);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, checkerActor);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, checkerActor);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, checkerActor);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, checkerActor);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, checkerActor);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, checkerActor);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, checkerActor);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, checkerActor);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, checkerActor);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, checkerActor);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, checkerActor);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, checkerActor);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, checkerActor);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, checkerActor);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, checkerActor);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, checkerActor);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, checkerActor);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, checkerActor);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, checkerActor);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, checkerActor);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, checkerActor);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor36, checkerActor);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor37, checkerActor);
		client38.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor38, checkerActor);
		client39.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor39, checkerActor);
		client40.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor40, checkerActor);
		client41.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor41, checkerActor);
		client42.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor42, checkerActor);
		client43.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor43, checkerActor);
		client44.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor44, checkerActor);
		client45.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor45, checkerActor);
		client46.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor46, checkerActor);
		client47.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor47, checkerActor);
		client48.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor48, checkerActor);
		client49.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor49, checkerActor);
		client50.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor50, checkerActor);
		
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation.setSyncRefs(actorVoice);
		voiceAgregation.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice1);
		
		discAgregation.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc1);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation =  setClientRefs(clients, voiceAgregation);
		discAgregation =  setClientRefs(clients, discAgregation);
		
		client1 = setClientRefs(clients, client1);
		client2 = setClientRefs(clients, client2);
		client3 = setClientRefs(clients, client3);
		client4 = setClientRefs(clients, client4);
		client5 = setClientRefs(clients, client5);
		client6 = setClientRefs(clients, client6);
		client7 = setClientRefs(clients, client7);
		client8 = setClientRefs(clients, client8);
		client9 = setClientRefs(clients, client9);
		client10 = setClientRefs(clients, client10);
		client11 = setClientRefs(clients, client11);
		client12 = setClientRefs(clients, client12);
		client13 = setClientRefs(clients, client13);
		client14 = setClientRefs(clients, client14);
		client15 = setClientRefs(clients, client15);
		client16 = setClientRefs(clients, client16);
		client17 = setClientRefs(clients, client17);
		client18 = setClientRefs(clients, client18);
		client19 = setClientRefs(clients, client19);
		client20 = setClientRefs(clients, client20);
		client21 = setClientRefs(clients, client21);
		client22 = setClientRefs(clients, client22);
		client23 = setClientRefs(clients, client23);
		client24 = setClientRefs(clients, client24);
		client25 = setClientRefs(clients, client25);
		client26 = setClientRefs(clients, client26);
		client27 = setClientRefs(clients, client27);
		client28 = setClientRefs(clients, client28);
		client29 = setClientRefs(clients, client29);
		client30 = setClientRefs(clients, client30);
		client31 = setClientRefs(clients, client31);
		client32 = setClientRefs(clients, client32);
		client33 = setClientRefs(clients, client33);
		client34 = setClientRefs(clients, client34);
		client35 = setClientRefs(clients, client35);
		client36 = setClientRefs(clients, client36);
		client37 = setClientRefs(clients, client37);
		client38 = setClientRefs(clients, client38);
		client39 = setClientRefs(clients, client39);
		client40 = setClientRefs(clients, client40);
		client41 = setClientRefs(clients, client41);
		client42 = setClientRefs(clients, client42);
		client43 = setClientRefs(clients, client34);
		client44 = setClientRefs(clients, client44);
		client45 = setClientRefs(clients, client45);
		client46 = setClientRefs(clients, client46);
		client47 = setClientRefs(clients, client47);
		client48 = setClientRefs(clients, client48);
		client49 = setClientRefs(clients, client49);
		client50 = setClientRefs(clients, client50);
				
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");
		actor8.inform("notify");
		actor9.inform("notify");
		actor10.inform("notify");
		actor11.inform("notify");
		actor12.inform("notify");
		actor13.inform("notify");
		actor14.inform("notify");
		actor15.inform("notify");
		actor16.inform("notify");
		actor17.inform("notify");
		actor18.inform("notify");
		actor19.inform("notify");
		actor20.inform("notify");
		actor21.inform("notify");
		actor22.inform("notify");
		actor23.inform("notify");
		actor24.inform("notify");
		actor25.inform("notify");
		actor26.inform("notify");
		actor27.inform("notify");
		actor28.inform("notify");
		actor29.inform("notify");
		actor30.inform("notify");
		actor31.inform("notify");
		actor32.inform("notify");
		actor33.inform("notify");
		actor34.inform("notify");
		actor35.inform("notify");
		actor36.inform("notify");
		actor37.inform("notify");
		actor38.inform("notify");
		actor39.inform("notify");
		actor40.inform("notify");
		actor41.inform("notify");
		actor42.inform("notify");
		actor43.inform("notify");
		actor44.inform("notify");
		actor45.inform("notify");
		actor46.inform("notify");
		actor47.inform("notify");
		actor48.inform("notify");
		actor49.inform("notify");
		actor50.inform("notify");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Some client was communicating with several other clients at the same time.", checker.talkingToOne());
		assertTrue("Some clients are not communicating with each other after making a call.", checker.connectingToEachOther());
		assertTrue("Some clients are not communicating with each other at voicing.", checker.voicingToEachOther());
		assertTrue("Some clients are not communicating with each other at disconnecting.", checker.discToEachOther());
		assertTrue("A client was communicating with not someone it expected", checker.hasNoErrors());

		engine.stop();
		engineFactory.destroy(engine);
	}

	private ClientCaller setClientRefs(ArrayList<BIPActor> clients, ClientCaller object) {
		for (int i=0; i<clients.size(); i++)
		{
			object.setClientRefs(clients.get(i), i+1);
		}
		return object;
	}
	
	
	private AgregatorClient setClientRefs(ArrayList<BIPActor> clients, AgregatorClient object) {
		for (int i=0; i<clients.size(); i++)
		{
			object.setClientRefs(clients.get(i), i+1);
		}
		return object;
	}
}
