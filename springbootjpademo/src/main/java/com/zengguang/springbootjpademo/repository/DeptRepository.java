package com.zengguang.springbootjpademo.repository;

import com.zengguang.springbootjpademo.po.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeptRepository extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept> {
    List<Dept> findByFlagAndParentIsNullOrderByDeptIndexAsc(@Param("flag") int i);

}
