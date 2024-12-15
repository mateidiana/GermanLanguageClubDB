package org.example.service;

import org.example.model.Question;
import org.example.model.Reading;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.model.Book;
import org.example.repo.IRepository;

public class ReadingService {
    private final IRepository<Reading> readingRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<Book> bookRepo;

    public ReadingService(IRepository<Reading> readingRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<Book> bookRepo) {
        this.readingRepo = readingRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.bookRepo = bookRepo;
    }

    public Student getStudentById(int studentId){
        for (Student student : studentRepo.getAll()) {
            if (student.getId()==studentId)
                return student;
        }
        return null;
    }

    public Teacher getTeacherById(int teacherId){
        for (Teacher teacher : teacherRepo.getAll()) {
            if (teacher.getId()==teacherId)
                return teacher;
        }
        return null;
    }

    public Reading getReadingById(int readingId){
        for (Reading reading : readingRepo.getAll()) {
            if (reading.getId()==readingId)
                return reading;
        }
        return null;
    }



}
