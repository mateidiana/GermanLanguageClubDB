package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Reading extends Course{

    private List<Book> mandatoryBooks = new ArrayList<>();
    private String textTitle;
    private String textAuthor;
    private List<Question> exercises=new ArrayList<>();

    public Reading(int id, String courseName, int teacher, Integer maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    public List<Question> getExercises() {
        return exercises;
    }

    public void setExercises(List<Question> exercises) {
        this.exercises=exercises;
    }

    public List<Book> getMandatoryBooks() {
        return mandatoryBooks;
    }

    public void setMandatoryBooks(List<Book> mandatoryBooks) {this.mandatoryBooks=mandatoryBooks;}

    public String getTextTitle(){return textTitle;}
    public void setTextTitle(String textTitle){this.textTitle=textTitle;}

    public String getTextAuthor(){return textAuthor;}
    public void setTextAuthor(String textAuthor){this.textAuthor=textAuthor;}

    @Override
    public String toString() {
        return "Reading course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }
}
