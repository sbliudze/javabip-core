package org.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bip.api.ResourceType;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceRequired {

	/**
	 * Provides the name of the resource for future referencing.
	 *
	 * @return resource label.
	 */
	String label();
	
	/**
	 * Provides the type of the resource among given predefined.
	 *
	 * @return resource type.
	 */
	ResourceType type();
}
