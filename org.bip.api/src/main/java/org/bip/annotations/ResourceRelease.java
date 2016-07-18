package org.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It annotates the transition with the labels of resources it releases.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceRelease {

	/**
	 * It returns the labels  of resources being released.
	 * 
	 * @return the array of labels.
	 */
	String[] resources() default {};
}
