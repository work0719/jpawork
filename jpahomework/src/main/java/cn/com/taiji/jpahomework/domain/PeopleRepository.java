package cn.com.taiji.jpahomework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Integer>, JpaSpecificationExecutor<People> {
    //查询所有未删除people信息（flag=1）
    List<People> findByFlag(@Param("flag") int i);

    //删除（将flag置为0）
    @Modifying
    @Query("update People set flag=0 where id=:id")
    int delete(@Param("id") int i);

    //修改
    @Modifying
    @Query("update People set peopleName=:peopleName where id=2")
    int update(@Param("peopleName") String name);

}
