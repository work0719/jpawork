package com.duan.jpa.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * ClassName: Student
 *
 * @Description: ${todo}
 * @Author dph
 * @CreatDate ${date}
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int s_id;
    private String s_name;
    @OneToMany(targetEntity = Library.class)
    private List books_issued;
    @ManyToOne
    private Library library;




    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public List getBooks_issued() {
        return books_issued;
    }

    public void setBooks_issued(List books_issued) {
        this.books_issued = books_issued;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }
}
