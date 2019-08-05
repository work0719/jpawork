package cn.com.taiji;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 一对多 author article 增删改查
 * 多对一 article author 增删改查
 */
public class TestAuthor {

	@PersistenceContext
	EntityManager em;

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();

		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();

		// 4. 持久化操作
		//添加Author和Article的信息
		//addAuthor(entityManager);

		//查询表Author的信息
		//selectAuthor(entityManager);
		//查询表Article的信息
		selectArticle(entityManager);
		//search(entityManager, "xxx");
		//删除Author的信息
		//delAuthor(entityManager);

		//修改Author的信息
		//updateAuthor(entityManager);
		//修改Article的信息
		//updateArticle(entityManager);
		//searchByNameQuery(entityManager);

//		// 5. 提交事务
//		transaction.commit();

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}

	//添加Author和Article的信息
	private static void addAuthor(EntityManager entityManager) {

		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作

		Author author=new Author();
		author.setName("ccc");

		Article article=new Article();
		article.setAuthor(author);
		article.setTitle("ccc");
		article.setContent("ccc");

		entityManager.persist(author);
		entityManager.persist(article);

		// 5. 提交事务
		transaction.commit();
	}

	//删除Author的信息
	private static void delAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		//删除Author的信息

		Author author1=entityManager.find(Author.class,1);
		entityManager.remove(author1);

		// 5. 提交事务
		transaction.commit();
	}

	//修改Author的信息
	private static void updateAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		//修改Author的信息

		Author author=entityManager.find(Author.class,2);
		author.setName("小李");
		entityManager.merge(author);

		// 5. 提交事务
		transaction.commit();
	}

	//修改Article的信息
	private static void updateArticle(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		//修改Article的信息

		Article article=entityManager.find(Article.class,2);
		article.setTitle("java");
		article.setContent("学习");
		entityManager.merge(article);

		// 5. 提交事务
		transaction.commit();
	}

	//查询表Author的信息
	private static void selectAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Author> c = cb.createQuery(Author.class);
		Root<Author> root = c.from(Author.class);
		c.select(root)
				.where(cb.equal(root.get("name"), "小李"));
		TypedQuery query=entityManager.createQuery(c);
		List<Author> list=query.getResultList();
		System.out.println(list.size());

		// 5. 提交事务
		transaction.commit();
	}

	//查询表Article的信息
	private static void selectArticle(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4. 持久化操作
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> c = cb.createQuery(Article.class);
		Root<Article> root = c.from(Article.class);
		c.select(root)
				.where(cb.equal(root.get("content"), "学习"));
		TypedQuery query=entityManager.createQuery(c);
		List<Article> list=query.getResultList();
		System.out.println(list.size());

		// 5. 提交事务
		transaction.commit();
	}

}
