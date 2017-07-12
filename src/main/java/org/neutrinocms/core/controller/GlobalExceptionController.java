//package org.neutrinocms.core.controller;
//
//import java.io.IOException;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import fr.cedricsevestre.exception.FormException;
//
//@ControllerAdvice
//public class GlobalExceptionController {
//
//
////	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
////	public void authenticationCredentialsNotFoundExceptionHandler(AuthenticationCredentialsNotFoundException ex, HttpServletResponse response) {
////		response.setStatus(401);
////	}
////
//	@ExceptionHandler(FormException.class)
//	public void FormExceptionHandler(FormException ex, HttpServletResponse response) throws IOException {
//		System.out.println("FormExceptionHandler");
//		
//		response.setContentType("application/json");
//		ServletOutputStream out = response.getOutputStream();
//		ObjectMapper objectMapper = new ObjectMapper();
//
////		List<String> errors = new ArrayList<>();
////		for (ObjectError objectError : ex.getErrors()) {
////			errors.add(objectError.);
////		}
////		
//		
//		String json = objectMapper.writeValueAsString(ex.getErrors());
//		
//		
//		
//		
//		out.println(json);
//		
//		System.out.println(json);
//		
//		response.setStatus(401);
//	}
//	
//}
