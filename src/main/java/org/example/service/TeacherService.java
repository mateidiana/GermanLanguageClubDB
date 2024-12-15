package org.example.service;

import org.example.model.Teacher;
import org.example.repo.IRepository;

public class TeacherService {

    private final IRepository<Teacher> teacherRepo;

    public TeacherService(IRepository<Teacher> teacherRepo){
        this.teacherRepo=teacherRepo;
    }

    public boolean createTeacher(int teacherId, String name){
        for(Teacher teacher:teacherRepo.getAll())
            if (teacher.getId()==teacherId){
                return false;
            }

        Teacher teacher = new Teacher(name,teacherId);
        teacherRepo.create(teacher);
        return true;
    }

    public Teacher getTeacherById(int teacherId){
        for (Teacher teacher : teacherRepo.getAll()) {
            if (teacher.getId()==teacherId)
                return teacher;
        }
        return null;
    }
}
