package com.rabbitshop.springwebhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * No code is required in this exception. All the work is done by the @ResponseStatus annotation.
 * In this case, when the exception is thrown, the controller that threw it returns a response with the response code 403 (Forbidden).
 * If necessary, you can also add a message in the annotation that will be returned along with the response.
 *
 * It is important to note that while it is technically possible to make an exception return any status code, in most cases it only makes
 * logical sense to use exceptions for error codes (4XX and 5XX).
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden API")
public class ForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = -622417222822655076L;
	
}
