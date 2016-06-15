package org.bip.constraints.jacop;

import java.util.ArrayList;
import java.util.HashMap;

import org.bip.constraint.ResourceAllocation;
import org.jacop.constraints.Constraint;
import org.jacop.core.Domain;
import org.jacop.core.IntVar;

public class JacopResourceAllocation implements ResourceAllocation {

	//private ArrayList<IntVar> vars;
	private HashMap<String, Integer> resources;


	public JacopResourceAllocation(IntVar[] variables, Domain[] solution) {
		resources = new HashMap<String, Integer>();
		//System.err.println("%%%%%%%%%%%%%%%");
		for (int i = 0; i < variables.length; i++) {
			if (variables[i].id.contains("-")) 
			{
				int k = variables[i].id.indexOf("-");
				resources.put(variables[i].id.substring(0, k), Integer.parseInt(solution[i].toString()));
				//System.err.print(variables[i].id + " : ");
				//System.err.println(solution[i]);
			}
		}
		//System.err.println("%%%%%%%%%%%%%%%");
	}

	@Override
	public String resourceAmount(String resource) {
		return resources.get(resource).toString();
	}

	@Override
	public boolean hasResource(String unit) {
		return resources.containsKey(unit);
	}

	@Override
	public HashMap<String, Integer> resourceAmounts() {
		return resources;
	}

}
