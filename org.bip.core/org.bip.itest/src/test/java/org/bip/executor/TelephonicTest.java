package org.bip.executor;

import java.util.ArrayList;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
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
	
	@Test
	public void test()
	{
		
		BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

		int n=3; 
		Client client1 = new Client(1,n);
		Client client2 = new Client(2,n);
		Client client3 = new Client(3,n);
		
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

		try {
			Thread.sleep(60000);
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
