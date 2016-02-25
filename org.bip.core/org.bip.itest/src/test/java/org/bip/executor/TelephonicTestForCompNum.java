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
import org.bip.spec.telephonic.ClientNoChecker;
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

public class TelephonicTestForCompNum {

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
	public void test2()
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

		int n=2; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
			

		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		
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
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation1, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation1, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1,actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1,  actorDisc1,  actor2, null);
		
		
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
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test7()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=7; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
				
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);	
		
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

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		
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
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");
		actor6.inform("notify");
		actor7.inform("notify");


		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	
		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test12()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=12; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		
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

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);

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
	public void test17()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=17; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);		
		
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
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);

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
	public void test22()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=22; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);
		AgregatorClient client18 = new ClientNoChecker(18,n);
		AgregatorClient client19 = new ClientNoChecker(19,n);
		AgregatorClient client20 = new ClientNoChecker(20,n);		
		AgregatorClient client21 = new ClientNoChecker(21,n);
		AgregatorClient client22 = new ClientNoChecker(22,n);
		
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

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, null);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, null);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, null);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, null);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, null);
		
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
	public void test27()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=27; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);
		AgregatorClient client18 = new ClientNoChecker(18,n);
		AgregatorClient client19 = new ClientNoChecker(19,n);
		AgregatorClient client20 = new ClientNoChecker(20,n);		
		AgregatorClient client21 = new ClientNoChecker(21,n);
		AgregatorClient client22 = new ClientNoChecker(22,n);
		AgregatorClient client23 = new ClientNoChecker(23,n);
		AgregatorClient client24 = new ClientNoChecker(24,n);
		AgregatorClient client25 = new ClientNoChecker(25,n);
		AgregatorClient client26 = new ClientNoChecker(26,n);
		AgregatorClient client27 = new ClientNoChecker(27,n);
		
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

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, null);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, null);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, null);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, null);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, null);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, null);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, null);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, null);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, null);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, null);

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
	public void test32()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=32; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);
		AgregatorClient client18 = new ClientNoChecker(18,n);
		AgregatorClient client19 = new ClientNoChecker(19,n);
		AgregatorClient client20 = new ClientNoChecker(20,n);		
		AgregatorClient client21 = new ClientNoChecker(21,n);
		AgregatorClient client22 = new ClientNoChecker(22,n);
		AgregatorClient client23 = new ClientNoChecker(23,n);
		AgregatorClient client24 = new ClientNoChecker(24,n);
		AgregatorClient client25 = new ClientNoChecker(25,n);
		AgregatorClient client26 = new ClientNoChecker(26,n);
		AgregatorClient client27 = new ClientNoChecker(27,n);
		AgregatorClient client28 = new ClientNoChecker(28,n);
		AgregatorClient client29 = new ClientNoChecker(29,n);
		AgregatorClient client30 = new ClientNoChecker(30,n);
		AgregatorClient client31 = new ClientNoChecker(31,n);
		AgregatorClient client32 = new ClientNoChecker(32,n);

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

		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, null);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, null);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, null);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, null);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, null);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, null);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, null);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, null);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, null);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, null);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, null);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, null);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, null);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, null);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, null);

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
	public void test37()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=37; 
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);
		AgregatorClient client18 = new ClientNoChecker(18,n);
		AgregatorClient client19 = new ClientNoChecker(19,n);
		AgregatorClient client20 = new ClientNoChecker(20,n);		
		AgregatorClient client21 = new ClientNoChecker(21,n);
		AgregatorClient client22 = new ClientNoChecker(22,n);
		AgregatorClient client23 = new ClientNoChecker(23,n);
		AgregatorClient client24 = new ClientNoChecker(24,n);
		AgregatorClient client25 = new ClientNoChecker(25,n);
		AgregatorClient client26 = new ClientNoChecker(26,n);
		AgregatorClient client27 = new ClientNoChecker(27,n);
		AgregatorClient client28 = new ClientNoChecker(28,n);
		AgregatorClient client29 = new ClientNoChecker(29,n);
		AgregatorClient client30 = new ClientNoChecker(30,n);
		AgregatorClient client31 = new ClientNoChecker(31,n);
		AgregatorClient client32 = new ClientNoChecker(32,n);
		AgregatorClient client33 = new ClientNoChecker(33,n);
		AgregatorClient client34 = new ClientNoChecker(34,n);
		AgregatorClient client35 = new ClientNoChecker(35,n);
		AgregatorClient client36 = new ClientNoChecker(36,n);
		AgregatorClient client37 = new ClientNoChecker(37,n);
		
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
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, null);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, null);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, null);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, null);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, null);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, null);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, null);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, null);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, null);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, null);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, null);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, null);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, null);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, null);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, null);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, null);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, null);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, null);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor36, null);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor37, null);
		
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
	public void test42()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new BIPCoordinatorImpl(system));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n = 42;
		
		DummyComponent dummy = new DummyComponent();
		BIPActor dummyActor = engine.register(dummy, "dummy", true); 
		
		
		Checker checker = new Checker(n);
		
		AgregatorClient client1 = new ClientNoChecker(1,n);
		AgregatorClient client2 = new ClientNoChecker(2,n);
		AgregatorClient client3 = new ClientNoChecker(3,n);
		AgregatorClient client4 = new ClientNoChecker(4,n);
		AgregatorClient client5 = new ClientNoChecker(5,n);
		AgregatorClient client6 = new ClientNoChecker(6,n);
		AgregatorClient client7 = new ClientNoChecker(7,n);
		AgregatorClient client8 = new ClientNoChecker(8,n);
		AgregatorClient client9 = new ClientNoChecker(9,n);
		AgregatorClient client10 = new ClientNoChecker(10,n);
		AgregatorClient client11 = new ClientNoChecker(11,n);
		AgregatorClient client12 = new ClientNoChecker(12,n);
		AgregatorClient client13 = new ClientNoChecker(13,n);
		AgregatorClient client14 = new ClientNoChecker(14,n);
		AgregatorClient client15 = new ClientNoChecker(15,n);
		AgregatorClient client16 = new ClientNoChecker(16,n);
		AgregatorClient client17 = new ClientNoChecker(17,n);
		AgregatorClient client18 = new ClientNoChecker(18,n);
		AgregatorClient client19 = new ClientNoChecker(19,n);
		AgregatorClient client20 = new ClientNoChecker(20,n);		
		AgregatorClient client21 = new ClientNoChecker(21,n);
		AgregatorClient client22 = new ClientNoChecker(22,n);
		AgregatorClient client23 = new ClientNoChecker(23,n);
		AgregatorClient client24 = new ClientNoChecker(24,n);
		AgregatorClient client25 = new ClientNoChecker(25,n);
		AgregatorClient client26 = new ClientNoChecker(26,n);
		AgregatorClient client27 = new ClientNoChecker(27,n);
		AgregatorClient client28 = new ClientNoChecker(28,n);
		AgregatorClient client29 = new ClientNoChecker(29,n);
		AgregatorClient client30 = new ClientNoChecker(30,n);
		AgregatorClient client31 = new ClientNoChecker(31,n);
		AgregatorClient client32 = new ClientNoChecker(32,n);
		AgregatorClient client33 = new ClientNoChecker(33,n);
		AgregatorClient client34 = new ClientNoChecker(34,n);
		AgregatorClient client35 = new ClientNoChecker(35,n);
		AgregatorClient client36 = new ClientNoChecker(36,n);
		AgregatorClient client37 = new ClientNoChecker(37,n);
		AgregatorClient client38 = new ClientNoChecker(38,n);
		AgregatorClient client39 = new ClientNoChecker(39,n);
		AgregatorClient client40 = new ClientNoChecker(40,n);
		AgregatorClient client41 = new ClientNoChecker(41,n);
		AgregatorClient client42 = new ClientNoChecker(42,n);
		
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
		
		BIPActor actorCaller = engine.register(callerAgregation, "callerAgregation", false);
		BIPActor actorCallee = engine.register(calleeAgregation, "calleeAgregation", false);
		BIPActor actorVoice1 = engine.register(voiceAgregation, "voiceAgregation1", false);
		BIPActor actorDisc1 = engine.register(discAgregation, "discAgregation1", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor1, null);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor2, null);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor3, null);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor4, null);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor5, null);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor6, null);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor7, null);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor8, null);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor9, null);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor10, null);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor11, null);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor12, null);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor13, null);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor14, null);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor15, null);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor16, null);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor17, null);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor18, null);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor19, null);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor20, null);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor21, null);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor22, null);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor23, null);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor24, null);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor25, null);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor26, null);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor27, null);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor28, null);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor29, null);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor30, null);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor31, null);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor32, null);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor33, null);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor34, null);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor35, null);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor36, null);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor37, null);
		client38.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor38, null);
		client39.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor39, null);
		client40.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor40, null);
		client41.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor41, null);
		client42.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorDisc1,  actor42, null);

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
