package org.example.service;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class VocabService {
    private final IRepository<Vocabulary> vocabRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Word> wordRepo;

    private final IRepository<Enrolled> enrolledRepo;


    public VocabService(IRepository<Vocabulary> vocabRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Word> wordRepo, IRepository<Enrolled> enrolledRepo) {
        this.vocabRepo = vocabRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.wordRepo = wordRepo;
        this.enrolledRepo=enrolledRepo;
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

    public Vocabulary getVocabularyById(int vocabId){
        idDataCheck(vocabId);
        for (Vocabulary vocab : vocabRepo.getAll()) {
            if (vocab.getId()==vocabId)
                return vocab;
        }
        throw new EntityNotFoundException("Vocabulary course not found!");
    }

    public Word getWordById(int wordId){
        idDataCheck(wordId);
        for (Word word : wordRepo.getAll()) {
            if (word.getId()==wordId)
                return word;
        }
        throw new EntityNotFoundException("Word not found!");
    }

    public List<Student> getEnrolled(int courseId){
        List<Student> enrolled=new ArrayList<>();
        idDataCheck(courseId);
        for(Enrolled enrollment:enrolledRepo.getAll())
            if(enrollment.getCourse()==courseId)
                enrolled.add(getStudentById(enrollment.getStudent()));
        return enrolled;

    }

    public List<Vocabulary> getVocabCourses(int studentId){
        idDataCheck(studentId);
        List<Vocabulary> vocabCourses=new ArrayList<>();
        for (Vocabulary vocabulary:vocabRepo.getAll())
            for (Enrolled enrolled:enrolledRepo.getAll())
                if (vocabulary.getId()==enrolled.getCourse()&&enrolled.getStudent()==studentId)
                    vocabCourses.add(vocabulary);
        return vocabCourses;
    }

    public List<Word> getExercises(int courseId){
        List<Word> questions=new ArrayList<>();
        for (Word q:wordRepo.getAll())
            if (q.getVocabId()==courseId)
                questions.add(q);
        return questions;
    }

    public void enroll(int studentId, int vocabCourseId) {
        idDataCheck(studentId);
        idDataCheck(vocabCourseId);
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Vocabulary course = getVocabularyById(vocabCourseId);

        for (Vocabulary course1:getVocabCourses(studentId)){
            if (course1.getId()==vocabCourseId)
                alreadyEnrolled=1;
        }

        if (alreadyEnrolled==0){
            if (course.getAvailableSlots() > getEnrolled(vocabCourseId).size()) {
                int nextId=enrolledRepo.getAll().size();
                if (nextId==0)
                    nextId=1;
                else nextId+=1;
                Enrolled enrolled=new Enrolled(nextId,studentId,vocabCourseId);
                enrolledRepo.create(enrolled);
            }
        }

    }

    public List<Vocabulary> showEnrolledVocabularyCourses(int studentId){
        idDataCheck(studentId);
        return getVocabCourses(studentId);
    }

    public List<Word> practiceVocabulary(int studentId, int courseId) {
        idDataCheck(studentId);
        idDataCheck(courseId);

        int foundCourse=0;
        for (Course findCourse : getVocabCourses(studentId)){
            if (findCourse.getId()==courseId) {
                foundCourse=1;
                break;
            }
        }
        if (foundCourse==0){
            return new ArrayList<>();
        }
        return getExercises(courseId);
    }

    public String handleAnswer(int studentId, int wordId, String answer){
        idDataCheck(studentId);
        idDataCheck(wordId);
        stringDataCheck(answer);
        Word word=getWordById(wordId);
        if (answer.equals(word.getMeaning()))
            return "Correct!";
        else{
            //trebe adaugat in past word mistakes
            return "Wrong!";
        }
    }


    public List<Vocabulary> getAvailableVocabularyCourses(){
        return vocabRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        idDataCheck(courseId);
        return getEnrolled(courseId);
    }

    public List<Student> showStudentsEnrolledInVocabularyCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!getVocabCourses(student.getId()).isEmpty())
                studentList.add(student);
        return studentList;
    }

    public boolean removeCourse(int courseId, int teacherId) {
        idDataCheck(courseId);
        idDataCheck(teacherId);
        Vocabulary course = getVocabularyById(courseId);
        if (course.getTeacher()==teacherId){
            vocabRepo.delete(courseId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateVocabCourse(int courseId, int teacherId, String courseName, Integer maxStudents){
        idDataCheck(courseId);
        idDataCheck(teacherId);
        stringDataCheck(courseName);
        intDataCheck(maxStudents);
        int found=0;
        for (Vocabulary course: vocabRepo.getAll()){
            if (course.getId()==courseId)
            {
                found=1;
                updateVocabularyCourse(courseId,teacherId,courseName,maxStudents);
                return;
            }
        }
        if (found==0){
            createVocabularyCourse(courseId,teacherId,courseName,maxStudents);
        }
    }

    public void createVocabularyCourse(int courseId, int teacherId, String courseName, Integer maxStudents) {

        Vocabulary v1 = new Vocabulary(courseId, courseName, teacherId, maxStudents);
        vocabRepo.create(v1);
    }

    public void updateVocabularyCourse(int courseId, int teacherId, String courseName, Integer maxStudents) {

        Vocabulary v1 = getVocabularyById(courseId);
        v1.setTeacher(teacherId);
        v1.setCourseName(courseName);
        v1.setAvailableSlots(maxStudents);
        vocabRepo.update(v1);

    }


    public List<Vocabulary> viewVocabularyCoursesTaughtByTeacher(int teacherId){
        idDataCheck(teacherId);
        List<Vocabulary> taughtCourses=new ArrayList<>();
        for(Vocabulary course:vocabRepo.getAll())
            if (course.getTeacher()==teacherId)
                taughtCourses.add(course);
        return taughtCourses;

    }

    public void idDataCheck(int id){
        if (id<1)
            throw new ValidationException("Id cannot be less than 1!");
    }

    public void stringDataCheck(String string){
        if (string.isEmpty())
            throw new ValidationException("Name cannot be an empty string!");
    }

    public void intDataCheck(int number){
        if (number<1)
            throw new ValidationException("Number cannot be null!");
    }



}
