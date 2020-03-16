package com.java456.dao;

import com.java456.entity.Book;
import com.java456.entity.BookBorrow;
import com.java456.entity.User;
import com.java456.service.BookBorrowService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookBorrowDao extends JpaRepository<BookBorrow,Integer>,JpaSpecificationExecutor< BookBorrow> {
	
	
	@Query(value="select * from t_book_borrow where book_id = ?1",nativeQuery = true)
	public BookBorrow  findId(Integer id);

	@Query(value="select * from t_book_borrow where id = ?1",nativeQuery = true)
	public BookBorrow  findId1(Integer id);
	
	@Query(value = "select * from t_book where name like %:name%",nativeQuery = true)
	public List<Book> findByNameLike(@Param(value = "name") String name);

	@Query(value = "SELECT tb.* FROM t_book tb JOIN t_a_book_type on tb.book_type_id=t_a_book_type.id and t_a_book_type.name=?1",nativeQuery = true)
	public List<Book> findByType(@Param(value = "bookType") String name);

	@Query(value="select * from t_book_borrow where user_id = ?1",nativeQuery = true)
     public List<BookBorrow> findByUId(Integer id);

	@Query(value="select * from t_book_borrow where book_id = ?1 and user_id =?2",nativeQuery = true)
	BookBorrow findIdUid(@Param(value = "id") Integer id,@Param(value = "user_id") int userId );
}
