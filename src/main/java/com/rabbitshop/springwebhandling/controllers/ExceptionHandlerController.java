package com.rabbitshop.springwebhandling.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/moreExceptions")
class ExceptionHandlerController {

	@GetMapping(value = "/illegalArg")
	@ResponseBody
	public ResponseEntity<?> illegalArgument() {
		
		log.debug("Get IllegalArgument exception to test Exception handling to properly fill the ResponseBody");

		throw new IllegalArgumentException();
	}
	
	@GetMapping(value = "/illegalState")
	@ResponseBody
	public ResponseEntity<?> illegalState() {
		
		log.debug("Get IllegalState exception to test Exception handling to properly fill the ResponseBody");

		throw new IllegalStateException();
	}

}
