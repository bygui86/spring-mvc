package com.rabbitshop.springwebhandling.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rabbitshop.springwebhandling.daos.BookCase;
import com.rabbitshop.springwebhandling.http.converters.BookCaseHttpMessageConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GeneralWebConfiguration extends WebMvcConfigurerAdapter {

	private static final String REST_TEXT_MEDIA_TYPE = "text";
	private static final String REST_CSV_MEDIA_SUBTYPE = "csv";
	
	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> httpMessageConverters) {
		
		httpMessageConverters.add(
				getMessageConverterForClass(BookCase.class, REST_TEXT_MEDIA_TYPE, REST_CSV_MEDIA_SUBTYPE));
	}
	
	protected <T> AbstractHttpMessageConverter getMessageConverterForClass(final Class<T> clazz, final String restMediaType, final String restMediaSubtype) {
		
		log.debug("Get MessageConverter for class: " + clazz.getSimpleName());
		
		switch (clazz.getSimpleName()) {
			case "BookCase":
				log.debug("MessageConverter for class BookCase and MediaType " + restMediaType + "/" + restMediaSubtype + " added to HTTP message converters");
				return new BookCaseHttpMessageConverter(
						new MediaType(restMediaType, restMediaSubtype));
			default:
				// do nothing
				return null;
		}
	}
	
}
