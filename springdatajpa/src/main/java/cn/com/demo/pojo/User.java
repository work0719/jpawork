package cn.com.demo.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

// import cn.com.taiji.util.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * 类名称：User 类描述： 用户数据库表的持久类 创建人：chixue 创建时间：2016年5月5日 下午2:48:24
 * 
 * @version
 */
@Entity
@Table(name = "user_info")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
//因为懒加载这个对象属性只是一个代理对象，如果json直接当作一个存在的属性去序列化就会出现错误
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class User implements Serializable {
	private static final long serialVersionUID = -3149974916027750041L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;// id

	@Column(name = "user_name")
	private String userName; // 中文名
	@Column(name = "user_index")
	private Integer userIndex; // 排序索引
	@Column(name = "login_name")
	private String loginName; // 登录名
	private String password; // 密码

	private String name;
	private int flag;

	private String birthday; // 出生日期

	@Column(name = "integral", columnDefinition = "tinyint default 0")
	private Integer integral; // 用户积分
//merge- 合并  PERSIST保存REFRESH刷新
	@ManyToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	@JoinTable(name = "dept_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dept_id"))
	private List<Dept> depts;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User() {
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(Integer userIndex) {
		this.userIndex = userIndex;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}