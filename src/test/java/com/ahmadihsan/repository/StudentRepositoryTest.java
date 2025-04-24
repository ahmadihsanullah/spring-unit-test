package com.ahmadihsan.repository;

import com.ahmadihsan.entity.Student;
import com.ahmadihsan.repostiory.StudentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentExistsEmail() {
//        given
        Student student = new Student(
            "rehan", "rehan@gmail.com"
        );
        underTest.save(student);
//        when
        boolean expected = underTest.existsByEmail("rehan@gmail.com");
//        then
        Assertions.assertTrue(expected);
    }

    @Test
    void itShouldCheckIfStudentsEmailDoesNotExists() {
//        given
        String email = "ngasal@gmail.com";
//        when
        boolean expected = underTest.existsByEmail("rehan@gmail.com");
//        then
        Assertions.assertFalse(expected);
    }
}