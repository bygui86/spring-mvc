package com.rabbitshop.springwebhandling.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitshop.springwebhandling.exceptions.ForbiddenException;

import lombok.extern.slf4j.Slf4j;

/**
 * This quick article will demonstrate a few ways to return custom HTTP status codes from Spring MVC controllers.
 *
 * This is often important in order to more clearly express the result of a request to a client and using the full rich semantics of the HTTP protocol.
 * For example, if something goes wrong with a request, sending a specific error code for each type of possible problem would allow the client to display
 * an appropriate error message to the user.
 *
 * Spring provides a few primary ways to return custom status codes from its Controller classes:
 * . using a ResponseEntity
 * . using the @ResponseStatus annotation on exception classes, and
 * . using the @ControllerAdvice and @ExceptionHandler annotations.
 * These options are not mutually exclusive; far from it, they can actually complement one another.
 */
@Slf4j
@Controller
@RequestMapping("/statuses")
class ResponseStatusController {
	
	/**
	 * Upon receiving a GET request, Spring will return a response with the 406 Code (Not Acceptable). We arbitrarily selected the specific response,
	 * we can return any HTTP status code
	 *
	 * Full list of HTTP status codes
	 * {@link https://en.wikipedia.org/wiki/List_of_HTTP_status_codes}
	 */
	@GetMapping(value = "/viaResponseEntity")
	@ResponseBody
	public ResponseEntity<?> sendViaResponseEntity() {

		log.debug("Get custom ResponseStatus via ResponseEntity");
		
		return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	/**
	 * Upon receiving a GET request, Spring will throw a custom ForbiddenException.
	 */
	@GetMapping(value = "/viaException")
	@ResponseBody
	public ResponseEntity<?> sendViaException() {

		log.debug("Get custom ResponseStatus via Exception");
		
		throw new ForbiddenException();
	}
	
}
