package org.example.service;
import java.util.*;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;


public class GrammarExamService {
    private final IRepository<GrammarExam> grammarExamRepo;

    private final IRepository<Grammar> grammarRepo;

    private final IRepository<Enrolled> enrolledRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<ExamResult> examResultRepo;

    public GrammarExamService(IRepository<GrammarExam> grammarExamRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<ExamResult> examResultRepo, IRepository<Grammar> grammarRepo, IRepository<Enrolled> enrolledRepo) {
        this.grammarExamRepo = grammarExamRepo;
        this.grammarRepo=grammarRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.examResultRepo=examResultRepo;
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

    public GrammarExam getGrammarExamById(int grammarId){
        idDataCheck(grammarId);
        for (GrammarExam grammarExam : grammarExamRepo.getAll()) {
            if (grammarExam.getId()==grammarId)
                return grammarExam;
        }
        throw new EntityNotFoundException("Grammar exam not found!");
    }

    public Question getQuestionById(int questionId){
        idDataCheck(questionId);
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        throw new EntityNotFoundException("Question not found!");
    }

    public ExamResult getExamResultById(int resultId){
        idDataCheck(resultId);
        for (ExamResult result:examResultRepo.getAll())
        {
            if (result.getId()==resultId)
                return result;
        }
        throw new EntityNotFoundException("Exam Result not found!");
    }

    public List<Question> getExercises(int examId){
        List<Question> questions=new ArrayList<>();
        for (Question q:questionRepo.getAll())
            if (q.getGrammarExamId()==examId)
                questions.add(q);
        return questions;
    }

    public List<ExamResult> getResults(int studentId){
        List<ExamResult> results=new ArrayList<>();
        for (ExamResult result:examResultRepo.getAll())
            if (result.getStudent()==studentId)
                results.add(result);
        return results;
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

    public List<Question> takeGrammarExam(int studentId, int examId){
        idDataCheck(studentId);
        idDataCheck(examId);
        if (getGrammarCourses(studentId).isEmpty())
            return new ArrayList<>();
        return getExercises(examId);
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        idDataCheck(studentId);
        idDataCheck(questionId);
        stringDataCheck(answer);
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showGrammarExamResults(int studentId){
        idDataCheck(studentId);
        List<ExamResult> allResults=getResults(studentId);
        List<ExamResult> allGrammarResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getGrammarExamById(result.getExam())!=null)
                allGrammarResults.add(result);
        return allGrammarResults;
    }

    public void addResult(int studentId, int examId, Float result){
        idDataCheck(studentId);
        idDataCheck(examId);
        int nextId=examResultRepo.getAll().size()+1;
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

    }

    public List<GrammarExam> showAllGrammarExams(){
        return grammarExamRepo.getAll();
    }

    public List<GrammarExam> examsOfATeacher(int teacherId){
        idDataCheck(teacherId);
        List<GrammarExam> exams=new ArrayList<>();
        for (GrammarExam exam:grammarExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a grammar exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        idDataCheck(teacherId);
        idDataCheck(examId);
        List<ExamResult> allGrammarResults=new ArrayList<>();
        if (getGrammarExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:getResults(student.getId()))
                    if(result.getExam()==examId)
                        allGrammarResults.add(result);
        }
        return allGrammarResults;

    }

    public boolean removeGrammarExam(int examId, int teacherId) {
        idDataCheck(examId);
        idDataCheck(teacherId);
        GrammarExam exam = getGrammarExamById(examId);
        if (exam.getTeacher()==teacherId){
            grammarExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateGrammarExam(int examId, int teacherId, String examName){
        idDataCheck(examId);
        idDataCheck(teacherId);
        stringDataCheck(examName);

        int found=0;
        for (GrammarExam exam: grammarExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateGrammarExam(examId,teacherId,examName);
                return;
            }
        }
        if (found==0){
            createGrammarExam(examId,teacherId,examName);
        }
    }

    public void createGrammarExam(int examId, int teacherId, String examName){
        GrammarExam e1=new GrammarExam(examId,examName,teacherId);
        grammarExamRepo.create(e1);

    }

    public void updateGrammarExam(int examId, int teacherId, String courseName){
        GrammarExam exam=getGrammarExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);
        grammarExamRepo.update(exam);
    }

    //sort students by avg grade on all exams

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
