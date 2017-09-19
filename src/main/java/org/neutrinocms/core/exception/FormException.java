package org.neutrinocms.core.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class FormException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<ObjectError> errors;

	public FormException(String message, List<ObjectError> errors) {
		super(message);
		this.errors = errors;
	}
	
	public FormException(String message, List<ObjectError> errors, Throwable e) {
		super(message, e);
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}
	
}
