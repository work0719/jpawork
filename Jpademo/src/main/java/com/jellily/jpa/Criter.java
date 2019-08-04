package com.jellily.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Criter {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<People> c = cb.createQuery(People.class);
        Root<People> emp = c.from(People.class);
        c.select(emp)
                .where(cb.equal(emp.get("name"), "张三"));
//        People people=entityManager.createNamedQuery(c).getSingleResult();
        TypedQuery query = entityManager.createQuery(c);
        List<People> list = query.getResultList();
        System.out.println(list.size());
//      addAddress(entityManager);
//      deleteAddress(entityManager);
//        updateAddress(entityManager);
//      addUser(entityManager);
//      delPeople(entityManager);
//        addPeople(entityManager);
//        peopleByNameQuery(entityManager);

        // 6. 关闭EntityManager
        entityManager.close();
        // 7. 关闭EntityManagerFactory
        factory.close();
    }
}
