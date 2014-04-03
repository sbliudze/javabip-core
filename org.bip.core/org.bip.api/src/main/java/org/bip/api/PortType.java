package org.bip.api;

public enum PortType {
	
		/** This port is associated with enforceable transition. */
		enforceable,
		/** This port is associated with spontaneous transition. */
		spontaneous,
		/** This port is associated with internal transition that does not require any external trigger. */
		internal,
		/** This type is simply to make it possible to lazily instantiate the actual type of the port. */
		unknown	

}
