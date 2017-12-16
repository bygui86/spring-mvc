package com.rabbitshop.springwebhandling.exceptions.resolvers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

/**
 * The second solution is to define an HandlerExceptionResolver – this will resolve any exception thrown by the application. It will also allow us to implement
 * a uniform exception handling mechanism in our REST API.
 *
 * Before going for a custom resolver, let’s go over the existing implementations.
 * 		1. ExceptionHandlerExceptionResolver
 * 			This resolver was introduced in Spring 3.1 and is enabled by default in the DispatcherServlet. This is actually the core component of how the
 * 			@ExceptionHandler mechanism presented earlier works.
 * 		2. DefaultHandlerExceptionResolver
 * 			This resolver was introduced in Spring 3.0 and is enabled by default in the DispatcherServlet. It is used to resolve standard Spring exceptions to
 * 			their corresponding HTTP Status Codes, namely Client error – 4xx and Server error – 5xx status codes. Here is the full list of the Spring Exceptions
 * 			it handles, and how these are mapped to status codes.
 * 			While it does set the Status Code of the Response properly, one limitation is that it doesn’t set anything to the body of the Response. And for a
 * 			REST API – the Status Code is really not enough information to present to the Client – the response has to have a body as well, to allow the application
 * 			to give additional information about the failure.
 * 			This can be solved by configuring View resolution and rendering error content through ModelAndView, but the solution is clearly not optimal – which is
 * 			why a better option has been made available with Spring 3.2 – we’ll talk about that in the latter part of this article.
 * 		3. ResponseStatusExceptionResolver
 * 			This resolver was also introduced in Spring 3.0 and is enabled by default in the DispatcherServlet. Its main responsibility is to use the @ResponseStatus
 * 			annotation available on custom exceptions and to map these exceptions to HTTP status codes.
 * 			Same as the DefaultHandlerExceptionResolver, this resolver is limited in the way it deals with the body of the response – it does map the Status Code on
 * 			the response, but the body is still null.
 *		4. SimpleMappingExceptionResolver and AnnotationMethodHandlerExceptionResolver
 *			The SimpleMappingExceptionResolver has been around for quite some time – it comes out of the older Spring MVC model and is not very relevant for a REST
 *			Service. It is used to map exception class names to view names.
 *			The AnnotationMethodHandlerExceptionResolver was introduced in Spring 3.0 to handle exceptions through the @ExceptionHandler annotation but has been
 *			deprecated by ExceptionHandlerExceptionResolver as of Spring 3.2.
 *		5. Custom HandlerExceptionResolver (this example)
 *
 * The combination of DefaultHandlerExceptionResolver and ResponseStatusExceptionResolver goes a long way towards providing a good error handling mechanism for a
 * Spring RESTful Service. The downside is – as mentioned before – no control over the body of the response.
 * Ideally, we’d like to be able to output either JSON or XML, depending on what format the client has asked for (via the Accept header).
 * This alone justifies creating a new, custom exception resolver
 *
 * This approach is a consistent and easily configurable mechanism for the error handling of a Spring REST Service. It does however have limitations: it’s interacting with the
 * low-level HtttpServletResponse and it fits into the old MVC model which uses ModelAndView – so there’s still room for improvement.
 */
// @Component
@Deprecated
public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {
	
	@Override
	protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {

		try {
			if (ex instanceof IllegalArgumentException) {
				return handleIllegalArgument((IllegalArgumentException) ex, request, response);
			}
			// ...
		} catch (final Exception handlerException) {
			logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
		}
		return null;
	}
	
	/**
	 * One detail to notice here is the Request itself is available, so the application can consider the value of the Accept header sent by the client. For example,
	 * if the client asks for application/json then, in the case of an error condition, the application should still return a response body encoded with application/json.
	 *
	 * The other important implementation detail is that a ModelAndView is returned – this is the body of the response and it will allow the application to set whatever is
	 * necessary on it.
	 *
	 * @param ex
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	private ModelAndView handleIllegalArgument(final IllegalArgumentException ex, final HttpServletRequest request, final HttpServletResponse response) throws IOException {

		response.sendError(HttpServletResponse.SC_CONFLICT);
		final String accept = request.getHeader(HttpHeaders.ACCEPT);
		// ...
		return new ModelAndView();
	}

}
