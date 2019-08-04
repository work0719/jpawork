package com.xhp.taiji.jpa3demo;

import com.xhp.taiji.jpa3demo.entiry.Student;
import com.xhp.taiji.jpa3demo.entiry.StudentRepository;
import com.xhp.taiji.jpa3demo.service.StudentService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Jpa3demoApplicationTests {
    @Inject
    StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 增删改操作时 要操作Service层   做事务处理
     */
    @Test
    public void contextLoads() {
    }


  @Ignore
    @Test
                                        //   增加
    public void saveStudent() {
        for(int i = 1; i < 26;i++) {
            Student student = new Student();
            student.setName("张安"+i);
            student.setGender("1"+i);
            student.setAge(i);
            studentService.addStudent(student);
        }
    }

    @Ignore
    @Test
                                         //   查找全部
    public void findAll() {
        List<Student> list = studentService.getAll();
        System.out.println(list.toString());
    }

   @Ignore
    @Test
    //                                          条件查找
    public void findById() {
        Student student=studentService.findByIdStudent(3);
        System.out.println(student.toString());
    }

    @Ignore
    @Test
    //                                          删除
    public void deleteStudent(){
       studentService.deleteStudent(4);
        System.out.println("删除");
    }

    @Ignore
    @Test
    //                                         更新
    public void updateStudent(){
       studentService.updateStudent(40,2);
    }

    /**
     * 目标   实现自定义查询  （查询id大于五的条件）
     * 实现JpaSpecificationExecutor接口  的findAll(Specification,Pageable)方法
     * Specification 封装了 jpa criteria  查询的查询条件
     * Pageable 封装了一些分页信息  page pageSixe sort等
     */
//    @Ignore
    @Test
    public void testStudentPage() {
        int page = 1 -1;//第几页
        int size = 5;//一页五条
        PageRequest pageable = new PageRequest(page, size);

        Specification<Student> spec = new Specification<Student>() {
            /**
             * Root<User> root：代表查询的实体类（属性导航）
             * CriteriaQuery<?> query：可以获得root参数 告知jpa criteria 要查询的是哪个实体类
             * CriteriaBuilder cb：CriteriaBuilder用于创建criteria相应的工厂，从中获取Predicate对象
             *
             * return Predicate  作为一个查询条件
             */
            @Override
            public Predicate toPredicate(
                    Root<Student> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                Path path = root.get("id");
                Predicate predicate = cb.gt(path, 0);  //此处id大于0
                return predicate;
            }
        };
        Page<Student> p = studentRepository.findAll(spec, pageable);


        System.out.println("总数量:   "+p.getTotalElements());
        System.out.println("总页数:  "+p.getTotalPages());
        System.out.println("当前第几页: "+(p.getNumber()+1));
        System.out.println("当前页的总记录数:  "+p.getNumberOfElements());
        System.out.println("list集合:  "+p.getContent());
    }
}
