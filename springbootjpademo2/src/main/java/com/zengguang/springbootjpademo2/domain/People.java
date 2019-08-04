package com.zengguang.springbootjpademo2.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "peopleInfo")
public class People implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "people_name")
    private String peopleName;

    @Column(name = "people_age")
    private String peopleAge;

    private String birthday;
    @Column(name = "people_index")
    private Integer peopleIndex;//排序索引


    private Integer flag;

    public People() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleAge() {
        return peopleAge;
    }

    public void setPeopleAge(String peopleAge) {
        this.peopleAge = peopleAge;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getPeopleIndex() {
        return peopleIndex;
    }

    public void setPeopleIndex(Integer peopleIndex) {
        this.peopleIndex = peopleIndex;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", peopleName='" + peopleName + '\'' +
                ", peopleAge='" + peopleAge + '\'' +
                ", birthday='" + birthday + '\'' +
                ", peopleIndex=" + peopleIndex +
                ", flag=" + flag +
                '}';
    }
}
