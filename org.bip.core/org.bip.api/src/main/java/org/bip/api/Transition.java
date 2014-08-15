package org.bip.api;

import java.util.Collection;

public interface Transition {

	public String guard();

	public String name();

	public String source();

	public String target();

	public boolean hasGuard();

	public Collection<Guard> transitionGuards();
}
