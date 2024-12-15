package org.example.service;

import org.example.model.*;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class GrammarService {
    private final IRepository<Grammar> grammarRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;


    public GrammarService(IRepository<Grammar> readingRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo) {
        this.grammarRepo = readingRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
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

    public Grammar getGrammarById(int grammarId){
        for (Grammar grammar : grammarRepo.getAll()) {
            if (grammar.getId()==grammarId)
                return grammar;
        }
        return null;
    }

    public List<Grammar> showEnrolledGrammarCourses(int studentId){
        Student student=getStudentById(studentId);
        return student.getGrammarCourses();
    }

    public void enroll(Integer studentId, Integer grammarCourseId) {
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Grammar course = getGrammarById(grammarCourseId);

        for (Course course1:student.getGrammarCourses()){
            if (course1.getId()==grammarCourseId)
                alreadyEnrolled=1;
        }

        if (alreadyEnrolled==0){
            studentRepo.delete(studentId);
            grammarRepo.delete(grammarCourseId);
            if (course.getAvailableSlots() > course.getEnrolledStudents().size()) {
                course.getEnrolledStudents().add(student);
                student.getGrammarCourses().add(course);
                grammarRepo.create(course);
                studentRepo.create(student);
            }
        }

    }

    public List<Question> practiceGrammar(Integer studentId, Integer courseId) {

        Student student = getStudentById(studentId);
        Grammar course = getGrammarById(courseId);
        List<Question> questions=new ArrayList<>();

        int foundCourse=0;
        for (Course findCourse : student.getGrammarCourses()){
            if (findCourse.getId()==courseId) {
                foundCourse=1;
                break;
            }
        }
        if (foundCourse==0){
            return new ArrayList<>();
            }
            return course.getExercises();
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

    public Question getQuestionById(int questionId){
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        return null;
    }

    public List<Question> reviewPastMistakes(Integer studentId, Integer courseId) {

        Student student = getStudentById(studentId);
        Grammar course = getGrammarById(courseId);

        int foundCourse=0;
        for (Course findCourse : student.getGrammarCourses()){
            if (findCourse.getId()==courseId) {
                foundCourse=1;
                break;
            }
        }
        if (foundCourse==0){
            return new ArrayList<>();
        }
        return student.getPastGrammarMistakes();
    }

    public List<Grammar> getAvailableGrammarCourses(){
        return grammarRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        Grammar course = getGrammarById(courseId);
        return course.getEnrolledStudents();
    }

    public List<Student> showStudentsEnrolledInGrammarCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!student.getGrammarCourses().isEmpty())
                studentList.add(student);
        return studentList;
    }

    public void createGrammarCourse(Integer courseId, Integer teacherId, String courseName, Integer maxStudents) {

        Grammar g1 = new Grammar(courseId, courseName, teacherId, maxStudents);
        grammarRepo.create(g1);
        List<Question> grammarExercises=new ArrayList<>();
        grammarExercises.add(new Question(21, "Du (brauchen) _ Hilfe.", "brauchst"));
        grammarExercises.add(new Question(22, "Ich bin _ Hause.", "zu"));
        grammarExercises.add(new Question(23, "Er trägt _.", "bei"));
        grammarExercises.add(new Question(24, "Diana (setzen)_ sich auf das Sofa.", "setzt"));
        grammarExercises.add(new Question(25, "Stefi klettert auf _ Baum.", "den"));
        grammarExercises.add(new Question(26, "Ich (besuchen) _ diese Kirche.", "besuche"));
        grammarExercises.add(new Question(27, "Wir spielen DOTA in _ Klasse.", "der"));
        grammarExercises.add(new Question(28, "Mama kocht immer (lecker)_ Essen", "leckeres"));
        grammarExercises.add(new Question(29, "Der Ball ist unter _ Tisch gerollt.", "den"));
        grammarExercises.add(new Question(30, "Mein Mann kommt immer betrunken _ Hause.", "nach"));
        g1.setExercises(grammarExercises);
        grammarRepo.update(g1);
    }

    public void updateGrammarCourse(Integer courseId, Integer teacherId, String courseName, Integer maxStudents) {

        Grammar course = getGrammarById(courseId);
        Teacher teacher = getTeacherById(teacherId);
        grammarRepo.delete(courseId);
        Grammar g1 = new Grammar(courseId, courseName, teacherId, maxStudents);
        grammarRepo.create(g1);
        List<Question> grammarExercises=new ArrayList<>();
        grammarExercises.add(new Question(21, "Du (brauchen) _ Hilfe.", "brauchst"));
        grammarExercises.add(new Question(22, "Ich bin _ Hause.", "zu"));
        grammarExercises.add(new Question(23, "Er trägt _.", "bei"));
        grammarExercises.add(new Question(24, "Diana (setzen)_ sich auf das Sofa.", "setzt"));
        grammarExercises.add(new Question(25, "Stefi klettert auf _ Baum.", "den"));
        grammarExercises.add(new Question(26, "Ich (besuchen) _ diese Kirche.", "besuche"));
        grammarExercises.add(new Question(27, "Wir spielen DOTA in _ Klasse.", "der"));
        grammarExercises.add(new Question(28, "Mama kocht immer (lecker)_ Essen", "leckeres"));
        grammarExercises.add(new Question(29, "Der Ball ist unter _ Tisch gerollt.", "den"));
        grammarExercises.add(new Question(30, "Mein Mann kommt immer betrunken _ Hause.", "nach"));
        g1.setExercises(grammarExercises);
        g1.setExercises(grammarExercises);
        grammarRepo.update(g1);
    }
    public boolean removeCourse(int courseId, int teacherId) {
        Grammar course = getGrammarById(courseId);
        if (course.getTeacher()==teacherId){
            grammarRepo.delete(courseId);
            return true;
        }
        else
            return false;
    }

    public void viewCourseTaughtByTeacher(Integer teacherId) {

        for (Grammar course : grammarRepo.getAll()) {
            if (course.getTeacher()==teacherId) {
                System.out.println(course.getCourseName());
            }
        }
    }

    public void showEnrolledGrammarCourses(Integer studentId){
        Student student = getStudentById(studentId);
        for (Course course:student.getGrammarCourses())
            if (course.getCourseName().contains("Grammar"))
                System.out.println(course);
    }
}
