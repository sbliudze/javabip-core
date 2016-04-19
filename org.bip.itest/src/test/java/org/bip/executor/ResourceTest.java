package org.bip.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.antlr.v4.runtime.RecognitionException;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.api.ResourceProvider;
import org.bip.engine.factory.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.resources.AllocatorImpl;
import org.bip.resources.BoundedResourceManager;
import org.bip.resources.DNetException;
import org.bip.resources.VirtualResourceManager;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.PComponent;
import org.bip.spec.QComponent;
import org.bip.spec.RComponent;
import org.bip.spec.resources.Bus;
import org.bip.spec.resources.ComponentNeedingResource;
import org.bip.spec.resources.DataMemoryManager;
import org.bip.spec.resources.KalrayData;
import org.bip.spec.resources.KalrayMemory;
import org.bip.spec.resources.KalrayMemoryBank;
import org.bip.spec.resources.KalrayResource;
import org.bip.spec.resources.KalrayTask;
import org.bip.spec.resources.KalrayTaskResource;
import org.bip.spec.resources.MemoryManager;
import org.bip.spec.resources.RouteUser;
import org.bip.spec.resources.Memory;
import org.bip.spec.resources.Processor;
import org.bip.spec.resources.RouteManager;
import org.bip.spec.resources.RouteResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class ResourceTest {

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
	public void KalraySimpleTest() throws RecognitionException, IOException,
			DNetException {
		/*
		 * This test uses a dnet presenting simplified Kalray architecture.
		 * The weakness of this example is that the amount of memory given depends on the processor chosen.
		 * (Due to limitations of Left and Right banks saying that processors from one group cannot access the same bank)
		 *  The problem is solved (hopefully) in KalrayLRTest() which is using another version of a DNet, less concise.
		 *  In this test, 5 allocations are being requested, four of them are satisfied.
		 */
		String dnetSpec = "src/test/resources/simple_kalray.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec);
		
		KalrayResource p = new KalrayResource("p", 1, false);
		KalrayResource p1 = new KalrayResource("p1", 1, true);
		KalrayResource p2 = new KalrayResource("p2", 1, true);
		KalrayResource p3 = new KalrayResource("p3", 1, true);
		KalrayResource p4 = new KalrayResource("p4", 1, true);
		
		KalrayResource m = new KalrayResource("m", 10, false);
		KalrayResource m1 = new KalrayResource("m1", 10, true);
		KalrayResource m2 = new KalrayResource("m2", 10, true);
		KalrayResource m3 = new KalrayResource("m3", 10, true);
		KalrayResource m4 = new KalrayResource("m4", 10, true);
		
		KalrayMemoryBank L = new KalrayMemoryBank("L");
		KalrayMemoryBank R = new KalrayMemoryBank("R");

		
		KalrayResource b12L = new KalrayResource("b12L", 1, true);
		KalrayResource b34L = new KalrayResource("b34L", 1, true);
		KalrayResource b12R = new KalrayResource("b12R", 1, true);
		KalrayResource b34R = new KalrayResource("b34R", 1, true);
		
		alloc.addResource(p);alloc.addResource(p1);alloc.addResource(p2);alloc.addResource(p3);alloc.addResource(p4);
		alloc.addResource(m);alloc.addResource(m1);alloc.addResource(m2);alloc.addResource(m3);alloc.addResource(m4);
		alloc.addResource(R);alloc.addResource(L);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		
//		String firstRequest = "p=1 & m>0";
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		//this one will not allocate in the current setting - OK
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
		System.out.println();
	}
	
	@Test
	public void KalrayLRTest() throws RecognitionException, IOException,
			DNetException {
		/*
		 * This test uses a dnet presenting simplified Kalray architecture.
		 * Resources L and R are divided into two each, so that one (L1, R1) stores information about the memory
		 * and the other (L2, R2) stores information about the processors already using the bank.
		 */
		String dnetSpec = "src/test/resources/kalray_LR";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec);
		
		KalrayResource p = new KalrayResource("p", 1, false);
		KalrayResource p1 = new KalrayResource("p1", 1, true);
		KalrayResource p2 = new KalrayResource("p2", 1, true);
		KalrayResource p3 = new KalrayResource("p3", 1, true);
		KalrayResource p4 = new KalrayResource("p4", 1, true);
		
		KalrayResource m = new KalrayResource("m", 1, false);
		KalrayResource m1 = new KalrayResource("m1", 1, true);
		KalrayResource m2 = new KalrayResource("m2", 1, true);
		KalrayResource m3 = new KalrayResource("m3", 1, true);
		KalrayResource m4 = new KalrayResource("m4", 1, true);
		
		KalrayResource L1 = new KalrayResource("L1", 1, false);
		KalrayResource R1 = new KalrayResource("R1", 1, false);
		KalrayMemoryBank L2 = new KalrayMemoryBank("L2");
		KalrayMemoryBank R2 = new KalrayMemoryBank("R2");
		
		KalrayResource b12L = new KalrayResource("b12L", 1, true);
		KalrayResource b34L = new KalrayResource("b34L", 1, true);
		KalrayResource b12R = new KalrayResource("b12R", 1, true);
		KalrayResource b34R = new KalrayResource("b34R", 1, true);
		
		alloc.addResource(p);alloc.addResource(p1);alloc.addResource(p2);alloc.addResource(p3);alloc.addResource(p4);
		alloc.addResource(m);alloc.addResource(m1);alloc.addResource(m2);alloc.addResource(m3);alloc.addResource(m4);
		alloc.addResource(R1);alloc.addResource(L1);alloc.addResource(R2);alloc.addResource(L2);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		
		String firstRequest = "p=1 & m=1";
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//
//		ArrayList<String> unitNames = new ArrayList<String>();
//		int allocID = alloc.allocID();
//		unitNames.add("m");unitNames.add("p");
//		alloc.releaseResource(unitNames, allocID);
//		
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		} else {
//			System.out.println("FIASCO-1");
//		}
//		alloc.releaseResource(unitNames, 1);
//		
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		} else {
//			System.out.println("FIASCO-2");
//		}
	}
	
	@Test
	public void KalrayLRWithDataTest() throws RecognitionException, IOException,
			DNetException {
		/*
		 * This test uses a dnet presenting simplified Kalray architecture.
		 * Resources L and R are divided into two each, so that one (L1, R1) stores information about the memory
		 * and the other (L2, R2) stores information about the processors already using the bank.
		 */
		String dnetSpec = "src/test/resources/kalray_data";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec);
		
		VirtualResourceManager p = new VirtualResourceManager("p");
		BoundedResourceManager p1 = new BoundedResourceManager("p1", 1);
		BoundedResourceManager p2 = new BoundedResourceManager("p2", 1);
		BoundedResourceManager p3 = new BoundedResourceManager("p3", 1);
		BoundedResourceManager p4 = new BoundedResourceManager("p4", 1);
		
		VirtualResourceManager m = new VirtualResourceManager("m");
		BoundedResourceManager m1 = new BoundedResourceManager("m1", 1);
		BoundedResourceManager m2 = new BoundedResourceManager("m2", 1);
		BoundedResourceManager m3 = new BoundedResourceManager("m3", 1);
		BoundedResourceManager m4 = new BoundedResourceManager("m4", 1);
		
		VirtualResourceManager L1 = new VirtualResourceManager("L1");
		VirtualResourceManager R1 = new VirtualResourceManager("R1");
		KalrayMemoryBank L2 = new KalrayMemoryBank("L2");
		KalrayMemoryBank R2 = new KalrayMemoryBank("R2");
		
		BoundedResourceManager b12L = new BoundedResourceManager("b12L", 1);
		BoundedResourceManager b34L = new BoundedResourceManager("b34L", 1);
		BoundedResourceManager b12R = new BoundedResourceManager("b12R", 1);
		BoundedResourceManager b34R = new BoundedResourceManager("b34R", 1);
		
		KalrayResource T = new KalrayResource("T", 1, false);
		KalrayTaskResource T1 = new KalrayTaskResource("T1");
		KalrayTaskResource T2 = new KalrayTaskResource("T2");
		KalrayTaskResource T3 = new KalrayTaskResource("T3");
		KalrayTaskResource T4 = new KalrayTaskResource("T4");
		KalrayTaskResource T5 = new KalrayTaskResource("T5");
			
		//KalrayData D12 = new KalrayData("D12");
		KalrayData D13 = new KalrayData("D13");
		KalrayData D24 = new KalrayData("D24");
		KalrayData D53 = new KalrayData("D53");
		KalrayData D34 = new KalrayData("D34");
		
		alloc.addResource(p);alloc.addResource(p1);alloc.addResource(p2);alloc.addResource(p3);alloc.addResource(p4);
		alloc.addResource(m);alloc.addResource(m1);alloc.addResource(m2);alloc.addResource(m3);alloc.addResource(m4);
		alloc.addResource(R1);alloc.addResource(L1);alloc.addResource(R2);alloc.addResource(L2);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		alloc.addResource(T);alloc.addResource(T1);alloc.addResource(T2);alloc.addResource(T3);alloc.addResource(T4);alloc.addResource(T5);
		//alloc.addResource(D12);
		alloc.addResource(D13);alloc.addResource(D24);alloc.addResource(D53);alloc.addResource(D34);
		
		T1.setData(D13);
		T2.setData(D24);
		T3.setData(D34);
		T5.setData(D53);
		//T1.setData(D12); ???
		
		String firstRequest = "T=1";
		//first, T1, T5, T2 can be provided
		// after T1 and T5, T3 can be provided as well
		long starttime = System.currentTimeMillis();
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		long endtime = System.currentTimeMillis();
//		System.out.println(endtime-starttime);
//		starttime = System.currentTimeMillis();
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		endtime = System.currentTimeMillis();
//		System.out.println(endtime-starttime);
//		starttime = System.currentTimeMillis();
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		endtime = System.currentTimeMillis();
//		System.out.println(endtime-starttime);
//		starttime = System.currentTimeMillis();
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		}
//		endtime = System.currentTimeMillis();
//		System.out.println(endtime-starttime);
//	
//		 if (alloc.canAllocate(firstRequest, "id1")) {
//		 alloc.specifyRequest(firstRequest, "id1");
//		 } else {
//		 System.out.println("No possibility to provide - all p and m busy!");
//		 }
//
//		ArrayList<String> unitNames = new ArrayList<String>();
//		int allocID = alloc.allocID();
//		unitNames.add("m");unitNames.add("p");
//		alloc.releaseResource(unitNames, allocID);
//		
//		if (alloc.canAllocate(firstRequest, "id1")) {
//			alloc.specifyRequest(firstRequest, "id1");
//		} else {
//			System.out.println("FIASCO");
//		}
	}
	
	@Test
	public void KalrayTest() throws RecognitionException, IOException,
			DNetException {
		/*
		 * There are several tasks asking for p and m resources of the Kalray architecture. 
		 * Some tasks additionally require the data generated by other tasks.
		 * This data is generated in a particular memory unit, and when a data is asked, the memory unit must be allocated together with it.
		 */
		
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				// TODO create glue function for making links between methods and data automatically
				synchron(KalrayTask.class, "askResource").to(
						AllocatorImpl.class, "request");
				synchron(KalrayTask.class, "release").to(AllocatorImpl.class,
						"release");
				synchron(KalrayTask.class, "getResource").to(
						AllocatorImpl.class, "provideResource");
				synchron(KalrayTask.class, "readData").to(KalrayMemory.class,
						"read");
				port(KalrayTask.class, "generate").requires(KalrayMemory.class,
						"create", KalrayData.class, "create");
				port(KalrayMemory.class, "create").requires(KalrayData.class,
						"create", KalrayTask.class, "generate");
				port(KalrayData.class, "create").requires(KalrayMemory.class,
						"create", KalrayTask.class, "generate");
				port(KalrayTask.class, "generate").accepts(KalrayMemory.class,
						"create", KalrayData.class, "create");
				port(KalrayMemory.class, "create").accepts(KalrayData.class,
						"create", KalrayTask.class, "generate");
				port(KalrayData.class, "create").accepts(KalrayMemory.class,
						"create", KalrayTask.class, "generate");

				synchron(KalrayMemory.class, "deleteData").to(KalrayData.class,
						 "delete");
				 
				 //data(KalrayTask.class, "utility").to(AllocatorImpl.class, "request");
				 data(KalrayTask.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
				 data(KalrayTask.class, "allocID").to(AllocatorImpl.class, "allocID");
				 //data(KalrayTask.class, "componentID").to(AllocatorImpl.class, "id");
				 data(KalrayTask.class, "dataArray").to(AllocatorImpl.class, "request-id");
				 
				 data(KalrayMemory.class, "dataName").to(KalrayData.class, "id");
				 data(KalrayTask.class, "dataName").to(KalrayData.class, "id");
				 data(KalrayTask.class, "memory").to(KalrayMemory.class, "id");
				 data(KalrayTask.class, "dataName").to(KalrayMemory.class, "data");
				 data(KalrayTask.class, "dataToRead").to(KalrayMemory.class, "dataID");
				 data(KalrayTask.class, "dataReadCount").to(KalrayMemory.class, "count");
				 data(AllocatorImpl.class, "resources").to(KalrayTask.class, "resourceArray");
				 data(AllocatorImpl.class, "amounts").to(KalrayTask.class, "resourceAmounts");
				 data(AllocatorImpl.class, "allocID").to(KalrayTask.class, "allocID");
			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		String dnetSpec = "src/test/resources/kalray.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec);
		
		//RESOURCES
		
		VirtualResourceManager p = new VirtualResourceManager("p");
		BoundedResourceManager p1 = new BoundedResourceManager("p1", 1);
		BoundedResourceManager p2 = new BoundedResourceManager("p2", 1);
		BoundedResourceManager p3 = new BoundedResourceManager("p3", 1);
		BoundedResourceManager p4 = new BoundedResourceManager("p4", 1);
		
		VirtualResourceManager m = new VirtualResourceManager("m");
		MemoryManager m1 = new MemoryManager("m1", 1);
		MemoryManager m2 = new MemoryManager("m2", 1);
		MemoryManager m3 = new MemoryManager("m3", 1);
		MemoryManager m4 = new MemoryManager("m4", 1);
		// Now, the link between resource manager and memory is implicit in the names
		// (we do not make any additional connections).
		// Ideally, though,there should be one memory manager and several memories, no? 
		KalrayMemory km1 = new KalrayMemory("m1", m1);
		KalrayMemory km2 = new KalrayMemory("m2", m2);
		KalrayMemory km3 = new KalrayMemory("m3", m3);
		KalrayMemory km4 = new KalrayMemory("m4", m4);
		//now, this resource manager should have a link to the memory resource (where the data is created)
		DataMemoryManager dm1 = new DataMemoryManager("dm1", 1); m1.setDataMemoryManager(dm1);
		DataMemoryManager dm2 = new DataMemoryManager("dm2", 1); m2.setDataMemoryManager(dm2);
		DataMemoryManager dm3 = new DataMemoryManager("dm3", 1); m3.setDataMemoryManager(dm3);
		DataMemoryManager dm4 = new DataMemoryManager("dm4", 1); m4.setDataMemoryManager(dm4);
		
		
		VirtualResourceManager L1 = new VirtualResourceManager("L1");
		VirtualResourceManager R1 = new VirtualResourceManager("R1");
		KalrayMemoryBank L2 = new KalrayMemoryBank("L2");
		KalrayMemoryBank R2 = new KalrayMemoryBank("R2");
		
		BoundedResourceManager b12L = new BoundedResourceManager("b12L", 1);
		BoundedResourceManager b34L = new BoundedResourceManager("b34L", 1);
		BoundedResourceManager b12R = new BoundedResourceManager("b12R", 1);
		BoundedResourceManager b34R = new BoundedResourceManager("b34R", 1);
		
		KalrayData D13 = new KalrayData("D13");
		KalrayData D24 = new KalrayData("D24");
		KalrayData D53 = new KalrayData("D53");
		KalrayData D34 = new KalrayData("D34");
		
		// TASKS - COMPONENTS
		
		String request = "p=1 & m=1";
		KalrayTask T1 = new KalrayTask("T1", request, "D13"); T1.setRequiredData("-1"); 
		KalrayTask T2 = new KalrayTask("T2", request, "D24"); T2.setRequiredData("-1"); // this is done in order to have a list of resources to be released
		KalrayTask T3 = new KalrayTask("T3", "p=1 & m=1 & D13=13 & D53=53", "D34");  T3.setRequiredData("D13");  T3.setRequiredData("D53"); T3.setRequiredData("-1");
		KalrayTask T4 = new KalrayTask("T4", "p=1 & m=1 & D24=24 & D34=34", ""); T4.setRequiredData("D24");  T4.setRequiredData("D34"); T4.setRequiredData("-1");
		KalrayTask T5 = new KalrayTask("T5", request, "D53"); T5.setRequiredData("-1");
		
		BIPActor actor1 = engine.register(T1, "task1", true); 
		BIPActor actor2 = engine.register(T2, "task2", true); 
		BIPActor actor3 = engine.register(T3, "task3", true); 
		BIPActor actor4 = engine.register(T4, "task4", true); 
		BIPActor actor5 = engine.register(T5, "task5", true); 
		BIPActor actorm1 = engine.register(km1, "memory1", true); 
		BIPActor actorm2 = engine.register(km2, "memory2", true); 
		BIPActor actorm3 = engine.register(km3, "memory3", true); 
		BIPActor actorm4 = engine.register(km4, "memory4", true); 
		BIPActor actord1 = engine.register(D13, "data13", true); 
		BIPActor actord2 = engine.register(D24, "data24", true); 
		BIPActor actord3 = engine.register(D53, "data53", true); 
		BIPActor actord4 = engine.register(D34, "data34", true); 
		BIPActor allocatorActor = engine.register(alloc, "allocator", true); 
		
		alloc.addResource(p);alloc.addResource(p1);alloc.addResource(p2);alloc.addResource(p3);alloc.addResource(p4);
		alloc.addResource(m);alloc.addResource(m1);alloc.addResource(m2);alloc.addResource(m3);alloc.addResource(m4);
		alloc.addResource(dm1);alloc.addResource(dm2);alloc.addResource(dm3);alloc.addResource(dm4);
		alloc.addResource(R1);alloc.addResource(L1);alloc.addResource(R2);alloc.addResource(L2);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		alloc.addResource(D13);alloc.addResource(D24);alloc.addResource(D53);alloc.addResource(D34);
		
		engine.start();
		engine.execute();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void procMemBusTest() throws RecognitionException, IOException, DNetException
	{
		/*
		 * This test presents a very simple use-case when there are three resources:
		 * Process + Memeory and Bus, which depends on them.
		 */
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				 synchron(ComponentNeedingResource.class, "getResource").to(AllocatorImpl.class,
						 "request");
				 synchron(ComponentNeedingResource.class, "release").to(AllocatorImpl.class,
						 "release");
				 synchron(ComponentNeedingResource.class, "process").to(AllocatorImpl.class,
						 "provideResource");
				 
				 data(ComponentNeedingResource.class, "utility").to(AllocatorImpl.class, "request");
				 data(ComponentNeedingResource.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
				 data(ComponentNeedingResource.class, "allocID").to(AllocatorImpl.class, "allocID");
				 data(AllocatorImpl.class, "resources").to(ComponentNeedingResource.class, "resourceArray");
				 data(AllocatorImpl.class, "allocID").to(ComponentNeedingResource.class, "allocID");
			}

		}.build();

		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		String dnetSpec = "src/test/resources/dnet.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec); 
		
		ComponentNeedingResource aComp = new ComponentNeedingResource(128);
		ComponentNeedingResource bComp = new ComponentNeedingResource(100);

		// TODO fix this test
		// something very strange happens. 
		// when there is only one component, the test runs fine.
		// however, when there are two components, the one asking 100 is ok
		// but for 128 the model, even though correct, does not give the integer results. I HAVE NO IDEA WHY
		BIPActor actor1 = engine.register(aComp, "resourceNeeder1", true); 
		BIPActor actor2 = engine.register(bComp, "resourceNeeder2", true); 
		BIPActor allocatorActor = engine.register(alloc, "allocator", true); 
		ResourceProvider memory = new Memory(256);
		ResourceProvider processor = new Processor();
		ResourceProvider bus = new Bus(129);
		
		alloc.addResource(memory);
		alloc.addResource(processor);
		alloc.addResource(bus);

		engine.specifyGlue(bipGlue);

		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void simpleRouteTest() throws Exception
	{
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				 synchron(RouteUser.class, "askRoute").to(AllocatorImpl.class,
						 "request");
//				 synchron(RouteUser.class, "release").to(AllocatorImpl.class,
//						 "release");
				 synchron(RouteUser.class, "getRoute").to(AllocatorImpl.class,
						 "provideResource");
				 synchron(RouteUser.class, "initRoute").to(RouteResource.class,
						 "init");
				 synchron(RouteUser.class, "transfer").to(RouteResource.class,
						 "on");
//				 synchron(RouteUser.class, "release").to(RouteResource.class,
//						 "delete");

				port(RouteUser.class, "release").requires(RouteResource.class,
						"delete", AllocatorImpl.class, "release");
				port(AllocatorImpl.class, "release").requires(RouteUser.class, "release");
				port(RouteResource.class, "delete").requires(RouteUser.class, "release");
				port(RouteUser.class, "release").accepts(RouteResource.class,
						"delete", AllocatorImpl.class, "release");
				port(AllocatorImpl.class, "release").accepts(RouteResource.class,
						"delete", RouteUser.class, "release");
				port(RouteResource.class, "delete").accepts(RouteUser.class,
						"release", AllocatorImpl.class, "release"); 
				
				 data(RouteUser.class, "utility").to(AllocatorImpl.class, "request");
				 data(RouteUser.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
				 data(RouteUser.class, "allocID").to(AllocatorImpl.class, "allocID");
				 data(AllocatorImpl.class, "resources").to(RouteUser.class, "resourceArray");
				 data(AllocatorImpl.class, "allocID").to(RouteUser.class, "allocID");
				 data(RouteUser.class, "route").to(RouteResource.class, "id");
				 data(RouteUser.class, "inPath").to(RouteResource.class, "routeIn");
				 data(RouteUser.class, "outPath").to(RouteResource.class, "routeOut");
			}

		}.build();

		// bipGlue.toXML(System.out);
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		String dnetSpec = "src/test/resources/simpleRouteDnet.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec); 
		
		RouteUser routeUser1 = new RouteUser();

		BIPActor actor1 = engine.register(routeUser1, "routeUser1", true); 
		BIPActor allocatorActor = engine.register(alloc, "allocator", true); 

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);
		ArrayList<RouteResource> routes = new ArrayList<RouteResource>();

		RouteResource route1 = new RouteResource("1", camelContext);
		//RouteResource route2 = new RouteResource("2", camelContext);
		routes.add(route1); //routes.add(route2);
		
		BIPActor route1Actor = engine.register(route1, "route1", true);
		//BIPActor route2Actor = engine.register(route2, "route2", true);
		route1.setExecutor(route1Actor);//route2.setExecutor(route2Actor);
		
		ResourceProvider routeManager = new RouteManager(camelContext, routes);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(route1Actor);
		//final RoutePolicy routePolicy2 = createRoutePolicy(route2Actor);

		RouteBuilder builder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						.routePolicy(routePolicy1).to("file:outputfolder1");

//				from("file:inputfolder2?delete=true").routeId("2")
//						.routePolicy(routePolicy2).to("file:outputfolder2");
			}
			
		};
		
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		alloc.addResource(routeManager);

		engine.specifyGlue(bipGlue);

		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	
	
	@Test
	public void sortingTest() throws Exception
	{
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				// TODO create glue
			}

		}.build();

		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		//TODO create dnet
		String dnetSpec = "src/test/resources/sortDnet.txt";
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec); 
		
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);
		ArrayList<RouteResource> routes = new ArrayList<RouteResource>();

		RouteResource route1 = new RouteResource("1", camelContext);
		RouteResource route2 = new RouteResource("2", camelContext);
		RouteResource route3 = new RouteResource("3", camelContext);
		RouteResource route4 = new RouteResource("4", camelContext);
		routes.add(route1); routes.add(route2);
		routes.add(route3); routes.add(route4);
		
		BIPActor route1Actor = engine.register(route1, "1", true);
		BIPActor route2Actor = engine.register(route2, "2", true);
		BIPActor route3Actor = engine.register(route3, "3", true);
		BIPActor route4Actor = engine.register(route4, "4", true);
		
		RouteManager routeManager = new RouteManager(camelContext, routes);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(route1Actor);
		final RoutePolicy routePolicy2 = createRoutePolicy(route2Actor);
		final RoutePolicy routePolicy3 = createRoutePolicy(route3Actor);
		final RoutePolicy routePolicy4 = createRoutePolicy(route4Actor);

		RouteBuilder builder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1")
						.routePolicy(routePolicy1).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2")
						.routePolicy(routePolicy2).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3")
						.routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4")
						.routePolicy(routePolicy4).to("file:outputfolder4");
			}
			
		};
		
		try {
			camelContext.addRoutes(builder);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(200);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);


		BIPActor allocatorActor = engine.register(alloc, "allocator", true); 
		alloc.addResource(routeManager);

		engine.specifyGlue(bipGlue);

		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
	}
	
	private RoutePolicy createRoutePolicy(final BIPActor actor) {

		return new RoutePolicy() {

			public void onInit(Route route) {
			}

			public void onExchangeDone(Route route, Exchange exchange) {
				System.err.println("exchange done!!!");
				actor.inform("end");
			}

			public void onExchangeBegin(Route route, Exchange exchange) {
			}

			@Override
			public void onRemove(Route arg0) {
			}

			@Override
			public void onResume(Route arg0) {
			}

			@Override
			public void onStart(Route arg0) {
			}

			@Override
			public void onStop(Route arg0) {
			}

			@Override
			public void onSuspend(Route arg0) {
			}
		};

	}
}
