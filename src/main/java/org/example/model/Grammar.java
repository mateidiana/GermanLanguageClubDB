package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Grammar extends Course {

    private List<Question> exercises=new ArrayList<>();

    public Grammar(int id, String courseName, int teacher, Integer maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    public List<Question> getExercises() {
        return exercises;
    }

    public void setExercises(List<Question> exercises) {
        this.exercises=exercises;
    }

    @Override
    public String toString() {
        return "Grammar course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }
}
