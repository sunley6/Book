package com.java456.service;

import com.java456.entity.BookType;

import java.util.List;

public interface BookTypeService {
	
	
	public void update(BookType  bookType);

	public BookType findByName(String name);

	List<String> findAll();
}
