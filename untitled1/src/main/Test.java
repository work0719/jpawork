package main;

import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();
        // 3.开启事务
       /* EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();*/
        //增加
        //addPeople(entityManager);
        //删除
        //delPeople(entityManager);
        //CreateQuery查询
        //searchByCreateQuery(entityManager);
        //CreateNamedQuery查询
        //searchByCreateNamedQuery(entityManager);
        //更新
        //updatePeople(entityManager);
        //添加 中间表  拥有方测试
        //addUser(entityManager);
        
        /*List<Authority> list = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName("曾光");
        list.add(authority);

        List<User> list1 = new ArrayList<>();
        User user = new User();
        user.setUsername("zyn");
        user.setPassword("123456");*/
        //user.setAuthorityList(list);
        //authority.setUserList(list1);

        //entityManager.persist(authority);
        //entityManager.persist(user);
        //jpql查询
        //jpqlSelect(entityManager);
        //searchByParamter(entityManager,"","");






        // 4. 持久化操作
       /* Address a = new Address();
        a.setAddress("吉林");
        People b = new People();
        b.setName("曾光");
        b.setSex("女");*/
        // 添加user到数据库，相当于hibernate的save();
       /* entityManager.persist(a);
        entityManager.persist(b);*/






        // 5. 提交事务
       //transaction.commit();
        //add(entityManager);
        //search(entityManager,"tom");




        // 6. 关闭EntityManager
        entityManager.close();

        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    private static void searchByParamter(EntityManager entityManager,String username,String password) {
        // 3.开启事务

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> c = cb.createQuery(User.class);
        Root<User> emp = c.from(User.class);
        c.select(emp);
        c.distinct(true);
        /*Join<Employee,Project> project =
                emp.join("projects", JoinType.LEFT);*/
        List<Predicate> criteria = new ArrayList<Predicate>();
        if ( username!= null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "username");
            criteria.add(cb.equal(emp.get("username"), p));
        }
        if (password != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "password");
            criteria.add(cb.equal(emp.get("password"), p));
        }

        if (criteria.size() == 0) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
        }
        TypedQuery<User> q = entityManager.createQuery(c);
        if (username != null) { q.setParameter("username", username); }
        if (password != null) { q.setParameter("password", password); }

        System.out.println(q.getResultList());
        // 5. 提交事务
        transaction.commit();
    }


    private static void jpqlSelect(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> c = cb.createQuery(User.class);
        Root<User> emp = c.from(User.class);
        c.select(emp)
                .where(cb.equal(emp.get("username"), "John Smith"));
        TypedQuery<User> query =entityManager.createQuery(c);
        List<User> list =query.getResultList();
        System.out.println(list.size());
        // 5. 提交事务
        transaction.commit();
    }

    private static void addUser(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        List<Authority> list = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName("曾光");
        list.add(authority);

        List<User> list1 = new ArrayList<>();
        User user = new User();
        user.setUsername("zyn");
        user.setPassword("123456");
        user.setAuthorityList(list);
        //authority.setUserList(list1);
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(authority);
        entityManager.persist(user);



        // 5. 提交事务
        transaction.commit();
    }

    private static void updatePeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people = entityManager.find(People.class,4);
        people.setName("曾光");
        people.setSex("男");
        /*People people =new People();*/

        // 添加user到数据库，相当于hibernate的save();
        entityManager.merge(people);

        // 5. 提交事务
        transaction.commit();
    }

    private static void searchByCreateNamedQuery(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        List<People> list =  entityManager.createNamedQuery("findAllPeople",People.class).getResultList();
        System.out.println("数据总数"+list.size());
        // 添加user到数据库，相当于hibernate的save();
        //entityManager.persist(a);

        // 5. 提交事务
        transaction.commit();
    }

    private static void searchByCreateQuery(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        List<People> list =  entityManager.createQuery("select a from People a",People.class).getResultList();
        System.out.println("数据总数"+list.size());
        // 添加user到数据库，相当于hibernate的save();
        //entityManager.persist(a);

        // 5. 提交事务
        transaction.commit();
    }

    //删除
    private static void delPeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people = entityManager.find(People.class,1);




        // 添加user到数据库，相当于hibernate的save();
        entityManager.remove(people);

        // 5. 提交事务
        transaction.commit();
    }
    //添加
    private static void addPeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people = new People();
        people.setName("赵仁年");
        people.setSex("妖");


        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(people);

        // 5. 提交事务
        transaction.commit();
    }


}
