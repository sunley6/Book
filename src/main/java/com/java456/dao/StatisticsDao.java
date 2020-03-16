package com.java456.dao;


import com.java456.entity.Book;
import com.java456.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsDao  extends JpaRepository<Statistics,Integer>, JpaSpecificationExecutor<Statistics> {

    @Query(value="select * from t_book where id = ?1",nativeQuery = true)
    public Statistics  findId(Integer id);
}
