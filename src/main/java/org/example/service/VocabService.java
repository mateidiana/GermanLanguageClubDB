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

    public void enroll(int studentId, int vocabCourseId) {
        idDataCheck(studentId);
        idDataCheck(vocabCourseId);
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Vocabulary course = getVocabularyById(vocabCourseId);

        for (Course course1:student.getVocabCourses()){
            if (course1.getId()==vocabCourseId)
                alreadyEnrolled=1;
        }

        if (alreadyEnrolled==0){
            studentRepo.delete(studentId);
            vocabRepo.delete(vocabCourseId);
            if (course.getAvailableSlots() > course.getEnrolledStudents().size()) {
                course.getEnrolledStudents().add(student);
                student.getVocabCourses().add(course);
                vocabRepo.create(course);
                studentRepo.create(student);

                int nextId=enrolledRepo.getAll().size();
                Enrolled enrolled=new Enrolled(nextId,studentId,vocabCourseId);
                enrolledRepo.create(enrolled);
            }
        }

    }

    public List<Vocabulary> showEnrolledVocabularyCourses(int studentId){
        idDataCheck(studentId);
        Student student=getStudentById(studentId);
        return student.getVocabCourses();
    }

    public List<Word> practiceVocabulary(int studentId, int courseId) {
        idDataCheck(studentId);
        idDataCheck(courseId);

        Student student = getStudentById(studentId);
        Vocabulary course = getVocabularyById(courseId);
        List<Word> questions=new ArrayList<>();

        int foundCourse=0;
        for (Course findCourse : student.getVocabCourses()){
            if (findCourse.getId()==courseId) {
                foundCourse=1;
                break;
            }
        }
        if (foundCourse==0){
            return new ArrayList<>();
        }
        return course.getWords();
    }

    public String handleAnswer(int studentId, int wordId, String answer){
        idDataCheck(studentId);
        idDataCheck(wordId);
        stringDataCheck(answer);
        Word word=getWordById(wordId);
        if (answer.equals(word.getMeaning()))
            return "Correct!";
        else{
//            Student student=getStudentById(studentId);
//            List<Word> pastMistakes=student.getPastVocabMistakes();
//            pastMistakes.add(word);
//            student.setPastVocabMistakes(pastMistakes);
//            studentRepo.update(student);
            return "Wrong!";
        }
    }

//    public List<Word> reviewPastVocabMistakes(int studentId){
//        idDataCheck(studentId);
//        Student student= getStudentById(studentId);
//        return student.getPastVocabMistakes();
//    }


    public List<Vocabulary> getAvailableVocabularyCourses(){
        return vocabRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        idDataCheck(courseId);
        Vocabulary course = getVocabularyById(courseId);
        return course.getEnrolledStudents();
    }

    public List<Student> showStudentsEnrolledInVocabularyCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!student.getVocabCourses().isEmpty())
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
        List<Word> vocabExercises=new ArrayList<>();
        vocabExercises.add(new Word(41, "Hund", "dog"));
        vocabExercises.add(new Word(42, "Katze", "cat"));
        vocabExercises.add(new Word(43, "Apfel", "apple"));
        vocabExercises.add(new Word(44, "Buch", "book"));
        vocabExercises.add(new Word(45, "Haus", "house"));
        vocabExercises.add(new Word(46, "Auto", "car"));
        vocabExercises.add(new Word(47, "Baum", "tree"));
        vocabExercises.add(new Word(48, "Blume", "flower"));
        vocabExercises.add(new Word(49, "Fisch", "fish"));
        vocabExercises.add(new Word(50, "Brot", "bread"));
        vocabExercises.add(new Word(51, "Schule", "school"));
        v1.setWords(vocabExercises);
        vocabRepo.update(v1);
    }

    public void updateVocabularyCourse(int courseId, int teacherId, String courseName, Integer maxStudents) {

        Vocabulary course = getVocabularyById(courseId);
        Teacher teacher = getTeacherById(teacherId);
        vocabRepo.delete(courseId);
        Vocabulary v1 = new Vocabulary(courseId, courseName, teacherId, maxStudents);
        vocabRepo.create(v1);
        List<Word> vocabExercises=new ArrayList<>();
        vocabExercises.add(new Word(41, "Hund", "dog"));
        vocabExercises.add(new Word(42, "Katze", "cat"));
        vocabExercises.add(new Word(43, "Apfel", "apple"));
        vocabExercises.add(new Word(44, "Buch", "book"));
        vocabExercises.add(new Word(45, "Haus", "house"));
        vocabExercises.add(new Word(46, "Auto", "car"));
        vocabExercises.add(new Word(47, "Baum", "tree"));
        vocabExercises.add(new Word(48, "Blume", "flower"));
        vocabExercises.add(new Word(49, "Fisch", "fish"));
        vocabExercises.add(new Word(50, "Brot", "bread"));
        vocabExercises.add(new Word(51, "Schule", "school"));
        v1.setWords(vocabExercises);
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
