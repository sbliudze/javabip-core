/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package org.bip.api;

import java.util.List;
import java.util.Map;

public interface BIPComponent extends Identifiable {

	void execute(String portID);

	void inform(String portID);

	// TODO, make it possible to specify data for execution of spontaneous
	// transitions that requires data.
	// void inform(String portID, Map<String, ?> data);

	// Parameter clazz is provided only so the return argument can be of the
	// proper type.
	// The type is already available/provided within BIP spec.
	public <T> T getData(String name, Class<T> clazz);

	/**
	 * It makes it possible to query BIP Executor if a given port is enabled for given parameters.
	 * 
	 * @param port
	 * @param data
	 * @return
	 */
	public List<Boolean> checkEnabledness(Port port, List<Map<String, Object>> data);

	public void setData(String dataName, Object value);

}
