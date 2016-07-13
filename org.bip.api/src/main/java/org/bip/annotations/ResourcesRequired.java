package org.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bip.api.ResourceType;


/**
 * It specifies/aggregates the resources for a given BIP transition.
 */	
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourcesRequired {
	/**
	 * The required resources of the BIP transition.
	 *
	 * @return the array containing the resources of the BIP transition.
	 */
	ResourceRequired[] value();
}
