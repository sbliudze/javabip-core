package org.bip.api;

public interface Publishable extends Identifiable {

	/**
	 * It publishes BIPComponent in such a manner that it can be found directly or indirectly by BIP engine.
	 */
	void publish();

	/**
	 * It unpublishes BIPComponent so that is no longer visible to BIP engine.
	 */
	void unpublish();
}
