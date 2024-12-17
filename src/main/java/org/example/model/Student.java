package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    public Student(String name, int studentId) {
        super(studentId, name);
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() + '}';
    }
}
