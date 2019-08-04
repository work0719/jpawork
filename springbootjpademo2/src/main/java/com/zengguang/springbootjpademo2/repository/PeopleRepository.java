package com.zengguang.springbootjpademo2.repository;

import com.zengguang.springbootjpademo2.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Integer>, JpaSpecificationExecutor<People> {
   //增加
   @Query(value = "insert into people(peopleName,peopleAge,birthday,peopleIndex) value(?1,?2,?3,?4)", nativeQuery = true)
   @Modifying
    int insertPeople(String peopleName,String peopleAge,int birthday,int peopleIndex);



    //删除（将flag置为0）
    @Modifying
    @Query("update People set flag=0 where id=:id")
    int delete(@Param("id") int i);

    //修改
    @Modifying
    @Query("update People set peopleName=:peopleName where id=2")
    int update(@Param("peopleName") String name);

    //查询所有未删除people信息（当flag=1的时候）
    List<People> findByFlag(@Param("flag") int i);



}
