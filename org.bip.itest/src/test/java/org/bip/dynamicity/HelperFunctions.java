package org.bip.dynamicity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.bip.api.BIPComponent;
import org.bip.api.BIPGlue;
import org.bip.executor.ExecutorKernel;
import org.bip.glue.GlueBuilder;

public final class HelperFunctions {
	static BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;

	}

	static <T> BIPComponent createComponent(T instance, String id, boolean useSpec) {
		return new ExecutorKernel(instance, id, useSpec);
	}

}
