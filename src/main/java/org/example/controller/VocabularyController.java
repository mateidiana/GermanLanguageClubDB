package org.example.controller;
import org.example.service.VocabService;
import org.example.model.Exceptions.*;
import org.example.model.*;

import java.util.List;

public class VocabularyController {
    private VocabService vocabService;

    public VocabularyController(VocabService vocabService){this.vocabService=vocabService;}

    public void enroll(int studentId, int courseId){vocabService.enroll(studentId,courseId);}

    public List<Vocabulary> showEnrolledVocabCourses(int studentId){return vocabService.showEnrolledVocabularyCourses(studentId);}

    public List<Word> practiceVocabulary(int studentId, int courseId){return vocabService.practiceVocabulary(studentId,courseId);}

    public String handleAnswer(int studentId, int questionId, String answer){return vocabService.handleAnswer(studentId,questionId,answer);}

    //public List<Word> reviewPastVocabMistakes(int studentId){return vocabService.reviewPastVocabMistakes(studentId);}

    public List<Vocabulary> getAvailableVocabCourses(){return vocabService.getAvailableVocabularyCourses();}

    public List<Student> getAllStudents(){return vocabService.getAllStudents();}

    public List<Student> getEnrolledStudents(int courseId){return vocabService.getEnrolledStudents(courseId);}

    public List<Student> showStudentsEnrolledInVocabCourses(){return vocabService.showStudentsEnrolledInVocabularyCourses();}

    public boolean removeCourse(int courseId, int teacherId){return vocabService.removeCourse(courseId,teacherId);}

    public void createOrUpdateVocabCourse(int courseId, int teacherId, String courseName, Integer maxStudents){
        vocabService.createOrUpdateVocabCourse(courseId,teacherId,courseName,maxStudents);
    }

    public List<Vocabulary> viewVocabCoursesTaughtByTeacher(int teacherId){return vocabService.viewVocabularyCoursesTaughtByTeacher(teacherId);}

}
