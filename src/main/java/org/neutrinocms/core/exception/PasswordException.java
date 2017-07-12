package org.neutrinocms.core.exception;

import java.util.List;

public class PasswordException extends MessageErrorException{

	private static final long serialVersionUID = 1L;
	
	public PasswordException(String message, List<String> errors) {
		super(message, errors);
	}

	@Override
	public String toString() {
		String r = "";
		for (String e : this.getErrors()) {
			r += e + System.getProperty("line.separator");
		}
		return r;
	}

	
	
	
	
	
	
}
