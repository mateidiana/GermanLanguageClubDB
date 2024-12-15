package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class VocabularyExam extends Exam {
    private List<Word> words=new ArrayList<>();

    public VocabularyExam(int id, String courseName, int teacher)
    {
        super(id, courseName, teacher);
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "Vocabulary course{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
