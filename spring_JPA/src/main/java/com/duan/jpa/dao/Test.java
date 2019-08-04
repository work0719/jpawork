package com.duan.jpa.dao;

import com.duan.jpa.pojo.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import static javafx.beans.binding.Bindings.select;

public class Test {

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();

		/*Address*/
		//addAddress(entityManager);
		//removeAddress(entityManager);
		//selectPeople(entityManager)
		//updatePeople(entityManager);

		/*Author*/
		//addAuthorTest(entityManager);
        //search(entityManager,"tom");

		/*User*/
		//selectUserByName(entityManager);

		/*Teacher-Library*/
		//addT_L(entityManager);

		/*Student-Library*/
		//addS_L(entityManager);

		/*复杂查询*/
		//selectAll(entityManager);

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}


	/**
	 * 复杂查询
	 * @param entityManager
	 */
	public static void selectAll(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		String username = "木木";
		String password = "mumu";

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> emp = c.from(User.class);
		c.select(emp);
		c.distinct(true);
		List<Predicate> criteria = new ArrayList<Predicate>();
		if (username != null) {
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
	}


	/**
	 * User 实体类
	 * @param entityManager
	 */
	/*查询*/
	private static void selectUserByName(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4.持久化操作
		CriteriaBuilder cd = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = cd.createQuery(User.class);
		Root<User> root = c.from(User.class);

		c.select(root).where(cd.equal(root.get("username"),"林"));

		TypedQuery query = entityManager.createQuery(c);
		List<User> list = query.getResultList();

		System.out.println(list.size());

		// 5. 提交事务
		transaction.commit();
	}


	/**
	 * Address/people 实体类的增删改查
	 * @param entityManager
	 */
	/*添加*/
	private static void addAddress(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		People people = new People();
		Address address = new Address();
		people.setName("华华");
		people.setSex("男");
		people.setAddress(address);
		address.setAddress("北京");

		// 添加a到数据库，相当于hibernate的save();
		entityManager.persist(people);
		entityManager.persist(address);

		// 5. 提交事务
		transaction.commit();
	}
	/*删除*/
	private static void removeAddress(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		People people = entityManager.find(People.class,1);
		// 移除people表中数据
		entityManager.remove(people);

		// 5. 提交事务
		transaction.commit();
	}
	/*查询(people)*/
	private static void selectPeople(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		People people = entityManager.find(People.class,1);
		// 移除people表中数据
		entityManager.remove(people);

		// 5. 提交事务
		transaction.commit();
	}
	/*更新(people)*/
	private static void updatePeople(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4.持久化操作
		People people = new People();
		people.setName("中华");

		entityManager.merge(people);

		// 5. 提交事务
		transaction.commit();
	}


	/**
	 * Authority 实体类
	 * @param entityManager
	 */
	private static void addAuthorTest(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		//4.持久化操作
		List<Authority> authorityList = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setName("华");

		List<User> userList = new ArrayList<User>();
		authority.setUserList(userList);

		User user = new User();
		user.setUsername("林");
		user.setPassword("1234");
		user.setAuthorityList(authorityList);
		//authority.setUserList(userList);

		//添加authority到数据库，相当于hibernate的save();
		entityManager.persist(authority);
		entityManager.persist(user);

		// 5. 提交事务
		transaction.commit();
	}


	/**
	 * 一对多实例：
	 * 			Teacher-Library 实体类
	 * @param entityManager
	 */
	private static void addT_L(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		//4.持久化操作
		Library lib1 = new Library();
		lib1.setB_id(101);
		lib1.setB_name("百年孤独");
		Library lib2 = new Library();
		lib2.setB_id(102);
		lib2.setB_name("丰乳肥臀");

		entityManager.persist(lib1);
		entityManager.persist(lib2);

		ArrayList<Library> list = new ArrayList<Library>();
		list.add(lib1);
		list.add(lib2);

		Teacher teacher = new Teacher();
		teacher.setT_id(1);
		teacher.setT_name("linlin");
		teacher.setBooks(list);

		entityManager.persist(teacher);

		//提交事务
		transaction.commit();
	}


	/**
	 *  多对一实例：
	 *  		Student-Library 实体类
	 * @param entityManager
	 */
	private static void addS_L(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		//4.持久化操作
		Library library = new Library();
		library.setB_id(101);
		library.setB_name("百年孤独");

		entityManager.persist(library);

		Student student1 = new Student();
		student1.setS_id(1);
		student1.setS_name("huahua");
		student1.setLibrary(library);

		Student student2 = new Student();
		student2.setS_id(2);
		student2.setS_name("mumu");
		student2.setLibrary(library);

		entityManager.persist(student1);
		entityManager.persist(student2);

		// 5. 提交事务
		transaction.commit();
	}
}
