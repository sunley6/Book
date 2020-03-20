package com.java456.dao;


import com.java456.entity.Book;
import com.java456.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsDao  extends JpaRepository<Statistics,Integer>, JpaSpecificationExecutor<Statistics> {

    @Query(value="select * from t_book_statistics where bianhao = ?1",nativeQuery = true)
    public Statistics  findId(String id);

    @Query(value="select MAX(id) from t_book_statistics ",nativeQuery = true)
    public int  findMaxId();
}
