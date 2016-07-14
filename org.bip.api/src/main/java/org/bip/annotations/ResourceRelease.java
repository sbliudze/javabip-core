package org.bip.annotations;

public @interface ResourceRelease {

	/**
	 * It returns the labels  of resources being released.
	 * 
	 * @return the array of labels.
	 */
	String[] resources() default {};
}
