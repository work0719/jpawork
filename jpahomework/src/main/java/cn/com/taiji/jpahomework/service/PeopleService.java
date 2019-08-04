package cn.com.taiji.jpahomework.service;

import cn.com.taiji.jpahomework.domain.People;
import cn.com.taiji.jpahomework.domain.PeopleRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeopleService {
    @Inject
    PeopleRepository peopleRepository;
    //添加
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePeople(People people) {
        this.peopleRepository.saveAndFlush(people);
    }
    //查寻全部信息
    @Transactional(propagation = Propagation.REQUIRED)
    public List<People> findAllPeople() {

        List<People> people1 = this.peopleRepository.findAll();

        System.out.println(people1.size()+"--------");
        return people1;
    }
    //查询所有未删除信息
    @Transactional(propagation = Propagation.REQUIRED)
    public List<People> findByFlag(){
        return peopleRepository.findByFlag(1);
    }
    //分页查询
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<People> pageList;
        //获取当前页
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        //获取页数
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
            pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        //获取排序方式
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Sort.Order> orders = new ArrayList();
        //指定排序方式，则按照给定的列名进行排序
        if (orderMaps != null) {
            for (Map b : orderMaps) {
                if (b.get("field") == null)
                    continue;
                String field = b.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = b.get("dir").toString();
                    //逆序排序
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, field));
                        //顺序排序
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        //若指定排序方式，则按照给定的列名进行顺序排序
        if (orders.size() > 0) {
            pageable =  PageRequest.of(page, pageSize, Sort.by(orders));
        }
        //若未指定排序方式，则按照id进行顺序排序
        else {
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = PageRequest.of(page, pageSize, sort);
        }
        //动态的获取查询条件
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<People> spec = new Specification<People>() {
                @Override
                public Predicate toPredicate(Root<People> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("peopleName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.get(field), value + "%"));
                            } else if ("peopleAge".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };


            pageList = peopleRepository.findAll(spec, pageable);

        } else {
            //无条件查询，只要flag=1即显示该条数据
            Specification<People> spec = new Specification<People>() {
                public Predicate toPredicate(Root<People> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = peopleRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<People> list = pageList.getContent();

        map.put("peoples", list);
        return map;
    }
    //删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(int i){
        peopleRepository.delete( i);
    }
    //更新
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(String name){
        peopleRepository.update(name);
    }

}