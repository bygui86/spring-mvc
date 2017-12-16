package com.rabbitshop.springwebhandling.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rabbitshop.springwebhandling.daos.BookCase;

import lombok.extern.slf4j.Slf4j;

/**
 * @RequestBody and @ResponseBody are annotations of the Spring MVC framework and can be used in a controller to implement smart object
 * serialization and deserialization. They help you avoid boilerplate code by extracting the logic of message conversion and making it
 * an aspect. Other than that they help you support multiple formats for a single REST resource without duplication of code.
 * If you annotate a method with @ResponseBody, Spring will try to convert its return value and write it to the HTTP response automatically.
 * If you annotate a methods parameter with @RequestBody, Spring will try to convert the content of the incoming request body to your
 * parameter object on the fly.
 */
@Slf4j
@Controller
@RequestMapping(value = "/bodies")
public class RequestResponseBodyController {

	private BookCase bookCase;

	@GetMapping(value = "/response")
	@ResponseBody
	public BookCase getBookCase() {

		log.debug("Get BookCase");

		return bookCase;
	}

	@PutMapping(value = "/request")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setBookCase(@RequestBody final BookCase bookCase) {

		log.debug("Set BookCase: " + bookCase);
		
		this.bookCase = bookCase;
	}

}
