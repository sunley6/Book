package com.java456.service;

import com.java456.entity.Manager;
import com.java456.entity.User;

import java.util.List;
import java.util.Map;


public interface UserService {

	public Integer update(User user);

	/**
	 * @param map
	 * @param page  从0开始
	 * @param pageSize
	 */
	public List<User> list(Map<String, Object> map, Integer page, Integer pageSize);

	public Long getTotal(Map<String, Object> map);

	public User checklogin(String name,String pwd);
	
	
	
}
