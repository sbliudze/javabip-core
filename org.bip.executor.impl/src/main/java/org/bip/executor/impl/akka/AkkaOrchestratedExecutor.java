package org.bip.executor.impl.akka;

import org.bip.api.OrchestratedExecutor;

public interface AkkaOrchestratedExecutor extends OrchestratedExecutor {
	
	void init();
	
	void destroy();

}
