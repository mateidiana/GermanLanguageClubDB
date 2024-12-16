package org.example.service;
import java.util.*;

import org.example.model.*;
import org.example.repo.IRepository;


public class GrammarExamService {
    private final IRepository<GrammarExam> grammarExamRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<ExamResult> examResultRepo;

    public GrammarExamService(IRepository<GrammarExam> grammarExamRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<ExamResult> examResultRepo) {
        this.grammarExamRepo = grammarExamRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.examResultRepo=examResultRepo;
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

    public GrammarExam getGrammarExamById(int grammarId){
        for (GrammarExam grammarExam : grammarExamRepo.getAll()) {
            if (grammarExam.getId()==grammarId)
                return grammarExam;
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

    public ExamResult getExamResultById(int resultId){
        for (ExamResult result:examResultRepo.getAll())
        {
            if (result.getId()==resultId)
                return result;
        }
        return null;
    }

    public List<Question> takeGrammarExam(int studentId, int examId){
        if (getStudentById(studentId).getGrammarCourses().isEmpty())
            return new ArrayList<>();
        else return getGrammarExamById(examId).getExercises();
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showGrammarExamResults(int studentId){
        List<ExamResult> allResults=getStudentById(studentId).getResults();
        List<ExamResult> allGrammarResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getGrammarExamById(result.getExam())!=null)
                allGrammarResults.add(result);
        return allGrammarResults;
    }

    public void addResult(int studentId, int examId, Float result){
        int nextId=examResultRepo.getAll().size();
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

        Student student=getStudentById(studentId);
        student.getResults().add(examResult);
        studentRepo.update(student);
    }

    public List<GrammarExam> showAllGrammarExams(){
        return grammarExamRepo.getAll();
    }

    public List<GrammarExam> examsOfATeacher(int teacherId){
        List<GrammarExam> exams=new ArrayList<>();
        for (GrammarExam exam:grammarExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a grammar exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        List<ExamResult> allGrammarResults=new ArrayList<>();
        if (getGrammarExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:student.getResults())
                    if(result.getExam()==examId)
                        allGrammarResults.add(result);
        }
        return allGrammarResults;

    }

    public boolean removeGrammarExam(int examId, int teacherId) {
        GrammarExam exam = getGrammarExamById(examId);
        if (exam.getTeacher()==teacherId){
            grammarExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateGrammarExam(int examId, int teacherId, String examName, int exerciseSet){
        int found=0;
        for (GrammarExam exam: grammarExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateGrammarExam(examId,teacherId,examName,exerciseSet);
                return;
            }
        }
        if (found==0){
            createGrammarExam(examId,teacherId,examName,exerciseSet);
        }
    }

    public void createGrammarExam(int examId, int teacherId, String examName, int exerciseSet){
        GrammarExam e1=new GrammarExam(examId,examName,teacherId);
        grammarExamRepo.create(e1);
        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Du (brauchen) _ Hilfe.","brauchst");
            questionRepo.create(q1);
            q1.setGrammarExamId(examId);
            questionRepo.update(q1);
            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            e1.setExercises(questions);

        }

        grammarExamRepo.update(e1);
    }

    public void updateGrammarExam(int examId, int teacherId,String courseName, int exerciseSet){
        GrammarExam exam=getGrammarExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);

        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Du (brauchen) _ Hilfe.","brauchst");
            questionRepo.create(q1);
            q1.setGrammarId(examId);
            questionRepo.update(q1);

            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            exam.setExercises(questions);
        }
        grammarExamRepo.update(exam);
    }

    //sort students by avg grade on all exams

}
