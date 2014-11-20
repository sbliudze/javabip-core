package org.bip.api;

import java.util.Map;

public interface BIPActor {
	/**
	 * It informs BIP component that a given spontaneous event associated with a port id has
	 * occurred.
	 * 
	 * @param portID
	 *            the port id specifying a spontaneous event that has occurred.
	 */
	void inform(String portID);

	/**
	 * It informs BIP component that a given spontaneous event associated with a port id has
	 * occurred.
	 * 
	 * @param portID
	 *            the port id specifying a spontaneous event that has occurred.
	 * @param data
	 *            the data provided with a spontaneous event.
	 */
	void inform(String portID, Map<String, Object> data);

}
