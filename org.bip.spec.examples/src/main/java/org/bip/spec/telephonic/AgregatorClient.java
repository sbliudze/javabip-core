package org.bip.spec.telephonic;

import org.bip.api.BIPActor;

public interface AgregatorClient extends ClientCaller {
	public void setExecutorRefs(BIPActor caller, BIPActor callee,
			BIPActor voice, BIPActor disc, BIPActor client, BIPActor checker);
}
