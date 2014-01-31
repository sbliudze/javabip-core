package org.bip.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExecutableBehaviour extends Behaviour {
	public String getCurrentState();

	public void execute(String portId);

	public void execute(String portID, Map<String, ?> data);

	public void executeInternal(Map<String, Boolean> guardToValue);

	public boolean hasTransitionFromCurrentState(String portID);

	public Map<String, Method> getDataOutMapping();

	public List<Boolean> checkEnabledness(String port, List<Map<String, Object>> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	public Set<Port> getGloballyDisabledPorts(Map<String, Boolean> guardToValue);

	public boolean existEnabled(Port.Type transitionType, Map<String, Boolean> guardToValue);

	public Hashtable<String, Boolean> computeGuards();
}
