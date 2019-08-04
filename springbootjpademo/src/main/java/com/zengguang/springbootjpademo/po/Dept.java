package com.zengguang.springbootjpademo.po;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * 类名称：Dept 机构 类描述： 创建人：wanghw 创建时间：2018年2月5日 下午8:39:51
 * 
 * @version
 */
@Entity
@Table(name = "deptInfo")
@NamedQuery(name = "Dept.findAll", query = "SELECT d FROM Dept d")
@NamedQueries({ @NamedQuery(name = "Dept.findDeptTree", query = "select d from Dept d left join fetch d.children"),
		@NamedQuery(name = "Dept.findRoots", query = "select d from Dept d where d.parent is null") })
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Dept implements Serializable {

	private static final long serialVersionUID = 5119673746393145493L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;// id

	@Column(name = "dept_desc")
	private String deptDesc;

	@Column(name = "dept_index")
	private Integer deptIndex;

	@Column(name = "dept_name")
	private String deptName;
 

	/*
	 * @ManyToMany(cascade = { CascadeType.PERSIST,
	 * CascadeType.REFRESH,CascadeType.MERGE}, fetch = FetchType.LAZY)
	 * 
	 * @JoinTable( name="dept_user" , joinColumns={
	 * 
	 * @JoinColumn(name="dept_id") } , inverseJoinColumns={
	 * 
	 * @JoinColumn(name="user_id") } )
	 */
	@ManyToMany(mappedBy = "depts", fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Dept parent = null;

	@OrderBy("deptIndex asc")
	@Where(clause = "flag = 1")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	private List<Dept> children = new LinkedList<Dept>();

	private String remark;
	private Integer flag;

	public Dept() {
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public List<Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Dept> children) {
		this.children = children;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptDesc() {
		return this.deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Integer getDeptIndex() {
		return deptIndex;
	}

	public void setDeptIndex(Integer deptIndex) {
		this.deptIndex = deptIndex;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

  

 

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


}