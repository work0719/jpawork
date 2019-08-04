package com.jellily.jpa;


import javax.persistence.*;
import java.util.List;
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20, unique = true)
    private String username; // 用户账号，用户登录时的唯一标识
    @Column(length = 100)
    private String password;

    //    private List<Authority> authorityList;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;

    }
}
//    public List<Authority> getAuthorityList() {
//        return authorityList;
//    }
//    public void setAuthorityList(List<Authority> authorityList) {
//        this.authorityList = authorityList;
//}
