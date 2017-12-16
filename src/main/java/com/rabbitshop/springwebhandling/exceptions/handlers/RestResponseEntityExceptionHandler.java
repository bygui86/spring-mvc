package com.rabbitshop.springwebhandling.exceptions.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring 3.2 brings support for a global @ExceptionHandler with the new @ControllerAdvice annotation. This enables a mechanism that breaks
 * away from the older MVC model and makes use of ResponseEntity along with the type safety and flexibility of @ExceptionHandler.
 *
 * The new annotation allows the multiple scattered @ExceptionHandler from before to be consolidated into a single, global error handling component.
 *
 * The actual mechanism is extremely simple but also very flexible:
 * 		. it allows full control over the body of the response as well as the status code
 * 		. it allows mapping of several exceptions to the same method, to be handled together
 * 		. it makes good use of the newer RESTful ResposeEntity response
 *
 * One thing to keep in mind here is to match the exceptions declared with @ExceptionHandler with the exception used as the argument of the method.
 * If these don’t match, the compiler will not complain – no reason it should, and Spring will not complain either.
 * However, when the exception is actually thrown at runtime, the exception resolving mechanism will fail with:
 * 		java.lang.IllegalStateException: No suitable resolver for argument [0] [type=...]
 * 		HandlerMethod details: ...
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException exception, final WebRequest request) {

		// This should be application/exception/method specific
		final String responseBody = buildExceptionMsg(exception, request);
		log.error(responseBody);
		return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	protected String buildExceptionMsg(final RuntimeException exception, final WebRequest request) {

		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Exception occurred: " + exception.getClass());
		strBuilder.append("\n");
		strBuilder.append("Exception msg: " + exception.getMessage());
		strBuilder.append("\n");
		strBuilder.append("Request description: " + request.getDescription(false));
		
		return strBuilder.toString();
	}

}
