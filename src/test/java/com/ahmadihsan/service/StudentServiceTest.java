package com.ahmadihsan.service;

import com.ahmadihsan.entity.Student;
import com.ahmadihsan.repostiory.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void findAll() {
//        when
        underTest.findAll();
//        then
        verify(studentRepository).findAll();
    }

    @Test
    void add() {
//        given
        Student student = new Student(
                "rehan", "rehan@gmail.com"
        );
//        when
        underTest.addStudent(student);
//        then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(argumentCaptor.capture());

        Student captorValue = argumentCaptor.getValue();

        assertEquals(captorValue, student);
    }

    @Test
    @Disabled
    void delete() {
    }
}