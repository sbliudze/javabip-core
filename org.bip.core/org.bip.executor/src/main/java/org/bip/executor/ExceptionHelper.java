package org.bip.executor;

import org.slf4j.Logger;

public class ExceptionHelper {

	public static void printExceptionTrace(Logger logger, Throwable e) {
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
		logger.error(errorMsg.toString());
	}
}
