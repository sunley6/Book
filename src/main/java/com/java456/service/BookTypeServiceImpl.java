package com.java456.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java456.dao.BookTypeDao;
import com.java456.entity.BookType;

import java.util.List;


@Service("bookTypeService")
public class BookTypeServiceImpl implements BookTypeService {
	@Resource
	private BookTypeDao  bookTypeDao;
	
	
	@Override
	public void update(BookType bookType) {
		BookType origin = bookTypeDao.findId(bookType.getId());
		//把没有值的数据  换成原数据库的数据。
		bookType = repalce(bookType, origin);
		bookTypeDao.save(bookType);
	}

	@Override
	public BookType findByName(String name) {
		return bookTypeDao.findByName(name);
	}

	@Override
	public List<String> findAll() {
		return bookTypeDao.findall();
	}

	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public BookType repalce(BookType curr,BookType origin){
		
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getOrderNo()==null){
			curr.setOrderNo(origin.getOrderNo());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getUpdateDateTime()==null){
			curr.setUpdateDateTime(origin.getUpdateDateTime());
		}
		
		return curr;
	}
	
	
	
}
