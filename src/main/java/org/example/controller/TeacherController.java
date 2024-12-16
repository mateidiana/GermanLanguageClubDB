package org.example.controller;
import org.example.service.TeacherService;
import org.example.model.Exceptions.*;

public class TeacherController {
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService){
        this.teacherService=teacherService;
    }

    public boolean createTeacher(int teacherId, String name){
        return teacherService.createTeacher(teacherId,name);
    }
}