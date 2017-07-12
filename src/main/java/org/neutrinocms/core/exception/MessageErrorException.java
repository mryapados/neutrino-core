package org.neutrinocms.core.exception;

import java.util.ArrayList;
import java.util.List;

public class MessageErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> errors;
	
	public MessageErrorException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}

	public MessageErrorException(String message, List<String> errors, Throwable e) {
		super(message, e);
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		if (this.errors == null) this.errors = new ArrayList<>();
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
