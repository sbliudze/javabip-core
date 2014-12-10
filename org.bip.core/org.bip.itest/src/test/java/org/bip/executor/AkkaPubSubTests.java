package org.bip.executor;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
import lsr.concurrence.provided.tests.InputChecker;

import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.GlueBuilder;
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

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			BIPEngine engine = engineFactory.create("myEngine", new DataCoordinatorKernel(new BIPCoordinatorImpl(system)));

			
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
