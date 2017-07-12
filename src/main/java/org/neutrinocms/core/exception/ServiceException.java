package org.neutrinocms.core.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceException(Throwable e) {
		super(e);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable e) {
		super(message, e);
	}
}
