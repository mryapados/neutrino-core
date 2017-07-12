package org.neutrinocms.core.exception;

public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ControllerException(Throwable e) {
		super(e);
	}
	
	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Throwable e) {
		super(message, e);
	}
}
