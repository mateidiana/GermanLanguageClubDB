package org.example.controller;
import org.example.service.ReadingExamService;
import org.example.service.VocabularyExamService;
import org.example.service.GrammarExamService;
import org.example.model.Exceptions.*;
import org.example.model.*;

import java.util.List;

public class ExamController {
    private ReadingExamService readingExamService;
    private GrammarExamService grammarExamService;
    private VocabularyExamService vocabExamService;

    public ExamController(ReadingExamService readingExamService,GrammarExamService grammarExamService,VocabularyExamService vocabExamService)
    {
        this.readingExamService=readingExamService;
        this.grammarExamService=grammarExamService;
        this.vocabExamService=vocabExamService;
    }

    public List<Question> takeReadingExam(int studentId, int examId){return readingExamService.takeReadingExam(studentId,examId);}

    public String handleReadingAnswer(int studentId, int questionId, String answer){return readingExamService.handleAnswer(studentId,questionId,answer);}

    public List<ExamResult> showReadingExamResults(int studentId){return readingExamService.showReadingExamResults(studentId);}

    public void addReadingResult(int studentId, int examId, Float result){ readingExamService.addResult(studentId,examId,result);}

    public List<ReadingExam> showAllReadingExams(){return readingExamService.showAllReadingExams();}

    public List<ReadingExam> readingExamsOfATeacher(int teacherId){return readingExamService.examsOfATeacher(teacherId);}

    public List<ExamResult> showAllResultsOfReadingTeacherExam(int teacherId, int examId){return readingExamService.showAllResultsOfTeacherExam(teacherId,examId);}

    public boolean removeReadingExam(int examId, int teacherId){return readingExamService.removeReadingExam(examId,teacherId);}

    public void createOrUpdateReadingExam(int examId, int teacherId, String examName){readingExamService.createOrUpdateReadingExam(examId,teacherId,examName);}

    public List<Student> filterStudentsByPassingGradeOnReadingExam(int examId){return readingExamService.filterStudentsByPassingGradeOnReadingExam(examId);}



    public List<Question> takeGrammarExam(int studentId, int examId){return grammarExamService.takeGrammarExam(studentId,examId);}

    public String handleGrammarAnswer(int studentId, int questionId, String answer){return grammarExamService.handleAnswer(studentId,questionId,answer);}

    public List<ExamResult> showGrammarExamResults(int studentId){return grammarExamService.showGrammarExamResults(studentId);}

    public void addGrammarResult(int studentId, int examId, Float result){ grammarExamService.addResult(studentId,examId,result);}

    public List<GrammarExam> showAllGrammarExams(){return grammarExamService.showAllGrammarExams();}

    public List<GrammarExam> grammarExamsOfATeacher(int teacherId){return grammarExamService.examsOfATeacher(teacherId);}

    public List<ExamResult> showAllResultsOfGrammarTeacherExam(int teacherId, int examId){return grammarExamService.showAllResultsOfTeacherExam(teacherId,examId);}

    public boolean removeGrammarExam(int examId, int teacherId){return grammarExamService.removeGrammarExam(examId,teacherId);}

    public void createOrUpdateGrammarExam(int examId, int teacherId, String examName){grammarExamService.createOrUpdateGrammarExam(examId,teacherId,examName);}



    public List<Word> takeVocabExam(int studentId, int examId){return vocabExamService.takeVocabExam(studentId,examId);}

    public String handleVocabAnswer(int studentId, int questionId, String answer){return vocabExamService.handleAnswer(studentId,questionId,answer);}

    public List<ExamResult> showVocabExamResults(int studentId){return vocabExamService.showVocabExamResults(studentId);}

    public void addVocabResult(int studentId, int examId, Float result){ vocabExamService.addResult(studentId,examId,result);}

    public List<VocabularyExam> showAllVocabExams(){return vocabExamService.showAllVocabExams();}

    public List<VocabularyExam> vocabExamsOfATeacher(int teacherId){return vocabExamService.examsOfATeacher(teacherId);}

    public List<ExamResult> showAllResultsOfVocabTeacherExam(int teacherId, int examId){return vocabExamService.showAllResultsOfTeacherExam(teacherId,examId);}

    public boolean removeVocabExam(int examId, int teacherId){return vocabExamService.removeVocabExam(examId,teacherId);}

    public void createOrUpdateVocabExam(int examId, int teacherId, String examName){vocabExamService.createOrUpdateVocabExam(examId,teacherId,examName);}





}
