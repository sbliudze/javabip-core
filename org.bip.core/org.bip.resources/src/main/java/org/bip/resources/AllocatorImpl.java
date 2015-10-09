package org.bip.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.bip.api.Allocator;
import org.bip.api.ResourceProvider;
import org.bip.resources.grammar.constraintLexer;
import org.bip.resources.grammar.constraintParser;
import org.bip.resources.grammar.dNetLexer;
import org.bip.resources.grammar.dNetParser;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;

public class AllocatorImpl implements ContextProvider, Allocator {

	//later change to hashmap for resources of the same type?
	private ArrayList<ResourceProvider> resources;
	
	private ConstraintNode request;
	private DNet dnet;
	public HashMap<Place, ArrayList<Transition>> placeTokens;
	public HashMap<Place, ArrayList<IntExpr>> placeVariables;
	private HashMap<String, ConstraintNode> resourceToCost;
	private Context context;
	Solver solver;

	public AllocatorImpl() {
		placeTokens = new HashMap<Place, ArrayList<Transition>>();
		placeVariables = new HashMap<Place, ArrayList<IntExpr>>();
		resourceToCost = new HashMap<String, ConstraintNode>();
		resources = new ArrayList<ResourceProvider>();
	}
	
	void setContext(Context ctx) {
		this.context = ctx;
		solver = context.mkSolver();
	}
	
	private void initialiseTokensAndVariables() {
		for (Place place : dnet.places()) {
			placeTokens.put(place, new ArrayList<Transition>());
			placeVariables.put(place, new ArrayList<IntExpr>());
		}
	}

	public AllocatorImpl(String dNetPath) throws IOException, RecognitionException, DNetException {
		this();
		
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);

		FileInputStream stream = new FileInputStream(dNetPath);

		dNetLexer lexer = new dNetLexer(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		dNetParser parser = new dNetParser(tokens);
		parser.net();
		this.dnet = parser.net;
		this.dnet.addContext(ctx);
		initialiseTokensAndVariables();
	}
	
	public AllocatorImpl(Context ctx, String dNetPath) throws IOException, RecognitionException, DNetException {
		this();
		setContext(ctx);
		FileInputStream stream = new FileInputStream(dNetPath);

		dNetLexer lexer = new dNetLexer(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		dNetParser parser = new dNetParser(tokens);
		parser.net();
		this.dnet = parser.net;
		this.dnet.addContext(ctx);
		initialiseTokensAndVariables();
	}
	
	public AllocatorImpl(Context ctx, DNet net) {
		this();
		setContext(ctx);
		this.dnet = net;
		this.dnet.addContext(ctx);
		initialiseTokensAndVariables();
	}

	public void allocate() throws DNetException {
		for (BoolExpr constr : dnet.run(placeVariables, placeTokens)) {
			solver.add(constr);
		}
		addCost();
		solve();
	}

	private void solve() {
		for (BoolExpr expr : solver.getAssertions()) {
			System.out.println(expr.getSExpr());
		}
		System.out.println(solver.check());
		Model model = solver.getModel();
		for (Place place : placeVariables.keySet()) {
			if (placeVariables.get(place).size() > 0) {
				for (int i = 0; i < placeVariables.get(place).size(); i++) {
					System.out.println(placeVariables.get(place).get(i).getSExpr() + " " + model.evaluate(placeVariables.get(place).get(i), false));
				}
			}
		}
	}
	
	public void specifyRequest(String requestString) throws DNetException {
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(requestString));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		this.request = parser.constraint;
		this.request.setContextProvider(this);

		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		Map<String, ArithExpr> stringToConstraintVar = new HashMap<String, ArithExpr>();
		for (String placeName : resourcesRequested) {
			if (dnet.nameToPlace.containsKey(placeName)) {
				Place place = dnet.nameToPlace.get(placeName);
				place.addToken(new InitialTransition()); // probably we don't need this line
				// add a new initial token
				placeTokens.get(place).add(new InitialTransition());
				// add a new variable
				String variableName = createVariableName(place, "*");
				IntExpr var = createIntVariable(context, variableName);
				placeVariables.get(place).add(var);
				stringToConstraintVar.put(placeName, var);
			} else {
				throw new DNetException("The resource " + placeName + " does not belond to the space of resources described by places of the DNet.");
			}
		}
		solver.add(this.request.evaluate(stringToConstraintVar));
		System.err.println("Request: " + resourcesRequested);
		System.err.println("Tokens: " + placeTokens);
	}
	
// cost: each resource has its cost
	//cost is updated at every cycle and sent to the allocator
	
	public void addCost() throws DNetException{
		for (Place place : placeVariables.keySet()) {
			Map<String, ArithExpr> stringtoConstraintVar = new HashMap<String, ArithExpr>();
			if (placeVariables.get(place).size() > 0) {

				ArithExpr placeSum = placeVariables.get(place).get(0);

				for (int i = 1; i < placeVariables.get(place).size(); i++) {
					placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i));
				}
				stringtoConstraintVar.put(place.name(), placeSum);

				BoolExpr costExpr = resourceToCost.get(place.name()).evaluate(stringtoConstraintVar);
				solver.add(costExpr);
			}
		}

	}

	private String createVariableName(Place place, String transitionName) {
		return place.name() + "-" + transitionName;
	}

	private IntExpr createIntVariable(Context ctx, String name) {
		return (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
	}

	@Override
	public Context getContext() {
		return context;
	}

	private void specifyCost(String resource, String costString) {
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(costString));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		ConstraintNode cost = parser.constraint;
		cost.setContextProvider(this);
		resourceToCost.put(resource, cost);
	}

	@Override
	public void request() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResource(ResourceProvider resource) {
		resources.add(resource);
		this.specifyCost(resource.name(), resource.cost());
	}
}
