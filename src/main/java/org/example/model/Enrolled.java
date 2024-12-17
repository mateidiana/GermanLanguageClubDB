package org.example.model;

public class Enrolled {
    private int studentId;
    private int courseId;

    public Enrolled(int studentId, int courseId){
        this.studentId=studentId;
        this.courseId=courseId;
    }

    public int getStudent(){return studentId;}
    public void setStudent(int studentId){this.studentId=studentId;}

    public int getCourse(){return courseId;}
    public void setCourseId(int courseId){this.courseId=courseId;}
}
