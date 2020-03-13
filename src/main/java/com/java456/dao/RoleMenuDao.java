package com.java456.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.Role;
import com.java456.entity.RoleMenu;

public interface RoleMenuDao  extends JpaRepository<RoleMenu,Integer>,JpaSpecificationExecutor< RoleMenu>  {
	
	
	@Query(value="select * from t_role_menu where id = ?1",nativeQuery = true)
	public RoleMenu findId(Integer id);
	
	/**
	 * 设置权限 时需要 清空  之前的权限 。
	 * @param roleId
	 */
	@Modifying
	@Transactional
	@Query(value="delete  from t_role_menu where role_id = ?1",nativeQuery = true)
	public Integer deleteByRoleId(Integer roleId);
	
	/**
	 * 根据 roleId  menuId  查询 是否有内容
	 * @return
	 */
	@Query(value="select * from t_role_menu where role_id = ?1 and menu_id =?2",nativeQuery = true)
	public RoleMenu findByRoleIdAndMenuId(Integer roleId,Integer menuId);
	
	
	//根据role 拿 对应 的菜单 
	public  List<RoleMenu> findByRole(Role role);
	
	
}
