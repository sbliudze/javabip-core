package org.bip.api;

public interface Port extends PortBase , ComponentProvider  {

	/**
	 * It returns the type of the port.
	 * 
	 * @return the type
	 */
	public PortType getType();
	
}
