package org.example.service;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class GrammarService {
    private final IRepository<Grammar> grammarRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<Enrolled> enrolledRepo;

    private final IRepository<PastMistake> pastMistakesRepo;

    public GrammarService(IRepository<Grammar> grammarRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<Enrolled> enrolledRepo,IRepository<PastMistake> pastMistakesRepo) {
        this.grammarRepo = grammarRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.enrolledRepo = enrolledRepo;
        this.pastMistakesRepo=pastMistakesRepo;
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

    public Grammar getGrammarById(int grammarId){
        idDataCheck(grammarId);
        for (Grammar grammar : grammarRepo.getAll()) {
            if (grammar.getId()==grammarId)
                return grammar;
        }
        throw new EntityNotFoundException("Grammar course not found!");
    }

    public Question getQuestionById(int questionId){
        idDataCheck(questionId);
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        throw new EntityNotFoundException("Question not found!");
    }

    public PastMistake getPastMistakeById(int mistakeId){
        idDataCheck(mistakeId);
        for (PastMistake misteiccc : pastMistakesRepo.getAll()) {
            if (misteiccc.getId()==mistakeId)
                return misteiccc;
        }
        throw new EntityNotFoundException("Question not found!");
    }

    public List<PastMistake> getPastMistakes(int courseId, int studentId){
        idDataCheck(courseId);
        idDataCheck(studentId);
        List<PastMistake> misteics=new ArrayList<>();
        for( PastMistake pm: pastMistakesRepo.getAll())
            if (pm.getGrammarId()==courseId && pm.getStudentId()==studentId)
                misteics.add(pm);
        return misteics;
    }

    public List<Student> getEnrolled(int courseId){
        List<Student> enrolled=new ArrayList<>();
        idDataCheck(courseId);
        for(Enrolled enrollment:enrolledRepo.getAll())
            if(enrollment.getCourse()==courseId)
                enrolled.add(getStudentById(enrollment.getStudent()));
        return enrolled;

    }

    public List<Grammar> getGrammarCourses(int studentId){
        idDataCheck(studentId);
        List<Grammar> grammarCourses=new ArrayList<>();
        for (Grammar grammar:grammarRepo.getAll())
            for (Enrolled enrolled:enrolledRepo.getAll())
                if (grammar.getId()==enrolled.getCourse()&&enrolled.getStudent()==studentId)
                    grammarCourses.add(grammar);
        return grammarCourses;
    }

    public List<Question> getExercises(int courseId){
        List<Question> questions=new ArrayList<>();
        for (Question q:questionRepo.getAll())
            if (q.getGrammarId()==courseId)
                questions.add(q);
        return questions;
    }


    public void enroll(int studentId, int grammarCourseId) {
        idDataCheck(studentId);
        idDataCheck(grammarCourseId);
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Grammar course = getGrammarById(grammarCourseId);

        for (Course course1:getGrammarCourses(studentId)){
            if (course1.getId()==grammarCourseId)
                alreadyEnrolled=1;
        }

        if (alreadyEnrolled==0){
            if (course.getAvailableSlots() > getEnrolled(grammarCourseId).size()) {

                int nextId=enrolledRepo.getAll().size();
                if (nextId==0)
                    nextId=1;
                else nextId+=1;
                Enrolled enrolled=new Enrolled(nextId,studentId,grammarCourseId);
                enrolledRepo.create(enrolled);
            }
        }

    }

    public List<Grammar> showEnrolledGrammarCourses(int studentId){
        idDataCheck(studentId);
        return getGrammarCourses(studentId);
    }

    public List<Question> practiceGrammar(int studentId, int courseId) {
        idDataCheck(studentId);
        idDataCheck(courseId);

        int foundCourse=0;
        for (Course findCourse : getGrammarCourses(studentId)){
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

    public String handleAnswer(int studentId, int questionId, String answer){
        idDataCheck(studentId);
        idDataCheck(questionId);
        stringDataCheck(answer);

        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else {
            List<PastMistake> misteicssssss=pastMistakesRepo.getAll();
            PastMistake curucaca=misteicssssss.get(misteicssssss.size()-1);
            int aidi=curucaca.getId();
            PastMistake misteic=new PastMistake( aidi,question.getQuestion(), question.getRightAnswer(),studentId );
            misteic.setGrammarId(question.getGrammarId());
            pastMistakesRepo.create(misteic);  //aici o adaug in tabela vietii aca e greseala la practice
            return "Wrong!";
        }
    }

    public List<PastMistake> reviewPastMistakes(int studentId, int courseId){
        idDataCheck(studentId);
        idDataCheck(courseId);
        int isEnrolled=0;

        for (Grammar grammar: getGrammarCourses(studentId))
            if (grammar.getId()==courseId)
            {
                isEnrolled=1;
                break;
            }
        if (isEnrolled==0)
            return new ArrayList<>();
        else{
            return getPastMistakes(courseId,studentId);
        }
    }

    public String handlePMAnswer(int studentId, int pastMistakeId, String answer){
        idDataCheck(studentId);
        idDataCheck(pastMistakeId);
        stringDataCheck(answer);
        answerDataCheck(answer);

        PastMistake misteic=getPastMistakeById(pastMistakeId);
        if (answer.equals(misteic.getRightAnswer())) {
            pastMistakesRepo.delete(pastMistakeId);
            return "Correct!";
        }
        else{

            return "Wrong!";
        }
    }

    public List<Grammar> getAvailableGrammarCourses(){
        return grammarRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        idDataCheck(courseId);
        return getEnrolled(courseId);
    }

    public List<Student> showStudentsEnrolledInGrammarCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!getGrammarCourses(student.getId()).isEmpty())
                studentList.add(student);
        return studentList;
    }

    public boolean removeCourse(int courseId, int teacherId) {
        idDataCheck(courseId);
        idDataCheck(teacherId);
        Grammar course = getGrammarById(courseId);
        if (course.getTeacher()==teacherId){
            grammarRepo.delete(courseId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateGrammarCourse(int courseId, int teacherId, String courseName, Integer maxStudents){
        idDataCheck(courseId);
        idDataCheck(teacherId);
        stringDataCheck(courseName);
        intDataCheck(maxStudents);

        int found=0;
        for (Grammar course: grammarRepo.getAll()){
            if (course.getId()==courseId)
            {
                found=1;
                updateGrammarCourse(courseId,teacherId,courseName,maxStudents);
                return;
            }
        }
        if (found==0){
            createGrammarCourse(courseId,teacherId,courseName,maxStudents);
        }
    }

    public void createGrammarCourse(int courseId, int teacherId, String courseName, Integer maxStudents) {

        Grammar g1 = new Grammar(courseId, courseName, teacherId, maxStudents);
        grammarRepo.create(g1);

    }

    public void updateGrammarCourse(int courseId, int teacherId, String courseName, Integer maxStudents) {

        Grammar g1 = getGrammarById(courseId);
        g1.setTeacher(teacherId);
        g1.setCourseName(courseName);
        g1.setAvailableSlots(maxStudents);
        grammarRepo.update(g1);
    }

    public List<Grammar> viewGrammarCoursesTaughtByTeacher(int teacherId){
        idDataCheck(teacherId);
        List<Grammar> taughtCourses=new ArrayList<>();
        for(Grammar course:grammarRepo.getAll())
            if (course.getTeacher()==teacherId)
                taughtCourses.add(course);
        return taughtCourses;

    }

    public void answerDataCheck(String string){
        if (!string.equals("wahr") && !string.equals("falsch"))
            throw new ValidationException("Invalid answer!");
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
