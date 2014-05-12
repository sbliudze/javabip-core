package org.bip.executor;

import org.bip.api.BIPEngine;
import org.bip.exceptions.BIPException;

public class ExecutorImpl extends AbstractExecutor {

	private String id;
	
	
	public ExecutorImpl(String id, Object bipComponent) throws BIPException {
		super(bipComponent, true);
		this.id = id;
	}

	public ExecutorImpl(String id, Object bipComponent, boolean useSpec)
			throws BIPException {
		super(bipComponent, useSpec);
		this.id = id;
	}

	public ExecutorImpl(String id, Object bipComponent, boolean useSpec, BIPEngine engine)
			throws BIPException {
		super(bipComponent, useSpec);
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void step() {
		throw new BIPException("Not implemented in this version.");
	}

}
