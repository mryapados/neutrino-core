package org.neutrinocms.core.exception;

public class JSPNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public JSPNotFoundException(Throwable e) {
		super(e);
	}
	
	public JSPNotFoundException(String message) {
		super(message);
	}
	
	public JSPNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
