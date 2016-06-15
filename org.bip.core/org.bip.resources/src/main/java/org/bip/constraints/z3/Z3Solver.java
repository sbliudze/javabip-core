package org.bip.constraints.z3;

import java.util.ArrayList;
import java.util.HashMap;

import org.bip.constraint.ConstraintSolver;
import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.ResourceAllocation;
import org.bip.resources.ContextProvider;
import org.bip.resources.Place;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

public class Z3Solver implements ConstraintSolver, ContextProvider {

	private Context context;
	private Solver solver;
	private Model model;
	private ExpressionCreator factory;
	private ResourceAllocation allocation;
	
	// a map: dnet place <-> list of variables contained in that place (should be equal in size to the list of tokens)
	public HashMap<Place, ArrayList<IntExpr>> placeVariables;
	
	// a map: resource name <-> value produced by the solver (is used to find out how much we should release upon spontaneous release event)
	// TODO it won't work this way if we have several components asking for the same resource - even if it is done in several times. or will I erase it? CHECK
	//private HashMap<String, Expr> resourceNameToGivenValue;
	private HashMap<Integer, HashMap<String, Expr>> resourceNameToGivenValueInAllocation;
	
	// a map: request string <-> model provided by the solver. is used in order not to solve the thing twice during the guard evaluation and the actual
	// allocation
	private HashMap<String, Model> requestToModel;
	
	public Z3Solver() {
		placeVariables = new HashMap<Place, ArrayList<IntExpr>>();
		resourceNameToGivenValueInAllocation = new HashMap<Integer, HashMap<String,Expr>>(); //new HashMap<String, Expr>();
		requestToModel = new HashMap<String, Model>();
		factory = new Z3Factory(this);
		
		newCycle();
	}
	
	/******************* End of Constructors *****************/
	
	/**************** Initializing functions *****************/

	void setContext(Context ctx) {
		this.context = ctx;
		solver = ctx.mkSolver();
	}

	// at the initialization phase of the dnet:
	// the context of the dnet is specified,
	// the variables and tokens are cleared and initialized with empty lists
	// in case of reinitialization (list of resources is not empty), the resource cost is stored in a local map
	// TODO the cost must be resend by resource, not asked by allocator
	// however, in this case it might be ok since it is the allocator that reinitialises itself


	// new context is created in the dnet
	// TODO find how to clear context so that not to recreate it every time.
	public void newCycle() {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);
	}

	/**************** End of Initializing functions *****************/
	
	@Override
	public void addConstraint(DnetConstraint constraint) {
		Z3BooleanConstraint expr = (Z3BooleanConstraint) constraint;
		solver.add(expr.constraint);

	}

	
	
	@Override
	public boolean isSolvable() {
		if (solver.check() == Status.SATISFIABLE) {
			this.model = solver.getModel();
			this.allocation = new Z3ResourceAllocation(model, this);
			//System.out.println(model.toString());
		}
		//else {
			//System.err.println("Allocation for " + requestString + " in component " + componentID + " IS NOT POSSIBLE.");
			//System.err.println("Constraints are " + solver);
		//}
		return solver.check() == Status.SATISFIABLE;
	}


	
	@Override
	public ResourceAllocation getAllocation() {
		return this.allocation;
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public ExpressionCreator expressionCreator() {
		return factory;
	}

}
