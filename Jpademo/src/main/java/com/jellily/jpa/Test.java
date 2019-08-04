package com.jellily.jpa;

import com.jellily.jpa.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Test {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

//      addAddress(entityManager);
//      deleteAddress(entityManager);
//      updateAddress(entityManager);
//      addPeople(entityManager);
//      peopleByNameQuery(entityManager);
//        findAddress(entityManager);
//       referenceAddress(entityManager);


        // 6. 关闭EntityManager
        entityManager.close();
        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    private static void referenceAddress(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Address a = entityManager.getReference(Address.class, 1);
        log.println(a);
        transaction.commit();
    }

    private static void findAddress(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Address a = entityManager.find(Address.class, 1);
        log.println(a);
//        entityManager.persist();
        transaction.commit();
    }

    private static void peopleByNameQuery(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        List<People> list = new ArrayList<>();

        // 添加user到数据库，相当于hibernate的save();
        list = entityManager.createNamedQuery("people.all", People.class).getResultList();
//       list=entityManager.createNamedQuery("select * from people",People.class).getResultList();

        for (People p : list) {
            System.out.println("人员信息：" + p.toString());
        }
        // 5. 提交事务
        transaction.commit();
    }

    private static void addPeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        People a = new People();
        a.setId(1);
        a.setName("张三");
        a.setSex("男");
        // a.setAddress("大同");

        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(a);

        // 5. 提交事务
        transaction.commit();
    }

    private static void deleteAddress(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        Address a = new Address();
        a = entityManager.find(Address.class, 2);

        // 添加user到数据库，相当于hibernate的save();
        entityManager.remove(a);

        // 5. 提交事务
        transaction.commit();

    }

    private static void addAddress(EntityManager entityManager) {

        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        Address a = new Address();
        a.setAddress("北京");

        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(a);

        // 5. 提交事务
        transaction.commit();
    }

    private static void updateAddress(EntityManager entityManager) {

        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        Address a = new Address();
        a = entityManager.find(Address.class, 4);
        a.setAddress("太原");
        // 添加user到数据库，相当于hibernate的save();
//        entityManager.persist(a);
        entityManager.merge(a);
        // 5. 提交事务
        transaction.commit();
    }

    private static void guess(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        User a = new User();
        a.setUsername("李四");
        a.setPassword("123456");
        Authority b = new Authority();
        b.setName("王五");
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(a);
        entityManager.persist(b);

        // 5. 提交事务
        transaction.commit();
    }
}

