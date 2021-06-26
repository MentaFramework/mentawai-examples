package org.menta.exception;

public class LoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Type type;

	public static enum Type {
		USERNAME_NOTFOUND,
		WRONG_PASSWORD;
	}
	
	public LoginException(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}
