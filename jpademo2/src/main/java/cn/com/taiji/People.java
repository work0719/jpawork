package cn.com.taiji;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * People和Address是一对一的关系 OneToOne
 * People是关系的维护端，当删除 people，会级联删除 address
 */
@Entity
public class People {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;// id
	@Column(name = "name", nullable = true, length = 20)
	private String name;// 姓名
	@Column(name = "sex", nullable = true, length = 1)
	private String sex;// 性别
	@OneToOne(cascade = CascadeType.ALL) // People是关系的维护端，当删除 people，会级联删除 address
	@JoinColumn(name = "address_id", referencedColumnName = "id") // people中的address_id字段参考address表中的id字段
	private Address address;// 地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
