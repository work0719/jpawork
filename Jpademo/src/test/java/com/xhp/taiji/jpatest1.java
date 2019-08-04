package com.xhp.taiji;


import com.jellily.jpa.Class;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class jpatest1 {
    @Test
    public void test1(){
        EntityManagerFactory factory= Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em=factory.createEntityManager();
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        Class c=new Class();
        c.setPassword("234");
        c.setUsername("lisi");
        em.persist(c);
        tx.commit();
        em.close();
        factory.close();
    }
}
