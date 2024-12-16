package org.example.service;

import org.example.model.Exceptions.ValidationException;
import org.example.model.Teacher;
import org.example.repo.IRepository;

public class TeacherService {

    private final IRepository<Teacher> teacherRepo;

    public TeacherService(IRepository<Teacher> teacherRepo){
        this.teacherRepo=teacherRepo;
    }

    public boolean createTeacher(int teacherId, String name){
        dataCheck(teacherId,name);
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

    public void dataCheck(int teacherId, String name){
        if (name.isEmpty())
            throw new ValidationException("Name cannot be an empty string!");
        if (teacherId<1)
            throw new ValidationException("Id cannot be less than 1!");
    }
}
