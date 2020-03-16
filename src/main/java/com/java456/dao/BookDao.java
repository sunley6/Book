package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.Book;
import com.java456.entity.BookType;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Integer>,JpaSpecificationExecutor< Book> {
	
	
	@Query(value="select * from t_book where id = ?1",nativeQuery = true)
	public Book  findId(Integer id);

	
	@Query(value = "select * from t_book where name like %:name%",nativeQuery = true)
	public List<Book> findByNameLike(@Param(value = "name") String name);

	@Query(value = "SELECT tb.* FROM t_book tb JOIN t_a_book_type on tb.book_type_id=t_a_book_type.id and t_a_book_type.name=?1",nativeQuery = true)
	public List<Book> findByType(@Param(value = "bookType") String name);
}
