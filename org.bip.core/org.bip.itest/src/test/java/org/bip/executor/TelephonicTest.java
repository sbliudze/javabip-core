package org.bip.executor;

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
import org.bip.spec.telephonic.CalleeAgregation;
import org.bip.spec.telephonic.CallerAgregation;
import org.bip.spec.telephonic.Client;
import org.bip.spec.telephonic.ClientCaller;
import org.bip.spec.telephonic.DialWaitSync;
import org.bip.spec.telephonic.DiscAgregation1;
import org.bip.spec.telephonic.DiscAgregation2;
import org.bip.spec.telephonic.DiscSync;
import org.bip.spec.telephonic.VoiceAgregation1;
import org.bip.spec.telephonic.VoiceAgregation2;
import org.bip.spec.telephonic.VoiceSync;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import scala.Array;
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
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=5; 
		Client client1 = new Client(1,n);
		Client client2 = new Client(2,n);
		Client client3 = new Client(3,n);
		Client client4 = new Client(4,n);
		Client client5 = new Client(5,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation1 = new VoiceAgregation1(n);
		ClientCaller voiceAgregation2 = new VoiceAgregation2(n); 
		
		ClientCaller discAgregation1 = new DiscAgregation1(n);
		ClientCaller discAgregation2 = new DiscAgregation2(n); 
		
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
		BIPActor actorVoice2 = engine.register(voiceAgregation2, "voiceAgregation2", false);
		BIPActor actorDisc1 = engine.register(discAgregation1, "discAgregation1", false);
		BIPActor actorDisc2 = engine.register(discAgregation2, "discAgregation2", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor1);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor2);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor3);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor4);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor5);
		
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation1.setSyncRefs(actorVoice);
		voiceAgregation2.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice2);
		
		discAgregation1.setSyncRefs(actorDisc);
		discAgregation2.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc2);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation1 =  setClientRefs(clients, voiceAgregation1);
		voiceAgregation2 =  setClientRefs(clients, voiceAgregation2);
		discAgregation1 =  setClientRefs(clients, discAgregation1);
		discAgregation2 =  setClientRefs(clients, discAgregation2);
		
