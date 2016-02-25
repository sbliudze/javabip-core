package org.bip.spec.telephonicNoAggr;

import org.bip.api.BIPActor;

public interface AgregatorClient extends ClientCaller {
	
	public void setExecutorRefs(BIPActor dialwaitSync, BIPActor voiceSync,
			BIPActor discSync, BIPActor client, BIPActor checker);
}
