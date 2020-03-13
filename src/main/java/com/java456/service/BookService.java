package com.java456.service;

import java.util.List;
import java.util.Map;
import com.java456.entity.Book;

public interface BookService {
	
	
	public void update(Book   book );
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Book> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);

	public List<Book> findByNameLike(String name);

	public List<Book> findByType(String TypeName);
	
	
	
}