//		calleeAgregation.setClientRefs(actor1, 1);
//		calleeAgregation.setClientRefs(actor2, 2);
//		calleeAgregation.setClientRefs(actor3, 3);
		
		engine.start();
		engine.execute();

		actor1.inform("notify");
		actor2.inform("notify");
		actor3.inform("notify");
		actor4.inform("notify");
		actor5.inform("notify");

		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@Test
	public void test50()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		BIPGlue bipGlue = createGlue("src/test/resources/EmptyGlue.xml");
		engine.specifyGlue(bipGlue);
		
		int n=50; 
		Client client1 = new Client(1,n);
		Client client2 = new Client(2,n);
		Client client3 = new Client(3,n);
		Client client4 = new Client(4,n);
		Client client5 = new Client(5,n);
		Client client6 = new Client(6,n);
		Client client7 = new Client(7,n);
		Client client8 = new Client(8,n);
		Client client9 = new Client(9,n);
		Client client10 = new Client(10,n);
		Client client11 = new Client(11,n);
		Client client12 = new Client(12,n);
		Client client13 = new Client(13,n);
		Client client14 = new Client(14,n);
		Client client15 = new Client(15,n);
		Client client16 = new Client(16,n);
		Client client17 = new Client(17,n);
		Client client18 = new Client(18,n);
		Client client19 = new Client(19,n);
		Client client20 = new Client(20,n);		
		Client client21 = new Client(21,n);
		Client client22 = new Client(22,n);
		Client client23 = new Client(23,n);
		Client client24 = new Client(24,n);
		Client client25 = new Client(25,n);
		Client client26 = new Client(26,n);
		Client client27 = new Client(27,n);
		Client client28 = new Client(28,n);
		Client client29 = new Client(29,n);
		Client client30 = new Client(30,n);
		Client client31 = new Client(31,n);
		Client client32 = new Client(32,n);
		Client client33 = new Client(33,n);
		Client client34 = new Client(34,n);
		Client client35 = new Client(35,n);
		Client client36 = new Client(36,n);
		Client client37 = new Client(37,n);
		Client client38 = new Client(38,n);
		Client client39 = new Client(39,n);
		Client client40 = new Client(40,n);
		Client client41 = new Client(41,n);
		Client client42 = new Client(42,n);
		Client client43 = new Client(43,n);
		Client client44 = new Client(44,n);
		Client client45 = new Client(45,n);
		Client client46 = new Client(46,n);
		Client client47 = new Client(47,n);
		Client client48 = new Client(48,n);
		Client client49 = new Client(49,n);
		Client client50 = new Client(50,n);
		
		ClientCaller callerAgregation = new CallerAgregation(n);
		ClientCaller calleeAgregation = new CalleeAgregation(n); 
		
		ClientCaller voiceAgregation1 = new VoiceAgregation1(n);
		ClientCaller voiceAgregation2 = new VoiceAgregation2(n); 
		
		ClientCaller discAgregation1 = new DiscAgregation1(n);
		ClientCaller discAgregation2 = new DiscAgregation2(n); 
		
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
		BIPActor actorVoice1 = engine.register(voiceAgregation1, "voiceAgregation1", false);
		BIPActor actorVoice2 = engine.register(voiceAgregation2, "voiceAgregation2", false);
		BIPActor actorDisc1 = engine.register(discAgregation1, "discAgregation1", false);
		BIPActor actorDisc2 = engine.register(discAgregation2, "discAgregation2", false);
		
		BIPActor actorDialWait = engine.register(dialWait, "dialWait", true);
		BIPActor actorVoice = engine.register(voice, "voice", true);
		BIPActor actorDisc = engine.register(disc, "disc", true);
		
		client1.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor1);
		client2.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor2);
		client3.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor3);
		client4.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor4);
		client5.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor5);
		client6.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor6);
		client7.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor7);
		client8.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor8);
		client9.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor9);
		client10.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor10);
		client11.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor11);
		client12.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor12);
		client13.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor13);
		client14.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor14);
		client15.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor15);
		client16.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor16);
		client17.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor17);
		client18.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor18);
		client19.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor19);
		client20.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor20);
		client21.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor21);
		client22.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor22);
		client23.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor23);
		client24.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor24);
		client25.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor25);
		client26.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor26);
		client27.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor27);
		client28.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor28);
		client29.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor29);
		client30.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor30);
		client31.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor31);
		client32.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor32);
		client33.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor33);
		client34.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor34);
		client35.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor35);
		client36.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor36);
		client37.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor37);
		client38.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor38);
		client39.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor39);
		client40.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor40);
		client41.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor41);
		client42.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor42);
		client43.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor43);
		client44.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor44);
		client45.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor45);
		client46.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor46);
		client47.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor47);
		client48.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor48);
		client49.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor49);
		client50.setExecutorRefs(actorCaller, actorCallee, actorVoice1, actorVoice2, actorDisc1, actorDisc2, actor50);
		
		
		callerAgregation.setSyncRefs(actorDialWait);
		calleeAgregation.setSyncRefs(actorDialWait);
		dialWait.setExecutorRefs(actorCaller, actorCallee);
		
		voiceAgregation1.setSyncRefs(actorVoice);
		voiceAgregation2.setSyncRefs(actorVoice);
		voice.setExecutorRefs(actorVoice1, actorVoice2);
		
		discAgregation1.setSyncRefs(actorDisc);
		discAgregation2.setSyncRefs(actorDisc);
		disc.setExecutorRefs(actorDisc1, actorDisc2);
		
		callerAgregation = setClientRefs(clients, callerAgregation);
		calleeAgregation = setClientRefs(clients, calleeAgregation);
		voiceAgregation1 =  setClientRefs(clients, voiceAgregation1);
		voiceAgregation2 =  setClientRefs(clients, voiceAgregation2);
		discAgregation1 =  setClientRefs(clients, discAgregation1);
		discAgregation2 =  setClientRefs(clients, discAgregation2);
		
//		calleeAgregation.setClientRefs(actor1, 1);
//		calleeAgregation.setClientRefs(actor2, 2);
//		calleeAgregation.setClientRefs(actor3, 3);
		
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
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
}
