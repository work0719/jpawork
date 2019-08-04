package com.xhp.taiji.jpa3demo.service;

import com.xhp.taiji.jpa3demo.entiry.Student;
import com.xhp.taiji.jpa3demo.entiry.StudentRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class StudentService {
    @Inject
    StudentRepository studentRepository;

    public List<Student> getAll() {
        List<Student> list = studentRepository.findAll();
        return list;
    }

    public Student findByIdStudent(Integer id) {
        Student student = studentRepository.findOne(id);
        return student;
    }

    public Student addStudent(Student student) {
        Student student1 = studentRepository.save(student);
        return student1;
    }

    public void deleteStudent(Integer id) {
        studentRepository.delete(id);
    }

    @Query(value = "update orders set age=?1 where id=?2 ", nativeQuery = true)
    @Modifying
    public void updateStudent(Integer age, Integer id) {

    }
}
