package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Grammar extends Course {

    public Grammar(int id, String courseName, int teacher, int maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    @Override
    public String toString() {
        return "Grammar course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }
}
