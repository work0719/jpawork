package com.jellily.jpa;


import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.U;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

//jpql
public class Test2 {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

//        fingAll(entityManager);//全部查询
//        orderFind(entityManager);//排序查询
//        countFind(entityManager);//统计查询  单条返回
//        pageFind(entityManager);//分页查询
//        conditionFind(entityManager);//条件查询


        // 6. 关闭EntityManager
        entityManager.close();
        // 7. 关闭EntityManagerFactory
        factory.close();
    }

   /* private static void conditionFind(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql = " from  Address where address like ?";
        Query query=entityManager.createQuery(jpql);
        *//*Object result=query.getSingleResult();
        System.out.println(result);*//*
        //参数赋值
        query.setParameter(1,"马鞍山");
       *//* query.setFirstResult(0);
        query.setMaxResults(2);*//*
        List list= query.getResultList();
        for (Object obj:list){
            System.out.println(obj);
        }
        // 5. 提交事务
        transaction.commit();

    }

    private static void pageFind(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql = "from  Address";
        Query query=entityManager.createQuery(jpql);
        *//*Object result=query.getSingleResult();
        System.out.println(result);*//*
        //分页参数
        query.setFirstResult(0);
        query.setMaxResults(2);
       List list= query.getResultList();
        for (Object obj:list){
            System.out.println(obj);
        }
        // 5. 提交事务
        transaction.commit();
    }

    private static void countFind(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql = "select count(id) from  Address";
        Query query=entityManager.createQuery(jpql);
       Object result=query.getSingleResult();
        System.out.println(result);
        // 5. 提交事务
        transaction.commit();
    }

    private static void orderFind(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql = "from Address order by id desc";
        Query query=entityManager.createQuery(jpql);
        List list=query.getResultList();
        for (Object obj:list){
            System.out.println(obj);
        }
        // 5. 提交事务
        transaction.commit();
    }

    private static void fingAll(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql = "from Address";
      Query query=entityManager.createQuery(jpql);
   List list=query.getResultList();
   for (Object obj:list){
       System.out.println(obj);
   }
        // 5. 提交事务
        transaction.commit();
    }
*/
}


