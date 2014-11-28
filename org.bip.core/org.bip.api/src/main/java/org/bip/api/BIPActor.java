package org.bip.api;

public interface BIPActor {
	/**
	 * It informs BIP component that a given spontaneous event associated with a port id has
	 * occurred.
	 * 
	 * @param portID
	 *            the port id specifying a spontaneous event that has occurred.
	 */
	void inform(String portID);

}
