package com.java456.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.java456.entity.Manager;
import org.springframework.stereotype.Service;

import com.java456.dao.ManagerDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

	@Resource
	private ManagerDao managerDao;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Manager repalce(Manager curr, Manager origin){
		
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getPwd()==null){
			curr.setPwd(origin.getPwd());
		}
		if(curr.getTrueName()==null){
			curr.setTrueName(origin.getTrueName());
		}
		if(curr.getRemark()==null){
			curr.setRemark(origin.getRemark());
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
		if(curr.getRole()==null){
			curr.setRole(origin.getRole());
		}
		
		return curr;
	}
	
	
	@Override
	public Integer update(Manager manager) {
		Manager origin = managerDao.findId(manager.getId());
		//把没有值的数据  换成原数据库的数据。
		manager = repalce(manager, origin);
		
		managerDao.save(manager);
		return 1;
	}
	
	@Override
	public List<Manager> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable=new PageRequest(page, pageSize, Sort.Direction.DESC,"orderNo");
		Page<Manager> list = managerDao.findAll(pageable);
		List<Manager> managers = list.getContent();//拿到list集合
		return managers;//拿到list集合
	}
	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long totaLong = 	managerDao.count();
		return totaLong;
	}
	
}
