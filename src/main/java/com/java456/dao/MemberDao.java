package com.java456.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.java456.entity.Member;


public interface MemberDao extends JpaRepository<Member,Integer>,JpaSpecificationExecutor< Member> {
	
	@Query(value="select * from t_member where id = ?1",nativeQuery = true)
	public Member  findId(Integer id);
	
	public Member findByName(String name);  
	
	
}
