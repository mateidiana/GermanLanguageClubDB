package org.example.model;

import java.util.List;

public class Vocabulary extends Course{

    private List<Word> words;


    public Vocabulary(int id, String courseName, int teacher, Integer maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
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
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }

}
