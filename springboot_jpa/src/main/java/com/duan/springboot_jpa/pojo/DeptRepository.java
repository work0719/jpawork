package com.duan.springboot_jpa.pojo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ClassName: DeptRepository
 *
 * @Description: Dept工厂
 * @Author dph
 * @CreatDate ${date}
 */
public interface DeptRepository extends JpaRepository<Dept, Integer>,
        JpaSpecificationExecutor<Dept> {

    //根据条件查询
    List<Dept> findByFlagAndParentIsNullOrderByDeptIndexAsc(@Param("flag") int i);
}
