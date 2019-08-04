package com.duan.jpa.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * ClassName: Teacher
 *
 * @Description: 用来描述教师的实体类
 * @Author dph
 * @CreatDate ${date}
 */
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int t_id;
    private String t_name;
    @OneToMany(targetEntity = Library.class)
    private List books;


    public Teacher() {}

    public Teacher(String t_name) {
        this.t_name = t_name;
    }


    public List getBooks() {
        return books;
    }

    public void setBooks(List books) {
        this.books = books;
    }
    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
}
