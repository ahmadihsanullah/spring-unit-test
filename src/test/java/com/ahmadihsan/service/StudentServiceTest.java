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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    void addFailWithEmailTaken() {
//        given
        Student student = new Student(
                "rehan", "rehan@gmail.com"
        );
        given(studentRepository.existsByEmail(any()))
                .willReturn(true);
//        when
//        then
        RuntimeException thrown = assertThrows(RuntimeException.class, ()->{
            underTest.addStudent(student);
    });

        assertEquals(thrown.getMessage(), "Email "  + student.getEmail() + " have taken");
        }

    @Test
    void deleteSuccess() throws StudentNotFoundException {
/// given
        Long id = 1L;
        given(studentRepository.existsById(any())).willReturn(true); // datanya ada

        // when
        underTest.deleteStudent(id);

        // then
        verify(studentRepository).existsById(id); // kepanggil
        verify(studentRepository).deleteById(id); //kepanggil
    }

    @Test
    void deleteFailWithNotExistId() {
        // given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(false);

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            underTest.deleteStudent(id);
        });

        assertEquals("Student with id " + id + " is not exist", exception.getMessage());
        verify(studentRepository).existsById(id);
    }

}