package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class GrammarExam extends Exam {

    public GrammarExam(int id, String name, int teacher)
    {
        super(id, name, teacher);
    }

    @Override
    public String toString() {
        return "Grammar exam{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
