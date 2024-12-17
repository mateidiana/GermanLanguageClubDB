package org.example.model;

public class Enrolled extends Entity{
    private int studentId;
    private int courseId;

    public Enrolled(int id, int studentId, int courseId){
        super(id);
        this.studentId=studentId;
        this.courseId=courseId;
    }

    public int getStudent(){return studentId;}
    public void setStudent(int studentId){this.studentId=studentId;}

    public int getCourse(){return courseId;}
    public void setCourseId(int courseId){this.courseId=courseId;}
}
