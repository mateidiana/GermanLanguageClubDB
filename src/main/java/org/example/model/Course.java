package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Course extends Entity {

    private String courseName;
    //private Teacher teacher;
    private int teacherId;
    private List<Student> enrolledStudents;
    private Integer availableSlots;


    public Course(int id, String courseName, int teacherId, Integer maxStudents) {
        super(id);
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.availableSlots = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacher() {
        return teacherId;
    }

    public void setTeacher(int teacher) {
        this.teacherId = teacher;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    @Override
    public String toString() {
        return "Course{" +
                super.toString() +
                ", courseName='" + courseName + '\'' +
                ", teacher=" + teacherId +
                ", availableSlots=" + availableSlots +
                '}';
    }
}
