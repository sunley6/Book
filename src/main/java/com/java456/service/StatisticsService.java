package com.java456.service;

import com.java456.entity.Book;
import com.java456.entity.Statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    public List<Statistics> list(Map<String,Object> map, Integer page, Integer pageSize);

    long getTotal(Map<String, Object> map);
}
