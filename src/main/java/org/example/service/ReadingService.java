package org.example.service;

import org.example.model.*;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

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

    public Question getQuestionById(int questionId){
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        return null;
    }

    public void enroll(int studentId, int readingCourseId) {
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Reading course = getReadingById(readingCourseId);

        for (Reading reading:student.getReadingCourses()){
            if (reading.getId()==readingCourseId)
            {
                alreadyEnrolled=1;
                break;
            }

        }

        if (alreadyEnrolled==0){
            studentRepo.delete(studentId);
            readingRepo.delete(readingCourseId);
            if (course.getAvailableSlots() > course.getEnrolledStudents().size()) {
                course.getEnrolledStudents().add(student);
                student.getReadingCourses().add(course);
                readingRepo.create(course);
                studentRepo.create(student);
            }
        }

    }

    public List<Reading> showEnrolledReadingCourses(int studentId){
        Student student=getStudentById(studentId);
        return student.getReadingCourses();
    }

    public List<Question> practiceReading(int studentId, int courseId){
        int isEnrolled=0;
        Reading course=getReadingById(courseId);
        Student student=getStudentById(studentId);
        List<Question> questions=new ArrayList<>();

        for (Reading reading: student.getReadingCourses())
            if (reading.getId()==courseId)
            {
                isEnrolled=1;
                break;
            }
        if (isEnrolled==0)
            return new ArrayList<>();
        else{
            return course.getExercises();
        }
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else{
            Student student=getStudentById(studentId);
            List<Question> pastMistakes=student.getPastReadingMistakes();
            pastMistakes.add(question);
            student.setPastReadingMistakes(pastMistakes);
            studentRepo.update(student);
            return "Wrong!";
        }
    }

    //in view
    //int score
    //read(courseId)
    //read(studentId)
    //List<Question> q=practiceReading(studentId, courseId)
    //if q is empty
    //System out... "You are not enrolled"
    //else
    //for (Question question: q){
    //String answer
    //print(question)
    // read(answer)
    // System out ....handleAnswer()    -> correct score++    ->>wrong
    //System out ... score
    //
    // }

    //void dataCheck(studentId, courseId)
    //if studenid<0
    //raise AttributeError()


}
