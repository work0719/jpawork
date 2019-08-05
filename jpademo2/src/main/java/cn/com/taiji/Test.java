package cn.com.taiji;

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

/**
 * 一对一 people address 增删改查
 * 多对多 user authority 增删改查
 */
public class Test {

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();

//		add(entityManager);	 
//		search(entityManager,"tom");
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		//增加People里面的数据和下面的方法对应
		//addPeople(entityManager);

		//增加Address里面的数据和下面的方法对应
		//addAddress(entityManager);

		//多对多的增加里面的数据和下面的方法对应
		//addPeopleAuthority(entityManager);

		//删除里面的数据和下面的方法对应
		//deletePeople(entityManager);

		//删除里面的数据和下面的方法对应
		//deleteUser(entityManager);

		//查询people里面的数据和下面的方法对应
		//selectPeopleAll(entityManager);

		//查询User的信息
		//selectUser(entityManager);

		//操作查询
		//selectUserSearch(entityManager);

		//修改people信息操作
		//updatePeople(entityManager);

		//修改User的信息
		//updateUser(entityManager);

		/*// 4. 持久化操作
		Address a = new Address();
		a.setAddress("吉林");
		// 添加user到数据库，相当于hibernate的save();
		entityManager.persist(a);
		// 5. 提交事务
		transaction.commit();*/

		// 6. 关闭EntityManager
		entityManager.close();
		// 7. 关闭EntityManagerFactory
		factory.close();
	}


	//操作查询
	private static void selectUserSearch(EntityManager entityManager) {
		EntityTransaction transaction = entityManager.getTransaction();
		String username="123";
		String password="123456";
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

	//查询people表中的几条数据
	private static void selectPeopleAll(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> root = c.from(User.class);
		c.select(root)
				.where(cb.equal(root.get("username"), "王五"));
		TypedQuery query=entityManager.createQuery(c);
		List<User> list=query.getResultList();
		System.out.println(list.size());
		// 5. 提交事务
		transaction.commit();
	}

	//查询User的信息
	private static void selectUser(EntityManager entityManager) {
        // 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> root = c.from(User.class);
		c.select(root)
				.where(cb.equal(root.get("username"), "小王"));
		TypedQuery query=entityManager.createQuery(c);
		List<User> list=query.getResultList();
		System.out.println(list.size());
		// 5. 提交事务
		transaction.commit();
	}

    //增加people信息
	private static void addPeople(EntityManager entityManager){
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		/*Address a = new Address();
		a.setAddress("吉林");*/
		People people=new People();
		people.setName("猪羊男");
		people.setSex("妖");
		// 添加user到数据库，相当于hibernate的save();
		entityManager.persist(people);
		// 5. 提交事务
		transaction.commit();
	}
    //增加address信息
	private static void addAddress(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		Address address = new Address();
		address.setAddress("甘肃");
		// 添加user到数据库，相当于hibernate的save();
		entityManager.persist(address);
		// 5. 提交事务
		transaction.commit();
	}

    //增加Authority和User信息和中间表的信息
	private static void addPeopleAuthority(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		List<Authority> authorityList=new ArrayList<Authority>();
		Authority authority=new Authority();
		authority.setName("asdasd");
		authorityList.add(authority);

		List<User> userList=new ArrayList<User>();
		User user=new User();
		user.setPassword("123456");
		user.setUsername("王五");
		user.setAuthorityList(authorityList);
		// 添加user到数据库，相当于hibernate的save();
		entityManager.persist(authority);
		entityManager.persist(user);
		// 5. 提交事务
		transaction.commit();
	}

    //删除people的信息
	private static void deletePeople(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		People people=entityManager.find(People.class,1);
		entityManager.remove(people);
		// 删除操作 remove
		// 5. 提交事务
		transaction.commit();
	}

	//删除User的信息
	private static void deleteUser(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		User user=entityManager.find(User.class,1);
		entityManager.remove(user);
		// 删除操作 remove
		// 5. 提交事务
		transaction.commit();
	}

	//修改people信息
	private static void updatePeople(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		People people=entityManager.find(People.class,1);
        people.setName("小李");
        entityManager.merge(people);
		// 5. 提交事务
		transaction.commit();
	}

	//修改User的信息
	private static void updateUser(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		// 4. 持久化操作
		User user=entityManager.find(User.class,2);
		user.setUsername("小王");
		entityManager.merge(user);
		// 5. 提交事务
		transaction.commit();
	}
}
