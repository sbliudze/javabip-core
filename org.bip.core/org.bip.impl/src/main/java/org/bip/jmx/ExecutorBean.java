package org.bip.jmx;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

import org.bip.api.BIPEngine;
import org.bip.api.Executor;

public class ExecutorBean implements DynamicMBean {

	private Executor executor;

	public ExecutorBean(Executor executor) {
		this.executor = executor;
	}

	public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
		// if (attribute.equals("bipComponent")) {
		// return mbean.bipComponent;
		// }
		return null;
	}

	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		String name = attribute.getName();
		// if (name.equals("bipComponent")) {
		// mbean.bipComponent = attribute.getValue().toString();
		// }
		throw new AttributeNotFoundException(name);
	}

	public AttributeList getAttributes(String[] attributes) {
		return null;
	}

	public AttributeList setAttributes(AttributeList attributes) {
		return null;
	}

	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		if (actionName == null) {
			throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"), "Cannot invoke a null operation in Executor MBean");
		}
		if (actionName.equals("inform")) {
			executor.inform(params[0].toString());
			return null;
		} else if (actionName.equals("execute")) {
			executor.execute(params[0].toString());
			return null;
		} else if (actionName.equals("register")) {
			executor.register((BIPEngine) params[0]);
			return null;
		}
		throw new ReflectionException(new NoSuchMethodException(actionName));
	}

	public MBeanInfo getMBeanInfo() {
		MBeanOperationInfo[] operations = {
				new MBeanOperationInfo("inform", "Inform the executor of spontaneous event", new MBeanParameterInfo[] { new MBeanParameterInfo("portID", "java.lang.String",
						"The id of the port that is influenced") }, "void", MBeanOperationInfo.ACTION),
				new MBeanOperationInfo("execute", "Tell the executor to execute transition triggered by the port", new MBeanParameterInfo[] { new MBeanParameterInfo("portID", "java.lang.String",
						"The id of the port that is influenced") }, "void", MBeanOperationInfo.ACTION) };
		return new MBeanInfo(this.getClass().getName(), "OSGi Executor MBean", null, null, operations, null);
	}
}
