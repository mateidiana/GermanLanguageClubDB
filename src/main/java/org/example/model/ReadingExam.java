package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ReadingExam extends Exam{
    private String textTitle;
    private String textAuthor;
    private List<Question> exercises=new ArrayList<>();
    private String text;
    public ReadingExam(int id, String name, int teacher)
    {
        super(id, name, teacher);
    }
    public String getTextTitle(){return textTitle;}
    public void setTextTitle(String textTitle){this.textTitle=textTitle;}

    public String getTextAuthor(){return textAuthor;}
    public void setTextAuthor(String textAuthor){this.textAuthor=textAuthor;}

    public List<Question> getExercises() {
        return exercises;
    }

    public void setExercises(List<Question> exercises) {
        this.exercises=exercises;
    }

    public String getText(){return text;}
    public void setText(String text){this.text=text;}

    @Override
    public String toString() {
        return "Reading exam{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
