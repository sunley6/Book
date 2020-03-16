package com.java456.dao;


import com.java456.entity.Manager;
import com.java456.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDao extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

    @Query(value="select * from t_b_user where name = ?1 and pwd=?2",nativeQuery = true)
    public User checkLogin(@Param(value = "name") String name,@Param(value = "pwd") String pwd);

    @Query(value="select * from t_b_user where id = ?1",nativeQuery = true)
	public User findId(Integer id);

    @Query(value="select * from t_b_user where phone = ?1",nativeQuery = true)
    public User findByPhone(String phone);
	
	public User findByName(String name);
	
	
}
