package com.zengguang.springbootjpademo.repository;

import com.zengguang.springbootjpademo.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * 查询
     */
    List<User> findByFlagAndNameOrderByUserIndexAsc(int flag,String name);

    List<User> findByFlagOrderByUserIndexAsc(int i);

    //分页
    Page<User> findAll(Pageable pageable);

}
