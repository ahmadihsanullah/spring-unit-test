package com.ahmadihsan.service;

import com.ahmadihsan.entity.Student;
import com.ahmadihsan.repostiory.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student){
        boolean existsedByEmail = studentRepository.existsByEmail(student.getEmail());
        if(existsedByEmail){
            throw new RuntimeException(
                    "Email " + student.getEmail() + " have taken"
            );
        }
        studentRepository.save(student);
    }

    public Iterable<Student> findAll(){
        return studentRepository.findAll();
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student with id " + id + " is not exist");
        }
        studentRepository.deleteById(id);
    }
}
