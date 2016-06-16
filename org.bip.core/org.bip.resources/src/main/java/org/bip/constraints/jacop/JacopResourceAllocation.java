package org.bip.constraints.jacop;

import java.util.HashMap;

import org.bip.constraint.ResourceAllocation;
import org.jacop.core.Domain;
import org.jacop.core.IntVar;

public class JacopResourceAllocation implements ResourceAllocation {

	private HashMap<String, Integer> resources;

	public JacopResourceAllocation(IntVar[] variables, Domain[] solution) {
		resources = new HashMap<String, Integer>();
		generateResourceAmounts(variables, solution);
	}

	private void generateResourceAmounts(IntVar[] variables, Domain[] solution) {
		for (int i = 0; i < variables.length; i++) {
			if (!variables[i].id.contains("-")) {
				continue;
			}

			int k = variables[i].id.indexOf("-");
			String placeName = variables[i].id.substring(0, k);

			// we assume the value is int (which is not the case in general)
			int i_r = Integer.parseInt(solution[i].toString());

			if (!resources.containsKey(placeName)) {
				resources.put(placeName, i_r);
			} else {
				int prevIntValue = resources.get(placeName);
				resources.put(placeName, i_r + prevIntValue);
			}
		}
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
