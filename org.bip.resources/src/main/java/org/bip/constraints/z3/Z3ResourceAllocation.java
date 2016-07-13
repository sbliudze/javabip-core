package org.bip.constraints.z3;

import java.util.HashMap;

import org.bip.constraint.ResourceAllocation;
import org.bip.constraint.VariableExpression;
import org.bip.resources.ContextProvider;

import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;

public class Z3ResourceAllocation implements ResourceAllocation {

	private Model model;
	private HashMap<String, VariableExpression> resourceAmounts;
	Z3Factory f;
	private HashMap<String, Integer> resourceLableToAmount;

	public Z3ResourceAllocation(Model model, ContextProvider ctxProvider) {
		this.model = model;
		resourceAmounts = new HashMap<String, VariableExpression>();
		resourceLableToAmount = new HashMap<String, Integer>();
		f = new Z3Factory(ctxProvider);
		generateResourceAmounts();
	}

	// resourceAmounts contains placeName <-> z3 expression which is a sum of token variables
	// do I leave it like this or do I create another map for int values? 
	
	private void generateResourceAmounts() {
		for (FuncDecl func : model.getConstDecls()) {
			//System.err.println(func.getName() + " - "  + model.getConstInterp(func));
			String tokenName = func.getName().toString();
			String placeName = tokenName.substring(0, tokenName.indexOf('-'));
			
			String allocAmount = model.getConstInterp(func).toString();
			// we assume the value is int (which is not the case in general)
			int i_r = Integer.parseInt(allocAmount);
			IntExpr expr = (IntExpr) model.getConstInterp(func);
			
			// TODO this line makes it possible only for one item of each resource to be allocated which is maybe not what we want
			if (!resourceAmounts.containsKey(placeName)) {
			
				resourceAmounts.put(placeName, new Z3VariableExpression(expr)); 
				resourceLableToAmount.put(placeName, i_r);
			}
			else {
				//TODO put a sum of two expressions here
				VariableExpression prevValue = resourceAmounts.get(placeName);
				resourceAmounts.put(placeName, f.createAddition(prevValue,  new Z3VariableExpression(expr)));
				int prevIntValue = resourceLableToAmount.get(placeName);
				resourceLableToAmount.put(placeName, i_r+prevIntValue);
			}
		}
		//System.out.println(resourceAmounts);
		//System.out.println(resourceLableToAmount);
	}

	@Override
	public String resourceAmount(String resourceName) {
		return resourceLableToAmount.get(resourceName).toString();

	}
	
	@Override
	public HashMap<String,Integer> resourceAmounts() {
		return resourceLableToAmount;

	}

	@Override
	public boolean hasResource(String unit) {
		return resourceLableToAmount.containsKey(unit);
	}
}
