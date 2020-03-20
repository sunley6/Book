package com.java456.service;

import com.java456.dao.BookDao;
import com.java456.dao.StatisticsDao;
import com.java456.entity.Book;
import com.java456.entity.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Service("StatisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsDao statisticsDao;


    @Override
    public List<Statistics> list(Map<String, Object> map, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Statistics> pages = statisticsDao.findAll(new Specification<Statistics>() {

            @Override
            public Predicate toPredicate(Root<Statistics> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                // 加入 等于
//                if (map.get("bookType") != null) {
//                    predicate.getExpressions().add(cb.equal(root.get("bookType"), map.get("bookType")));
//                }
//                if(map.get("q")!=null){
//                    predicate.getExpressions().add(cb.or(cb.like(root.get("author"),"%"+map.get("q")+"%"),
//                            cb.like(root.get("name"),"%"+map.get("q")+"%"),cb.like(root.get("press"),"%"+map.get("q")+"%")));
//                }
                return predicate;
            }
        }, pageable);
        return pages.getContent();
    }

    @Override
    public long getTotal(Map<String, Object> map) {
        Long count=statisticsDao.count(new Specification<Statistics>() {
            @Override
            public Predicate toPredicate(Root<Statistics> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();

                // 加入 等于
                if (map.get("bookType") != null) {
                    predicate.getExpressions().add(cb.equal(root.get("bookType"), map.get("bookType")));
                }
                if(map.get("q")!=null){
                    predicate.getExpressions().add(cb.or(cb.like(root.get("author"),"%"+map.get("q")+"%"),
                            cb.like(root.get("name"),"%"+map.get("q")+"%"),cb.like(root.get("press"),"%"+map.get("q")+"%")));
                }
                return predicate;
            }
        });
        System.out.println(count);
        return count;
    }
}
