package com.java456.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.java456.dao.MenuDao;
import com.java456.dao.RoleDao;
import com.java456.dao.RoleMenuDao;
import com.java456.entity.Role;
import com.java456.entity.RoleMenu;
import com.java456.util.StringUtil;


@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	@Resource
	private MenuDao menuDao;
	@Resource
	private RoleMenuDao roleMenuDao;
	
	@Override
	public Integer update(Role role) {
		Role origin = roleDao.findId(role.getId());
		role = repalce(role, origin);
		roleDao.save(role);
		return 1;
	}
	
	@Override
	public List<Role> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, "orderNo");
		Page<Role> list = roleDao.findAll(pageable);
		return list.getContent();// 拿到list集合
	}
	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		return roleDao.count();
	}
	
	/**
	 * 可以回滚数据
	 */
	@Transactional
	@Override
	public Integer updateMenu(Integer roleId, String menuIds) {
		String[] idsStr = menuIds.split(",");
		RoleMenu roleMenu ;
		
		//删除之前的菜单  根据 角色id
		roleMenuDao.deleteByRoleId(roleId);
		//删除之前的菜单  根据 角色id
		
		//添加现在新的 （角色 对应的菜单 ）
		for (int i = 0; i < idsStr.length; i++) {
			if(StringUtil.isNotEmpty(idsStr[i])) {
				roleMenu = new RoleMenu();
				roleMenu.setRole(roleDao.findId(roleId));
				roleMenu.setMenu(menuDao.findId(Integer.parseInt(idsStr[i])));
				roleMenuDao.save(roleMenu);
			}
		}
		
		//修改角色 的更新 时间 
		Role role = roleDao.findId(roleId);
		role.setUpdateDateTime(new Date());
		roleDao.save(role);
		//修改角色 的更新 时间 
		
		return 1;
	}
	
	
	
	/**
	 * @param curr   当前更新的数据
	 * @param origin 源数据 以前的数据
	 * @return curr
	 */
	public Role repalce(Role curr, Role origin) {

		if (curr.getName() == null) {
			curr.setName(origin.getName());
		}
		if (curr.getOrderNo() == null) {
			curr.setOrderNo(origin.getOrderNo());
		}
		if (curr.getCreateDateTime() == null) {
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if (curr.getUpdateDateTime() == null) {
			curr.setUpdateDateTime(origin.getUpdateDateTime());
		}
		
		return curr;
	}

}
