package com.epsilon.exceptions;

/**
 * @deprecated This isn't really necessary
 */
@Deprecated
public class WrongVersionException extends Exception {

	private static final long serialVersionUID = 637783424848621421L;
	
	public WrongVersionException(String msg) {
		super(msg);
	}
}
