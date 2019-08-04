package com.example.demo.service;

import com.example.demo.domain.Dept;
import com.example.demo.domain.DeptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class DeptService {
    @Inject
    DeptRepository deptRepository;
    @Transactional(propagation= Propagation.REQUIRED)
    public void saveDept(Dept dept) {
        this.deptRepository.saveAndFlush(dept);
    }
    @Transactional(propagation= Propagation.REQUIRED)
    public void findAllDept(Dept dept) {
        this.deptRepository.findAll();

    }
    @Transactional(propagation= Propagation.REQUIRED)
    public void oneFindByFlagAndParentIsNullOrderByDeptIndexAsc() {
        List<Dept> dept = deptRepository.findByFlagAndParentIsNullOrderByDeptIndexAsc(1);
        System.out.println(dept.size());
    }

    /**
     *
     * @Description 功能描述
     * @Author wanghw
     * @CreatDate 2018年1月31日
     * @param searchParameters
     * @param
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
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, field));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable =  PageRequest.of (page, pageSize,  Sort.by(orders));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "deptIndex");
            pageable =  PageRequest.of(page, pageSize, sort);
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


    /**
     *
     * @Description 添加和编辑机构信息
     * @Author wanghw
     * @CreatDate 2017年5月5日
     * @param list
     * @param salt
     *            返回值void
     */
/*    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDept(Dept dept) {
        this.deptRepository.saveAndFlush(dept);
    }*/


    /**
     *
     * @Description 查询所有的机构
     * @Author wanghw
     * @CreatDate 2017年05月05日
     * @param salt
     * @return 返回值List<DeptDto>
     */
    /*@Transactional(propagation = Propagation.SUPPORTS)
    public List<Dept> findAllDepts() {
        List<Dept> depts = this.deptRepository.findAllDepts();

        System.out.println(depts.size()+"--------");
        return depts;
    }*/

    /**
     *
     * @Description 按用户Id查询机构信息
     * @Author chixue
     * @CreatDate 2017年5月2日
     * @UpdateUser wanghw
     * @UpdateDate 2018年1月12日
     * @param
     * @param
     * @return 返回值DeptDto
     */
    public Dept findDeptById(int id) {
//		Dept dept = this.deptRepository.findById(id);
//		this.deptRepository.findByFlagAndParentIsNullOrderByDeptIndexAsc(1);
        //Dept dept = this.deptRepository.findOne(id);
        Optional<Dept> O = this.deptRepository.findById(id);
        Dept dept = O.get();
        return dept;
    }

    /**
     *
     * @Description 查询树根
     * @Author wanghw
     * @CreatDate 2018年1月31日
     * @param
     * @return 返回值List<DeptDto>
     */
    /*public List<Dept> findByParentId() {
        List<Dept> depts = this.deptRepository.findRoots();
        return depts;
    }*/

    public List<Dept> AAA(Integer flag){
        return this.deptRepository.findByFlagAndParentIsNullOrderByDeptIndexAsc(flag);
    }

}
