package com.duan.jpa.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ClassName: Library
 *
 * @Description: 用来描述图书馆的实体类
 * @Author dph
 * @CreatDate ${date}
 */
@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int b_id;
    private String b_name;



    public Library(int b_id, String b_name) {
        this.b_id = b_id;
        this.b_name = b_name;
    }

    public Library() {}



    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }
}
