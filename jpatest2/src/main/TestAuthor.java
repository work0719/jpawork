package main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class TestAuthor {

	@PersistenceContext
	EntityManager em;

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-demo");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();
		//添加作者
		//addAuthor(entityManager);
		//添加作者文章
		// addArticle(entityManager);
		//查询
		// search(entityManager, "xxx");
		//删除
		// delAuthor(entityManager);
		//根据名字查询
		// searchByNameQuery(entityManager);

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}
	//查询
	private static void search(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		//Author author= (Author) entityManager.createNativeQuery("select * from Author where id=?1").setParameter(1,2).getSingleResult();
		//System.out.println("数据总数" + author.toString());
		List<Author> list=entityManager.createNativeQuery("select * from author where id=?1").setParameter(1,2).getResultList();
		//System.out.println("数据总数" + list.size());
		//entityManager.persist(a);

		// 5. 提交事务
		transaction.commit();
	}
	//更新
	private static void updateAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Author author = entityManager.find(Author.class, 2);
		author.setName("哈哈哈");

		/*People people =new People();*/

		entityManager.merge(author);

		// 5. 提交事务
		transaction.commit();
	}
	//根据名字查询
	private static void searchByNameQuery(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		List<Author> list1 = entityManager.createNamedQuery("findAllAuthor", Author.class).getResultList();
		// 添加user到数据库，相当于hibernate的save();
		//entityManager.persist(a);

		// 5. 提交事务
		transaction.commit();
	}

	private static void delAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Author author = entityManager.find(Author.class, 2);


		// 添加user到数据库，相当于hibernate的save();
		entityManager.remove(author);

		// 5. 提交事务
		transaction.commit();
	}

	private static void addAuthorArticle(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();



		Author author = new Author();
		author.setName("曾光");


		Article article = new Article();
		article.setAuthor(author);
		article.setContent("hahah");
		article.setTitle("java java");

		entityManager.persist(author);
		entityManager.persist(article);


		// 5. 提交事务
		transaction.commit();
	}


}
