package org.example.service;
import java.util.*;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;


public class VocabularyExamService {
    private final IRepository<VocabularyExam> vocabExamRepo;

    private final IRepository<Vocabulary> vocabRepo;

    private final IRepository<Enrolled> enrolledRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Word> wordRepo;

    private final IRepository<ExamResult> examResultRepo;

    public VocabularyExamService(IRepository<VocabularyExam> vocabExamRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Word> wordRepo, IRepository<ExamResult> examResultRepo, IRepository<Vocabulary> vocabRepo, IRepository<Enrolled> enrolledRepo) {
        this.vocabExamRepo = vocabExamRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.wordRepo = wordRepo;
        this.examResultRepo=examResultRepo;
        this.vocabRepo=vocabRepo;
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

    public VocabularyExam getVocabExamById(int vocabId){
        idDataCheck(vocabId);
        for (VocabularyExam vocabExam : vocabExamRepo.getAll()) {
            if (vocabExam.getId()==vocabId)
                return vocabExam;
        }
        throw new EntityNotFoundException("Vocabulary exam not found!");
    }

    public Word getWordById(int wordId){
        idDataCheck(wordId);
        for (Word word : wordRepo.getAll()) {
            if (word.getId()==wordId)
                return word;
        }
        throw new EntityNotFoundException("Word not found!");
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

    public List<Word> getExercises(int examId){
        List<Word> questions=new ArrayList<>();
        for (Word q:wordRepo.getAll())
            if (q.getVocabExamId()==examId)
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

    public List<Vocabulary> getVocabCourses(int studentId){
        idDataCheck(studentId);
        List<Vocabulary> vocabCourses=new ArrayList<>();
        for (Vocabulary vocabulary:vocabRepo.getAll())
            for (Enrolled enrolled:enrolledRepo.getAll())
                if (vocabulary.getId()==enrolled.getCourse()&&enrolled.getStudent()==studentId)
                    vocabCourses.add(vocabulary);
        return vocabCourses;
    }

    public List<Word> takeVocabExam(int studentId, int examId){
        idDataCheck(studentId);
        idDataCheck(examId);
        if (getVocabCourses(studentId).isEmpty())
            return new ArrayList<>();
        return getExercises(examId);
    }

    public String handleAnswer(int studentId, int wordId, String answer){
        idDataCheck(studentId);
        idDataCheck(wordId);
        stringDataCheck(answer);
        Word word=getWordById(wordId);
        if (answer.equals(word.getMeaning()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showVocabExamResults(int studentId){
        idDataCheck(studentId);
        List<ExamResult> allResults=getResults(studentId);
        List<ExamResult> allVocabResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getVocabExamById(result.getExam())!=null)
                allVocabResults.add(result);
        return allVocabResults;
    }

    public void addResult(int studentId, int examId, Float result){
        idDataCheck(studentId);
        idDataCheck(examId);
        int nextId=examResultRepo.getAll().size()+1;
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

    }

    public List<VocabularyExam> showAllVocabExams(){
        return vocabExamRepo.getAll();
    }

    public List<VocabularyExam> examsOfATeacher(int teacherId){
        idDataCheck(teacherId);
        List<VocabularyExam> exams=new ArrayList<>();
        for (VocabularyExam exam:vocabExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a grammar exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        idDataCheck(teacherId);
        idDataCheck(examId);
        List<ExamResult> allVocabResults=new ArrayList<>();
        if (getVocabExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:getResults(student.getId()))
                    if(result.getExam()==examId)
                        allVocabResults.add(result);
        }
        return allVocabResults;

    }

    public boolean removeVocabExam(int examId, int teacherId) {
        idDataCheck(teacherId);
        idDataCheck(examId);
        VocabularyExam exam = getVocabExamById(examId);
        if (exam.getTeacher()==teacherId){
            vocabExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateVocabExam(int examId, int teacherId, String examName){
        idDataCheck(teacherId);
        idDataCheck(examId);
        stringDataCheck(examName);
        int found=0;
        for (VocabularyExam exam: vocabExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateVocabExam(examId,teacherId,examName);
                return;
            }
        }
        if (found==0){
            createVocabExam(examId,teacherId,examName);
        }
    }

    public void createVocabExam(int examId, int teacherId, String examName){
        VocabularyExam e1=new VocabularyExam(examId,examName,teacherId);
        vocabExamRepo.create(e1);
    }

    public void updateVocabExam(int examId, int teacherId, String courseName){
        VocabularyExam exam=getVocabExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);

        vocabExamRepo.update(exam);
    }

    public List<Student> filterStudentsByBestGradeOnVocabExam(int examId){
        idDataCheck(examId);
        List<Student> filteredStud=new ArrayList<>();
        for (Student stud:studentRepo.getAll())
            for (ExamResult result:getResults(stud.getId()))
                if (result.getExam()==examId)
                    if (result.getResult()==10.0)
                        filteredStud.add(stud);
        return filteredStud;
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




