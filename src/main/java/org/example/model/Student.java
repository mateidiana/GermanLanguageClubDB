package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private List<Reading> readingCourses = new ArrayList<>();
    private List<Grammar> grammarCourses = new ArrayList<>();
    private List<Writing> writingCourses = new ArrayList<>();
    private List<Vocabulary> vocabCourses = new ArrayList<>();
    private List<Question> pastReadingMistakes;
    private List<Question> pastGrammarMistakes;
    private List<Word> pastVocabMistakes;
    private List<ExamResult> examResults;
    //private Map<Writing, Float> writingFeedback=new HashMap<>();
    private List<WritingFeedback> writingFeedback;

    public Student(String name, int studentId) {
        super(studentId, name);
    }

    public List<Reading> getReadingCourses() {
        return readingCourses;
    }

    public void setReadingCourses(List<Reading> readingCourses){this.readingCourses=readingCourses;}

    public List<Grammar> getGrammarCourses() {
        return grammarCourses;
    }

    public void setGrammarCourses(List<Grammar> grammarCourses){this.grammarCourses=grammarCourses;}

    public List<Writing> getWritingCourses() {
        return writingCourses;
    }

    public void setWritingCourses(List<Writing> writingCourses){this.writingCourses=writingCourses;}

    public List<Vocabulary> getVocabCourses() {
        return vocabCourses;
    }

    public void setVocabCourses(List<Vocabulary> vocabCourses){this.vocabCourses=vocabCourses;}

    public List<Question> getPastReadingMistakes() {
        return pastReadingMistakes;
    }

    public void setPastReadingMistakes(List<Question> pastMistakes){
        this.pastReadingMistakes=pastMistakes;
    }

    public List<ExamResult> getResults() {
        return examResults;
    }

    public void setResults(List<ExamResult> results) {
        this.examResults = results;
    }

    public List<Question> getPastGrammarMistakes() {
        return pastGrammarMistakes;
    }

    public void setPastGrammarMistakes(List<Question> pastGrammarMistakes){
        this.pastGrammarMistakes=pastGrammarMistakes;
    }

    public List<Word> getPastVocabMistakes(){return pastVocabMistakes;}

    public void setPastVocabMistakes(List<Word> vocabs){this.pastVocabMistakes=vocabs;}

//    public Map<Writing, Float> getWritingFeedback() {
//        return writingFeedback;
//    }
//
//    public void setWritingFeedback(Map<Writing, Float> writingFeedback) {
//        this.writingFeedback = writingFeedback;
//    }

    public List<WritingFeedback> getWritingFeedback() {
        return writingFeedback;
    }

    public void setWritingFeedback(List<WritingFeedback> writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() + '}';
    }
}
