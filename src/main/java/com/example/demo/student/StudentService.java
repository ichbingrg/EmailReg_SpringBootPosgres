package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
        //return List.of(new Student(1l,"John",18, LocalDate.of(2000, Month.JANUARY,5),"john@something.com"));
    }

    public void addNewStudent(Student student) {
        System.out.println(student);
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email already in DB");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);

    }

    @Transactional  //Db cannot be updated without this
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("student with id : " + id + " does not exist in the DB"));

        if(name != null && name.length()>0 && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }

        if (email != null && email.length()>0 && !Objects.equals(email,student.getEmail())){
            if(studentRepository.findStudentByEmail(email).isPresent()){
                throw new IllegalStateException("Email already exists in DB");
            }
            student.setEmail(email);
        }
    }
}
