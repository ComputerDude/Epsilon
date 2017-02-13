package com.epsilon.exceptions;

public class WrongVersionException extends Exception {

	private static final long serialVersionUID = 637783424848621421L;
	
	public WrongVersionException(String msg) {
		super(msg);
	}
}
