package org.bip.impl;

import org.bip.api.BIPComponent;
import org.bip.api.ComponentProvider;

public class ComponentProviderImpl implements ComponentProvider {

	private BIPComponent component;

	public ComponentProviderImpl() {
	}

	public ComponentProviderImpl(BIPComponent component) {
		this.component = component;
	}

	public BIPComponent getComponent() {
		return component;
	}

}
