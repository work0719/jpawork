package com.duan.springboot_jpa.service;

import com.duan.springboot_jpa.pojo.Dept;
import com.duan.springboot_jpa.pojo.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * ClassName: ${type_name}
 *
 * @Description: ${todo}
 * @Author dph
 * @CreatDate ${date}
 */
@Service
public class DeptService {

    @Autowired
    DeptRepository deptRepository;


    /**
     * 插入dept信息并保存
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDept(Dept dept) {
        this.deptRepository.saveAndFlush(dept);
    }

    /**
     * 查询dept全部信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void selectDept() {
        List<Dept> list = this.deptRepository.findAll();
        System.out.println(list.size());
    }


    /**
     * 查询(根据条件查询)
     */
    public Dept selectById(int id) {
        Optional<Dept> o = this.deptRepository.findById(id);
        Dept dept = o.get();
        return dept;
    }


    /**
     * 查询(根据条件查询)
     */
    public void selectByDeptIndex(){
        List<Dept> list= deptRepository.findByFlagAndParentIsNullOrderByDeptIndexAsc(1);
        System.out.println(list.size());
    }


    /**
     * Dept 数据分页查询
     *
     * @Description 功能描述
     * @Author wanghw
     * @CreatDate 2018年1月31日
     * @param searchParameters
     * @return 返回值Map map类型 total:总条数 depts:查询结果list集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<Dept> pageList;
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
            pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Order> orders = new ArrayList<Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Order(Sort.Direction.DESC, field));
                    } else {
                        orders.add(new Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "deptIndex");
            pageable = PageRequest.of(page, pageSize, sort);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<Dept> spec = new Specification<Dept>() {
                @Override
                public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("deptName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("deptType".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("deptUrl".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };


            pageList = deptRepository.findAll(spec, pageable);

        } else {
            Specification<Dept> spec = new Specification<Dept>() {
                public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = deptRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<Dept> list = pageList.getContent();

        map.put("depts", list);
        return map;
    }
}


















