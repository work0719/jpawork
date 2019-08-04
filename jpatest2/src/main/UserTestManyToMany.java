package main;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserTestManyToMany {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");

        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

       // addUser(entityManager);

        //selectByJPQL(entityManager);

        findUsers(entityManager,"22","22");

        //findUsersByMultiSelect();




        // 6. 关闭EntityManager
        entityManager.close();

        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    private static void selectByJPQL(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //持久化操作
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> c = criteriaBuilder.createQuery(User.class);
        Root<User> user = c.from(User.class);
        c.select(user).where(criteriaBuilder.equal(user.get("username"), "111"));
        TypedQuery query=entityManager.createQuery(c);
        List<User> list =query.getResultList();
        System.out.println(list.size());

        // 5. 提交事务
        transaction.commit();

    }

    private static void addUser(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Authority> authorityList=new ArrayList();

        Authority authority=new Authority();
        authority.setName("aa");
        authorityList.add(authority);

        List<User> userList=new ArrayList();

        User user=new User();
        user.setPassword("222");
        user.setUsername("111");

        userList.add(user);

        // 添加user到数据库，相当于hibernate的save();
        //persist(增)
        entityManager.persist(user);
        entityManager.persist(authority);

        //user.setAuthorityList(authorityList);//2条数据
        authority.setUserList(userList);//3条数据

        // 5. 提交事务
        transaction.commit();
    }

    public static void findUsers(EntityManager entityManager, String username, String password){
        //3.事务开启
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //持久化操作
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> c = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = c.from(User.class);
        c.select(userRoot);
        c.distinct(true);
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (username != null) {
            ParameterExpression<String> p =
                    criteriaBuilder.parameter(String.class, "username");
            criteria.add(criteriaBuilder.equal(userRoot.get("username"), p));
        }
        if (password != null) {
            ParameterExpression<String> p =
                    criteriaBuilder.parameter(String.class, "password");
            criteria.add(criteriaBuilder.equal(userRoot.get("password"), p));
        }
        if (criteria.size() == 0) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));
        }
        TypedQuery<User> q = entityManager.createQuery(c);
        if (username != null) { q.setParameter("username", username); }
        if (password != null) { q.setParameter("password", password); }
        System.out.println( q.getResultList());

        // 5. 提交事务
        transaction.commit();

    }

}
