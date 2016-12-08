/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.javabip.executor;

import org.slf4j.Logger;

/**
 * Helper class to print out exceptions and their trace.
 * 
 * @author Alina Zolotukhina
 * 
 */
public class ExceptionHelper {

	public static void printExceptionTrace(Logger logger, Throwable e) {
		logger.error(getStringErrorMessage(e));
	}

	public static void printExceptionTrace(Logger logger, Throwable e, String message) {
		logger.error(message + "\n" + getStringErrorMessage(e));
	}

	private static String getStringErrorMessage(Throwable e) {
		StackTraceElement[] trace = e.getStackTrace();
		StringBuilder errorMsg = new StringBuilder();
		errorMsg.append(e.getClass());
		errorMsg.append(" : ");
		errorMsg.append(e.getMessage());
		errorMsg.append('\n');
		for (StackTraceElement element : trace) {

			errorMsg.append(element.toString());
			errorMsg.append('\n');
			errorMsg.append("              ");
		}
		return errorMsg.toString();
	}

}
