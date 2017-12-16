package com.rabbitshop.springwebhandling.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.rabbitshop.springwebhandling.exceptions.ForbiddenException;
import com.rabbitshop.springwebhandling.exceptions.PaymentRequiredException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/exceptions")
class ExceptionController {

	@GetMapping(value = "/forbidden")
	@ResponseBody
	public ResponseEntity<?> forbidden() {

		log.debug("Get Forbidden exception to test custom Response status");

		throw new ForbiddenException();
	}
	
	@GetMapping(value = "/payRequired")
	@ResponseBody
	public ResponseEntity<?> paymentRequired() {

		log.debug("Get PaymentRequired exception to test custom Exception handling");

		throw new PaymentRequiredException();
	}

	/**
	 * This approach has a major drawback – the @ExceptionHandler annotated method is only active for that particular Controller, not
	 * globally for the entire application. Of course adding this to every controller makes it not well suited for a general exception
	 * handling mechanism.
	 *
	 * The limitation is often worked around by having all Controllers extend a Base Controller class – however, this can be a problem
	 * for applications where, for whatever reasons, the Controllers cannot be made to extend from such a class. For example, the Controllers
	 * may already extend from another base class which may be in another jar or not directly modifiable, or may themselves not be directly
	 * modifiable.
	 */
	@ExceptionHandler({ PaymentRequiredException.class })
	public ResponseEntity<Object> handlePaymentRequiredException(final Exception exception, final WebRequest request) {
		
		log.error("Exception occurred: " + exception.getMessage(), exception);
		return new ResponseEntity<Object>("A payment is required to use this API", HttpStatus.PAYMENT_REQUIRED);
	}

}
