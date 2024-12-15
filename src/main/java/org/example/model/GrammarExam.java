package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class GrammarExam extends Exam {

    private List<Question> exercises=new ArrayList<>();

    public GrammarExam(int id, String name, int teacher)
    {
        super(id, name, teacher);
    }

    public List<Question> getExercises() {
        return exercises;
    }

    public void setExercises(List<Question> exercises) {
        this.exercises=exercises;
    }

    @Override
    public String toString() {
        return "Grammar exam{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
