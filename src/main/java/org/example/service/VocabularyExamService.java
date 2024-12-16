package org.example.service;
import java.util.*;

import org.example.model.*;
import org.example.repo.IRepository;


public class VocabularyExamService {
    private final IRepository<VocabularyExam> vocabExamRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Word> wordRepo;

    private final IRepository<ExamResult> examResultRepo;

    public VocabularyExamService(IRepository<VocabularyExam> vocabExamRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Word> wordRepo, IRepository<ExamResult> examResultRepo) {
        this.vocabExamRepo = vocabExamRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.wordRepo = wordRepo;
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

    public VocabularyExam getVocabExamById(int grammarId){
        for (VocabularyExam vocabExam : vocabExamRepo.getAll()) {
            if (vocabExam.getId()==grammarId)
                return vocabExam;
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

    public ExamResult getExamResultById(int resultId){
        for (ExamResult result:examResultRepo.getAll())
        {
            if (result.getId()==resultId)
                return result;
        }
        return null;
    }

    public List<Word> takeVocabExam(int studentId, int examId){
        if (getStudentById(studentId).getVocabCourses().isEmpty())
            return new ArrayList<>();
        else return getVocabExamById(examId).getWords();
    }

    public String handleAnswer(int studentId, int wordId, String answer){
        Word word=getWordById(wordId);
        if (answer.equals(word.getMeaning()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showVocabExamResults(int studentId){
        List<ExamResult> allResults=getStudentById(studentId).getResults();
        List<ExamResult> allVocabResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getVocabExamById(result.getExam())!=null)
                allVocabResults.add(result);
        return allVocabResults;
    }

    public void addResult(int studentId, int examId, Float result){
        int nextId=examResultRepo.getAll().size();
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

        Student student=getStudentById(studentId);
        student.getResults().add(examResult);
        studentRepo.update(student);
    }

    public List<VocabularyExam> showAllVocabExams(){
        return vocabExamRepo.getAll();
    }

    public List<VocabularyExam> examsOfATeacher(int teacherId){
        List<VocabularyExam> exams=new ArrayList<>();
        for (VocabularyExam exam:vocabExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a grammar exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        List<ExamResult> allVocabResults=new ArrayList<>();
        if (getVocabExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:student.getResults())
                    if(result.getExam()==examId)
                        allVocabResults.add(result);
        }
        return allVocabResults;

    }

    public boolean removeVocabExam(int examId, int teacherId) {
        VocabularyExam exam = getVocabExamById(examId);
        if (exam.getTeacher()==teacherId){
            vocabExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateVocabExam(int examId, int teacherId, String examName, int exerciseSet){
        int found=0;
        for (VocabularyExam exam: vocabExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateVocabExam(examId,teacherId,examName,exerciseSet);
                return;
            }
        }
        if (found==0){
            createVocabExam(examId,teacherId,examName,exerciseSet);
        }
    }

    public void createVocabExam(int examId, int teacherId, String examName, int exerciseSet){
        VocabularyExam e1=new VocabularyExam(examId,examName,teacherId);
        vocabExamRepo.create(e1);
        if(exerciseSet==1)
        {
            int nextId=wordRepo.getAll().size();
            Word w1=new Word(nextId,"tree","Baum");
            wordRepo.create(w1);
            Word w2=new Word(nextId+1,"flower","Blume");
            wordRepo.create(w2);
            Word w3=new Word(nextId+2,"house","Haus");
            wordRepo.create(w3);

            w1.setVocabExamId(examId);
            w2.setVocabExamId(examId);
            w3.setVocabExamId(examId);
            wordRepo.update(w1);
            wordRepo.update(w2);
            wordRepo.update(w3);
            List<Word> words=new ArrayList<>();
            words.add(w1);
            words.add(w2);
            words.add(w3);
            e1.setWords(words);

        }

        vocabExamRepo.update(e1);
    }

    public void updateVocabExam(int examId, int teacherId,String courseName, int exerciseSet){
        VocabularyExam exam=getVocabExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);

        if(exerciseSet==1)
        {
            int nextId=wordRepo.getAll().size();
            Word w1=new Word(nextId,"tree","Baum");
            wordRepo.create(w1);
            Word w2=new Word(nextId+1,"flower","Blume");
            wordRepo.create(w2);
            Word w3=new Word(nextId+2,"house","Haus");
            wordRepo.create(w3);

            w1.setVocabExamId(examId);
            w2.setVocabExamId(examId);
            w3.setVocabExamId(examId);
            wordRepo.update(w1);
            wordRepo.update(w2);
            wordRepo.update(w3);

            List<Word> words=new ArrayList<>();
            words.add(w1);
            words.add(w2);
            words.add(w3);
            exam.setWords(words);
        }
        vocabExamRepo.update(exam);
    }

    public List<Student> filterStudentsByBestGradeOnVocabExam(int examId){
        List<Student> filteredStud=new ArrayList<>();
        for (Student stud:studentRepo.getAll())
            for (ExamResult result:stud.getResults())
                if (result.getExam()==examId)
                    if (result.getResult()==10.0)
                        filteredStud.add(stud);
        return filteredStud;
    }

}




