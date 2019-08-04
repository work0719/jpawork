package com.jellily.jpa;

import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Rollback(false)
//一对多 多对一
public class Test3 {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

//     addAuthor(entityManager);//增加
//        removeAuthor(entityManager);//删除


        // 6. 关闭EntityManager
        entityManager.close();
        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    private static void removeAuthor(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作

        Author a = new Author();
        a = entityManager.find(Author.class, 5);

        // 添加user到数据库，相当于hibernate的save();
        entityManager.remove(a);
        // 5. 提交事务
        transaction.commit();
    }

    private static void addAuthor(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4. 持久化操作
        Author a = new Author();
        a.setName("张4");

        Articles b = new Articles();
        b.setTitle("词典33");
        b.setAuthor(a);
        b.setContent("111111");
//        a.getArticleList().add(b);
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(a);
        entityManager.persist(b);
        // 5. 提交事务
        transaction.commit();
    }
}
