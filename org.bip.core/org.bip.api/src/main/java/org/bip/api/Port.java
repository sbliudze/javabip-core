package org.bip.api;

public interface Port extends PortBase , ComponentProvider  {

	/**
	 * It returns the type of the port.
	 * 
	 * @return the type
	 */
	public Type getType();
	
	// TODO, There are no internal ports yet still we have an internal type within Type enum within Port class. Transition specificities are
	// leaking into Port interface.

	/**
	 * The Enum Type to encode different port/transition types within a BIP component.
	 */
	public enum Type {

		/** This port is associated with enforceable transition. */
		enforceable,
		/** This port is associated with spontaneous transition. */
		spontaneous,
		/** This port is associated with internal transition that does not require any external trigger. */
		internal,
		/** This type is simply to make it possible to lazily instantiate the actual type of the port. */
		unknown,
	}
}
