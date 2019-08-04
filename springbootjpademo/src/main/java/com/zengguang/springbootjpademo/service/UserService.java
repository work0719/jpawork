package com.zengguang.springbootjpademo.service;

import com.zengguang.springbootjpademo.po.Dept;
import com.zengguang.springbootjpademo.po.User;
import com.zengguang.springbootjpademo.repository.DeptRepository;
import com.zengguang.springbootjpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    DeptRepository deptRepository;

    //增删改需要开启事务
    //查询不用开启
    @Transactional(propagation= Propagation.REQUIRED)
    public void saveUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    public void findAllUser(){
        List<User> userList=this.userRepository.findAll();
        System.out.println(userList.size());
    }

    public List<User> findByFlagAndNameOrderByUserIndexAsc(){
        List<User> userList=this.userRepository.findByFlagAndNameOrderByUserIndexAsc(1,"hahah");
        return userList;
    }

   public void findByFlagAndParentIsNullOrderByDeptIndexAsc(){
       List<User> userList=this.userRepository.findByFlagOrderByUserIndexAsc(1);
       System.out.println(userList.size());
   }
    //分页自己
    public void testPageQuery() {
        int page=1,size=3;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> list= userRepository.findAll(pageable);
        System.out.println(list);
        //userRepository.findByUserName("testName", pageable);
    }
    //分页demo
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<User> pageList;
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
            pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "userIndex");
            pageable = PageRequest.of(page, pageSize, sort);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<User> spec = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("name".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("password".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("flag".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };


            pageList = userRepository.findAll(spec,pageable);

        } else {
            Specification<User> spec = new Specification<User>() {
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = userRepository.findAll(spec,pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<User> list = pageList.getContent();

        map.put("depts", list);
        return map;
    }


}
