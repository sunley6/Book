package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.BookType;
import com.java456.entity.Menu;

import java.util.List;


public interface BookTypeDao extends JpaRepository<BookType,Integer>,JpaSpecificationExecutor< BookType>  {
	
	
	@Query(value="select * from t_a_book_type where id = ?1",nativeQuery = true)
	public BookType findId(Integer id);

	@Query(value="select name from t_a_book_type ",nativeQuery = true)
	public List<String>  findall();

	@Query(value="select * from t_a_book_type where name = ?1 ",nativeQuery = true)
    BookType findByName(String name);
}
