package org.bip.executor;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
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
import org.bip.constraint.ConstraintSolver;
import org.bip.constraint.DnetConstraint;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.bip.constraints.jacop.JacopConstraint;
import org.bip.constraints.jacop.JacopFactory;
import org.bip.constraints.jacop.JacopPlaceVariable;
import org.bip.constraints.jacop.JacopSolver;
import org.bip.constraints.z3.Z3Solver;
import org.bip.engine.factory.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.resources.AllocatorImpl;
import org.bip.resources.BoundedResourceManager;
import org.bip.resources.ConstraintNode;
import org.bip.resources.DNetException;
import org.bip.resources.InitialTransition;
import org.bip.resources.Place;
import org.bip.resources.Utility;
import org.bip.resources.VirtualResourceManager;
import org.bip.resources.grammar.dNetLexer;
import org.bip.resources.grammar.dNetParser;
import org.bip.spec.MemoryMonitor;
import org.bip.spec.resources.Bus;
import org.bip.spec.resources.ComponentNeedingResource;
import org.bip.spec.resources.DataMemoryManager;
import org.bip.spec.resources.KalrayBus;
import org.bip.spec.resources.KalrayData;
import org.bip.spec.resources.KalrayMemory;
import org.bip.spec.resources.KalrayMemoryBank;
import org.bip.spec.resources.KalrayResource;
import org.bip.spec.resources.KalrayTask;
import org.bip.spec.resources.KalrayTaskResource;
import org.bip.spec.resources.Memory;
import org.bip.spec.resources.MemoryManager;
import org.bip.spec.resources.Processor;
import org.bip.spec.resources.RouteManager;
import org.bip.spec.resources.RouteResource;
import org.bip.spec.resources.RouteUser;
import org.jacop.constraints.And;
import org.jacop.constraints.Constraint;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XplusYeqC;
import org.jacop.constraints.XplusYeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMax;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
		 * When a data is needed, the task eventually requires two memories: the one which stores data and the other which is used to process data.
		 */
		
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				
				//askResource -- request
				// getResource -- provide
				// read -- read
				// generate -- create -- create (task, data, memory)
				// delete -- delete
				// release -- release

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
						"create", KalrayData.class, "create", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");
				port(KalrayMemory.class, "create").accepts(KalrayData.class,
						"create", KalrayTask.class, "generate", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");
				port(KalrayData.class, "create").accepts(KalrayMemory.class,
						"create", KalrayTask.class, "generate", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");

				synchron(KalrayMemory.class, "deleteData").to(KalrayData.class,
						 "delete");
				
				
				// Kalray task ACCEPTS
				/*port(KalrayTask.class, "askResource").accepts(
						KalrayMemory.class, "deleteData", KalrayMemory.class, "create", KalrayMemory.class, "read", 
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayData.class, "create", KalrayData.class, "delete");
				port(KalrayTask.class, "getResource").accepts(
						KalrayMemory.class, "deleteData", KalrayMemory.class, "create", KalrayMemory.class, "read", 
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayData.class, "create", KalrayData.class, "delete");
				port(KalrayTask.class, "generate").accepts(
						KalrayMemory.class, "deleteData", KalrayMemory.class, "create", KalrayMemory.class, "read", 
						KalrayTask.class, "askResource", KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayData.class, "create", KalrayData.class, "delete");
				port(KalrayTask.class, "readData").accepts(
						KalrayMemory.class, "deleteData", KalrayMemory.class, "create", KalrayMemory.class, "read", 
						KalrayTask.class, "askResource", KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayData.class, "create", KalrayData.class, "delete");
				port(KalrayTask.class, "release").accepts(
						KalrayMemory.class, "deleteData", KalrayMemory.class, "create", KalrayMemory.class, "read", 
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayData.class, "create", KalrayData.class, "delete");
				// Allocator ACCEPTS
				port(AllocatorImpl.class, "request").accepts(
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayMemory.class, "deleteData", KalrayMemory.class, "read", KalrayMemory.class, "create",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				port(AllocatorImpl.class, "provideResource").accepts(
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayMemory.class, "deleteData", KalrayMemory.class, "read", KalrayMemory.class, "create",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				port(AllocatorImpl.class, "release").accepts(
						KalrayTask.class, "generate", KalrayTask.class, "readData",
						KalrayMemory.class, "deleteData", KalrayMemory.class, "read", KalrayMemory.class, "create",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				// Memory ACCEPTS
				port(KalrayMemory.class, "create").accepts(
						KalrayTask.class, "askResource",KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayMemory.class, "read", KalrayMemory.class, "create",KalrayMemory.class, "deleteData",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				port(KalrayMemory.class, "read").accepts(
						KalrayTask.class, "askResource",KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayMemory.class, "read", KalrayMemory.class, "create",KalrayMemory.class, "deleteData",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				port(KalrayMemory.class, "deleteData").accepts(
						KalrayTask.class, "askResource",KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayMemory.class, "read", KalrayMemory.class, "create",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				// Data ACCEPTS
				port(KalrayData.class, "delete").accepts(KalrayTask.class, "askResource",KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayMemory.class, "read", KalrayMemory.class, "create",KalrayMemory.class, "deleteData",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				port(KalrayData.class, "create").accepts(KalrayTask.class, "askResource",KalrayTask.class, "getResource", KalrayTask.class, "release", KalrayTask.class, "generate", KalrayTask.class, "readData",
						AllocatorImpl.class, "request", AllocatorImpl.class, "provideResource", AllocatorImpl.class, "release",
						KalrayMemory.class, "read", KalrayMemory.class, "create",KalrayMemory.class, "deleteData",
						KalrayData.class, "create", KalrayData.class, "delete"
						);
				*/
				 // --------- DATA -----------
				 //data(KalrayTask.class, "utility").to(AllocatorImpl.class, "request");
				 data(KalrayTask.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
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
		ConstraintSolver z3Solver = new Z3Solver();
		ConstraintSolver jacopSolver = new JacopSolver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, jacopSolver);
		
		//RESOURCES
		
		VirtualResourceManager p = new VirtualResourceManager("p");
		BoundedResourceManager p1 = new BoundedResourceManager("p1", 1);
		BoundedResourceManager p2 = new BoundedResourceManager("p2", 1);
		BoundedResourceManager p3 = new BoundedResourceManager("p3", 1);
		BoundedResourceManager p4 = new BoundedResourceManager("p4", 1);
		
		// there is one virtual memory, and four "physical" memories.
		// each physical memory has two managers: one for normal usage, and one for the usage when memory stores data
		// in the dnet each is represented by two different places
		VirtualResourceManager m = new VirtualResourceManager("m");
		MemoryManager m1 = new MemoryManager("m1", 1);
		MemoryManager m2 = new MemoryManager("m2", 1);
		MemoryManager m3 = new MemoryManager("m3", 1);
		MemoryManager m4 = new MemoryManager("m4", 1);
		// Ideally, though,there should be one memory manager and several memories, no? 
		KalrayMemory km1 = new KalrayMemory(m1);
		KalrayMemory km2 = new KalrayMemory(m2);
		KalrayMemory km3 = new KalrayMemory(m3);
		KalrayMemory km4 = new KalrayMemory(m4);
		//now, this memory with data resource manager should have a link to the memory resource manager 
		DataMemoryManager dm1 = new DataMemoryManager("dm1", 1); m1.setDataMemoryManager(dm1);
		DataMemoryManager dm2 = new DataMemoryManager("dm2", 1); m2.setDataMemoryManager(dm2);
		DataMemoryManager dm3 = new DataMemoryManager("dm3", 1); m3.setDataMemoryManager(dm3);
		DataMemoryManager dm4 = new DataMemoryManager("dm4", 1); m4.setDataMemoryManager(dm4);
		
		// two memory banks, left and right. 
		VirtualResourceManager L1 = new VirtualResourceManager("L1");
		VirtualResourceManager R1 = new VirtualResourceManager("R1");
		
		// four buses
		KalrayBus b12L = new KalrayBus("b12L");
		KalrayBus b34L = new KalrayBus("b34L");
		KalrayBus b12R = new KalrayBus("b12R");
		KalrayBus b34R = new KalrayBus("b34R");
		
		// kalray data - fixed for the particular data dependencies.
		// in order to change dependencies, change the data names here an in dnet spec,
		// as well as the task specification below
		KalrayData D13 = new KalrayData("D13");
		KalrayData D24 = new KalrayData("D24");
		KalrayData D53 = new KalrayData("D53");
		KalrayData D34 = new KalrayData("D34");
		
		// TASKS - COMPONENTS
		
		// each task is initialized with a request and the single data it creates.
		// in order for the task to have a list of resources to be released, we need to call setRequiredData method
		String request = "p=1 & m=1";
		KalrayTask T1 = new KalrayTask("T1", request, "D13"); T1.setRequiredData("-1"); 
		KalrayTask T2 = new KalrayTask("T2", request, "D24"); T2.setRequiredData("-1"); 
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
		alloc.addResource(R1);alloc.addResource(L1);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		alloc.addResource(D13);alloc.addResource(D24);alloc.addResource(D53);alloc.addResource(D34);
		
		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
		
		assertEquals("The number of data created is wrong", 4, m1.dataCreationCount() + m2.dataCreationCount() + m3.dataCreationCount() + m4.dataCreationCount());
		assertEquals("The number of data deleted is wrong", 4, m1.dataDeletionCount() + m2.dataDeletionCount() + m3.dataDeletionCount() + m4.dataDeletionCount());
		assertEquals("The number of data read in task is wrong", 4, T1.readCount() + T2.readCount() + T3.readCount() + T4.readCount()+ T5.readCount());
		assertEquals("The number of data created in task is wrong", 4, T1.createDataCount() + T2.createDataCount() + T3.createDataCount() + T4.createDataCount()+ T5.createDataCount());
			
	}
	
	@Test
	public void KalrayUtilityTest() throws RecognitionException, IOException,
			DNetException {
		/*
		 * There are several tasks asking for p and m resources of the Kalray architecture. 
		 * Some tasks additionally require the data generated by other tasks. 
		 * When a data is needed, the task eventually requires two memories: the one which stores data and the other which is used to process data.
		 */
		
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				
				//askResource -- request
				// getResource -- provide
				// read -- read
				// generate -- create -- create (task, data, memory)
				// delete -- delete
				// release -- release

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
						"create", KalrayData.class, "create", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");
				port(KalrayMemory.class, "create").accepts(KalrayData.class,
						"create", KalrayTask.class, "generate", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");
				port(KalrayData.class, "create").accepts(KalrayMemory.class,
						"create", KalrayTask.class, "generate", 
						KalrayMemory.class, "deleteData", KalrayData.class, "delete");

				synchron(KalrayMemory.class, "deleteData").to(KalrayData.class,
						 "delete");
				
				 // --------- DATA -----------
				 data(KalrayTask.class, "resourceUnit").to(AllocatorImpl.class, "resourceUnit");
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
		ConstraintSolver z3Solver = new Z3Solver();
		ConstraintSolver jacopSolver = new JacopSolver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, jacopSolver);
		
		//RESOURCES
		
		VirtualResourceManager p = new VirtualResourceManager("p");
		BoundedResourceManager p1 = new BoundedResourceManager("p1", 1);
		BoundedResourceManager p2 = new BoundedResourceManager("p2", 1);
		BoundedResourceManager p3 = new BoundedResourceManager("p3", 1);
		BoundedResourceManager p4 = new BoundedResourceManager("p4", 1);
		
		// there is one virtual memory, and four "physical" memories.
		// each physical memory has two managers: one for normal usage, and one for the usage when memory stores data
		// in the dnet each is represented by two different places
		VirtualResourceManager m = new VirtualResourceManager("m");
		MemoryManager m1 = new MemoryManager("m1", 1);
		MemoryManager m2 = new MemoryManager("m2", 1);
		MemoryManager m3 = new MemoryManager("m3", 1);
		MemoryManager m4 = new MemoryManager("m4", 1);
		// Ideally, though,there should be one memory manager and several memories, no? 
		KalrayMemory km1 = new KalrayMemory(m1);
		KalrayMemory km2 = new KalrayMemory(m2);
		KalrayMemory km3 = new KalrayMemory(m3);
		KalrayMemory km4 = new KalrayMemory(m4);
		//now, this memory with data resource manager should have a link to the memory resource manager 
		DataMemoryManager dm1 = new DataMemoryManager("dm1", 1); m1.setDataMemoryManager(dm1);
		DataMemoryManager dm2 = new DataMemoryManager("dm2", 1); m2.setDataMemoryManager(dm2);
		DataMemoryManager dm3 = new DataMemoryManager("dm3", 1); m3.setDataMemoryManager(dm3);
		DataMemoryManager dm4 = new DataMemoryManager("dm4", 1); m4.setDataMemoryManager(dm4);
		
		// two memory banks, left and right. 
		VirtualResourceManager L1 = new VirtualResourceManager("L1");
		VirtualResourceManager R1 = new VirtualResourceManager("R1");
		
		// four buses
		KalrayBus b12L = new KalrayBus("b12L");
		KalrayBus b34L = new KalrayBus("b34L");
		KalrayBus b12R = new KalrayBus("b12R");
		KalrayBus b34R = new KalrayBus("b34R");
		
		// kalray data - fixed for the particular data dependencies.
		// in order to change dependencies, change the data names here an in dnet spec,
		// as well as the task specification below
		KalrayData D13 = new KalrayData("D13");
		KalrayData D24 = new KalrayData("D24");
		KalrayData D53 = new KalrayData("D53");
		KalrayData D34 = new KalrayData("D34");
		
		// TASKS - COMPONENTS
		
		// each task is initialized with a request and the single data it creates.
		// in order for the task to have a list of resources to be released, we need to call setRequiredData method
		String request = "0, p=0 & m=0; 1, p=1 & m=1;";
		KalrayTask T1 = new KalrayTask("T1", request, "D13"); T1.setRequiredData("-1"); 
		KalrayTask T2 = new KalrayTask("T2", request, "D24"); T2.setRequiredData("-1"); 
		KalrayTask T3 = new KalrayTask("T3", "1, p=1 & m=1 & D13=13 & D53=53;", "D34");  T3.setRequiredData("D13");  T3.setRequiredData("D53"); T3.setRequiredData("-1");
		KalrayTask T4 = new KalrayTask("T4", "1,p=1 & m=1 & D24=24 & D34=34;", ""); T4.setRequiredData("D24");  T4.setRequiredData("D34"); T4.setRequiredData("-1");
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
		alloc.addResource(R1);alloc.addResource(L1);
		alloc.addResource(b12L);alloc.addResource(b34L);alloc.addResource(b12R);alloc.addResource(b34R);
		alloc.addResource(D13);alloc.addResource(D24);alloc.addResource(D53);alloc.addResource(D34);
		
		engine.start();
		engine.execute();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		engine.stop();
		engineFactory.destroy(engine);
		
		assertEquals("The number of data created is wrong", 4, m1.dataCreationCount() + m2.dataCreationCount() + m3.dataCreationCount() + m4.dataCreationCount());
		assertEquals("The number of data deleted is wrong", 4, m1.dataDeletionCount() + m2.dataDeletionCount() + m3.dataDeletionCount() + m4.dataDeletionCount());
		assertEquals("The number of data read in task is wrong", 4, T1.readCount() + T2.readCount() + T3.readCount() + T4.readCount()+ T5.readCount());
		assertEquals("The number of data created in task is wrong", 4, T1.createDataCount() + T2.createDataCount() + T3.createDataCount() + T4.createDataCount()+ T5.createDataCount());
			
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
		ConstraintSolver z3Solver = new Z3Solver();
		AllocatorImpl alloc = new AllocatorImpl(dnetSpec, z3Solver);
		
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
	
	@Test
	public void costUtilityTest() throws RecognitionException, DNetException {
		String utility = "0, p=0 & m=0; 5, p=1 & m=1;";
		String costP = "0, p=0; 1, p=1;";
		String costM = "0, m=0; 1, m=1;";
		
		ArrayList<IntVar> vars = new ArrayList<IntVar>();
		store = new Store();
		JacopFactory f = new JacopFactory(store, vars);
		Search<IntVar> search;
		
		dNetLexer lexer = new dNetLexer(new ANTLRInputStream(utility));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		dNetParser parser = new dNetParser(tokens);
		parser.utility();
		Utility u = parser.utility;
		IntVar uVar = new IntVar(store, "costU", 0, 1000);
		//vars.add(uVar);
		
		dNetLexer lexer1 = new dNetLexer(new ANTLRInputStream(costP));
		CommonTokenStream tokens1 = new CommonTokenStream(lexer1);
		dNetParser parser1 = new dNetParser(tokens1);
		parser1.utility();
		Utility u1 = parser1.utility;
		IntVar pVar = new IntVar(store, "costp", 0, 1000);
		//vars.add(pVar);
		
		dNetLexer lexerM = new dNetLexer(new ANTLRInputStream(costM));
		CommonTokenStream tokensM = new CommonTokenStream(lexerM);
		dNetParser parserM = new dNetParser(tokensM);
		parserM.utility();
		Utility uM = parserM.utility;
		IntVar mVar = new IntVar(store, "costM", 0, 1000);
		//vars.add(mVar);
				
		System.out.println(u);
		System.out.println(u1);
		System.out.println(uM);
		

		Map<String, VariableExpression> nameToVariable = new HashMap<String, VariableExpression>();
		JacopPlaceVariable pVariable = (JacopPlaceVariable) f.createVariable("p" + "-*");
		nameToVariable.put("p", pVariable);
		JacopPlaceVariable mVariable = (JacopPlaceVariable) f.createVariable("m" + "-*");
		nameToVariable.put("m", mVariable);
		
		// sum all the cost variables to get the big sum
		ArrayList<PlaceVariable> costs = new ArrayList<PlaceVariable>();
		costs.add(new JacopPlaceVariable(mVar)); costs.add(new JacopPlaceVariable(pVar));  
		JacopPlaceVariable sumCost = (JacopPlaceVariable) f.sumTokens(costs);
		IntVar bigCost = new IntVar(store, "Global", 0, 1000);
		store.impose(new XplusYeqZ(bigCost, sumCost.intVar(), uVar));
		
		
		store.impose(createOr(u, nameToVariable, f, uVar));
		store.impose(createOr(u1, nameToVariable, f, pVar));
		store.impose(createOr(uM, nameToVariable, f, mVar));
		
		vars.add(pVariable.intVar());vars.add(mVariable.intVar());
		
		SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(vars.toArray(new IntVar[1]), null,
				new IndomainMax<IntVar>());

		search = new DepthFirstSearch<IntVar>();
		IntVar negCost = new IntVar(store, "negCost", -1000, 1000);
		store.impose(new XplusYeqC(bigCost, negCost, 0));
		vars.add(negCost);
		
		search.getSolutionListener().searchAll(true);
		search.getSolutionListener().recordSolutions(true);
		boolean result = search.labeling(store, select, negCost);
		search.getSolutionListener().printAllSolutions();
		System.out.println("# " + search.getSolutionListener().solutionsNo());
	}
	
	Store store;
	
	private PrimitiveConstraint createOr(Utility u, Map<String, VariableExpression> nameToVariable, JacopFactory f,
			IntVar cost) throws DNetException {
		// TODO can the same cost be there several times? suppose not...
		// TODO replace div with division!!!
		PrimitiveConstraint prev = null;
		for (Integer costValue : u.utility().keySet()) {
			PrimitiveConstraint costC = new XeqC(cost, costValue);
			ConstraintNode dc = u.utility().get(costValue);
			dc.addFactory(f);
			PrimitiveConstraint one = new And(costC, ((JacopConstraint) dc.evaluateN(nameToVariable)).cstr());
			if (prev == null) {
				prev = one;
				continue;
			}
			prev = new Or(prev, one);
		}
		return prev;
	}
}
