package org.example.service;

import org.example.model.Student;
import org.example.repo.IRepository;


public class StudentService {

    private final IRepository<Student> studentRepo;

    public StudentService(IRepository<Student> studentRepo){
        this.studentRepo=studentRepo;
    }

    public boolean createStudent(int studentId, String name){
        for(Student student:studentRepo.getAll())
            if (student.getId()==studentId){
                return false;
            }

        Student student = new Student(name,studentId);
        studentRepo.create(student);
        return true;
    }

    public Student getStudentById(int studentId){
        for (Student student : studentRepo.getAll()) {
            if (student.getId()==studentId)
                return student;
        }
        return null;
    }
}

