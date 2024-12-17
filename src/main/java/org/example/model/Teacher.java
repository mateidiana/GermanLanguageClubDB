package org.example.model;

import java.util.List;


public class Teacher extends Person {

    public Teacher(String name, int teacherId) {
        super(teacherId, name);
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() + '}';
    }
}
