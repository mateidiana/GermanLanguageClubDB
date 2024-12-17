package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class VocabularyExam extends Exam {

    public VocabularyExam(int id, String courseName, int teacher)
    {
        super(id, courseName, teacher);
    }

    @Override
    public String toString() {
        return "Vocabulary Exam{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
