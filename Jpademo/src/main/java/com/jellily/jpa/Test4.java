package com.jellily.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

// 多对多
public class Test4 {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();


     add(entityManager);
//                remove(entityManager);

        // 6. 关闭EntityManager
        entityManager.close();
        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    private static void remove(EntityManager entityManager) {
        //        开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
       User user=new User();
       user=entityManager.find(User.class,1);

       entityManager.remove(user);
        // 5. 提交事务
        transaction.commit();
    }

    private static void add(EntityManager entityManager) {
//        开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        User a = new User();
        a.setUsername("zyn");
        a.setPassword("654321");

        Authority b = new Authority();
        b.setName("dph");
//        a.getAuthorityList().add(b);
//       b.getUserList().add(a);
        entityManager.persist(a);
        entityManager.persist(b);
        // 5. 提交事务
        transaction.commit();
    }


}

