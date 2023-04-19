package io.anywr.javaspringsecuritytest.exception;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerInterceptor {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgumentException(MethodArgumentNotValidException ex){
		
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
		.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EntityNotFoundException.class)
	public Map<String, String> handleTechnicalException(EntityNotFoundException e){
		Map<String, String> error = new HashMap<>();
		error.put("erreur", e.getMessage());

		return error;
	}

	@ExceptionHandler(value = { ServletException.class })
	public ResponseEntity servletException(ServletException e) {
		String message = e.getMessage();
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		if (message.equals("token_expired")) {
			httpStatus = HttpStatus.UNAUTHORIZED;
			message = "the token is expired and not valid anymore";
		}
		/*RestErrorResponse restErrorResponse = new RestErrorResponse(httpStatus, message);*/
		return ResponseEntity.status(httpStatus).body(message);
	}
	
}
