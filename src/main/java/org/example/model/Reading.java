package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Reading extends Course{

    private String textTitle;
    private String textAuthor;
    private String text;

    public Reading(int id, String courseName, int teacher, int maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    public String getTextTitle(){return textTitle;}
    public void setTextTitle(String textTitle){this.textTitle=textTitle;}

    public String getTextAuthor(){return textAuthor;}
    public void setTextAuthor(String textAuthor){this.textAuthor=textAuthor;}

    public String getText(){return text;}
    public void setText(String text){this.text=text;}


    @Override
    public String toString() {
        return "Reading course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }
}
