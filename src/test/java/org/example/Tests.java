package org.example;

import org.example.model.Student;
import org.example.model.Exceptions.DatabaseException;
import org.example.repo.*;
import org.example.service.StudentService;
import org.example.controller.StudentController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class Tests {
    @Test
    public void CRUDStudent() {
        StudentDBRepository studentRepository = new StudentDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        Student student = new Student("StudentTest",101);

        studentRepository.create(student);
        assertEquals("StudentTest", studentRepository.read(101).getName());
        studentRepository.update(new Student("StudentTest1",101));
        assertEquals("StudentTest1", studentRepository.read(101).getName());
        studentRepository.delete(101);
        assertNull(studentRepository.read(101));
    }
}