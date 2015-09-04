package org.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It specifies the global request utility for several resources for a given BIP transition.
 */	
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceUtility {

	/**
	 * The request utility of required resources for the BIP transition.
	 *
	 * @return the string adhering to the constraint grammar.
	 */
	String utility();
}
