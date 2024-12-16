package org.example.controller;
import org.example.service.ReadingService;
import org.example.model.Exceptions.*;
import org.example.model.*;

import java.util.List;

public class ReadingController {
    private ReadingService readingService;

    public ReadingController(ReadingService readingService){this.readingService=readingService;}

    public void enroll(int studentId, int courseId){readingService.enroll(studentId,courseId);}

    public List<Reading> showEnrolledReadingCourses(int studentId){return readingService.showEnrolledReadingCourses(studentId);}

    public List<Question> practiceReading(int studentId, int courseId){return readingService.practiceReading(studentId,courseId);}

    public String handleAnswer(int studentId, int questionId, String answer){return readingService.handleAnswer(studentId,questionId,answer);}

    public List<Question> reviewPastReadingMistakes(int studentId){return readingService.reviewPastReadingMistakes(studentId);}

    public List<Reading> getAvailableReadingCourses(){return readingService.getAvailableReadingCourses();}

    public List<Student> getAllStudents(){return readingService.getAllStudents();}

    public List<Student> getEnrolledStudents(int courseId){return readingService.getEnrolledStudents(courseId);}

    public List<Student> showStudentsEnrolledInReadingCourses(){return readingService.showStudentsEnrolledInReadingCourses();}

    public boolean removeCourse(int courseId, int teacherId){return readingService.removeCourse(courseId,teacherId);}

    public void createOrUpdateReadingCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
        readingService.createOrUpdateReadingCourse(courseId,teacherId,courseName,maxStudents,exerciseSet);
    }

    public List<Reading> viewReadingCoursesTaughtByTeacher(int teacherId){return readingService.viewReadingCoursesTaughtByTeacher(teacherId);}

    public List<Book> viewMandatoryBooks(int courseId){return readingService.viewMandatoryBooks(courseId);}

    public boolean addMandatoryBook(Integer teacherId, Integer courseId, String title, String author){return readingService.addMandatoryBook(teacherId,courseId,title,author);}
}
