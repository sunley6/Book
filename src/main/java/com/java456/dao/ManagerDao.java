package com.java456.dao;


import com.java456.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ManagerDao extends JpaRepository<Manager,Integer>,JpaSpecificationExecutor<Manager> {
	
	
	@Query(value="select * from t_a_manager where id = ?1",nativeQuery = true)
	public Manager findId(Integer id);
	
	public Manager findByName(String name);
	
	
}
