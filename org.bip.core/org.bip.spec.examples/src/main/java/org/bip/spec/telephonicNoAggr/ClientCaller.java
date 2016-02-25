package org.bip.spec.telephonicNoAggr;

import org.bip.api.BIPActor;

public interface ClientCaller {
	public void setClientRefs(BIPActor client, int id);
	//public void setSyncRefs(BIPActor syncActor);
}
