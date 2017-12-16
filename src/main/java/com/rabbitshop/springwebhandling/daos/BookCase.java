package com.rabbitshop.springwebhandling.daos;

import java.util.ArrayList;
import java.util.Collection;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookCase extends ArrayList<Book> {

	private static final long serialVersionUID = 898573153903910086L;
	
	public BookCase(final Collection<? extends Book> c) {
		
		super(c);
	}

}
