package org.example.service;

import org.example.model.*;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class VocabService {
    private final IRepository<Vocabulary> vocabRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Word> wordRepo;


    public VocabService(IRepository<Vocabulary> vocabRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Word> wordRepo) {
        this.vocabRepo = vocabRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.wordRepo = wordRepo;
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

    public Vocabulary getVocabularyById(int vocabId){
        for (Vocabulary vocab : vocabRepo.getAll()) {
            if (vocab.getId()==vocabId)
                return vocab;
        }
        return null;
    }

    public Word getWordById(int wordId){
        for (Word word : wordRepo.getAll()) {
            if (word.getId()==wordId)
                return word;
        }
        return null;
    }

    public void enroll(Integer studentId, Integer vocabCourseId) {
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
            }
        }

    }

    public List<Word> practiceVocabulary(Integer studentId, Integer courseId) {

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
        Word word=getWordById(wordId);
        if (answer.equals(word.getMeaning()))
            return "Correct!";
        else{
            Student student=getStudentById(studentId);
            List<Word> pastMistakes=student.getPastVocabMistakes();
            pastMistakes.add(word);
            student.setPastVocabMistakes(pastMistakes);
            studentRepo.update(student);
            return "Wrong!";
        }
    }

    public List<Word> reviewPastVocabMistakes(int studentId){
        Student student= getStudentById(studentId);
        return student.getPastVocabMistakes();
    }

    public List<Vocabulary> showEnrolledVocabularyCourses(int studentId){
        Student student=getStudentById(studentId);
        return student.getVocabCourses();
    }


    public List<Vocabulary> getAvailableVocabularyCourses(){
        return vocabRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
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
        Vocabulary course = getVocabularyById(courseId);
        if (course.getTeacher()==teacherId){
            vocabRepo.delete(courseId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateVocabCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
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

    public void createVocabularyCourse(Integer courseId, Integer teacherId, String courseName, Integer maxStudents) {

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

    public void updateVocabularyCourse(Integer courseId, Integer teacherId, String courseName, Integer maxStudents) {

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
        List<Vocabulary> taughtCourses=new ArrayList<>();
        for(Vocabulary course:vocabRepo.getAll())
            if (course.getTeacher()==teacherId)
                taughtCourses.add(course);
        return taughtCourses;

    }



}
