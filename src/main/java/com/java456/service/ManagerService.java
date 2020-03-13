package com.java456.service;

import java.util.List;
import java.util.Map;
import com.java456.entity.Manager;


public interface ManagerService {
	
	public Integer update(Manager manager);
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Manager> list(Map<String,Object> map, Integer page, Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
	
}
