package com.rabbitshop.springwebhandling.http.converters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.rabbitshop.springwebhandling.daos.Book;
import com.rabbitshop.springwebhandling.daos.BookCase;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Depending on your configuration, spring has a list of HttpMessageConverters registered in the background (for JSON/XML and so on).
 * A HttpMessageConverter's responsibility is to convert the request body to a specific class and back to the response body again,
 * depending on a predefined mime type. Every time an issued request is hitting a @RequestBody or @ResponseBody annotation spring
 * loops through all registered HttpMessageConverters seeking for the first that fits the given mime type and class and then uses
 * it for the actual conversion.
 */
@Slf4j
@NoArgsConstructor
public class BookCaseHttpMessageConverter extends AbstractHttpMessageConverter<BookCase> {
	
	public BookCaseHttpMessageConverter(final MediaType supportedMediaType) {

		super(supportedMediaType);

		log.debug("Create HTTP MessageConverter for type " + supportedMediaType.getType() + "/" + supportedMediaType.getSubtype());
	}
	
	public BookCaseHttpMessageConverter(final MediaType... supportedMediaTypes) {
		
		super(supportedMediaTypes);

		log.debug("Create HTTP MessageConverter for types: " + supportedMediaTypes.toString());
	}
	
	@Override
	protected boolean supports(final Class<?> clazz) {

		log.debug("Check if this converter support class: " + clazz.getName());
		
		return BookCase.class.equals(clazz);
	}
	
	@Override
	protected BookCase readInternal(final Class<? extends BookCase> clazz, final HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
		
		log.debug("Read HTTP RequestBody to find out Books to be added to BookCase"); // TODO set to debug after tests

		final CSVReader reader = new CSVReader(new InputStreamReader(httpInputMessage.getBody()));
		final List<String[]> rows = reader.readAll();
		final BookCase bookCase = new BookCase();
		for (final String[] row : rows) {
			bookCase.add(new Book(row[0], row[1]));
		}
		reader.close();
		return bookCase;
	}
	
	@Override
	protected void writeInternal(final BookCase books, final HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
		
		log.debug("Write out all book in BookCase to HTTP ResponseBody"); // TODO set to debug after tests

		final CSVWriter writer = new CSVWriter(new OutputStreamWriter(httpOutputMessage.getBody()));
		for (final Book book : books) {
			writer.writeNext(new String[] { book.getIsbn(), book.getTitle() });
		}
		writer.close();
	}
	
}
