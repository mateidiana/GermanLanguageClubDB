package org.example.model;

import java.util.List;

public class Vocabulary extends Course{

    public Vocabulary(int id, String courseName, int teacher, int maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    @Override
    public String toString() {
        return "Vocabulary course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }

}
