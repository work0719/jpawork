package main;

import javax.persistence.*;

//文章 --> 作者
@Entity
@NamedQuery(name = "Article.find",query = "select u from Article u")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;
    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;//文章全文内容
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=true
    		)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
//    @JoinColumn(name="author_id")//设置在article表中的关联字段(外键)
    private Author author;//所属作者
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", author=" + author +
				'}';
	}
}
