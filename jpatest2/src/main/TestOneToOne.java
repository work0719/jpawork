package main;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class TestOneToOne {

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");
		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();


		//调用add方法
		//addAddress(entityManager);
		//addArticle(entityManager);
		//调用删除方法
		//delAddress(entityManager);
		//调用更新方法
		//updateAddress(entityManager);
		//根据CreateQuery查询
		//searchByCreateQuery(entityManager);
		//根据NameQuery查询
		//searchByNameQuery(entityManager);
		//根据id条件查询
		searchByIdNameQuery(entityManager);


		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}





	//调用add方法
	private static void addArticle(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Article article=new Article();
		article.setTitle("哈哈哈");
		article.setContent("java大法好");

		// 添加user到数据库，相当于hibernate的save();
		//persist(增)
		entityManager.persist(article);

		// 5. 提交事务
		transaction.commit();

	}

	//添加数据
	public static void addAddress(EntityManager entityManager){
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Address address = new Address();
		address.setAddress("吉林");

		// 添加user到数据库，相当于hibernate的save();
		//persist(增)
		entityManager.persist(address);

		// 5. 提交事务
		transaction.commit();
	}
	//删除数据
	private static void delAddress(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Article article=entityManager.find(Article.class,1);
		article.setTitle("哈哈哈");
		// 添加user到数据库，相当于hibernate的save();
		//persist(增)
		entityManager.remove(article);

		// 5. 提交事务
		transaction.commit();
	}

	//更新操作
	private static void updateAddress(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Article article=entityManager.find(Article.class,1);
		System.out.println(article.getContent());
		System.out.println(article.getTitle());
		//article.setId(article.getId());
		article.setContent("asd");
		article.setTitle("哈哈哈");
		entityManager.merge(article);

		// 5. 提交事务
		transaction.commit();
		System.out.println("11111");
	}

	//根据CreateQuery查询
	private static void searchByCreateQuery(EntityManager entityManager) {
		//开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		//持久化
		List<Article> list=entityManager.createQuery("select content,title  from Article").getResultList();
		System.out.println(list.size());
		System.out.println("list end");
		// 5. 提交事务
		transaction.commit();
	}

	//根据NameQuery查询
	private static void searchByNameQuery(EntityManager entityManager) {
		//开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		//持久化操作
		List<Article> list=entityManager.createNamedQuery("Article.find").getResultList();
		System.out.println(list.size());
		System.out.println("list end");
		// 5. 提交事务
		transaction.commit();

	}

	//根据id进行查询
	private static void searchByIdNameQuery(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		//4.持久化操作
		/*List<Article> list=entityManager.createQuery("select u from Article u where u.id=?1")
				.setParameter(1,2).getResultList();*/
		//上面持久化操作同样也可以写成一下方式
		Article article= (Article) entityManager.createNativeQuery("select * from article u where u.id=?1").setParameter(1,1).getSingleResult();
		System.out.println(article.toString());
		//System.out.println(list.size());

		// 5. 提交事务
		transaction.commit();

	}



}
