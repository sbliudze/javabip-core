package org.bip.api;

import java.util.Collection;

public interface Accepts {
	public Port getEffect();

	public void addCauses(Collection<Port> causes);

	public Collection<Port> getCauses();
}
