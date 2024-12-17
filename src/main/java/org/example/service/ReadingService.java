package org.example.service;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ReadingService {
    private final IRepository<Reading> readingRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<Book> bookRepo;

    private final IRepository<Enrolled> enrolledRepo;
    private final IRepository<BookBelongsToCourse> bookBelongsRepo;

    public ReadingService(IRepository<Reading> readingRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<Book> bookRepo, IRepository<Enrolled> enrolledRepo, IRepository<BookBelongsToCourse> bookBelongsRepo) {
        this.readingRepo = readingRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.bookRepo = bookRepo;
        this.enrolledRepo=enrolledRepo;
        this.bookBelongsRepo=bookBelongsRepo;
    }

    public Student getStudentById(int studentId){
        idDataCheck(studentId);
        for (Student student : studentRepo.getAll()) {
            if (student.getId()==studentId)
                return student;
        }
        throw new EntityNotFoundException("Student not found!");
    }

    public Teacher getTeacherById(int teacherId){
        idDataCheck(teacherId);
        for (Teacher teacher : teacherRepo.getAll()) {
            if (teacher.getId()==teacherId)
                return teacher;
        }
        throw new EntityNotFoundException("Teacher not found!");
    }

    public Reading getReadingById(int readingId){
        idDataCheck(readingId);
        for (Reading reading : readingRepo.getAll()) {
            if (reading.getId()==readingId)
                return reading;
        }
        throw new EntityNotFoundException("Reading Course not found!");
    }

    public Question getQuestionById(int questionId){
        idDataCheck(questionId);
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        throw new EntityNotFoundException("Question not found!");
    }

    public Book getBookById(int bookId){
        idDataCheck(bookId);
        for (Book book:bookRepo.getAll())
            if (book.getId()==bookId)
                return book;
        throw new EntityNotFoundException("Book not found!");
    }

    public List<Student> getEnrolled(int courseId){
        List<Student> enrolled=new ArrayList<>();
        idDataCheck(courseId);
        for(Enrolled enrollment:enrolledRepo.getAll())
            if(enrollment.getCourse()==courseId)
                enrolled.add(getStudentById(enrollment.getStudent()));
        return enrolled;

    }

    public List<Reading> getReadingCourses(int studentId){
        idDataCheck(studentId);
        List<Reading> readingCourses=new ArrayList<>();
        for (Reading reading:readingRepo.getAll())
            for (Enrolled enrolled:enrolledRepo.getAll())
                if (reading.getId()==enrolled.getCourse()&&enrolled.getStudent()==studentId)
                    readingCourses.add(reading);
        return readingCourses;
    }

    public List<Question> getExercises(int courseId){
        idDataCheck(courseId);
        List<Question> questions=new ArrayList<>();
        for (Question q:questionRepo.getAll())
            if (q.getReadingId()==courseId)
                questions.add(q);
        return questions;
    }

    public List<Book> getMandatoryBooks(int courseId){
        idDataCheck(courseId);
        List<Book> books=new ArrayList<>();
        for (BookBelongsToCourse belongs:bookBelongsRepo.getAll())
            if (belongs.getReading()==courseId)
                books.add(getBookById(belongs.getId()));
        return books;
    }

    public void enroll(int studentId, int readingCourseId) {
        idDataCheck(studentId);
        idDataCheck(readingCourseId);
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Reading course = getReadingById(readingCourseId);

        for (Reading reading:getReadingCourses(studentId)){
            if (reading.getId()==readingCourseId)
            {
                alreadyEnrolled=1;
                break;
            }
        }
        if (alreadyEnrolled==0){

            if (course.getAvailableSlots() > getEnrolled(readingCourseId).size()) {

                int nextId=enrolledRepo.getAll().size();
                Enrolled enrolled=new Enrolled(nextId,studentId,readingCourseId);
                enrolledRepo.create(enrolled);
            }
        }

    }

    public List<Reading> showEnrolledReadingCourses(int studentId){
        idDataCheck(studentId);
        return getReadingCourses(studentId);
    }

    public List<Question> practiceReading(int studentId, int courseId){
        idDataCheck(studentId);
        idDataCheck(courseId);
        int isEnrolled=0;

        for (Reading reading: getReadingCourses(studentId))
            if (reading.getId()==courseId)
            {
                isEnrolled=1;
                break;
            }
        if (isEnrolled==0)
            return new ArrayList<>();
        else{
            return getExercises(courseId);
        }
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        idDataCheck(studentId);
        idDataCheck(questionId);
        stringDataCheck(answer);
        answerDataCheck(answer);

        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else{
            return "Wrong!";
        }
    }

    public List<Reading> getAvailableReadingCourses(){
        return readingRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        idDataCheck(courseId);
        return getEnrolled(courseId);
    }

    public List<Student> showStudentsEnrolledInReadingCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!getReadingCourses(student.getId()).isEmpty())
                studentList.add(student);
        return studentList;

    }

    public boolean removeCourse(int courseId, int teacherId) {
        idDataCheck(courseId);
        idDataCheck(teacherId);
        Reading course = getReadingById(courseId);
        if (course.getTeacher()==teacherId){
            readingRepo.delete(courseId);
            return true;
        }
        return false;
    }

    public void createOrUpdateReadingCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
        idDataCheck(courseId);
        idDataCheck(teacherId);
        stringDataCheck(courseName);
        intDataCheck(maxStudents);
        intDataCheck(exerciseSet);

        int found=0;
        for (Reading course: readingRepo.getAll()){
            if (course.getId()==courseId)
            {
                found=1;
                updateReadingCourse(courseId,teacherId,courseName,maxStudents, exerciseSet);
                return;
            }
        }
        if (found==0){
            createReadingCourse(courseId,teacherId,courseName,maxStudents, exerciseSet);
        }
    }

    public void createReadingCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
        Reading r1=new Reading(courseId,courseName,teacherId,maxStudents);
        readingRepo.create(r1);
    }

    public void updateReadingCourse(int courseId, int teacherId,String courseName, Integer maxStudents, int exerciseSet){
        Reading course=getReadingById(courseId);

        course.setCourseName(courseName);
        course.setTeacher(teacherId);
        course.setAvailableSlots(maxStudents);

        readingRepo.update(course);
    }

    public List<Reading> viewReadingCoursesTaughtByTeacher(int teacherId){
        idDataCheck(teacherId);
        List<Reading> taughtCourses=new ArrayList<>();
        for(Reading course:readingRepo.getAll())
            if (course.getTeacher()==teacherId)
                taughtCourses.add(course);
        return taughtCourses;

    }

    public List<Book> viewMandatoryBooks(int studentId,int courseId){
        idDataCheck(courseId);
        idDataCheck(studentId);

        for (Reading course:getReadingCourses(studentId))
            if (course.getId()==courseId)
                return getMandatoryBooks(courseId);
        return new ArrayList<>();

    }

    public boolean addMandatoryBook(Integer teacherId, Integer courseId, String title, String author){
        idDataCheck(teacherId);
        idDataCheck(courseId);
        stringDataCheck(title);
        stringDataCheck(author);

        Reading course=getReadingById(courseId);
        int nextId=bookRepo.getAll().size();
        Book book=new Book(nextId, title, author);
        bookRepo.create(book);

        if(course.getTeacher()==teacherId)
        {

            int nextIdBook=bookBelongsRepo.getAll().size();
            BookBelongsToCourse bookBelongsToCourse=new BookBelongsToCourse(nextIdBook,courseId,nextId);
            bookBelongsRepo.create(bookBelongsToCourse);
            return true;
        }
        else return false;
    }

    public void idDataCheck(int id){
        if (id<1)
            throw new ValidationException("Id cannot be less than 1!");
    }

    public void stringDataCheck(String string){
        if (string.isEmpty())
            throw new ValidationException("Name cannot be an empty string!");
    }

    public void answerDataCheck(String string){
        if (!string.equals("wahr") && !string.equals("falsch"))
            throw new ValidationException("Invalid answer!");
    }

    public void intDataCheck(int number){
        if (number<1)
            throw new ValidationException("Number cannot be null!");
    }

}
