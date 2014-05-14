package org.bip.exceptions;

public class BIPException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BIPException(String message)	{
		super(message);
	}
	
	public BIPException(Throwable cause) {
		super(cause);
	}
	
}
