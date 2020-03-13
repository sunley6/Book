package com.java456.service;

import com.java456.entity.Book;
import com.java456.entity.BookBorrow;

import java.util.List;
import java.util.Map;

public interface BookBorrowService {

    public List<BookBorrow> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);

    List<BookBorrow> findByUid(Integer id);

    List<BookBorrow> findAll();
}
