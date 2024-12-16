package org.example.controller;
import org.example.service.StudentService;
import org.example.model.Exceptions.*;

public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    public boolean createStudent(int studentId, String name){
        return studentService.createStudent(studentId,name);
    }


}
