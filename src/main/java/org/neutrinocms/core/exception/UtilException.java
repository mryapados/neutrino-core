package org.neutrinocms.core.exception;

public class UtilException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UtilException(Throwable e) {
		super(e);
	}
	
	public UtilException(String message) {
		super(message);
	}
	
	public UtilException(String message, Throwable e) {
		super(message, e);
	}
}
